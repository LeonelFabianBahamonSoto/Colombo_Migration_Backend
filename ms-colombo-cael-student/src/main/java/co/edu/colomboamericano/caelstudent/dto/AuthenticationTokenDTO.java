package co.edu.colomboamericano.caelstudent.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AuthenticationTokenDTO {
	private String accessToken;
	private Date expiresIn;

}

