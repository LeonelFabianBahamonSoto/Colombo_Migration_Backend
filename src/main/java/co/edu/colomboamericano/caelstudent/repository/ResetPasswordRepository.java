package co.edu.colomboamericano.caelstudent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.colomboamericano.caelstudent.entity.ResetPassword;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, String>
{
	Optional<ResetPassword> findByEmail( String email );
};