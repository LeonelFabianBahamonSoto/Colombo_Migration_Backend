package co.edu.colomboamericano.caelassessment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.colomboamericano.caelassessment.entity.Assessment;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Integer>
{
//	@Query(value = "SELECT CASE WHEN exists (SELECT documentType FROM paginaweb.estudiantes WHERE documentNumber= :documentNumber "
//			+ "AND documentType= :documentType AND assessmentStatus= : assessmentStatus) THEN 1 ELSE 0 END", nativeQuery = true)
//	Integer findByDocumentNumberAndDocumentTypeAndAssessmentStatus( String documentNumber, String documentType, String assessmentStatus );

	Optional<Assessment> findByProspectiveId( Integer prospectiveId );
	
	@Query(value = "SELECT * FROM miniveldeingles.assessment limit 10", nativeQuery = true)
	List<Assessment> findAllAssessment();
	
	@Query(value = "SELECT * FROM miniveldeingles.assessment limit 10", nativeQuery = true)
	List<Assessment>  getAssessment();
	
	@Query(value = "select questionsStepper from assessment where id = :id", nativeQuery = true)
	String  getAssessmentQuestion(@Param("id")Integer id);
	
	@Query(value = "SELECT a FROM Assessment a WHERE a.prospective.id = :id")
	Assessment getAssessments(@Param("id")Integer id);
	
	@Query(value = "SELECT a.assessments FROM Assessment a WHERE a.id = :id")
	String getAssessmentForValidateQuestion(@Param("id")Integer id);
}
