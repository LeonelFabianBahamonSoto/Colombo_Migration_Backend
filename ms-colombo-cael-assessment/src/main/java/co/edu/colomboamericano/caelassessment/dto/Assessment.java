package co.edu.colomboamericano.caelassessment.dto;

import lombok.Data;

/**
 * DTO generado para mapear el JSON que se guarda
 * en la columna assessments de la tabla assessment de la 
 * base de datos miniveldeingles
  */
@Data
public class Assessment {
	private int assessmentID;
	private String courseName;
	private String title;
	private String totalScore;
	private String approvalScore;
	private Level level;
	private String program;
}
