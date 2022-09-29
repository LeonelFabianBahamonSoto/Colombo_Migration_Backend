package co.edu.colomboamericano.caelstudent.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import co.edu.colomboamericano.caelstudent.dto.AuthenticationTokenDTO;
import co.edu.colomboamericano.caelstudent.security.UserPrincipal;
import co.edu.colomboamericano.caelstudent.service.utils.Constant;
import co.edu.colomboamericano.caelstudent.service.utils.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProviderImpl implements JwtProvider{

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    /**
     * @author Smarthink
     * @param Student entity ( String documentNumber, String Password ).
     * @return Token el servicio de login del estudiante.
     */
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
    
    /**
     * @author Smarthink
     * @param String documentNumber.
     * @return Token para restablecer la contrasenia del estudiante.
     */
    @Override
    public String tokenToResetPassword( String documentNumber )
    {
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
				        .setSubject( documentNumber )
				        .setIssuer("ColomboAdmin")
				        .setIssuedAt( new Date() )
				        .setExpiration( new Date(System.currentTimeMillis() + Constant.JWT_RESET_PASSWORD_EXPIRATION_IN_MS ) )
				        .signWith( key, SignatureAlgorithm.HS512 )
				        .compact();

        return token;
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
    public boolean isTokenValid(HttpServletRequest request)
    {
		Claims claims = extractClaims(request);
		
		if (claims == null){
		    return false;
		};

		//si el token ya espiro a la fecha actual
		if (claims.getExpiration().before(new Date())){
		    return false;
		};

		return true;
    };
    
    /**
     * @author Smarthink
     * @param String token ( token de restablecimiento de la cotrasenia ).
     * @return True si el token no ha expirado de lo contrario, retorna false.
     */
    @Override
	public boolean validateAccessToken(String token) 
	{
		try {
	        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
	        
	        @SuppressWarnings("unused")
			Claims tokenAlreadyExpire = Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();

            return true;

        } catch (ExpiredJwtException ex) {
        	log.warn("JWT expirado. Message: "+ ex.getMessage());
        } catch (IllegalArgumentException ex) {
        	log.warn("Token es null, está vacío o contiene espacios. Message: "+ ex.getMessage());
        } catch (MalformedJwtException ex) {
        	log.warn("JWT es inválido. Message: "+ ex);
        } catch (UnsupportedJwtException ex) {
        	log.warn("JWT no soportado. Message: "+ ex);
        } catch (SignatureException ex) {
        	log.warn("Validación de firma errónea");
        }
         
        return false;
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
    };
}