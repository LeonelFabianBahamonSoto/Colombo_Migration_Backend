package co.edu.colomboamericano.caelassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.edu.colomboamericano.caelassessment.entity.Assessment;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Integer>
{
	@Query(value = "SELECT CASE WHEN exists (SELECT documentType FROM paginaweb.estudiantes WHERE documentNumber= :documentNumber "
			+ "AND documentType= :documentType AND assessmentStatus= : assessmentStatus) THEN 1 ELSE 0 END", nativeQuery = true)
	Integer findByDocumentNumberAndDocumentTypeAndAssessmentStatus( String documentNumber, String documentType, String assessmentStatus );
}
