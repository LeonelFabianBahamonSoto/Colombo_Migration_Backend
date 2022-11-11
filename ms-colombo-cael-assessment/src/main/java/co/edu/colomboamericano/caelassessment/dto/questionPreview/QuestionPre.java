package co.edu.colomboamericano.caelassessment.dto.questionPreview;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import co.edu.colomboamericano.caelassessment.dto.Sentence;
import lombok.Data;

@Data
public class QuestionPre {

	@JsonProperty("ID") 
    private String ID;
	
	private String score;
	private String statement;
	private Object question;
	private List<String> correctOrder;
	private List<AnswerPreview> answers;
	private List<Sentence> sentences;

}
