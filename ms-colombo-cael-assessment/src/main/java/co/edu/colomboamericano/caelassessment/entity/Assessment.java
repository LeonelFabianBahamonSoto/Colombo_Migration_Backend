package co.edu.colomboamericano.caelassessment.entity;

import java.io.Serializable;
import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assessment")
public class Assessment implements Serializable
{
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
    @Column(name = "id")
	private int id;
	
	@Size( min= 1, max=255, message = "El nombre del curso debe tener entre 1 a 255 caracteres")
	@NotNull(message = "El nombre del curso no puede ser nulo")
    @Column(name = "course")
	private String course;
	
	@NotNull(message = "El assessment no puede ser nulo")
    @Column(name = "assessments")
	private String assessments;
	
	@NotNull(message = "El questionsStepper no puede ser nulo")
    @Column(name = "questionsStepper")
	private String questionsStepper;
	
	@NotNull(message = "El remainingTime no puede ser nulo")
    @Column(name = "remainingTime")
	private int remainingTime;
	
	@NotNull(message = "El createAt no puede ser nulo")
    @Column(name = "createdAt")
	private Date createAt;
	
	@NotNull(message = "El updateAt no puede ser nulo")
    @Column(name = "updatedAt")
	private Date updateAt;
	
	@NotNull(message = "El program no puede ser nulo")
    @Column(name = "program")
	private String program;
	
	@NotNull(message = "El headquarter no puede ser nulo")
    @Column(name = "headquarter")
	private String headquarter;
	
    @ManyToOne(optional=false)
    @JoinColumn(name="assessmentStatusId", nullable=false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AssessmentStatus assessmentStatus;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="prospectiveId", nullable=false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Prospective prospective;
    
	private static final long serialVersionUID = 1L;
}
