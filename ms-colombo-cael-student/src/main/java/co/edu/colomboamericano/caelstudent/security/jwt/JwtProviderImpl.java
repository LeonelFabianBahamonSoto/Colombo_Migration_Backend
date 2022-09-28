package co.edu.colomboamericano.caelstudent.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import co.edu.colomboamericano.caelstudent.dto.AuthenticationTokenDTO;
import co.edu.colomboamericano.caelstudent.security.UserPrincipal;
import co.edu.colomboamericano.caelstudent.service.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProviderImpl implements JwtProvider{

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;


    //crear token
    @Override
    public AuthenticationTokenDTO generateToken(UserPrincipal auth){
    	AuthenticationTokenDTO authenticationTokenDTO = new AuthenticationTokenDTO();
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        
        authenticationTokenDTO.setAccessToken(Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles",authorities)
                .claim("userId",auth.getId())
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact());
        authenticationTokenDTO.setExpiresIn(new Date(System.currentTimeMillis()+JWT_EXPIRATION_IN_MS));
        return authenticationTokenDTO;
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request){
            Claims claims = extractClaims(request);
            if(claims == null){
                return null;
            }
            String username = claims.getSubject();
            Integer userId = claims.get( "userId", Integer.class );
        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());

        UserDetails userDetails = UserPrincipal.builder()
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();

        if (username == null){
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,authorities);
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request){
            Claims claims = extractClaims(request);
            if (claims == null){
                return false;
            }
            //si el token ya espiro a la fecha actual
            if (claims.getExpiration().before(new Date())){
                return false;
            }
            return true;
    }

    //extraer todas las propiedades del token
    private Claims extractClaims(HttpServletRequest request){
        String token = SecurityUtils.extractAuthTokenFromRequest(request);
        if (token== null){
            return null;
        }
        //generar la llave secreta del token para acceder
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}