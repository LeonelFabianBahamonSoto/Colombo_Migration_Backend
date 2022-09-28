package co.edu.colomboamericano.caelstudent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.colomboamericano.caelstudent.entity.Student;
import co.edu.colomboamericano.caelstudent.service.AuthenticationService;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController
{
    @Autowired
    private AuthenticationService authenticationService;
	
    //comentario
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody Student student){
    		  return new ResponseEntity<>( authenticationService.signInAndReturnJWT(student),HttpStatus.OK ); 
    }
}
