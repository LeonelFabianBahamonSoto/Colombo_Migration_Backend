package co.edu.colomboamericano.caelstudent.security.jwt;

import org.springframework.security.core.Authentication;

import co.edu.colomboamericano.caelstudent.security.UserPrincipal;

import javax.servlet.http.HttpServletRequest;

public interface JwtProvider
{
    String generateToken(UserPrincipal auth);

    Authentication getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);
}
