package co.edu.colomboamericano.caelassessment.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.colomboamericano.caelassessment.dto.ProspectiveDto;
import co.edu.colomboamericano.caelassessment.entity.Prospective;
import co.edu.colomboamericano.caelassessment.service.ProspectiveService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/prospectives")
public class ProspectiveController
{
	@Autowired
	private ProspectiveService prospectiveService;
	
	/**
	 * @param Prospective
	 * @return Creacion de Prospective
	 * @throws Exception
	 */
	@PostMapping("")
	public ResponseEntity<Prospective> createProspective( @RequestBody ProspectiveDto prospective ) throws Exception
	{
		log.info("Prospective: " + prospective);
		
		if( prospective == null ) {
			throw new Exception("El prospective es nulo");
		};
		
		if( !prospective.getEmail().equals( prospective.getConfirmEmail() ) ) {
			throw new Exception("Los email ingresados no son iguales");
		};
		
//		return ResponseEntity.status( HttpStatus.CREATED ).body( prospectiveService.save( prospective ) );
		return null;
	};
	
	/**
	 * @param
	 * @return levels.
	 * @throws Exception
	 */
	@GetMapping("/getByDocumentNumber/{documentNumber}")
	public ResponseEntity<Optional<Prospective>> getProspectiveByDocumentNumber( @PathVariable Long documentNumber ) throws Exception
	{
		if( documentNumber == null ) {
			throw new Exception("El numero de documento del Prospective a buscar es nulo");
		};

		return ResponseEntity.status( HttpStatus.OK ).body( prospectiveService.findByDocumentNumber( documentNumber ) );
	};
	
	/**
	 * @param
	 * @return levels.
	 * @throws Exception
	 */
//	@GetMapping("/findAll")
//	public ResponseEntity<List<Prospective>> getAll() throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( prospectiveService.findAll() );
//	};
//	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getbyprospectiveid2")
//	public ResponseEntity<Optional<Prospective>> getProspectivesByDateRangeFilter( @RequestParam Integer id ) throws Exception
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
//	public ResponseEntity<Optional<Prospective>> getProspectivesByDateRangeFilterAndState( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( prospectiveService.findById( id ) );
//	};
}