package co.edu.colomboamericano.caelstudent.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
//
//import io.awspring.cloud.ses.SimpleEmailServiceMailSender;

@Configuration
public class BeanConfig
{
    @Value("${cloud.aws.region.static}")
    private String AWS_REGION;
	
    @Value("${cloud.aws.credentials.access-key}")
    private String AWS_ACCESS_KEY;
    
    @Value("${cloud.aws.credentials.secret-key}")
    private String AWS_SECRET_KEY;
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	};
	
	@Bean
	public AmazonSimpleEmailService getAmazonSimpleEmailService()
	{
		return AmazonSimpleEmailServiceClientBuilder.standard()
				.withCredentials(getAwsCredentialProvider())
				.withRegion(AWS_REGION)
				.build();
	}
	  
	private AWSCredentialsProvider getAwsCredentialProvider()
	{
		AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
		return new AWSStaticCredentialsProvider(awsCredentials);
	}
}
