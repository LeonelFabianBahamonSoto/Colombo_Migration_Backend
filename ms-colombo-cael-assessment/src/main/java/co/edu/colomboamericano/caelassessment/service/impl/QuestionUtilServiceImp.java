package co.edu.colomboamericano.caelassessment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.colomboamericano.caelassessment.dto.CompleteDraggable;
import co.edu.colomboamericano.caelassessment.dto.CorrectOrderDraggable;
import co.edu.colomboamericano.caelassessment.dto.OrderSentences;
import co.edu.colomboamericano.caelassessment.dto.PairingDraggable;
import co.edu.colomboamericano.caelassessment.dto.Question;
import co.edu.colomboamericano.caelassessment.dto.SelectDraggable;
import co.edu.colomboamericano.caelassessment.dto.SelectSingleAnswer;
import co.edu.colomboamericano.caelassessment.dto.Writing;
import co.edu.colomboamericano.caelassessment.dto.questionPreview.QuestionPre;
import co.edu.colomboamericano.caelassessment.service.QuestionUtilService;

@Service
public class QuestionUtilServiceImp implements QuestionUtilService{
	
	/**
	 * Gets transformar una pregunta al tipo SelectSingleAnswer,
	 * SelectDraggable, CompleteDraggable, PairingDraggable,
	 * CorrectOrderDraggable, Writing, OrderSentences de acuerdo al tipo
	 * questionTypes de typeName
	 * @param tipo de nombre 'typeName', la pregunta 'question'
	 * @return la pregunta segun su tipo
	 */


	@Override
	public Object transformQuestion(String typeName, Question question) {
		
		String resultTypeName = putFirsLetterInUppercase(typeName);
		if (SelectSingleAnswer.class.getSimpleName().equals(resultTypeName)) {
			List<String> answers = new ArrayList<String>();
			SelectSingleAnswer selectSingleAnswer = new SelectSingleAnswer();
			selectSingleAnswer.setID(question.getID());
			selectSingleAnswer.setScore(question.getScore());
			selectSingleAnswer.setQuestion(question.getQuestion());
			question.getAnswers().forEach(answer ->{
				answers.add(answer.getAnswer().trim());
				selectSingleAnswer.setAnswers(answers);
			});
			selectSingleAnswer.setAnswered(question.isAnswered());
			return selectSingleAnswer;
		} 
		
		if (SelectDraggable.class.getSimpleName().equals(resultTypeName)) {
			SelectDraggable selectDraggable = new SelectDraggable();
			List<String> answers = new ArrayList<String>();
			selectDraggable.setID(question.getID());
			selectDraggable.setScore(question.getScore());
			selectDraggable.setQuestion(question.getQuestion());
			question.getAnswers().forEach(answer ->{
				answers.add(answer.getAnswer().trim());
				selectDraggable.setAnswers(answers);
			});
			selectDraggable.setAnswered(question.isAnswered());
			return selectDraggable;
		}
		
		if (CompleteDraggable.class.getSimpleName().equals(resultTypeName)) {
			CompleteDraggable completeDraggable = new CompleteDraggable();
			List<String> answers = new ArrayList<String>();
			completeDraggable.setID(question.getID());
			completeDraggable.setScore(question.getScore());
			completeDraggable.setStatement(question.getStatement());
			completeDraggable.setQuestion(question.getQuestion());
			question.getAnswers().forEach(answer ->{
				answers.add(answer.getAnswer().trim());
				completeDraggable.setAnswers(answers);
			});
			completeDraggable.setAnswered(question.isAnswered());
			return completeDraggable;
		}
				
		if (PairingDraggable.class.getSimpleName().equals(resultTypeName)) {
			PairingDraggable pairingDraggable = new PairingDraggable();
			List<String> answers = new ArrayList<String>();
			pairingDraggable.setID(question.getID());
			pairingDraggable.setScore(question.getScore());
			pairingDraggable.setQuestion(question.getQuestion());
			question.getAnswers().forEach(answer ->{
				answers.add(answer.getAnswer().trim());
				pairingDraggable.setAnswers(answers);
			});
			pairingDraggable.setAnswered(question.isAnswered());
			return pairingDraggable;
		}
		
		
		if(CorrectOrderDraggable.class.getSimpleName().equals(resultTypeName)) {
			CorrectOrderDraggable correctOrderDraggable = new CorrectOrderDraggable();
			correctOrderDraggable.setID(question.getID());
			correctOrderDraggable.setScore(question.getScore());
			correctOrderDraggable.setStatement(question.getStatement());
			correctOrderDraggable.setQuestion(question.getQuestion());
			return correctOrderDraggable;
		}
		
		if (Writing.class.getSimpleName().equals(resultTypeName)) {
			Writing writing = new Writing();
			writing.setID(question.getID());
			writing.setScore(question.getScore());
			writing.setQuestion(question.getQuestion());
			writing.setAnswered(question.isAnswered());
			return writing;
		}
	
		if (OrderSentences.class.getSimpleName().equals(resultTypeName)) {
			OrderSentences orderSentences = new OrderSentences();
			List<String> sentences = new ArrayList<String>();
			orderSentences.setID(question.getID());
			orderSentences.setScore(question.getScore());
			question.getSentences().forEach(sentence ->{
				sentences.add(sentence.getSentence().trim());
				orderSentences.setSentences(sentences);
			});
			orderSentences.setAnswered(question.isAnswered());
			return orderSentences;
		}
		return null;
	}

	@Override
	public Object transformQuestion(String typeName, QuestionPre question) {
		
		String resultTypeName = putFirsLetterInUppercase(typeName);
		
		if (SelectSingleAnswer.class.getSimpleName().equals(resultTypeName)) {
			List<String> answers = new ArrayList<String>();
			SelectSingleAnswer selectSingleAnswer = new SelectSingleAnswer();
			selectSingleAnswer.setID(Integer.parseInt(question.getID()));
			selectSingleAnswer.setScore(question.getScore());
			selectSingleAnswer.setQuestion(question.getQuestion());
			question.getAnswers().forEach(aswer ->{
				answers.add(aswer.getAnswer().trim());
				selectSingleAnswer.setAnswers(answers);
			});
			return selectSingleAnswer;
		} 
		
		if (SelectDraggable.class.getSimpleName().equals(resultTypeName)) {
			SelectDraggable selectDraggable = new SelectDraggable();
			List<String> answers = new ArrayList<String>();
			selectDraggable.setID(Integer.parseInt(question.getID()));
			selectDraggable.setScore(question.getScore());
			selectDraggable.setQuestion(question.getQuestion());
			question.getAnswers().forEach(answer ->{
				answers.add(answer.getAnswer().trim());
				selectDraggable.setAnswers(answers);
			});
			
			return selectDraggable;
		}
		
		if (CompleteDraggable.class.getSimpleName().equals(resultTypeName)) {
			CompleteDraggable completeDraggable = new CompleteDraggable();
			List<String> answers = new ArrayList<String>();
			completeDraggable.setID(Integer.parseInt(question.getID()));
			completeDraggable.setScore(question.getScore());
			completeDraggable.setStatement(question.getStatement());
			completeDraggable.setQuestion(question.getQuestion());
			question.getAnswers().forEach(answer ->{
				answers.add(answer.getAnswer().trim());
				completeDraggable.setAnswers(answers);
			});
			
			return completeDraggable;
		}
				
		if (PairingDraggable.class.getSimpleName().equals(resultTypeName)) {
			PairingDraggable pairingDraggable = new PairingDraggable();
			List<String> answers = new ArrayList<String>();
			pairingDraggable.setID(Integer.parseInt(question.getID()));
			pairingDraggable.setScore(question.getScore());
			pairingDraggable.setQuestion(question.getQuestion());
			question.getAnswers().forEach(answer ->{
				answers.add(answer.getAnswer().trim());
				pairingDraggable.setAnswers(answers);
			});
			
			return pairingDraggable;
		}
		
		
		if(CorrectOrderDraggable.class.getSimpleName().equals(resultTypeName)) {
			CorrectOrderDraggable correctOrderDraggable = new CorrectOrderDraggable();
			correctOrderDraggable.setID(Integer.parseInt(question.getID()));
			correctOrderDraggable.setScore(question.getScore());
			correctOrderDraggable.setStatement(question.getStatement());
			correctOrderDraggable.setQuestion(question.getQuestion());
			return correctOrderDraggable;
		}
		
		if (Writing.class.getSimpleName().equals(resultTypeName)) {
			Writing writing = new Writing();
			writing.setID(Integer.parseInt(question.getID()));
			writing.setScore(question.getScore());
			writing.setQuestion(question.getQuestion());
			return writing;
		}
	
		if (OrderSentences.class.getSimpleName().equals(resultTypeName)) {
			OrderSentences orderSentences = new OrderSentences();
			List<String> sentences = new ArrayList<String>();
			orderSentences.setID(Integer.parseInt(question.getID()));
			
			orderSentences.setScore(question.getScore());
			question.getSentences().forEach(sentence ->{
				sentences.add(sentence.getSentence().trim());
				orderSentences.setSentences(sentences);
			});
			return orderSentences;
		}
		return null;
	}
	
	
	/*
	 * obtener la ultima pocision del array de la columna assessments 
	 * 
	 */
	public String putFirsLetterInUppercase(String typeName) {
		if (typeName == null || typeName.isEmpty()) {
	        return typeName;
	    }
		String result = typeName.substring(0,1).toUpperCase()+typeName.substring(1);
		return result;
	}


}
