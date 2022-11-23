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
			validation.setCorrect(isCorrect);
			validation.setScore(this.score);
		}else {
			validation.setCorrect(isCorrect);
			validation.setLostScore(this.score);
		}
	return validation;
	}
}
