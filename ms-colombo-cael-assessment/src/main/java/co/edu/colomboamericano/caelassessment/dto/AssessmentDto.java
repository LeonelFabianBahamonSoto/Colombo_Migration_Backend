package co.edu.colomboamericano.caelassessment.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentDto implements Serializable
{
	private Integer id;

	private String course;
	
	private String assessments;

	private String questionsStepper;

	private Integer remainingTime;

	private Date createAt;

	private Date updateAt;

	private String program;

	private String headquarter;

    private Integer assessmentStatusId;

    private Integer prospectiveId;

	private static final long serialVersionUID = 1L;
}
