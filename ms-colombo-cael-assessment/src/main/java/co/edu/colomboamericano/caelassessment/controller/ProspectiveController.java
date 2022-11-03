package co.edu.colomboamericano.caelassessment.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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

import co.edu.colomboamericano.caelassessment.dto.ProspectiveByDateRangeStateFilterDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveToSaveDto;
import co.edu.colomboamericano.caelassessment.entity.Prospective;
import co.edu.colomboamericano.caelassessment.service.MailService;
import co.edu.colomboamericano.caelassessment.service.ProspectiveService;
import co.edu.colomboamericano.caelassessment.utils.CsvExportHelper;

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
	 * @Note ESTE METODO ES OPCIONAL DEBIDO QUE NO SE ENCUENTRA DONDE SE ESTA CREANDO EL PROSPECTIVE Y ESTA EXPUESTO A CAMBIOS.
	 * 				 EN el flujo funcional el posible metodo de creacion del prospective no tiene un endpoint por lo que se deja vacio.
	 */
	@PostMapping("")
	public ResponseEntity<Prospective> createProspective( @Valid @RequestBody ProspectiveToSaveDto prospectiveToSave ) throws Exception
	{		
		if( prospectiveToSave == null )	throw new Exception("El prospective es nulo");
		
		if( !prospectiveToSave.getEmail().equals( prospectiveToSave.getConfirmEmail() ) ) throw new Exception("Los email ingresados no son iguales");
		
		if( !prospectiveToSave.getTermsConditions().equals( true ) ) throw new Exception("El usuario debe aceptar los terminos y condiciones");
		
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
		if( documentNumber == null ) throw new Exception("El numero de documento del Prospective a buscar es nulo");
		
		ProspectiveDto prospective = prospectiveService.findByDocumentNumber( documentNumber );

		if( prospective == null ) throw new Exception("No se encontro el prospective por el numero de documento indicado");

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
		if( email == null || email.isEmpty() ) throw new Exception("El email indicado no es valido");
		
		if( program == null || program.isEmpty() ) throw new Exception("El programa indicado no es valido");
		
		String response = mailService.sendInstructionsInterviewAssessment(email, program);
		
		if( response.equals("Email not sent") ) return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( "EL email no pudo ser enviado" );
		
		return ResponseEntity.status( HttpStatus.OK ).body( response );
	};

	/**
	 * @author Smarthink
	 * @param  Take, Skip and filter.
	 * @return Json with findByFilterDto and numberOfRows of a relationship of the Assessment and Prospective tables.
	 * @throws Exception if any parameter is null.
	 */
	@GetMapping("/prospectiveByFilter")
	public ResponseEntity<Map<String, Object>> getProspectiveByFilter( @RequestParam Integer take, @RequestParam Integer skip, @RequestParam String filter ) throws Exception
	{		
		if( take == null ) throw new Exception("El valor del take no puede ser nulo");
		
		if( skip == null ) throw new Exception("El valor del skip no puede ser nulo");
		
		if( filter == null || filter.isEmpty()) throw new Exception("El valor del filter no puede ser nulo");
		
		return ResponseEntity.status( HttpStatus.OK ).body( prospectiveService.findByFilter( take, skip, filter ) );
	};

	/**
	 * @author Smarthink
	 * @param State, Start and end date for filter in the sql query
	 * @return Csv document.
	 * @throws Exception
	 */
	@GetMapping("/prospectivesByDateRangeFilterState")
	public void getProspectivesByDateRangeFilterAndState( @RequestParam Date startDate, @RequestParam Date endDate, @RequestParam String state, HttpServletResponse response ) throws Exception
	{
		if( startDate == null ) throw new Exception("El valor del filter no puede ser nulo");

		if( endDate == null )  throw new Exception("El valor del filter no puede ser nulo");
		
		if( state == null )  throw new Exception("El valor del filter no puede ser nulo");
		
		List<ProspectiveByDateRangeStateFilterDto> filteredProspectives = prospectiveService.findByDateRangeFilterAndState( startDate, endDate, state );
		
		if( filteredProspectives == null ) throw new Exception("No fue posible obtener los datos filtrados");

		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format( new Date() );

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = informe-nivelados-" + state + "-" + currentDateTime + ".xlsx";
        response.setHeader( headerKey, headerValue );
		
        CsvExportHelper excelExporter = new CsvExportHelper( filteredProspectives );
        
        excelExporter.getProspectiveCsvFile( response, state );
	};

}