package co.edu.colomboamericano.caelassessment.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.colomboamericano.caelassessment.dto.FindByFilterDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveByDateRangeFilterDto;
import co.edu.colomboamericano.caelassessment.entity.Prospective;

@Repository
public interface ProspectiveRepository extends JpaRepository<Prospective, Integer>
{
	Optional<Prospective> findByDocumentNumber( Long documentNumber );
	
	@Query(value = 
			"SELECT * FROM ( SELECT * FROM ( SELECT * FROM miniveldeingles.prospective WHERE id IN ( SELECT MAX(id) FROM miniveldeingles.prospective GROUP BY documentNumber ) ) AS subQuery ) AS prospectiveQuery\n"
			+ "	INNER JOIN (\n"
			+ "		SELECT assessment.id AS assessmentId, assessment.course, assessment.prospectiveId, assessment.createdAt AS assessmentCreateAt,\n"
			+ "			assessment.updatedAt AS assessmentUpdateAt, assessment.assessmentStatusId, assessment.headquarter\n"
			+ "			FROM miniveldeingles.assessment WHERE id IN ( SELECT MAX(id) FROM miniveldeingles.assessment GROUP BY prospectiveId ) ) AS assessmentQuery\n"
			+ "	ON prospectiveQuery.id = assessmentQuery.prospectiveId WHERE (prospectiveQuery.documentNumber LIKE ?3%)\n"
			+ "	OR ( CONCAT_WS( ' ', NULLIF(prospectiveQuery.firstName, ''), NULLIF(prospectiveQuery.secondName, ''), NULLIF(prospectiveQuery.surname, ''), \n"
			+ "		NULLIF(prospectiveQuery.secondSurname, '') ) LIKE ?3% )\n"
			+ "LIMIT ?1,?2", nativeQuery = true)
	ArrayList<FindByFilterDto> listOfProspectiveAndAssessment( Integer skip, Integer take, String filter );
	
	@Query(value = 
			"SELECT COUNT(*) AS count FROM ( SELECT * FROM ( SELECT * FROM miniveldeingles.prospective WHERE id IN ( SELECT MAX(id) FROM miniveldeingles.prospective GROUP BY documentNumber ) ) AS subQuery ) AS prospectiveQuery\n"
			+ "	INNER JOIN (\n"
			+ "		SELECT assessment.id AS assessmentId, assessment.course, assessment.prospectiveId, assessment.createdAt AS assessmentCreateAt,\n"
			+ "			assessment.updatedAt AS assessmentUpdateAt, assessment.assessmentStatusId, assessment.headquarter\n"
			+ "			FROM miniveldeingles.assessment WHERE id IN ( SELECT MAX(id) FROM miniveldeingles.assessment GROUP BY prospectiveId ) ) AS assessmentQuery\n"
			+ "	ON prospectiveQuery.id = assessmentQuery.prospectiveId WHERE (prospectiveQuery.documentNumber LIKE ?3%)\n"
			+ "	OR ( CONCAT_WS( ' ', NULLIF(prospectiveQuery.firstName, ''), NULLIF(prospectiveQuery.secondName, ''), NULLIF(prospectiveQuery.surname, ''), \n"
			+ "		NULLIF(prospectiveQuery.secondSurname, '') ) LIKE ?3% )\n"
			+ "LIMIT ?1,?2", nativeQuery = true)
	Integer numberOfProspectiveAndAssessment( Integer skip, Integer take, String filter );
	
	@Query(value = 
			"SELECT prospective.id, CONCAT_WS( \"\", prospective.firstName, prospective.secondName, prospective.surname, prospective.secondSurname ) AS fullName,"
			+ " prospective.documentNumber, prospective.birthdate, prospective.email, prospective.cellphone, prospective.documentType, assessmentQuery.updatedAt AS assessmentUpdatedAt,"
			+ " assessmentQuery.course, assessmentQuery.headquarter, assessmentStatus.id AS assessmentStatus FROM miniveldeingles.prospective INNER JOIN ("
			+ " SELECT assessment.id, assessment.course, assessment.prospectiveId, assessment.updatedAt, assessment.assessmentStatusId, assessment.headquarter FROM miniveldeingles.assessment"
			+ " WHERE id IN ( SELECT MAX(id) FROM miniveldeingles.assessment GROUP BY prospectiveId ) ) AS assessmentQuery"
			+ " ON prospective.id = assessmentQuery.prospectiveId INNER JOIN miniveldeingles.assessmentStatus ON assessmentQuery.assessmentStatusId = assessmentStatus.id"
			+ " WHERE assessmentQuery.updatedAt BETWEEN ?1 AND ?2 ORDER BY assessmentQuery.UpdatedAt DESC;", nativeQuery = true)
	List<ProspectiveByDateRangeFilterDto> findByDateRangeFilterQuery( Date startDate, Date endDate );
	
	@Query(value = 
			"SELECT prospective.id, CONCAT_WS( \"\", prospective.firstName, prospective.secondName, prospective.surname, prospective.secondSurname ) as fullName,"
			+ " prospective.documentNumber, prospective.birthdate, prospective.email, prospective.cellphone, prospective.documentType,"
			+ " assessmentQuery.updatedAt as assessmentUpdatedAt, assessmentQuery.course, assessmentQuery.headquarter, assessmentStatus.id as assessmentStatus"
			+ " FROM miniveldeingles.prospective INNER JOIN ( SELECT assessment.id, assessment.course, assessment.prospectiveId, assessment.updatedAt, assessment.assessmentStatusId,"
			+ " assessment.headquarter FROM miniveldeingles.assessment WHERE id IN ( SELECT MAX(id) FROM miniveldeingles.assessment GROUP BY prospectiveId ) ) AS assessmentQuery"
			+ " ON prospective.id = assessmentQuery.prospectiveId INNER JOIN miniveldeingles.assessmentStatus ON assessmentQuery.assessmentStatusId = assessmentStatus.id"
			+ " WHERE assessmentQuery.updatedAt BETWEEN ?1 AND ?2 ORDER BY assessmentQuery.UpdatedAt DESC", nativeQuery = true)
	List<ProspectiveByDateRangeFilterDto> findByDateRangeFilterAndStateQuery( Date startDate, Date endDate, String state );
}