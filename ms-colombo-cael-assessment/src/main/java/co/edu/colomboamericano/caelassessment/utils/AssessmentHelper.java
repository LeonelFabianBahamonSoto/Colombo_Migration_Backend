package co.edu.colomboamericano.caelassessment.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import co.edu.colomboamericano.caelassessment.dto.AssessmentInfoDto;
import co.edu.colomboamericano.caelassessment.dto.AssessmentWithinAssessmentsDto;
import co.edu.colomboamericano.caelassessment.dto.AssessmentsData;
import co.edu.colomboamericano.caelassessment.dto.CurrentQuestion;
import co.edu.colomboamericano.caelassessment.dto.MultimediaFile;
import co.edu.colomboamericano.caelassessment.dto.Question;
import co.edu.colomboamericano.caelassessment.dto.QuestionStepper;
import co.edu.colomboamericano.caelassessment.dto.QuestionType;
import co.edu.colomboamericano.caelassessment.dto.QuestionsGroup;
import co.edu.colomboamericano.caelassessment.entity.Assessment;
import co.edu.colomboamericano.caelassessment.service.QuestionUtilService;

@Service
public class AssessmentHelper
{
	@Autowired
	ProgramsAgeRangesUtils programsAgeRangesUtils;

	@Autowired
	private QuestionUtilService questionUtilService;

	public AssessmentInfoDto getAssessmentConfigInfo( AssessmentsData root, Assessment assessment )
	{
		AssessmentInfoDto assessmentInfo = new AssessmentInfoDto();

		assessmentInfo.setId( root.getAssessment().getAssessmentID() );
		assessmentInfo.setCourse( root.getAssessment().getCourseName() );
		assessmentInfo.setProspectiveId( assessment.getProspective().getId() );
		assessmentInfo.setIsLastLevel( false );
		assessmentInfo.setLevel( root.getAssessment().getLevel() );
		assessmentInfo.setTotalScore( root.getAssessment().getTotalScore() );
		assessmentInfo.setApprovalScore( root.getAssessment().getApprovalScore() );
		
		return assessmentInfo;
	};
	
	public List< Object > startNewAssessment( Assessment assessment, String createAssessmentConfig ) throws Exception
	{
		AssessmentsData newAssessmentConfig = initAssessmentConfig( createAssessmentConfig );

		List< AssessmentsData > assessmentConfigList = new ArrayList< AssessmentsData >();
		Type collectionType  =  new TypeToken<List< AssessmentsData >>() {}.getType();
		assessmentConfigList = new Gson().fromJson( assessment.getAssessments(), collectionType );

		assessmentConfigList.add( newAssessmentConfig );

		List< Object > organizedAssessment = new ArrayList<>();
		for( AssessmentsData assessmentRoot : assessmentConfigList )
		{
			AssessmentsData newAssessments = new AssessmentsData();

			newAssessments.setScore( assessmentRoot.getScore() );
			newAssessments.setLostScore( assessmentRoot.getLostScore() );
			newAssessments.setAssessment( assessmentRoot.getAssessment() );
			newAssessments.setQuestionsGroup( assessmentRoot.getQuestionsGroup() );
			newAssessments.setApproved( assessmentRoot.getApproved() );

			organizedAssessment.add( new Gson().toJson( newAssessments ) );
		};

		return organizedAssessment;
	};

	public AssessmentsData initAssessmentConfig( String createAssessmentConfig ) throws Exception
	{
		JSONObject jsonAssessmentConfig = new JSONObject( createAssessmentConfig );

		AssessmentsData newAssessmentConfig = new AssessmentsData();
		
		newAssessmentConfig.setScore( 0 );
		newAssessmentConfig.setLostScore( 0 );

		AssessmentWithinAssessmentsDto assessment = new Gson().fromJson( String.valueOf( jsonAssessmentConfig.getJSONObject("assessment") ), AssessmentWithinAssessmentsDto.class );
		newAssessmentConfig.setAssessment( assessment );

    	Type questionsGroupType = new TypeToken<ArrayList<QuestionsGroup>>(){}.getType();
    	ArrayList<QuestionsGroup> questionsGroup =  new Gson().fromJson( String.valueOf( jsonAssessmentConfig.getJSONArray("questionsGroup") ), questionsGroupType );
    	newAssessmentConfig.setQuestionsGroup( questionsGroup );

    	newAssessmentConfig.setApproved( false );

		return newAssessmentConfig;
	};
	
	public CurrentQuestion getCurrentQuestion( Assessment assessment ) throws Exception
	{
		//QUESTION STEPPER
		QuestionStepper questionStepper = new Gson().fromJson( assessment.getQuestionsStepper(), QuestionStepper.class );
		Integer questionGroupIndex = questionStepper.getQuestionGroupIndex();
		Integer questionTypeIndex = questionStepper.getQuestionTypeIndex();
		Integer questionIndex = questionStepper.getQuestionIndex();

		//OBTIENE EL ULTIMO ELEMENTO DE ASSESSMENTS
		List< AssessmentsData > assessmentConfigList = new ArrayList< AssessmentsData >();
		Type collectionType  =  new TypeToken<List< AssessmentsData >>() {}.getType();
		assessmentConfigList = new Gson().fromJson( assessment.getAssessments(), collectionType );
		AssessmentsData assessmentConfig = assessmentConfigList.get( assessmentConfigList.size() -1 );

		//OBTIENE UN ELEMENTO COMPLETO DEL QUESTIONGROUP CONSULTANDO POR EL questionGroupIndex
		ArrayList<QuestionsGroup> questionsGroup = assessmentConfig.getQuestionsGroup();
		QuestionsGroup selectQuestionGroup = questionsGroup.get( questionGroupIndex );
		String title = selectQuestionGroup.getTitle();
		Integer questionGroupId = selectQuestionGroup.getQuestionGroupId();

		//DESESTRUCTURA EL QUESTIONTYPES DEL ELEMENTO ANTERIOR
		QuestionType questionTypes = selectQuestionGroup.getQuestionTypes().get( questionTypeIndex );
		String typeName = questionTypes.getTypeName();
		Integer questionTypeId = questionTypes.getID();
		MultimediaFile multimediaFile = questionTypes.getMultimediaFile();

		//DESESTRUCTURA EL QUESTIONS DEL ELEMENTO questionTypes
		Question question = questionTypes.getQuestions().get( questionIndex );

		//OBTIENE EL ID DEL QUESTION DE question
		Integer questionId = question.getID();

		//OBTIENE EL ID DEL ASSESSMENT ???????????
		Integer assessmentId = assessment.getId();

		CurrentQuestion currentCuestion = new CurrentQuestion();
		currentCuestion.setAssessmentId( assessmentId );
		currentCuestion.setQuestionGroupId( questionGroupId );
		currentCuestion.setQuestionTypeId( questionTypeId );
		currentCuestion.setQuestionId( questionId );
		currentCuestion.setTitle( title );
		currentCuestion.setTypeName( typeName );
		currentCuestion.setMultimediaFile( multimediaFile );

		Object questionTransform = questionUtilService.transformQuestion( typeName, question );
		currentCuestion.setQuestion( questionTransform );

		return currentCuestion;
	};

}