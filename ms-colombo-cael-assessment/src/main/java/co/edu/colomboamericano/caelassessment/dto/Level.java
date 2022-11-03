package co.edu.colomboamericano.caelassessment.dto;

import lombok.Data;

/**
 * DTO generado para mapear el JSON que se guarda
 * en la columna assessments de la tabla assessment de la 
 * base de datos miniveldeingles
  */
@Data
public class Level {
	private String slug;
	private String image;
}
