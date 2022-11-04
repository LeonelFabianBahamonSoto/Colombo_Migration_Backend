package co.edu.colomboamericano.caelassessment.dto;

import lombok.Data;

@Data
public class CurrentQuestion {
	private int assessmentId;
	private int questionGroupId;
	private int questionTypeId;
	private int questionId;
	private String title;
	private String typeName;
	private Object multimediaFile;
	private Object question;
}
