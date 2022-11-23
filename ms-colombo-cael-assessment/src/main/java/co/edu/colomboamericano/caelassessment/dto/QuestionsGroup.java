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
public class QuestionsGroup
{
	@JsonProperty(value = "questionGroupId")
	private int questionGroupId;
	
	@JsonProperty(value = "title")
	private String title;
	
	@JsonProperty(value = "questionTypes")
	private ArrayList<QuestionType> questionTypes;

}