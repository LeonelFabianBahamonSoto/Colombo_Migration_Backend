package co.edu.colomboamericano.caelassessment.dto.questionPreview;

import java.util.List;

import co.edu.colomboamericano.caelassessment.dto.QuestionsGroup;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Data {
	
	private List<QuestionsGroupPreview> questionsGroup;
	
	
	public List<QuestionsGroupPreview> getQuestionsGroup() {
		return questionsGroup;
	}

	public void setQuestionsGroup(List<QuestionsGroupPreview> questionsGroup) {
		this.questionsGroup = questionsGroup;
	}
	 
	 
}
