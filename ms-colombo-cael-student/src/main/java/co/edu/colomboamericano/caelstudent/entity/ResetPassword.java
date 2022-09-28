package co.edu.colomboamericano.caelstudent.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "password_resets" )
public class ResetPassword
{
	@Id
	@Size( min= 1, max=100, message = "El email del usuario debe tener entre 1 a 100 caracteres")
	@NotNull(message = "El email del usuario no puede ser nulo")
	private String email;
	
	@Size( min= 1, max=255, message = "El token del usuario debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El token del usuario no puede ser nulo")
	private String token;
	
	@NotNull(message = "La fecha de generacion del token no puede ser nulo")
	private Date created_at;
}
