package co.edu.colomboamericano.caelassessment.dto;

import java.util.Date;

public interface ProspectiveByDateRangeFilterDto
{
	Integer getId();
    String  getFullName();
    Long    getDocumentNumber();
	Date    getBirthdate();
	String  getEmail();
    String  getCellphone();
    Integer getDocumentType();
    Date 	getAssessmentUpdatedAt();
    String  getCourse();
    String 	getHeadquarter();
    String 	getAssessmentStatus();
}