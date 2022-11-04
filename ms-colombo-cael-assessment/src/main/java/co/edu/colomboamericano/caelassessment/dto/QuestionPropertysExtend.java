package co.edu.colomboamericano.caelassessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QuestionPropertysExtend {
	@JsonProperty(value = "ID")
	private Integer ID;	
	private String score;
	@JsonProperty(value = "isAnswered")
	private boolean isAnswered;
}
