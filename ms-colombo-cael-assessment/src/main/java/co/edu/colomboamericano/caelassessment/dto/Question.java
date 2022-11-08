package co.edu.colomboamericano.caelassessment.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO generado para mapear el JSON que se guarda
 * en la columna assessments de la tabla assessment de la 
 * base de datos miniveldeingles
  */
@Data
public class Question {
	@JsonProperty(value = "ID")
	private int ID;
	
	private String score;
	private String statement;
	private Object question;
	private ArrayList<String> correctOrder;
	private boolean isAnswered;
	private ArrayList<Answer> answers;
	public ArrayList<Sentence> sentences;
}
