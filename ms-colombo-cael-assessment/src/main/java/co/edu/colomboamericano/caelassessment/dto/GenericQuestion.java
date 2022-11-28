package co.edu.colomboamericano.caelassessment.dto;

import lombok.Data;

@Data
public class GenericQuestion {

	private int score;
	private int lostScore;
	private boolean isAnswered;
	
	public  Validation getValidation(boolean isCorrect) {
		Validation validation = new Validation();
		if(isCorrect) {
			score++;
			validation.setCorrect(isCorrect);
			validation.setScore(score);
		}else {
			score++;
			validation.setCorrect(isCorrect);
			validation.setLostScore(score);
		}
	return validation;
	}
}
