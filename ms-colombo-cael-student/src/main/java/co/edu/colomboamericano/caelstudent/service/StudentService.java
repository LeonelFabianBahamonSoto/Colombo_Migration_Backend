package co.edu.colomboamericano.caelstudent.service;

import java.util.Optional;

import co.edu.colomboamericano.caelstudent.entity.Student;

public interface StudentService extends GenericService<Student, Integer>
{
	Optional<Student> findByDocument(String documentNumber);

	Student updateForgottenPassword( String password, String verificationPassword, String resetPasswordToken ) throws Exception;

	boolean validateStudentSignIn(String numberDocument, String password);
}