package co.edu.colomboamericano.caelassessment.service;

import java.util.Optional;

import co.edu.colomboamericano.caelassessment.entity.Assessment;

public interface AssessmentService extends GenericService<Assessment, Integer>
{
	Integer getAssessmentBy( String documentNumber, String documentType ) throws Exception;

	Optional<Assessment> findByProspectiveId( Integer prospectiveId );
}