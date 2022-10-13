package co.edu.colomboamericano.caelassessment.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.colomboamericano.caelassessment.entity.Assessment;
import co.edu.colomboamericano.caelassessment.service.AssessmentService;

@RestController
@RequestMapping("/v1/assessment")
public class AssessmentController
{
	@Autowired
	private AssessmentService assessmentService;
	
	/**
	 * @param assessment
	 * @return Creacion de assessment
	 * @throws Exception
	 */
	@PostMapping("/createAssessment")
	public ResponseEntity<Assessment> createAssessment( @Valid @RequestBody Assessment assessment ) throws Exception
	{
		return ResponseEntity.status( HttpStatus.CREATED ).body( assessmentService.save( assessment ) );
	};
	
	/**
	 * @param Numero documento 'documentNumber', tipo del documento 'documentType'
	 * @return BadRequest para que el usuario pueda continuar de lo contrario es que ya realizo una previamente.
	 * @throws Exception cuando el usuario ya realizo la nivelacion.
	 */
	@GetMapping("/assessments")
	public ResponseEntity<Integer> getAssessment( @RequestParam String documentNumber, @RequestParam String documentType ) throws Exception
	{
		if( documentNumber == null || documentType == null ) {
			throw new Exception("EL numero de docuemnto y el tipo no es valido para la consulta");
		}
		
		Integer isAssessment = assessmentService.getAssessmentBy( documentNumber, documentType );
		
		if( isAssessment == 1 ) {
			return ResponseEntity.status( HttpStatus.OK ).body( isAssessment );
		};
		
		return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( isAssessment );
	};
}
