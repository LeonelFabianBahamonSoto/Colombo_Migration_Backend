package co.edu.colomboamericano.caelassessment.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

import co.edu.colomboamericano.caelassessment.service.WordPressService;

@Service
public class WordPressServiceImpl implements WordPressService{
	
	private RestTemplate restTemplate;
	private HttpHeaders headers;
	private Gson gson = new Gson();
	private static final String AUTHORIZATION = "Authorization";
	
    @Value("${url.Wordpress}")
	private static String URL_ASSESSMENT_CONFIG_API;
	
	@PostConstruct
	public void initConstruct() {
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
	}

	@Override
	public Object getAssessmentPreview(Integer questionGroupId, Integer questionTypeId, Integer questionId) {
		this.headers.add(AUTHORIZATION, "Bearer Bearer");
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(URL_ASSESSMENT_CONFIG_API);
		urlBuilder.append(questionGroupId);
		urlBuilder.append("/questionType/");
		urlBuilder.append(questionTypeId);
		urlBuilder.append("/question/");
		urlBuilder.append(questionId);
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = new RestTemplate().exchange(urlBuilder.toString(), HttpMethod.GET,
				entity, String.class);
		Object assessmentQuestionConfig = response.getBody();	
		return assessmentQuestionConfig;
	}

	@Override
	public Object getAssessmentLevels() {
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = new RestTemplate().exchange(URL_ASSESSMENT_CONFIG_API+"levels", HttpMethod.GET,
				entity, String.class);
		Object responseLevels = response.getBody();	
		return responseLevels;
	}
}