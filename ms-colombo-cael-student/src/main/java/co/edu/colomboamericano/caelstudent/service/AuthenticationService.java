package co.edu.colomboamericano.caelstudent.service;

import co.edu.colomboamericano.caelstudent.dto.AuthenticationTokenDTO;
import co.edu.colomboamericano.caelstudent.entity.Student;

public interface AuthenticationService
{
	AuthenticationTokenDTO signInAndReturnJWT(Student signInRequest);
}
