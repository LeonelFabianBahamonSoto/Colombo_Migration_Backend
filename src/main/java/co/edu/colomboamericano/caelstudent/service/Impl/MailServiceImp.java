package co.edu.colomboamericano.caelstudent.service.Impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.google.gson.Gson;

import co.edu.colomboamericano.caelstudent.dto.ForgotPasswordUrlDTO;
import co.edu.colomboamericano.caelstudent.service.MailService;
import co.edu.colomboamericano.caelstudent.service.utils.Constant;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailServiceImp implements MailService
{
    @Value("${spring.mail.host}")
    private String FROM_EMAIL;
    
    @Autowired
    AmazonSimpleEmailService amazonSimpleEmailService;

    /**
     * @author Smarthink
     * @param String email destinatario, String token.
     * @return Envia el correo electronico para el restablecimiento de la contrasenia.
     */
    public String sendResetPasswordEmail( String toEmail, String resetPasswordToken )
    {    
    	try {
    		String summaryData = Constant.SELF_SERVICES_URL + "resetPassword?resetPasswordToken=" + resetPasswordToken;
    		
    		ForgotPasswordUrlDTO forgotPasswordUrlDTO = new ForgotPasswordUrlDTO();
    		forgotPasswordUrlDTO.setUpdateUrl( summaryData );
    		
    		String gsonSummaryData = new Gson().toJson( forgotPasswordUrlDTO );
    		
    		SendTemplatedEmailRequest sendTemplatedEmailRequest = new SendTemplatedEmailRequest();
    		sendTemplatedEmailRequest.setTemplate("forgotPasswordTemplate");
    		sendTemplatedEmailRequest.setSource( FROM_EMAIL );
    	    sendTemplatedEmailRequest.setDestination( new Destination(Arrays.asList(toEmail )));
    	    sendTemplatedEmailRequest.setTemplateData( gsonSummaryData );
    	    
    	    amazonSimpleEmailService.sendTemplatedEmail( sendTemplatedEmailRequest );
    		
    		return "send";

		} catch (Exception e) {
			log.error("Error al enviar el correo electronico al usuario. Error: " + e.getCause() + " - " + e.getMessage());
			return "Error al enviar el email";
		}
    }
}
