package co.edu.colomboamericano.caelassessment.service;

import org.springframework.stereotype.Service;

import co.edu.colomboamericano.caelassessment.dto.EmailLevelingDto;

@Service
public interface MailService
{
	String sendInstructionsInterviewAssessment( String email, String program );

	String sendLevelingNotification( EmailLevelingDto emailData );
}
