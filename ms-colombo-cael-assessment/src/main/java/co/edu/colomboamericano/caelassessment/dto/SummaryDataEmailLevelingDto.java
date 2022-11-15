package co.edu.colomboamericano.caelassessment.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDataEmailLevelingDto implements Serializable
{
	private String emailImage;

    private Boolean isLastLevel;

    private String course;

    private String prospective;

    private String link;

	private static final long serialVersionUID = 4057477889083673704L;
}