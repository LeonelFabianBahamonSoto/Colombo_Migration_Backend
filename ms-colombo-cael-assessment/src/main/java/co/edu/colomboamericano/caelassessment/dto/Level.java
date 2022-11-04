package co.edu.colomboamericano.caelassessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO generado para mapear el JSON que se guarda
 * en la columna assessments de la tabla assessment de la 
 * base de datos miniveldeingles
  */
@Data
public class Level {
	
	@JsonProperty(value = "slug")
	private String slug;
	
	@JsonProperty(value = "image")
	private String image;
}
