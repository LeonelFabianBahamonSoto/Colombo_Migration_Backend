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
import org.springframework.web.bind.annotation.RestController;

import co.edu.colomboamericano.caelstudent.entity.Student;
import co.edu.colomboamericano.caelstudent.service.StudentService;

@RestController
@RequestMapping("/v1/student")
public class StudentController
{
	@Autowired
	private StudentService studentSertvice;
	
	public StudentController( StudentService studentSertvice )
	{
		this.studentSertvice = studentSertvice;
	};
	
	@GetMapping("/findByDocument/{documentNumber}")
	public ResponseEntity<Optional<Student>>  findByDocument(@PathVariable("documentNumber") String documentNumber) throws Exception
	{
		return new ResponseEntity<>( studentSertvice.findByDocument( documentNumber ), HttpStatus.OK );
	}
	
	@GetMapping("/allstudent")
	public ResponseEntity<List<Student>>  listar() throws Exception{
		return new ResponseEntity<>( studentSertvice.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/studentById/{id}")
	public ResponseEntity<Optional<Student>> listarPorId(@PathVariable("id") Integer id) throws Exception
	{
		Optional<Student> obj = studentSertvice.findById(id);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	@PostMapping("/saveStudent")
	public  ResponseEntity<Student> save( @Valid @RequestBody Student student )throws Exception
	{
		return ResponseEntity.status( HttpStatus.CREATED ).body( studentSertvice.save(student) );
	}
	
	@PutMapping("/updateStudent")
	public  ResponseEntity<Student> update( @Valid @RequestBody Student student )throws Exception
	{
		return ResponseEntity.status( HttpStatus.CREATED ).body( studentSertvice.save(student) );
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete( @RequestBody Student student ) throws Exception
	{
		studentSertvice.delete(student);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById( @PathVariable("id") Integer id ) throws Exception
	{
		studentSertvice.deleteById( id );
		return ResponseEntity.ok().build();
	}
}










