package co.edu.colomboamericano.caelstudent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.colomboamericano.caelstudent.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>
{
	Optional<Student> findByDocumentNumber( String documentNumber );
}