package co.edu.colomboamericano.caelassessment.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AssessmentInfoDto implements Serializable
{
	private Integer id;
	
	private Integer prospectiveId;

	private Level level;

	private String totalScore;

	private String approvalScore;
	
	private String course;
	
	private static final long serialVersionUID = 1L;
}