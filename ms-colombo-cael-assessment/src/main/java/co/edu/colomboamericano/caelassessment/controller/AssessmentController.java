package co.edu.colomboamericano.caelassessment.controller;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.colomboamericano.caelassessment.dto.AssessmentDto;
import co.edu.colomboamericano.caelassessment.entity.Assessment;
import co.edu.colomboamericano.caelassessment.entity.Prospective;
import co.edu.colomboamericano.caelassessment.exception.ModeloNotFoundException;
import co.edu.colomboamericano.caelassessment.service.AssessmentService;
import co.edu.colomboamericano.caelassessment.service.ProspectiveService;
import co.edu.colomboamericano.caelassessment.service.WordPressService;

@RestController
@RequestMapping("/v1/assessment")
public class AssessmentController
{
	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
	private WordPressService wordPressService;
	
	@Autowired
	private ProspectiveService prospectiveService;

	/**
	 * @author Smarthink
	 * @param Numero documento 'documentNumber', tipo del documento 'documentType', nivel 'level', programa 'program', sede 'headquarter', fecha nacimiento 'birthdate'.
	 * @return New registered Assessment.
	 * @throws If any parameter is null.
	 */
	@PostMapping("/createAssessment")
	public ResponseEntity<AssessmentDto> createAssessment( @RequestParam Integer documentType, @RequestParam Long documentNumber,
			@RequestParam Date birthdate, @RequestParam String level, @RequestParam String program, @RequestParam String headquarter ) throws Exception
	{
		AssessmentDto assessment = assessmentService.createAssessment(documentType, documentNumber, birthdate, level, program, headquarter);

		return ResponseEntity.status( HttpStatus.CREATED ).body( assessment );
	};
	
	/**
	 * Gets an assessment by propspective information.
	 * @param Numero documento 'documentNumber', tipo del documento 'documentType'
	 * @return BadRequest para que el usuario pueda continuar de lo contrario es que ya realizo una previamente.
	 * @throws Exception cuando el usuario ya realizo la nivelacion.
	 */
	@GetMapping("/assessments")
	public ResponseEntity<?> getAssessment(@RequestParam Integer documentType, @RequestParam Long documentNumber,
			@RequestParam Optional<Integer> assessmentStatus) throws Exception
	{
		if( documentNumber == null || documentType == null ) {
			throw new Exception("EL numero de docuemnto y el tipo no es valido para la consulta");
		}
		
		Prospective prospective = new Prospective();
		Assessment assessment = new Assessment();
		prospective = prospectiveService.getFindByDocument(documentType, documentNumber);
		if (prospective == null) {
			throw new ModeloNotFoundException("Prospective doesn't exist");
		}

		assessment = assessmentService.generateDtoAssessmentByStatusAndProspective(prospective.getId(), prospective.getProspectiveStatusId().getId());
		if (assessment == null) {
			throw new ModeloNotFoundException("Assessment not found");
		}
		
		return new ResponseEntity<>(assessment,HttpStatus.OK);
	};

	/**
	 * @param Numero documento 'documentNumber', tipo del documento 'documentType'
	 * @return BadRequest para que el usuario pueda continuar de lo contrario es que ya realizo una previamente.
	 * @throws Exception cuando el usuario ya realizo la nivelacion.
	 */
	@GetMapping("/getbyprospectiveid")
	public ResponseEntity<Optional<Assessment>> getByProspectiveId( @RequestParam Integer id ) throws Exception
	{
		if( id == null ) {
			throw new Exception("EL numero del prospective es nulo");
		}
		
		Optional<Assessment> isAssessment = assessmentService.findByProspectiveId( id );
		
		if( isAssessment.isPresent() ) {
			return ResponseEntity.status( HttpStatus.OK ).body( isAssessment );
		};
		
		return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( isAssessment );
	};

//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
	//Pendiente por probar
	@GetMapping("/levels")
	public ResponseEntity<?> getAssessmentLevels() throws Exception
	{
		Object result = this.wordPressService.getAssessmentLevels();
		return new ResponseEntity<>(result,HttpStatus.OK);
	};
//
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getbyprospectiveid")
//	public ResponseEntity<Optional<Assessment>> getAssessment( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.findByProspectiveId( id ) );
//	};
//	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */

	//pendiente por desestructurar objeto de respuesta del wordpress
	@GetMapping("/questionPreview")
	public ResponseEntity<?> getAssessmentQuestionPreview(@RequestParam Integer questionGroupId,
			@RequestParam Integer questionTypeId,@RequestParam Integer questionId) throws Exception
	{		
		Object result = wordPressService.getAssessmentPreview(questionGroupId, questionTypeId, questionId);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
//	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
	@GetMapping("/getAssessmentQuestion")
	public ResponseEntity<?> getAssessmentQuestion( @RequestParam Integer id ) throws Exception
	{
		Object result = assessmentService.transformAssessmentsAndQuestionStepper(id);
		if (result == null) {
			throw new ModeloNotFoundException("id assessment doesn't exist");
		}
		return new ResponseEntity<>(result,HttpStatus.OK);
	};
	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getAssessmentQuestion1")
//	public ResponseEntity<Optional<Assessment>> updateAssessment( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.findByProspectiveId( id ) );
//	};
//	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getAssessmentQuestion2")
//	public ResponseEntity<Optional<Assessment>> validateQuestion( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.findByProspectiveId( id ) );
//	};
//	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getAssessmentQuestion3")
//	public ResponseEntity<Optional<Assessment>> sendEmailLeveling( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.findByProspectiveId( id ) );
//	};
//	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getAssessmentQuestion4")
//	public ResponseEntity<Optional<Assessment>> getApprovedAssessmentResponse( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.findByProspectiveId( id ) );
//	};
//	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getAssessmentQuestion5")
//	public ResponseEntity<Optional<Assessment>> getFailedAssessmentResponse( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.findByProspectiveId( id ) );
//	};
//	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getAssessmentQuestion6")
//	public ResponseEntity<Optional<Assessment>> getNextQuestion( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.findByProspectiveId( id ) );
//	};
//	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getAssessmentQuestion7")
//	public ResponseEntity<Optional<Assessment>> startNewAssessment( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.findByProspectiveId( id ) );
//	};
//	
//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
//	@GetMapping("/getAssessmentQuestion8")
//	public ResponseEntity<Optional<Assessment>> finishAssessment( @RequestParam Integer id ) throws Exception
//	{
//		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.findByProspectiveId( id ) );
//	};
	
	
	
	
	
	
	
	
	//----------------------------------                ----------------------------------------------------------------------------//
	
	
	
	
	
	
	
	
	//PRUEBA
	@GetMapping("/buscarid/{id}")
	public ResponseEntity<Optional<Assessment>> findById( @PathVariable Integer id ) throws Exception
	{
		if( id == null ) {
			throw new Exception("EL numero del prospective es nulo");
		}
		
		return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( assessmentService.findById( id ) );
	};
	
	//PRUEBA
//	@GetMapping("/todos")
//	public ResponseEntity<List<Assessment>> findAll() throws Exception
//	{		
//		System.out.println("LLEGO!");
//		return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( assessmentService.findAll() );
//	};
}
