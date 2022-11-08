package co.edu.colomboamericano.caelassessment.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CorrectOrderDraggable{
	
	@JsonProperty(value = "ID")
	private int ID;	
	private String score;
	private String statement;
	private Object question;
}
