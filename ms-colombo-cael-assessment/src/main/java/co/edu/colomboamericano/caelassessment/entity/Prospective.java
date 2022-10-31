package co.edu.colomboamericano.caelassessment.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prospective")
public class Prospective implements Serializable
{
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
    @Column(name = "id")
    private Integer id;
	
	@Size( min= 1, max=255, message = "El nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El nombre del estudiante no puede ser nulo")
    @Column(name = "firstName")
    private String firstName;
	
	@Size( min= 1, max=255, message = "El segundo nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El segundo nombre del estudiante no puede ser nulo")
    @Column(name = "secondName")
    private String secondName;
	
	@Size( min= 1, max=255, message = "El segundo nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El apellido del estudiante no puede ser nulo")
    @Column(name = "surname")
    private String surname;
	
	@Size( min= 1, max=255, message = "El segundo nombre del estudiante debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El segundo apellido del estudiante no puede ser nulo")
    @Column(name = "secondSurname")
    private String secondSurname;

	@NotNull(message = "El numero de documento del estudiante no puede ser nulo")
    @Column(name = "documentNumber")
    private Long documentNumber;
	
	@Column(name = "birthdate")
    private LocalDate birthdate;
	
	@NotNull(message = "El tipo de documento del estudiante no puede ser nulo")
	@Column(name = "documentType")
	private Integer documentType;
	
	@Size( min= 1, max=500, message = "El email debe tener entre 1 a 255 caracteres")
	@Email
	@Column(name = "email")
    private String email;
	
	@Size( min= 1, max=500, message = "El numero celular debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El numero celular no puede ser nulo")
	@Column(name = "cellphone")
    private String cellphone;
	
//	@NotNull(message = "El school grade no puede ser nulo")
	@Column(name = "schoolGrade")
    private Integer schoolGrade;
	
	@Column(name = "termsConditions")
    private int termsConditions;
	
	@NotNull(message = "El createAt no puede ser nulo")
	@Column(name = "createdAt")
    private Date createdAt;
	
	@NotNull(message = "El updateAt no puede ser nulo")
	@Column(name = "updatedAt")
    private Date updatedAt;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name="prospectiveStatusId", nullable=false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProspectiveStatus prospectiveStatusId;

	private static final long serialVersionUID = 1L;
}
