package co.edu.colomboamericano.caelassessment.service;

import java.util.List;

import co.edu.colomboamericano.caelassessment.dto.ProspectiveDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveToSaveDto;
import co.edu.colomboamericano.caelassessment.entity.Prospective;

public interface ProspectiveService extends GenericService<Prospective, Integer>
{
	Prospective createProspective( ProspectiveToSaveDto prospectiveToSave ) throws Exception;

	ProspectiveDto findByDocumentNumber( Long documentNumber ) throws Exception;
	
	String sendInstructionsInterviewAssessment( String email, String program ) throws Exception;
	
	Prospective generateDtoFindDocument(Integer documentType,Long documentNumber)throws Exception;
	
	Prospective getFindByDocument(Integer documentType,Long documentNumber)throws Exception;
} 