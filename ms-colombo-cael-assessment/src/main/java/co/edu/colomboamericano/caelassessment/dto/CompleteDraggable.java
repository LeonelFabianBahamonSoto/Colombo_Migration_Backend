package co.edu.colomboamericano.caelassessment.dto;

import java.util.List;

import lombok.Data;

@Data
public class CompleteDraggable extends QuestionPropertysExtend{
	private String statement;
	private List<String> answers;
	private Object question;
}
