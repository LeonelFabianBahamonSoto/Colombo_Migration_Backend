package co.edu.colomboamericano.caelassessment.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.google.gson.Gson;

import co.edu.colomboamericano.caelassessment.dto.EmailLevelingDto;
import co.edu.colomboamericano.caelassessment.dto.ParamsEmailDto;
import co.edu.colomboamericano.caelassessment.dto.SummaryDataEmailLevelingDto;
import co.edu.colomboamericano.caelassessment.service.MailService;
import co.edu.colomboamericano.caelassessment.utils.Constant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImp implements MailService
{
    @Autowired
    AmazonSimpleEmailService amazonSimpleEmailService;
	
    @Value("${spring.mail.host}")
    private String SOURCE_EMAIL;
	
    /**
     * @author Smarthink
     * @param String email, String program (Adult or Kids).
     * @return Send an email using aws.
     */
	@Override
	public String sendInstructionsInterviewAssessment( String email, String program )
	{
    	try {
    		String template = null, params = null;
    		
    		if( program.equals("KIDS") ) template = Constant.AWS_SES_ASSESSMENT_INTERVIEW_INSTRUCTIONS_KIDS_TEMPLATE;

    		if( program.equals("YOUNG") ) template = Constant.AWS_SES_ASSESSMENT_INTERVIEW_INSTRUCTIONS_YOUNGS_TEMPLATE;
    		
    		ParamsEmailDto paramsEmailDto = new ParamsEmailDto();
    		paramsEmailDto.setDestination( new Gson().toJson( email ) );
    		paramsEmailDto.setTemplate( template );
    		paramsEmailDto.setTemplateData("{}");
    		paramsEmailDto.setSource( SOURCE_EMAIL );
    		
    		params = new Gson().toJson( paramsEmailDto );

    		SendTemplatedEmailRequest sendTemplatedEmailRequest = new SendTemplatedEmailRequest();
    		sendTemplatedEmailRequest.setTemplate( template );
    		sendTemplatedEmailRequest.setSource( SOURCE_EMAIL );
    	    sendTemplatedEmailRequest.setDestination( new Destination( Arrays.asList( email ) ));
    	    sendTemplatedEmailRequest.setTemplateData( params );
    	    
    	    amazonSimpleEmailService.sendTemplatedEmail( sendTemplatedEmailRequest );
    		
    		return "Email sent";

		} catch (Exception e) {
			log.error("Error al enviar el correo electronico al usuario desde sendInstructionsInterviewAssessment. Error: " + e.getCause() + " - " + e.getMessage());
			return "Email not sent";
		}
	};

	/**
     * @author Smarthink
	 * @param EmailLevelingDto entity
	 * @return a message indicating whether or not the message was sent
	 * @throws If the email couldn't be sent
	 */
	@Override
	public String sendLevelingNotification( EmailLevelingDto emailData )
	{
    	try {
    		if( emailData.getProspective() == null || emailData.getCourse() == null || emailData.getEmailImage() == null || emailData.getIsLastLevel() == null )
    			return "El email no pudo ser enviado debido que los datos proporcionados no son correctos en sendLevelingNotification";
    		
    		SummaryDataEmailLevelingDto summaryData = new SummaryDataEmailLevelingDto();
    		summaryData.setProspective(
    				emailData.getProspective().getFirstName() + " " + emailData.getProspective() .getSecondName() + " " + emailData.getProspective().getSurname() + " " + emailData.getProspective().getSecondSurname()
    		);
    		summaryData.setEmailImage( emailData.getEmailImage() );
    		summaryData.setIsLastLevel( emailData.getIsLastLevel() );
    		summaryData.setCourse( emailData.getCourse() );
    		summaryData.setLink( Constant.SELF_SERVICES_URL + Constant.SELF_SERVICES_REGISTER_PAGE_URL + "/?prospective=" + emailData.getProspective().getId() );

    		String params = new Gson().toJson( summaryData );

    		SendTemplatedEmailRequest sendTemplatedEmailRequest = new SendTemplatedEmailRequest();
    		sendTemplatedEmailRequest.setTemplate( Constant.AWS_SES_SUMMARY_ASSESSMENT_TEMPLATE );
    		sendTemplatedEmailRequest.setSource( SOURCE_EMAIL );
    	    sendTemplatedEmailRequest.setDestination( new Destination( Arrays.asList( emailData.getProspective().getEmail() ) ));
    	    sendTemplatedEmailRequest.setTemplateData( params );

    	    amazonSimpleEmailService.sendTemplatedEmail( sendTemplatedEmailRequest );

    		return "Email sent";

		} catch (Exception e) {
			log.error("Error al enviar el correo electronico al usuario desde sendLevelingNotification. Error: " + e.getCause() + " - " + e.getMessage());
			return "Email not sent";
		}
	};

}
