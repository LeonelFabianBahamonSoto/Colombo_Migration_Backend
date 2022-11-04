package co.edu.colomboamericano.caelassessment.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderSentences extends QuestionPropertysExtend{
	private List<String> sentences;
}
