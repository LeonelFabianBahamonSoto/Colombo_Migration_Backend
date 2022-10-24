package co.edu.colomboamericano.caelassessment.service;

import java.util.Optional;

import co.edu.colomboamericano.caelassessment.entity.Prospective;

public interface ProspectiveService extends GenericService<Prospective, Integer>
{
	Optional<Prospective> findByDocumentNumber( Long documentNumber ) throws Exception;
} 