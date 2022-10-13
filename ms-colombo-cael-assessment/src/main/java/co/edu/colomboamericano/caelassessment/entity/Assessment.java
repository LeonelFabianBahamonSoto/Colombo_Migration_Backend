package co.edu.colomboamericano.caelassessment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "assessment")
public class Assessment
{
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
    @Column(name = "id")
	private Integer id;
	
	@Size( min= 1, max=255, message = "El nombre del curso debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El nombre del curso no puede ser nulo")
	private String course;
	
	@NotNull(message = "El assessment no puede ser nulo")
	private String assessment;
	
	@NotNull(message = "El questionsStepper no puede ser nulo")
	private String questionsStepper;
	
	@NotNull(message = "El remainingTime no puede ser nulo")
	private Integer remainingTime;
	
	@NotNull(message = "El assessmentStatusId no puede ser nulo")
	private Integer assessmentStatusId;
	
	@NotNull(message = "El prospectiveId no puede ser nulo")
	private Integer prospectiveId;
	
	@NotNull(message = "El createAt no puede ser nulo")
	private Date createAt;
	
	@NotNull(message = "El updateAt no puede ser nulo")
	private Date updateAt;
	
	@NotNull(message = "El program no puede ser nulo")
	private String program;
	
	@NotNull(message = "El headquarter no puede ser nulo")
	private String headquarter;
}
