package co.edu.colomboamericano.caelstudent.service.Impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import co.edu.colomboamericano.caelstudent.dto.AuthenticationTokenDTO;
import co.edu.colomboamericano.caelstudent.entity.ResetPassword;
import co.edu.colomboamericano.caelstudent.entity.Student;
import co.edu.colomboamericano.caelstudent.repository.ResetPasswordRepository;
import co.edu.colomboamericano.caelstudent.repository.StudentRepository;
import co.edu.colomboamericano.caelstudent.security.UserPrincipal;
import co.edu.colomboamericano.caelstudent.security.jwt.JwtProvider;
import co.edu.colomboamericano.caelstudent.service.AuthenticationService;
import co.edu.colomboamericano.caelstudent.service.MailService;
import co.edu.colomboamericano.caelstudent.service.StudentService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService
{
    @Autowired
    private AuthenticationManager authenticationManager;
    
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ResetPasswordRepository resetPasswordRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
    private StudentService studentService;

    @Autowired
    private JwtProvider jwtProvider;

    /**
     * @author Smarthink
     * @param String documentNumber, String password.
     * @return Metodo para el inicio de sesion del estudiante, valida si el estudiante ya cambio
     * la contrasena.
     */
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
    
    /**
     * @author Smarthink
     * @param String documentNumber.
     * @return Consulta el estudiante, crea el token para restablecer contrasenia, almacena el token,
     * el email y la fecha y por ultimo envia el correo electronico con el enlace de restablecimiento.
     */
    @Override
    public String forgotPassword( String documentNumber ) throws Exception
    {    	
    	Optional<Student> student = studentRepository.findByDocumentNumber( documentNumber );

    	if( student.isEmpty() ) {
    		throw new Exception("No se encontro un estudiante asociado al numero de documento");
    	};

    	String token = jwtProvider.tokenToResetPassword( documentNumber );
    	String response = mailService.sendResetPasswordEmail( student.get().getEmail(), token );

    	if( !response.equals("send") ) {
    		throw new Exception("No fue posible enviar el email");
    	};

    	ResetPassword resetPasword = new ResetPassword();
    	resetPasword.setEmail( student.get().getEmail() );
    	resetPasword.setToken( token );
    	resetPasword.setCreated_at( new Date() );

    	resetPasswordRepository.save( resetPasword );

        return response;
    };
}