package co.edu.colomboamericano.caelassessment.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AssessmentInfoDto implements Serializable
{
	private Integer id;

	private String course;
	
	private Integer prospectiveId;
	
	private Boolean isLastLevel;

	private Level level;

	private String totalScore;

	private String approvalScore;
	
	private static final long serialVersionUID = 1L;
}