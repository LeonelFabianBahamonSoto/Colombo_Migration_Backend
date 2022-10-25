package co.edu.colomboamericano.caelassessment.dto;

import lombok.Data;

@Data
public class Validation {
	 private boolean isCorrect;
	 private Integer score;
	 private Integer lostScore;
}
