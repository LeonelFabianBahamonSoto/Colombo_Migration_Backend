package co.edu.colomboamericano.caelassessment.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SelectSingleAnswer extends QuestionPropertysExtend{
	private Object question;
	private List<String> answers;
}
