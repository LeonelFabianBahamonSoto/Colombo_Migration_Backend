package co.edu.colomboamericano.caelassessment.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AssessmentWithinAssessmentsDto implements Serializable
{
	private Integer assessmentID;
	
	private String courseName;
	
	private String title;
	
	private String totalScore;
	
	private String approvalScore;
	
	private String program;

	private Level level;

	private static final long serialVersionUID = 9092249266345177957L;
}
