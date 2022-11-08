package co.edu.colomboamericano.caelassessment.dto;

import java.io.Serializable;
import java.time.LocalDate;
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
public class ProspectiveDto implements Serializable
{
    private Integer id;

	@Size( min= 1, max=255, message = "El nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El nombre del estudiante no puede ser nulo")
    private String firstName;
	
	@Size( min= 1, max=255, message = "El segundo nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El segundo nombre del estudiante no puede ser nulo")
    private String secondName;
	
	@Size( min= 1, max=255, message = "El segundo nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El apellido del estudiante no puede ser nulo")
    private String surname;
	
	@Size( min= 1, max=255, message = "El segundo nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El segundo apellido del estudiante no puede ser nulo")
    private String secondSurname;

	@NotNull(message = "El numero de documento del estudiante no puede ser nulo")
    private Long documentNumber;
	
    private LocalDate birthdate;
	
	@NotNull(message = "El tipo de documento del estudiante no puede ser nulo")
	private Integer documentType;
	
	@Size( min= 1, max=500, message = "El email debe tener entre 1 a 255 caracteres")
	@Email
    private String email;
	
	@Size( min= 1, max=500, message = "El numero celular debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El numero celular no puede ser nulo")
    private String cellphone;
	
	@NotNull(message = "El school grade no puede ser nulo")
    private Integer schoolGrade;
	
    private int termsConditions;
	
	@NotNull(message = "El createAt no puede ser nulo")
    private Date createdAt;
	
	@NotNull(message = "El updateAt no puede ser nulo")
    private Date updatedAt;

	@NotNull(message = "El categoria del prospective no puede ser nula" )
    private Integer prospectiveStatusId;
	
	private static final long serialVersionUID = 1L;
}
