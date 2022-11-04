package co.edu.colomboamericano.caelassessment.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Root {
	
	 @JsonProperty(value = "score")
	 private int score;
	 
	 @JsonProperty(value = "lostScore")
	 private int lostScore;
	 
	 @JsonProperty(value = "assessment")
	 private Assessment assessment;
	 
	 @JsonProperty(value = "questionsGroup")
	 private ArrayList<QuestionsGroup> questionsGroup;
	 
	 @JsonProperty(value = "approved")
	 private boolean approved;

}
