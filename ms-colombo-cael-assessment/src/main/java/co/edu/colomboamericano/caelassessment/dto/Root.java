package co.edu.colomboamericano.caelassessment.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Root {

	 private int score;
	 private int lostScore;
	 private Assessment assessment;
	 private ArrayList<QuestionsGroup> questionsGroup;
	 private boolean approved;

}
