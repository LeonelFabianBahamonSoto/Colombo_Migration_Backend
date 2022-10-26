package co.edu.colomboamericano.caelassessment.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.colomboamericano.caelassessment.dto.ProspectiveDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveToSaveDto;
import co.edu.colomboamericano.caelassessment.entity.Prospective;
import co.edu.colomboamericano.caelassessment.service.MailService;
import co.edu.colomboamericano.caelassessment.service.ProspectiveService;

@RestController
@RequestMapping("/v1/prospectives")
public class ProspectiveController
{
	@Autowired
	private ProspectiveService prospectiveService;
	
	@Autowired
	private MailService mailService;

	/**
	 * @author Smarthink
	 * @param ProspectiveToSaveDto Entity
	 * @return New Prospective
	 * @throws Exception if the person's prospective, email are different or null or document number is already registered
	 */
	@PostMapping("")
	public ResponseEntity<Prospective> createProspective( @Valid @RequestBody ProspectiveToSaveDto prospectiveToSave ) throws Exception
	{		
		if( prospectiveToSave == null ) {
			throw new Exception("El prospective es nulo");
		};
		
		if( !prospectiveToSave.getEmail().equals( prospectiveToSave.getConfirmEmail() ) ) {
			throw new Exception("Los email ingresados no son iguales");
		};
		
		if( !prospectiveToSave.getTermsConditions().equals( true ) ) {
			throw new Exception("El usuario debe aceptar los terminos y condiciones");
		};
		
		return ResponseEntity.status( HttpStatus.CREATED ).body( prospectiveService.createProspective( prospectiveToSave ) );
	};
	
	/**
	 * @author Smarthink
	 * @param Customer document number 
	 * @return prospectiveDto from customer
	 * @throws Exception if prospective doesn't exist
	 */
	@GetMapping("/getByDocumentNumber/{documentNumber}")
	public ResponseEntity<ProspectiveDto> getProspectiveByDocumentNumber( @PathVariable Long documentNumber ) throws Exception
	{
		if( documentNumber == null ) {
			throw new Exception("El numero de documento del Prospective a buscar es nulo");
		};
		
		ProspectiveDto prospective = prospectiveService.findByDocumentNumber( documentNumber );
		
		
		if( prospective == null ) {
			throw new Exception("No se encontro el prospective por el numero de documento indicado");
		};

		return ResponseEntity.status( HttpStatus.OK ).body( prospective );
	};

	/**
	 * @author Smarthink
	 * @param Student email and program
	 * @return String Email response.
	 * @throws Exception if email or program isn't valid
	 */
	@GetMapping("/sendInterviewInstructionsEmail")
	public ResponseEntity<String> sendInterviewInstructionsEmail( @RequestParam String email, @RequestParam String program ) throws Exception
	{
		if( email == null || email.isEmpty() ) {
			throw new Exception("El email indicado no es valido");
		};
		
		if( program == null || program.isEmpty() ) {
			throw new Exception("El programa indicado no es valido");
		};
		
		String response = mailService.sendInstructionsInterviewAssessment(email, program);
		
		if( response.equals("Email not sent") ) {
			return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( "EL email no pudo ser enviado" );
		};
		
		return ResponseEntity.status( HttpStatus.OK ).body( response );
	};

//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getbyprospectiveid2")
//	public ResponseEntity<Optional<Prospective>> getProspectiveByFilter( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( prospectiveService.findById( id ) );
//	};
//	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getbyprospectiveid3")
//	public ResponseEntity<Optional<Prospective>> getProspectivesByDateRangeFilter( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( prospectiveService.findById( id ) );
//	};
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getbyprospectiveid3")
//	public ResponseEntity<Optional<Prospective>> getProspectivesByDateRangeFilterAndState( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( prospectiveService.findById( id ) );
//	};
}