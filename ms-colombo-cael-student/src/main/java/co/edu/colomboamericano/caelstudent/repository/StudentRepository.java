package co.edu.colomboamericano.caelstudent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.colomboamericano.caelstudent.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>
{
	Optional<Student> findByDocumentNumber( String documentNumber );
	
	@Query(value = " select check_new_password from estudiantes where numero_documento = :numeroDocumento and password = :password", nativeQuery = true)
	String findStudentByPassword(@Param("numeroDocumento")String numeroDocumento,@Param("password")String password);
}