package co.edu.colomboamericano.caelstudent.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.colomboamericano.caelstudent.entity.ResetPassword;
import co.edu.colomboamericano.caelstudent.entity.Student;
import co.edu.colomboamericano.caelstudent.repository.ResetPasswordRepository;
import co.edu.colomboamericano.caelstudent.repository.StudentRepository;
import co.edu.colomboamericano.caelstudent.security.jwt.JwtProvider;
import co.edu.colomboamericano.caelstudent.service.StudentService;

@Service
@Scope("singleton")
public class StudentServiceImp implements StudentService
{
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	ResetPasswordRepository resetPasswordRepository;
	
    @Autowired
    private JwtProvider jwtProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public StudentServiceImp( StudentRepository studentRepository )
	{
		this.studentRepository = studentRepository;
	};

	@Override
	@Transactional( readOnly = true )
	public List<Student> findAll()
	{
		return studentRepository.findAll();
	}
	
	@Override
	@Transactional( readOnly = true )
	public Optional<Student> findByDocument( String documentNumber )
	{
		Optional<Student> student = studentRepository.findByDocumentNumber( documentNumber );
		
		return student;
	}

	@Override
	@Transactional( readOnly = true )
	public Optional<Student> findById(Integer id)
	{
		return studentRepository.findById(id);
	}
	
	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Student save(Student entity) throws Exception
	{
		entity.setPassword( passwordEncoder.encode( entity.getPassword() ) );
		entity.setCheck_new_password("yes");

		return studentRepository.save( entity );
	}

	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Student update(Student entity) throws Exception
	{
		return studentRepository.save( entity );
	}
	
    /**
     * @author Smarthink
     * @param String password ( contrasenia nueva), String verificationPassword (Verificacion de la 1ra contrasenia)
     * y String resetPasswordToken(Token generado en el proceso del email).
     * @return Consulta la tabla password_reset 
     */
	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Student updateForgottenPassword( String password, String verificationPassword, String resetPasswordToken ) throws Exception
	{
		Boolean isTokenValid = jwtProvider.validateAccessToken( resetPasswordToken );
		
		if( isTokenValid == false ) {
			throw new Exception("El tiempo para restablecer el token ya expirto");
		};
		
		Optional<ResetPassword> resetPasswordInfo = resetPasswordRepository.findByToken( resetPasswordToken );
		
		if( resetPasswordInfo.isEmpty() ) {
			throw new Exception("No se encontro informacion relacionada con el proceso de restablecer la contrasenia del estudiante");
		};
		
		Optional<Student> student = studentRepository.findByEmail( resetPasswordInfo.get().getEmail() );
		
		if( student.isEmpty() ) {
			throw new Exception("No se encontro el estudiante para restablecer su contrasenia");
		};
		
		student.get().setPassword( password );
		student.get().setCheck_new_password( "yes" );
		
		return this.save( student.get() );
	}

	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void delete(Student entity) throws Exception {
		studentRepository.delete(entity);
	}

	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void deleteById(Integer id) throws Exception {
		studentRepository.deleteById(id);
	}

	@Override
	public boolean validateStudentSignIn(String numberDocument, String password) {
		
		String resultFindStudentByPassword = null;
		resultFindStudentByPassword = this.studentRepository.findStudentByPassword(numberDocument, password);
		if (resultFindStudentByPassword == null || resultFindStudentByPassword.isEmpty()) {
			return false;//no ha cambiado la password
		}
		return true;
	}
}
