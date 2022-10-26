package co.edu.colomboamericano.caelassessment.service;

public interface WordPressService {
	Object getAssessmentPreview(Integer questionGroupId,Integer questionTypeId,Integer questionId);
	
	Object getAssessmentLevels();
}
