package co.edu.colomboamericano.caelstudent.entity;

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
@Table(name = "facturas_linea")
public class OnlineInvoice
{
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
    @Column(name = "id")
	private Integer id;
	
	@NotNull(message = "El id de la factura no puede ser nulo")
	@Column(name = "id_factura")
	private Integer invoiceId;
	
	@Size( min= 1, max=12, message = "El numero de documento debe tener entre 1 a 12 caracteres")
	@NotNull(message = "El numero de documento no puede ser nulo")
	@Column(name = "documento_estudiante", length = 12)
	private String documentNumber;
	
	@NotNull(message = "La fecha genera no puede ser nula")
	@Column(name = "fecha_genera")
	private Date generationDate;
	
	@Size( min= 1, max=12, message = "El numero de documento debe tener entre 1 a 12 caracteres")
	@NotNull(message = "La fecha genera no puede ser nula")
	@Column(name = "forma_confirmacion")
	private String ConfirmationForm;
}