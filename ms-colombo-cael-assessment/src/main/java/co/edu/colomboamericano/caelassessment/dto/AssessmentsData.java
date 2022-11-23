package co.edu.colomboamericano.caelassessment.dto;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

@Data
public class AssessmentsData implements Serializable
{
	 private Integer score;

	 private Integer lostScore;

	 private AssessmentWithinAssessmentsDto assessment;

	 private ArrayList<QuestionsGroup> questionsGroup;

	 private Boolean approved;

	 private static final long serialVersionUID = -9148219498050079871L;
}
