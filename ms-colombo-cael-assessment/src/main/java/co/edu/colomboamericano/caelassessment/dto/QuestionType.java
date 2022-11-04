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
public class QuestionType {

	@JsonProperty(value = "ID")
	private int ID;
	
	@JsonProperty(value = "typeName")
	private String typeName;
	
	@JsonProperty(value = "questions")
	private ArrayList<Question> questions;
	
	@JsonProperty(value = "multimediaFile")
	private MultimediaFile multimediaFile;

}
