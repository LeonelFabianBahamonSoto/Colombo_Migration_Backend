package co.edu.colomboamericano.caelassessment.utils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AssessmentWordPressHelper
{
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${url.Wordpress}")
	private String wordPressService;

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
}