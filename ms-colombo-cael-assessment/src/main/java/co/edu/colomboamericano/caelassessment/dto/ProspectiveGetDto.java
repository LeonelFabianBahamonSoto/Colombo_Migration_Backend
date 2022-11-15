package co.edu.colomboamericano.caelassessment.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProspectiveGetDto {
	private Integer prospectiveStatus;
	private Long id;
	private Date createdAt;
	private Date updatedAt;
	private String firstName;
	private String secondName;
	private String surname;
	private String secondSurname;
	private Integer documentType;
	private Long documentNumber;
	private LocalDate birthdate;
	private String email;
	private String cellphone;
	private Integer schoolGrade;
	private boolean termsConditions;
	private List<Object> personsInCharge;
}
