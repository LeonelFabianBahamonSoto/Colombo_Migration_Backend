package co.edu.colomboamericano.caelstudent.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import co.edu.colomboamericano.caelstudent.entity.Student;
import co.edu.colomboamericano.caelstudent.security.UserPrincipal;
import co.edu.colomboamericano.caelstudent.security.jwt.JwtProvider;
import co.edu.colomboamericano.caelstudent.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public Student signInAndReturnJWT(Student signInRequest){
    	System.out.println(signInRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getDocumentNumber(),signInRequest.getPassword())
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);

        Student sigInUser = userPrincipal.getStudent();
        sigInUser.setToken(jwt);
        return sigInUser;
    }
}
