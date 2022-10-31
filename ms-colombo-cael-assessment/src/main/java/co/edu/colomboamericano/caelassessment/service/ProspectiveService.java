package co.edu.colomboamericano.caelassessment.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import co.edu.colomboamericano.caelassessment.dto.ProspectiveByDateRangeFilterDto;
import java.util.List;

import co.edu.colomboamericano.caelassessment.dto.ProspectiveDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveToSaveDto;
import co.edu.colomboamericano.caelassessment.entity.Prospective;

public interface ProspectiveService extends GenericService<Prospective, Integer>
{
	Prospective createProspective( ProspectiveToSaveDto prospectiveToSave ) throws Exception;

	ProspectiveDto findByDocumentNumber( Long documentNumber ) throws Exception;
	
	Map<String, Object> findByFilter( Integer skip, Integer take, String filter ) throws Exception;
	
	List<ProspectiveByDateRangeFilterDto> findByDateRangeFilter( Date startDate, Date endDate ) throws Exception;
	String sendInstructionsInterviewAssessment( String email, String program ) throws Exception;
	
	Prospective generateDtoFindDocument(Integer documentType,Long documentNumber)throws Exception;
	
	Prospective getFindByDocument(Integer documentType,Long documentNumber)throws Exception;
} 