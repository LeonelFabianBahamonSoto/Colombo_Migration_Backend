package co.edu.colomboamericano.caelstudent.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.colomboamericano.caelstudent.entity.Student;
import co.edu.colomboamericano.caelstudent.service.StudentService;

@RestController
@RequestMapping("/v1/student")
public class StudentController
{
	@Autowired
	private StudentService studentService;
	
	public StudentController( StudentService studentSertvice )
	{
		this.studentService = studentSertvice;
	};
	
	/**
	 * 
	 * @param documentNumber
	 * @return student
	 * @throws Exception: El estudiante no exista o el numero de documento sea nulo.
	 */
	@GetMapping("/findByDocument/{documentNumber}")
	public ResponseEntity<Optional<Student>>  findByDocument( @PathVariable("documentNumber") String documentNumber ) throws Exception
	{		
		if( documentNumber == null || documentNumber.length() < 2 ) {
			throw new Exception("El numero de documento es nulo o esta vacio.");
		};
		
		return new ResponseEntity<>( studentService.findByDocument( documentNumber ), HttpStatus.OK );
	}
	
	@GetMapping("/allstudent")
	public ResponseEntity<List<Student>>  listar() throws Exception{
		return new ResponseEntity<>( studentService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/studentById/{id}")
	public ResponseEntity<Optional<Student>> listarPorId(@PathVariable("id") Integer id) throws Exception
	{
		Optional<Student> obj = studentService.findById(id);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	@PostMapping("/saveStudent")
	public  ResponseEntity<Student> save( @Valid @RequestBody Student student ) throws Exception
	{
		return ResponseEntity.status( HttpStatus.CREATED ).body( studentService.save(student) );
	}
	
	@PutMapping("/updateStudent")
	public  ResponseEntity<Student> update( @Valid @RequestBody Student student ) throws Exception
	{
		return ResponseEntity.status( HttpStatus.OK ).body( studentService.save(student) );
	}
	
    /**
     * @author Smarthink
     * @param String password ( contrasenia nueva), String verificationPassword (Verificacion de la 1ra contrasenia)
     * y String resetPasswordToken(Token generado en el proceso del email).
     * @return Retorna el estudiante con la nueva contrasenia.
     */
	@PutMapping("/resetPassword")
	public  ResponseEntity<Student> updateForgottenPassword( @RequestParam String password, @RequestParam String verificationPassword, @RequestParam String resetPasswordToken )throws Exception
	{
		if( password == null || verificationPassword == null || !password.equals(verificationPassword) ) {
			throw new Exception("Las contrasenias ingresadas no coinciden");
		};
		
		if( resetPasswordToken == null ) {
			throw new Exception("El token para restablecer la contrasenia del estudiante es nulo");
		};
		
		Student student = studentService.updateForgottenPassword(password, verificationPassword, resetPasswordToken);

		return ResponseEntity.status( HttpStatus.OK ).body( student );
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete( @RequestBody Student student ) throws Exception
	{
		studentService.delete(student);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById( @PathVariable("id") Integer id ) throws Exception
	{
		studentService.deleteById( id );
		return ResponseEntity.ok().build();
	}
}










