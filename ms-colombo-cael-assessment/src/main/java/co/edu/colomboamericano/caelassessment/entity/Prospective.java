package co.edu.colomboamericano.caelassessment.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "?????")
public class Prospective
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
	
	@Size( min= 1, max=255, message = "El segundo nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El segundo apellido del estudiante no puede ser nulo")
	private String secondSurname;
	
	@NotNull(message = "El tipo de documento del estudiante no puede ser nulo")
	private Integer documentType;
	
	@Size( min= 1, max=500, message = "El numero de documento del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El numero de documento del estudiante no puede ser nulo")
	private Integer documentNumber;
	
	private Date birthdate;
	
	@Size( min= 1, max=500, message = "El email debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El email no puede ser nulo")
	private String email;
	
	@Size( min= 1, max=500, message = "El numero celular debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El numero celular no puede ser nulo")
	private String cellphone;
	
	@NotNull(message = "El school grade no puede ser nulo")
	private Integer schoolGrade;
	
	private Boolean termsConditions;
	
	@OneToMany( fetch = FetchType.LAZY )
	private String personsInCharge;
	
	@ManyToOne( fetch = FetchType.LAZY )
	private String prospectiveStatus;
	
	@OneToMany( fetch = FetchType.LAZY )
	private Assessment assessments;
}
