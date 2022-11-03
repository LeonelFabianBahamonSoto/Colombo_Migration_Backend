package co.edu.colomboamericano.caelassessment.dto;

import java.util.Date;

public interface FindByFilterDto
{
	Integer getId();
    String   getFirstName();
    String   getSecondName();
    String   getSurname();
    String   getSecondSurname();
    Long     getDocumentNumber();
	Date     getBirthdate();
	String   getEmail();
    String   getCellphone();
    Integer getSchoolGrade();
	Integer getTermsConditions();
	Integer getProspectiveStatusId();
	Date     getCreatedAt();
    Date 	 getUpdatedAt();
	Integer getDocumentType();
	Integer getAssessmentId();
	String   getCourse();
	Integer getProspectiveId();
	Date 	 getAssessmentCreatedAt();
    Date 	 getAssessmentUpdatedAt();
    String 	 getAssessmentStatusId();
    String 	 getHeadquarter();
}
