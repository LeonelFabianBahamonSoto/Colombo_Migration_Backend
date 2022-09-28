package co.edu.colomboamericano.caelstudent.service;

public interface MailService
{
	String sendResetPasswordEmail( String toEmail, String resetPasswordToken );
}
