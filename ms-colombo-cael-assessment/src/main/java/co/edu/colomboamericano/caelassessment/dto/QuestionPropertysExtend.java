package co.edu.colomboamericano.caelassessment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QuestionPropertysExtend {
	@JsonProperty(value = "ID")
	private Integer ID;	
	private String score;
	
	
	@JsonProperty(value = "isAnswered")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private boolean isAnswered;
}
