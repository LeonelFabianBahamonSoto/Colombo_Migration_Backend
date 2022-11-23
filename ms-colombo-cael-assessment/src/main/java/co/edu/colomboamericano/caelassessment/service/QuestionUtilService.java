package co.edu.colomboamericano.caelassessment.service;

import co.edu.colomboamericano.caelassessment.dto.Question;
import co.edu.colomboamericano.caelassessment.dto.questionPreview.QuestionPre;

public interface QuestionUtilService {
	Object transformQuestion(String typeName, Question question);
	Object transformQuestion(String typeName, QuestionPre question);
	String putFirsLetterInUppercase(String typeName);

}
