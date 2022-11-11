package co.edu.colomboamericano.caelassessment.dto;

import java.util.Date;

import co.edu.colomboamericano.caelassessment.entity.AssessmentStatus;
import lombok.Data;

@Data
public class AssessmentGetDto {
	private Long id;
	private Date createAt;
	private Date updateAt;
	private String course;
	private Integer remainingTime;
	private String program;
	private String headquarter;
	private AssessmentStatus assessmentStatus;
	private ProspectiveGetDto prospective;
}
