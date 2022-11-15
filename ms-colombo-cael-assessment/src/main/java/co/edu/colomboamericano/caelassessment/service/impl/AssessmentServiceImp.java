package co.edu.colomboamericano.caelassessment.service.impl;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import co.edu.colomboamericano.caelassessment.dto.AssessmentDto;
import co.edu.colomboamericano.caelassessment.dto.AssessmentGetDto;
import co.edu.colomboamericano.caelassessment.dto.CurrentQuestion;
import co.edu.colomboamericano.caelassessment.dto.EmailLevelingDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveGetDto;
import co.edu.colomboamericano.caelassessment.dto.Question;
import co.edu.colomboamericano.caelassessment.dto.QuestionStepper;
import co.edu.colomboamericano.caelassessment.dto.Root;
import co.edu.colomboamericano.caelassessment.dto.questionPreview.QuestionPre;
import co.edu.colomboamericano.caelassessment.dto.questionPreview.QuestionPreview;
import co.edu.colomboamericano.caelassessment.entity.Assessment;
import co.edu.colomboamericano.caelassessment.entity.AssessmentStatus;
import co.edu.colomboamericano.caelassessment.mapper.AssessmentMapper;
import co.edu.colomboamericano.caelassessment.repository.AssessmentRepository;
import co.edu.colomboamericano.caelassessment.repository.AssessmentRepositoryCustom;
import co.edu.colomboamericano.caelassessment.service.AssessmentService;
import co.edu.colomboamericano.caelassessment.service.AssessmentStatusService;
import co.edu.colomboamericano.caelassessment.service.MailService;
import co.edu.colomboamericano.caelassessment.service.ProspectiveService;
import co.edu.colomboamericano.caelassessment.service.QuestionUtilService;
import co.edu.colomboamericano.caelassessment.utils.AssessmentWordPressHelper;
import co.edu.colomboamericano.caelassessment.utils.ProgramsAgeRangesUtils;

@Service
@Scope("singleton")
public class AssessmentServiceImp implements AssessmentService
{
	@Autowired
	private AssessmentRepository assessmentRepositrory;
	
	@Autowired
	private AssessmentRepositoryCustom assessmentRepositoryCustom;
	
	@Autowired
	private AssessmentMapper assessmentMapper;
	
	@Autowired
	private AssessmentRepository assessmentRepository;
	
	@Autowired
	private AssessmentWordPressHelper assessmentWordPressHelper;
	
	@Autowired
	private ProgramsAgeRangesUtils programsAgeRangesUtils;

	@Autowired
	private QuestionUtilService questionUtilService  ;
	
	@Autowired
	private ProspectiveService prospectiveService;
	
	@Autowired
	private AssessmentStatusService assessmentStatusService;
	
	@Autowired
	private MailService mailService;
	
	private Gson gson = new Gson();

	/**
	 * @author Smarthink
	 * @param Numero documento 'documentNumber', tipo del documento 'documentType', nivel 'level', programa 'program', sede 'headquarter', fecha nacimiento 'birthdate'.
	 * @return Assessment created.
	 * @throws If the person is not in the prospective table or is already registered in the assessment table.
	 */
	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public AssessmentDto createAssessment( Integer documentType, Long documentNumber, Date birthdate, String level, String program, String headquarter ) throws Exception
	{
		ProspectiveDto prospective = prospectiveService.findByDocumentNumber( documentNumber );
		if( prospective == null ) throw new Exception( "La persona con numero de documento " + documentNumber + " no se encuentra registrado en la tabla prospectives" );

		Optional<Assessment> isProspectiveInAssessment = findByProspectiveId( prospective.getId() );
		if( isProspectiveInAssessment.isPresent() ) throw new Exception( "La persona ya esta registrada" );

		String assessmentWp = assessmentWordPressHelper.getAssessment( DateFormatUtils.format( birthdate , "yyyy-MM-dd"), level );

        Date date = new Date();
		AssessmentDto assessment = new AssessmentDto();
		assessment.setCourse( program );
		assessment.setAssessments( assessmentWp );
		assessment.setQuestionsStepper( "" );
		assessment.setRemainingTime( 0 );
		assessment.setCreateAt( date );
		assessment.setUpdateAt( date );
		assessment.setProgram( program );
		assessment.setHeadquarter( headquarter );
		assessment.setAssessmentStatusId( 1 );
		assessment.setProspectiveId( prospective.getId() );
		
		Assessment newAssessment = assessmentMapper.assessmentDtoToAssessment( assessment );
		assessmentRepository.save( newAssessment );

		return assessment;
	};
	
	/**
	 * @param Numero documento 'documentNumber', tipo del documento 'documentType', estado del assessment 'ssessmentStatus'.
	 * @return Consulta si un usuario o estudiante ya realizo la nivelacion.
	 * @throws Exception cuando el usuario ya realizo la nivelacion.
	 */
	@Override
	@Transactional( readOnly = true )
	public Integer getAssessmentBy( String documentNumber, String documentType ) throws Exception
	{
//		Integer isAssessment = 0;
//		
//		isAssessment = assessmentRepositrory.findByDocumentNumberAndDocumentTypeAndAssessmentStatus(documentNumber, documentType, "1");
//		
//		if( isAssessment == 0 ) {
//			isAssessment = assessmentRepositrory.findByDocumentNumberAndDocumentTypeAndAssessmentStatus(documentNumber, documentType, "2");
//		};
//
//		return isAssessment;
		return 0;
	}
	
	@Override
	public Optional<Assessment> findByProspectiveId(Integer prospectiveId) {
		return assessmentRepositrory.findByProspectiveId(prospectiveId);
	}

//	@Override
//	public List<Assessment> findAll() {
//		return assessmentRepositrory.findAllAssessment();
//	}

	@Override
	public Optional<Assessment> findById(Integer id) {
		return assessmentRepositrory.findById( id );
	};
	
	/**
	 * @author Smarthink
	 * @param The url sends the attributes that refer to the field to be updated, with the id to find the entity and as the body of the request assessmentsObject that is the data to be updated in the db.
	 * @return Updated entity
	 * @throws If not found the entity
	 */
	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public AssessmentDto updateAssessmentFieldAssessments( Integer id, String assessmentsObject ) throws Exception
	{
		Optional<Assessment> assessmentEntity = findById( id );
		
		if( !assessmentEntity.isPresent() ) throw new Exception("No se encontro la la entidad del assessment para actualizar");
		
		assessmentEntity.get().setAssessments( assessmentsObject );
		assessmentRepository.save( assessmentEntity.get() );
		AssessmentDto assessmentDto = assessmentMapper.assessmentToAssessmentDto( assessmentEntity.get() );

		return assessmentDto;
	};

	@Override
	public Assessment update( Assessment entity ) throws Exception
	{
		return null;		// TODO Auto-generated method stub
	}

	@Override
	public void delete(Assessment entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AssessmentGetDto generateDtoAssessmentByStatusAndProspective(Long prospectiveId,Integer assessmentStatusId) throws Exception {	
		AssessmentGetDto assessment = new AssessmentGetDto();
		List<Object> resultGetAssessment = assessmentRepositoryCustom.getAssesments(prospectiveId, assessmentStatusId);
		if (resultGetAssessment.size() <= 0 || resultGetAssessment.get(0) == null) {
			return null;
		}
		 for (Iterator iterator = resultGetAssessment.iterator(); iterator.hasNext();) {
			
			 Object[] object = (Object[]) iterator.next();
			 assessment.setId(Long.parseLong(String.valueOf(object[0])));
			 assessment.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(object[1])));
			 assessment.setUpdateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(object[2])));
			 assessment.setCourse(String.valueOf(object[3]));
			 assessment.setRemainingTime(Integer.parseInt(String.valueOf(object[4])));
			 assessment.setProgram(String.valueOf(object[5]));
			 assessment.setHeadquarter(String.valueOf(object[6]));
			 
			 AssessmentStatus assessmentStatus = new AssessmentStatus();
			 assessmentStatus.setId(Integer.parseInt(String.valueOf(object[7])));
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");
			 assessmentStatus.setCreatedAt(LocalDateTime.parse(String.valueOf(object[8]), formatter));
			 assessmentStatus.setUpdatedAt(LocalDateTime.parse(String.valueOf(object[9]), formatter));
			 assessmentStatus.setName(String.valueOf(object[10]));
			 assessmentStatus.setKey(String.valueOf(object[11]));
			 assessment.setAssessmentStatus(assessmentStatus);
			 
			 ProspectiveGetDto prospective = new ProspectiveGetDto();
			 prospective.setProspectiveStatus(Integer.parseInt(String.valueOf(object[12])));
			 prospective.setId(Long.parseLong(String.valueOf(object[13])));
			 prospective.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(object[14])));
			 prospective.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(object[15])));
			
			 prospective.setFirstName(String.valueOf(object[16]));
			 prospective.setSecondName(String.valueOf(object[17]));
			 prospective.setSurname(String.valueOf(object[18]));
			 prospective.setSecondSurname(String.valueOf(object[19]));;
			 prospective.setDocumentType(Integer.parseInt(String.valueOf(object[20])));
			 prospective.setDocumentNumber(Long.parseLong(String.valueOf(object[21])));
			 prospective.setBirthdate(LocalDate.parse(String.valueOf(object[22])));
			 prospective.setEmail(String.valueOf(object[23]));
			 prospective.setCellphone(String.valueOf(object[24]));
			 prospective.setSchoolGrade(Integer.parseInt(String.valueOf(object[25])));
			 prospective.setTermsConditions(Boolean.parseBoolean(String.valueOf(object[25])));
			 assessment.setProspective(prospective);
		 }
		 	return assessment;
	}

	
	/*
	 * transformar la columna assements que es un json a una clase y igualmente para
	 * un QuestionStepper
	 */
	
	@Override
	public Object transformAssessmentsAndQuestionStepper(Integer id) throws Exception {
		List<Object> resultQuery = assessmentRepositoryCustom.getAssementAndQuestionsStepper(id);
		if (resultQuery.size() <= 0 || resultQuery.get(0) == null) {
			return null;
		}
		
		List<Root> root = new ArrayList<Root>();
		Type collectionType  =  new TypeToken<List<Root>>() {}.getType();
		QuestionStepper questionStepper = new QuestionStepper();
		Integer idAssessment = 0;
		 for (Iterator iterator = resultQuery.iterator(); iterator.hasNext();) {
			 Object[] object = (Object[]) iterator.next();
				
				root = gson.fromJson(String.valueOf(object[0]), collectionType);
				questionStepper = gson.fromJson(String.valueOf(object[1]), QuestionStepper.class);
				idAssessment = Integer.parseInt(String.valueOf(object[2]));
		}
		 Object resultGenerate = generateCurrentCuestion(root,questionStepper,idAssessment);
		 return resultGenerate;
	}
	
	
	/*
	 * generar un CurrentQuestion basado en el questionStepper
	 */
	public Object generateCurrentCuestion(List<Root> roots,QuestionStepper questionStepper,Integer idAssessment ) {
		Root root = getLastIndexAssessments(roots);
		CurrentQuestion currentQuestion = new CurrentQuestion();
		Question question = new Question();
		currentQuestion.setAssessmentId(idAssessment);
		currentQuestion.setQuestionGroupId(root.getQuestionsGroup().get(questionStepper.getQuestionGroupIndex()).getQuestionGroupId());
		currentQuestion.setTitle(root.getQuestionsGroup().get(questionStepper.getQuestionGroupIndex()).getTitle());
		currentQuestion.setQuestionTypeId(root.getQuestionsGroup().get(questionStepper.getQuestionGroupIndex())
				.getQuestionTypes().get(questionStepper.getQuestionTypeIndex()).getID());
		currentQuestion.setTypeName(root.getQuestionsGroup().get(questionStepper.getQuestionGroupIndex())
				.getQuestionTypes().get(questionStepper.getQuestionTypeIndex()).getTypeName());
		currentQuestion.setMultimediaFile(root.getQuestionsGroup().get(questionStepper.getQuestionGroupIndex())
				.getQuestionTypes().get(questionStepper.getQuestionTypeIndex()).getMultimediaFile());
		currentQuestion.setQuestionId(root.getQuestionsGroup().get(questionStepper.getQuestionGroupIndex())
				.getQuestionTypes().get(questionStepper.getQuestionTypeIndex()).getQuestions()
				.get(questionStepper.getQuestionIndex()).getID());
		question = root.getQuestionsGroup().get(questionStepper.getQuestionGroupIndex())
		.getQuestionTypes().get(questionStepper.getQuestionTypeIndex()).getQuestions()
		.get(questionStepper.getQuestionIndex());
		Object resultTrasnformQuestion = questionUtilService.transformQuestion(currentQuestion.getTypeName(),question);
		currentQuestion.setQuestion(resultTrasnformQuestion);

		return currentQuestion;
	}
	
	/*
	 * obtener la ultima pocision del array de la columna assessments 
	 * 
	 */
	public Root getLastIndexAssessments(List<Root> roots) {
		int lastIndex = roots.size() - 1;
		Root root =  roots.get(lastIndex);
		return root;
	}
	

	@Override
	public Assessment save( Assessment entity ) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAssessmentQuestion(Integer id) throws Exception {
		Object result = transformAssessmentsAndQuestionStepper(id);
		return result;
	}
	
	/**
     * @author Smarthink
	 * @param Assessment entity, name of course, name of emailImage, boolean state of isLastLevel
	 * @return a message indicating whether or not the message was sent
	 * @throws Exception If any of the parameters is null
	 */
	public String finishAssessment( Assessment assessment, String course, String emailImage, Boolean isLastLevel ) throws Exception
	{
		if( assessment == null ) throw new Exception("El assessment no puede ser nulo en finishAssessment");
		
		if( course == null || course.isEmpty() ) throw new Exception("El course no puede ser nulo en finishAssessment");
		
		if( emailImage == null || emailImage.isEmpty() ) throw new Exception("El emailImage no puede ser nulo en finishAssessment");
		
		if( isLastLevel == null ) throw new Exception("El isLastLevel no puede ser nulo en finishAssessment");
		
		LocalDate prospectiveBirthday = assessment.getProspective().getBirthdate();
		
		 if ( assessment.getProgram().equals("TEENSKIDS") && course.equals("Advanced 1") )
		 { 
				Object courseTeensKids = programsAgeRangesUtils.getCourseTeensKids( prospectiveBirthday, course );
				
				assessment.setCourse( String.valueOf( courseTeensKids ) );
				assessment.setAssessmentStatus( assessmentStatusService.findById( 2 ).get()  );
				assessmentRepository.save( assessment  );
				
				EmailLevelingDto emailData = new EmailLevelingDto();
				emailData.setProspective(null);
				emailData.setCourse(course);
				emailData.setEmailImage(emailImage);
				emailData.setIsLastLevel(isLastLevel);
				
				return mailService.sendLevelingNotification( emailData );

		 }  else {
				assessment.setCourse( String.valueOf( course ) );
				assessment.setAssessmentStatus( assessmentStatusService.findById( 2 ).get()  );
				assessmentRepository.save( assessment  );
				 
				EmailLevelingDto emailData = new EmailLevelingDto();
				emailData.setProspective(null);
				emailData.setCourse(course);
				emailData.setEmailImage(emailImage);
				emailData.setIsLastLevel(isLastLevel);
				
				return mailService.sendLevelingNotification( emailData );
		 }
	};

	@Override
	public Object buildQuestionPreview(Integer groupQuestionsId, Integer groupTypeId, Integer questionId)
			throws Exception {
		
		QuestionPreview questionPreview = assessmentWordPressHelper.getAssessmentPreview(groupQuestionsId,groupTypeId,questionId);
		CurrentQuestion currentQuestion = new CurrentQuestion();
		
		currentQuestion.setQuestionGroupId(questionPreview.getData().getQuestionsGroup().get(0).getQuestionGroupId());
		currentQuestion.setQuestionTypeId(Integer.parseInt(questionPreview.getData().getQuestionsGroup().get(0).getQuestionTypes().get(0).getID()));
		currentQuestion.setQuestionId(Integer.parseInt(questionPreview.getData().getQuestionsGroup().get(0).getQuestionTypes().get(0).getQuestions().get(0).getID()));
		currentQuestion.setTitle(questionPreview.getData().getQuestionsGroup().get(0).getTitle());
		currentQuestion.setTypeName(questionPreview.getData().getQuestionsGroup().get(0).getQuestionTypes().get(0).getTypeName());
		currentQuestion.setMultimediaFile(questionPreview.getData().getQuestionsGroup().get(0).getQuestionTypes().get(0).getMultimediaFile());
		QuestionPre question = questionPreview.getData().getQuestionsGroup().get(0).getQuestionTypes().get(0).getQuestions().get(0);
		Object result = questionUtilService.transformQuestion(currentQuestion.getTypeName(),question);
		currentQuestion.setQuestion(result);
		return currentQuestion;
	}

}
