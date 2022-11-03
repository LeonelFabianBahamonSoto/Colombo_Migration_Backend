package co.edu.colomboamericano.caelassessment.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProspectiveSofiHelper
{
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${url.SOFI_GATEWAY_API}")
	private String sofiGatewayService;
	
	/**
	 * @author Smarthink
	 * @param  Customer document number
	 * @return  Status code 200 if student exist, otherwise returns a string with a message "Student not found".
	 * @throws Status codes other than 200
	 */
	public String consultStudentSofi( Long documentNumber )
	{
		try {
			ResponseEntity<Object> registerProspective = restTemplate.getForEntity( sofiGatewayService + "/sofi-students?documentNumber=" + documentNumber, Object.class );
			return registerProspective.getStatusCode().toString();
		} catch (Exception e) {
			return "Student not found";
		}
	};
}
