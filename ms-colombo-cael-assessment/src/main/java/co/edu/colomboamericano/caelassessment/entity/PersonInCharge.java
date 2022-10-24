package co.edu.colomboamericano.caelassessment.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "personInCharge")
public class PersonInCharge implements Serializable
{
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
    private int id;

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

    @Column(name = "kinship")
    private String kinship;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "officePhone")
    private String officePhone;
    
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @ManyToOne(optional=false)
    @JoinColumn(name="prospectiveId", nullable=false)
    private Prospective prospective;
    
	private static final long serialVersionUID = 1L;
}
