package co.edu.colomboamericano.caelstudent.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estudiantes")
public class Student
{	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
    @Column(name = "id")
	private Integer id;
	
	@Size( min= 1, max=12, message = "El numero de documento debe tener entre 1 a 12 caracteres")
	@NotNull(message = "El numero de documento no puede ser nulo")
	@Column(name = "numero_documento", length = 12)
	private String documentNumber;
	
	@Size( min= 1, max=12, message = "El nombre del usuario debe tener entre 1 a 12 caracteres")
	@NotNull(message = "El nombre del usuario no puede ser nulo")
	@Column(name = "usuario", length = 12)
	private String usuario;
	
	@Size( min= 1, max=500, message = "La contrasenia debe tener entre 1 a 100 caracteres")
	@NotNull(message = "La contrasenia del usuario no puede ser nulo")
	@Column(name = "password", length = 500)
	private String password;
	
//	@Enumerated(EnumType.ORDINAL)
	@Column(name="politica_privacidad")
	private String privacyPolicy;
	
	@Column(name = "fecha_politica")
	private Date privacyPolicyDate;

//	@Enumerated(EnumType.ORDINAL)
	@Column(name = "activo")
	private String active;
	
	@Size( min= 1, max=100, message = "El remember token debe tener entre 1 a 12 caracteres")
	@Column(name = "remember_token", length = 100)
	private String remember_token;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "fecha_manual_convivencia")
	private Date handbookAcceptanceDate;
	
	@Transient
    private String token;
}