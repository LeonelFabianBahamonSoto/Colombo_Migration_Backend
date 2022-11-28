package co.edu.colomboamericano.caelassessment.controller;

import java.util.Date;
import java.util.Optional;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import co.edu.colomboamericano.caelassessment.dto.AssessmentDto;
import co.edu.colomboamericano.caelassessment.dto.AssessmentGetDto;
import co.edu.colomboamericano.caelassessment.dto.EmailLevelingDto;
import co.edu.colomboamericano.caelassessment.dto.RequestQuestion;
import co.edu.colomboamericano.caelassessment.entity.Assessment;
import co.edu.colomboamericano.caelassessment.exception.ModeloNotFoundException;
import co.edu.colomboamericano.caelassessment.repository.AssessmentRepository;
import co.edu.colomboamericano.caelassessment.repository.AssessmentRepositoryCustom;
import co.edu.colomboamericano.caelassessment.repository.ProspectiveRepository;
import co.edu.colomboamericano.caelassessment.service.AssessmentService;
import co.edu.colomboamericano.caelassessment.service.MailService;
import co.edu.colomboamericano.caelassessment.service.ProspectiveService;
import co.edu.colomboamericano.caelassessment.service.WordPressService;
import co.edu.colomboamericano.caelassessment.utils.AssessmentWordPressHelper;

@RestController
@RequestMapping("/v1/assessment")
public class AssessmentController
{
	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
	private WordPressService wordPressService;
	
	@Autowired
	private AssessmentWordPressHelper assessmentWordPressHelper;
	
	@Autowired
	private ProspectiveService prospectiveService;
	
	@Autowired
	private MailService mailService;

	@Autowired
	private AssessmentRepositoryCustom assessmentRepositoryCustom;
	
	
	@Autowired
	private ProspectiveRepository prospectiveRepository;

	/**
	 * @author Smarthink
	 * @param Numero documento 'documentNumber', tipo del documento 'documentType', nivel 'level', programa 'program', sede 'headquarter', fecha nacimiento 'birthdate'.
	 * @return New registered Assessment.
	 * @throws If any parameter is null.
	 */
	@PostMapping("")
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
		Long resultIdProspective = prospectiveRepository.findIdByDocumentAndType(documentNumber, documentType);
		
		if (resultIdProspective == null) {
			throw new ModeloNotFoundException("Prospective doesn't exist");
		}
	
		AssessmentGetDto assessment = assessmentService.generateDtoAssessmentByStatusAndProspective(resultIdProspective, assessmentStatus.get());
		if (assessment == null) {
			throw new ModeloNotFoundException("Assessment not found");
		}
			
		return new ResponseEntity<>(assessment,HttpStatus.OK);
	}

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
	@GetMapping("/levels")
	public ResponseEntity<?> getAssessmentLevels() throws Exception
	{
		Object result = assessmentWordPressHelper.getAssessmentLevels();
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
	public ResponseEntity<?> getAssessmentQuestionPreview(@RequestParam Integer groupQuestionsId,
			@RequestParam Integer groupTypeId,@RequestParam Integer questionId) throws Exception
	{	
		//Object result = assessmentWordPressHelper.getAssessmentPreview(groupQuestionsId, groupTypeId, questionId);
		Object result = assessmentService.buildQuestionPreview(groupQuestionsId, groupTypeId, questionId);
		//Object result = wordPressService.getAssessmentPreview(groupQuestionsId, groupTypeId, questionId);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}

//	/**
//	 * @param
//	 * @return levels.
//	 * @throws Exception
//	 */
	@GetMapping("/getAssessmentQuestion/{id}")
	public ResponseEntity<?> getAssessmentQuestion( @PathVariable("id") Integer id ) throws Exception
	{
		Object result = assessmentService.getAssessmentQuestion(id);
		if (result == null) {
			throw new ModeloNotFoundException("id assessment doesn't exist");
		}
		return new ResponseEntity<>(result,HttpStatus.OK);
	};

	/**
	 * @author Smarthink
	 * @param The url sends the attributes that refer to the field to be updated, with the id to find the entity and as the body of the request assessmentsObject that is the data to be updated in the db.
	 * @return Updated entity
	 * @throws If the entity couldn't be updated.
	 */
	@PutMapping("/attributes/{id}")
	public ResponseEntity< AssessmentDto > updateAssessmentFieldAssessments( @PathVariable("id") Integer id, @RequestBody Object assessmentsObject ) throws Exception
	{
		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.updateAssessmentFieldAssessments( id, String.valueOf( assessmentsObject ) ) );
	};
	
	/**
	 * @author Smarthink
	 * @param The url sends the attributes that refer to the field to be updated, with the id to find the entity and as the body of the request assessmentsObject that is the data to be updated in the db.
	 * @return Updated entity
	 * @throws If the entity couldn't be updated.
	 * @Note OPCIONAL DEBIDO A QUE EN EL METODO ANTIGUO EN LA URL APARACE EN VEZ DE 'assessments' CON 'attributes' DEL SIGUIENTE MODO ...attributes/{id}
	 */
	@PutMapping("/{id}")
	public ResponseEntity< AssessmentDto > updateAssessment( @PathVariable("id") Integer id, @RequestBody Object assessmentsObject ) throws Exception
	{
		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.updateAssessmentFieldAssessments( id, String.valueOf( assessmentsObject ) ) );
	};
	
	/**
     * @author Smarthink
	 * @param EmailLevelingDto entity
	 * @return a message indicating whether or not the message was sent
	 * @throws If the email couldn't be sent
	 */
	@PostMapping("/emailLevelings")
	public ResponseEntity< String > sendEmailLeveling( @RequestBody EmailLevelingDto emailData ) throws Exception
	{		
		String response = mailService.sendLevelingNotification( emailData );

		if( response.equals("Email sent") )
			return ResponseEntity.status( HttpStatus.OK ).body( "El email fue enviado" );

		return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( response );
	};
	
	/**
     * @author Smarthink
	 * @param  ES OTRO SERVICIO ?
	 * @return levels.
	 * @throws Exception
	 */
	@GetMapping("/getAssessmentQuestion4")
	public ResponseEntity<Optional<Assessment>> getApprovedAssessmentResponse( @RequestParam Integer id ) throws Exception
	{
		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.findByProspectiveId( id ) );
	};
	
	//BORRAR
	@GetMapping("/prueba")
	public ResponseEntity< Object > pruebaAssessment( @RequestParam Integer id ) throws Exception
	{
		return ResponseEntity.status( HttpStatus.OK ).body( assessmentService.getApprovedAssessmentResponse( id ) );
	};

	/**
	 * @param
	 * @return levels.
	 * @throws Exception
	 */
	@GetMapping("/getAssessmentQuestion2")
	public ResponseEntity<?> validateQuestion(@RequestBody String answer) throws Exception
	{
		
		Object result = assessmentService.validateQuestion(answer);
		if (result == null) {
			throw new ModeloNotFoundException("Ocurrio un problema");
		}
		
		return new ResponseEntity<>(result,HttpStatus.OK);
	};
	
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
