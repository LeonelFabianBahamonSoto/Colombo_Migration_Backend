package co.edu.colomboamericano.caelassessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO generado para mapear el JSON que se guarda
 * en la columna assessments de la tabla assessment de la 
 * base de datos miniveldeingles
  */
@Data
public class Assessment {
	
	@JsonProperty(value = "assessmentID")
	private int assessmentID;
	
	@JsonProperty(value = "courseName")
	private String courseName;
	
	@JsonProperty(value = "title")
	private String title;
	
	@JsonProperty(value = "totalScore")
	private String totalScore;
	
	@JsonProperty(value = "approvalScore")
	private String approvalScore;
	
	@JsonProperty(value = "level")
	private Level level;
	
	private String program;
}
