package co.edu.colomboamericano.caelassessment.dto.questionPreview;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.colomboamericano.caelassessment.dto.questionPreview.QuestionTypePreview;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsGroupPreview {

	 public int questionGroupId;
	 public String title;
	 public List<QuestionTypePreview> questionTypes;
}
