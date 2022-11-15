package co.edu.colomboamericano.caelassessment.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import co.edu.colomboamericano.caelassessment.dto.CurrentQuestion;
import co.edu.colomboamericano.caelassessment.dto.questionPreview.QuestionPre;
import co.edu.colomboamericano.caelassessment.dto.questionPreview.QuestionPreview;
import co.edu.colomboamericano.caelassessment.exception.ModeloNotFoundException;

@Service
public class AssessmentWordPressHelper
{
	@Autowired
	private RestTemplate restTemplate;
	
	private HttpHeaders headers;
	
	@Value("${url.Wordpress}")
	private String wordPressService;
	
	private final String ASSESSMENT = "assessments";
	
	private Gson gson = new Gson();
	
	@PostConstruct
	public void initConstruct() {
		headers = new HttpHeaders();
	}

	/**
	 * @author Smarthink
	 * @param  Date of birth and level of knowledge
	 * @return  Json with the question groups
	 * @throws if it's not possible to connect with WordPress
	 */
	public String getAssessment( String birthDate, String knowledgeLevel ) throws Exception
	{
		try {
			ResponseEntity<Object> registerProspective = restTemplate.getForEntity( wordPressService + "assessments/" + birthDate + "/level/" + knowledgeLevel, Object.class );
			
			if( registerProspective == null ) throw new Exception( "No fue posible realizar la consulta al WordPress desde createAssessment" );
			JSONObject response = new JSONObject( registerProspective );
			
			return String.valueOf( response.getJSONObject("body").getJSONObject("data") );
		} catch (Exception e) {
			return "No fue posible consultar al wordpress desde getAssessment. Error" + e.getCause() + " " + e.getMessage();
		}
	};
	
	public Object getAssessmentLevels() {
		try {
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			ResponseEntity<String> response = new RestTemplate().exchange(wordPressService+"levels", HttpMethod.GET,
					entity, String.class);
			Object responseLevels = response.getBody();
			
			return responseLevels;
		} catch (Exception e) {
			throw new ModeloNotFoundException("Error consultado el wordpress");
		}
	
	}
	
	public QuestionPreview getAssessmentPreview(Integer questionGroupId, Integer questionTypeId,Integer questionId) {
		try {
			StringBuilder url =new StringBuilder();
			url.append(wordPressService);
			url.append(ASSESSMENT);
			url.append("/"+questionGroupId);
			url.append("/questionType/");
			url.append(questionTypeId);
			url.append("/question/");
			url.append(questionId);
			
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			ResponseEntity<String> response = new RestTemplate().exchange(url.toString(), HttpMethod.GET,
					entity, String.class);
			String responseAssessmentPreview = response.getBody();
			
			QuestionPreview root = new QuestionPreview();
			root = gson.fromJson(responseAssessmentPreview, QuestionPreview.class);
			return root;
		}catch(Exception e) {
			throw new ModeloNotFoundException("Error consultado el wordpress: "+e.getMessage());
		}
		
	}
	
}