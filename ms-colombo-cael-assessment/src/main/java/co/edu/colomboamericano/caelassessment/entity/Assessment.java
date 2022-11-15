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
	private Integer id;
	
	@Size( min= 1, max=255, message = "El nombre del curso debe tener entre 1 a 255 caracteres")
    @Column(name = "course")
	private String course;
	
    @Column(name = "assessments")
	private String assessments;

    @Column(name = "questionsStepper")
	private String questionsStepper;

    @Column(name = "remainingTime")
	private int remainingTime;

    @Column(name = "createdAt")
	private Date createAt;

    @Column(name = "updatedAt")
	private Date updateAt;

    @Column(name = "program")
	private String program;

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
