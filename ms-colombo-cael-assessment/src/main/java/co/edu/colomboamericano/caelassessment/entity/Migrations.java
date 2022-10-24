package co.edu.colomboamericano.caelassessment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "migrations")
public class Migrations implements Serializable
{
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
    @Column(name = "id")
    private int id;

	@NotNull(message = "El program no puede ser nulo")
    @Column(name = "timestamp")
    private long timestamp;
	
	@NotNull(message = "El nombre no puede ser nulo")
    @Column(name = "name")
    private String name;
	
	private static final long serialVersionUID = 1L;
}
