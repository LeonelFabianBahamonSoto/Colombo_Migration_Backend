package co.edu.colomboamericano.caelassessment.dto;

import java.io.Serializable;

import co.edu.colomboamericano.caelassessment.entity.Prospective;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailLevelingDto implements Serializable
{
	private Prospective prospective;
	
	private String course;
	
	private String emailImage;
	
	private Boolean isLastLevel; 

	private static final long serialVersionUID = -436092665874547533L;
}
