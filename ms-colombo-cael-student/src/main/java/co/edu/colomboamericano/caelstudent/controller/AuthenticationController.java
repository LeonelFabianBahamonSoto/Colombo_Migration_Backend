package co.edu.colomboamericano.caelstudent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.colomboamericano.caelstudent.entity.Student;
import co.edu.colomboamericano.caelstudent.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/authentication")
@Slf4j
public class AuthenticationController
{
    @Autowired
    private AuthenticationService authenticationService;
	
    /**
     * @author Smarthink
     * @param String documentNumber, String password.
     * @return Permite la autenticacion del estudiante en la plataforma.
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody Student student){
    		  return new ResponseEntity<>( authenticationService.signInAndReturnJWT(student),HttpStatus.OK ); 
    }

    /**
     * @author Smarthink
     * @param String documentNumber.
     * @return Consulta para restablecer contrasenia y enviar el correo electronico con el enlace de restablecimiento.
     */
	@GetMapping("/forgotpassword")
	public ResponseEntity<String>  forgotPassword( @RequestParam String documentNumber ) throws Exception
    {
    	try {
    		if( documentNumber == null ) {
    			throw new Exception("El numero de documento ingresado no es valido");
    		};

    		String tokenToResetPassword = authenticationService.forgotPassword(documentNumber);
    		
    		return new ResponseEntity<>( tokenToResetPassword, HttpStatus.ACCEPTED );
		} catch (Exception e) {
			log.error("Se presento un error al recuperar la contrasenia para el usuario " + documentNumber + ". Error: " + e.getCause() + " - " + e.getMessage());
			return ResponseEntity.internalServerError().body( "Se presento un error al reestablecer el password del estudiante" + e.getCause() );
		}
      
    }
}
