package co.edu.colomboamericano.caelassessment.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProspectiveToSaveDto
{
	@Size( min= 1, max=255, message = "El nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El nombre del estudiante no puede ser nulo")
	private String firstName;
	
	@Size( min= 1, max=255, message = "El segundo nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El segundo nombre del estudiante no puede ser nulo")
	private String secondName;
	
	@Size( min= 1, max=255, message = "El segundo nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El apellido del estudiante no puede ser nulo")
	private String surname;
	
	@Size( min= 3, max=255, message = "El segundo nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El segundo apellido del estudiante no puede ser nulo")
	private String secondSurname;

	@NotNull(message = "El numero de documento del estudiante no puede ser nulo")
	private Long documentNumber;

	private Date birthdate;
	
	@NotNull(message = "El tipo de documento del estudiante no puede ser nulo")
	private Integer documentType;
	
	@Size( min= 1, max=500, message = "El email debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El email no puede ser nulo")
	@Email
	private String email;
	
	@Size( min= 1, max=500, message = "El email debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El email no puede ser nulo")
	@Email
	private String confirmEmail;
	
	@Size( min= 1, max=500, message = "El numero celular debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El numero celular no puede ser nulo")
	private String cellphone;
	
	@Size( min= 1, max=500, message = "El nombre de la sede debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El nombre de la sede no puede ser nulo")
	private String headquarter;

	private Boolean termsConditions;
}
