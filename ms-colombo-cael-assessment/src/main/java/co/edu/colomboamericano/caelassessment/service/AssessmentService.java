package co.edu.colomboamericano.caelassessment.service;

import java.util.Date;
import java.util.Optional;

import co.edu.colomboamericano.caelassessment.dto.AssessmentDto;
import co.edu.colomboamericano.caelassessment.entity.Assessment;

public interface AssessmentService extends GenericService<Assessment, Integer>
{
	Integer getAssessmentBy( String documentNumber, String documentType ) throws Exception;

	Optional<Assessment> findByProspectiveId( Integer prospectiveId );
	
	Assessment generateDtoAssessmentByStatusAndProspective(Integer prospectiveId,Integer assessmentStatusId) throws Exception ;

	Object transformAssessmentsAndQuestionStepper(Integer id) throws Exception;

	AssessmentDto createAssessment(Integer documentType, Long documentNumber, Date birthdate, String level, String program, String headquarter) throws Exception;
	
	Object getAssessmentQuestion(Integer id)throws Exception;

	AssessmentDto updateAssessmentFieldAssessments( Integer id, String assessmentsObject ) throws Exception ;

}