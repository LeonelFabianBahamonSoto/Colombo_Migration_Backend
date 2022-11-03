package co.edu.colomboamericano.caelassessment.dto;

import java.util.ArrayList;

import lombok.Data;

/**
 * DTO generado para mapear el JSON que se guarda
 * en la columna assessments de la tabla assessment de la 
 * base de datos miniveldeingles
  */
@Data
public class QuestionType {

	private int iD;
	private String typeName;
	private ArrayList<Question> questions;
	private MultimediaFile multimediaFile;

}
