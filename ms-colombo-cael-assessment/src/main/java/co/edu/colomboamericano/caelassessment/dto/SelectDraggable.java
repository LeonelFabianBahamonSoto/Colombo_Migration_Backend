package co.edu.colomboamericano.caelassessment.dto;

import java.util.List;

import lombok.Data;

@Data
public class SelectDraggable extends QuestionPropertysExtend{
	private Object question;
	private List<String> answers;
}
