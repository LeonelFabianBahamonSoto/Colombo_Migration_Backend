package co.edu.colomboamericano.caelstudent.service;

import java.util.Optional;

import co.edu.colomboamericano.caelstudent.entity.ResetPassword;

public interface ResetPasswordService extends GenericService<ResetPassword, String>
{
	Optional<ResetPassword> findByEmail( String email );
}
