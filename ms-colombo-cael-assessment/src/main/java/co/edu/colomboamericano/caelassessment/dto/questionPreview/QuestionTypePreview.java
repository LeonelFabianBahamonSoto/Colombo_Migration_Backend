package co.edu.colomboamericano.caelassessment.dto.questionPreview;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTypePreview {

	  @JsonProperty("ID") 
	  private String ID;
	  
	  @JsonProperty("typeName") 
	  private String typeName;
	  
	  @JsonInclude(JsonInclude.Include.NON_NULL)
	  private MultimediaFilePreview multimediaFile;
	  
	  private List<QuestionPre> questions;
}
