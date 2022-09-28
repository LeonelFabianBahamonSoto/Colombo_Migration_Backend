package co.edu.colomboamericano.caelstudent.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import co.edu.colomboamericano.caelstudent.dto.AuthenticationTokenDTO;
import co.edu.colomboamericano.caelstudent.entity.Student;
import co.edu.colomboamericano.caelstudent.security.UserPrincipal;
import co.edu.colomboamericano.caelstudent.security.jwt.JwtProvider;
import co.edu.colomboamericano.caelstudent.service.AuthenticationService;
import co.edu.colomboamericano.caelstudent.service.StudentService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;
    
    @Autowired
    private StudentService studentService;

    @Override
    public Object signInAndReturnJWT(Student signInRequest){
       	Map<String,Object> response = new HashMap<>();
    	AuthenticationTokenDTO authenticationTokenDTO = new AuthenticationTokenDTO();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getDocumentNumber(),signInRequest.getPassword())
        );
    
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        boolean resultValidateStudent = studentService.validateStudentSignIn(signInRequest.getDocumentNumber(),userPrincipal.getPassword());
        if (!resultValidateStudent) {
        	response.put("error", "El estudiante debe cambiar la contraseña");
        	return response;
		}
        authenticationTokenDTO = jwtProvider.generateToken(userPrincipal);   
        return authenticationTokenDTO;
    }
}
