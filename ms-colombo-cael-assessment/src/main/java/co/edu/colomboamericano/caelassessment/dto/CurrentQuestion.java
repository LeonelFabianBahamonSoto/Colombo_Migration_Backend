package co.edu.colomboamericano.caelassessment.dto;

import lombok.Data;

@Data
public class CurrentQuestion {
	private Integer assessmentId;
	private Integer questionGroupId;
	private Integer questionTypeId;
	private Integer questionId;
	private String title;
	private String typeName;
	private Object multimediaFile;
	private GenericQuestion question;
}
