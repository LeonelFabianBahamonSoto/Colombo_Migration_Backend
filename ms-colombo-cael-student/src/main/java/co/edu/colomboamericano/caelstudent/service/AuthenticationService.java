package co.edu.colomboamericano.caelstudent.service;

import co.edu.colomboamericano.caelstudent.entity.Student;

public interface AuthenticationService
{
	Student signInAndReturnJWT(Student signInRequest);
}
