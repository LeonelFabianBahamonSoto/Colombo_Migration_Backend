package co.edu.colomboamericano.caelassessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ModeloNotFoundException extends RuntimeException{
	
	public ModeloNotFoundException(String mensaje) {
		super(mensaje);
	}
}
