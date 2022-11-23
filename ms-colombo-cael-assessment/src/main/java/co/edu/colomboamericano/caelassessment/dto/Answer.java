package co.edu.colomboamericano.caelassessment.dto;

import java.util.Date;

import co.edu.colomboamericano.caelassessment.entity.AssessmentStatus;
import co.edu.colomboamericano.caelassessment.entity.Prospective;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * DTO generado para mapear el JSON que se guarda
 * en la columna assessments de la tabla assessment de la 
 * base de datos miniveldeingles
  */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Answer {
	private boolean correctAnswer;
	private String answer;
}
