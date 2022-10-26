package co.edu.colomboamericano.caelassessment.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService
{
	String sendInstructionsInterviewAssessment( String email, String program );
}
