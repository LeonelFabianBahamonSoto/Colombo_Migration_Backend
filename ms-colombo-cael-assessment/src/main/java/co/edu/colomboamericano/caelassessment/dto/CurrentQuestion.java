package co.edu.colomboamericano.caelassessment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class CurrentQuestion {
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer assessmentId;
	
	private int questionGroupId;
	private int questionTypeId;
	private int questionId;
	private String title;
	private String typeName;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private Object multimediaFile;
	
	private Object question;
}
