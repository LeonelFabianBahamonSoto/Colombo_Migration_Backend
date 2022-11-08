package co.edu.colomboamericano.caelassessment.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.colomboamericano.caelassessment.dto.FindByFilterDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveByDateRangeStateFilterDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveToSaveDto;
import co.edu.colomboamericano.caelassessment.entity.Prospective;
import co.edu.colomboamericano.caelassessment.entity.ProspectiveStatus;
import co.edu.colomboamericano.caelassessment.mapper.ProspectiveMapper;
import co.edu.colomboamericano.caelassessment.repository.ProspectiveRepository;
import co.edu.colomboamericano.caelassessment.repository.ProspectiveRepositoryCustom;
import co.edu.colomboamericano.caelassessment.service.ProspectiveService;
import co.edu.colomboamericano.caelassessment.utils.ProspectiveSofiHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("singleton")
public class ProspectiveServiceImp implements ProspectiveService
{
	@Autowired
	private ProspectiveRepository prospectiveRepository;
	
	@Autowired
	private ProspectiveMapper prospectiveMapper;
	
	@Autowired
	private ProspectiveRepositoryCustom prospectiveRepositoryCustom;
	
	@Autowired
	private ProspectiveSofiHelper prospectiveSofiHelper;

	/**
	 * @author Smarthink
	 * @param ProspectiveToSaveDto Entity
	 * @return New Prospective
	 * @throws Exception if the person's document number is already registered
	 */
	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Prospective createProspective( ProspectiveToSaveDto prospectiveToSave ) throws Exception
	{
		ProspectiveDto isProspective = findByDocumentNumber( prospectiveToSave.getDocumentNumber() );

		if( isProspective != null ) {
			throw new Exception("EL usuario ya se encuentra registrado");
		};

		ProspectiveDto prospective = new ProspectiveDto();
		Date currentDate = new Date();

		prospective.setFirstName( prospectiveToSave.getFirstName() );
		prospective.setSecondName( prospectiveToSave.getSecondName() );
		prospective.setSurname( prospectiveToSave.getSurname() );
		prospective.setSecondSurname( prospectiveToSave.getSecondSurname() );
		prospective.setDocumentNumber( prospectiveToSave.getDocumentNumber() );
		prospective.setDocumentType( prospectiveToSave.getDocumentType() );
		prospective.setBirthdate( prospectiveToSave.getBirthdate() );
		prospective.setEmail( prospectiveToSave.getEmail() );
		prospective.setCellphone( prospectiveToSave.getCellphone() );
		prospective.setSchoolGrade( 0 );		
		prospective.setTermsConditions( 1 );
		prospective.setProspectiveStatusId( 1 );
		prospective.setCreatedAt( currentDate );
		prospective.setUpdatedAt( currentDate );

		Prospective newProspective = prospectiveMapper.prospectiveDtoToProspective(prospective);

		return prospectiveRepository.save( newProspective );
	}
	
	/**
	 * @author Smarthink
	 * @param Customer document number 
	 * @return prospectiveDto from customer
	 * @throws Exception if prospective doesnt exist
	 */
	@Override
	@Transactional( readOnly = true )
	public ProspectiveDto findByDocumentNumber( Long documentNumber ) throws Exception
	{		
		Optional<Prospective> prospective = prospectiveRepository.findByDocumentNumber( documentNumber );
		
		if(!prospective.isPresent()) {
			log.warn("No fue posible encontrar el el cliente por el numero de documento");
			return null;
		};
		
		ProspectiveDto prospectiveDto = prospectiveMapper.prospectiveToProspectiveDto( prospective.get() );

		return prospectiveDto;
	}
	
	/**
	 * @author Smarthink
	 * @param  Take, Skip and filter.
	 * @return Json with findByFilterDto and numberOfRows of a relationship of the Assessment and Prospective tables.
	 * @throws Exception if prospective doesnt exist
	 */
	@Override
	@Transactional( readOnly = true )
	public Map<String, Object> findByFilter( Integer take, Integer skip, String filter ) throws Exception
	{		
		ArrayList<FindByFilterDto> listOfProspectives = prospectiveRepository.listOfProspectiveAndAssessment( skip, take, filter);
		Integer numberOfRows = prospectiveRepository.numberOfProspectiveAndAssessment(skip, take, filter);
		
		Map<String, Object> response = new HashMap<>();
		response.put("count", String.valueOf( numberOfRows ) );
		response.put("prospectives", listOfProspectives);

		return response;
	}
	
	/**
	 * @author Smarthink
	 * @param State letter, Start and End date for filter in the sql query
	 * @return List of existing students
	 */
	@Override
	@Transactional( readOnly = true )
	public List<ProspectiveByDateRangeStateFilterDto> findByDateRangeFilterAndState( Date startDate, Date endDate, String state ) throws Exception 
	{
		List<ProspectiveByDateRangeStateFilterDto> listOfProspectives = prospectiveRepository.findByDateRangeFilterAndStateQuery( startDate, endDate );
		if( listOfProspectives == null ) throw new Exception("No pue posible obtener el listado en listOfProspectives");
		
		List<ProspectiveByDateRangeStateFilterDto> responseList = new ArrayList<ProspectiveByDateRangeStateFilterDto>();
		
		for( ProspectiveByDateRangeStateFilterDto prospective : listOfProspectives )
		{
			String isStudent = prospectiveSofiHelper.consultStudentSofi( Long.parseLong( prospective.getDocumentNumber() ) );

			if( state.equals("R") && !isStudent.equalsIgnoreCase("Student not found") ) responseList.add(prospective);

			if( state.equals("NR") && isStudent.equalsIgnoreCase("Student not found") ) responseList.add(prospective);

			if( !state.equals("R") && !state.equals("NR") ) responseList.add(prospective);
		};

		return responseList;
	};

	@Override
	public Prospective generateDtoFindDocument(Integer documentType,Long documentNumber) throws Exception {
		List<Object> resultFindDocument =  prospectiveRepositoryCustom.findByDocument(documentType, documentNumber);

		Prospective prospective = new Prospective();
		ProspectiveStatus prospectiveStatus = new ProspectiveStatus();
		 for (Iterator iterator = resultFindDocument.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			if (String.valueOf(object[0]).equals("null")) {
				return null;
			}
			prospective.setId(Integer.parseInt(String.valueOf(object[0])));
			prospective.setFirstName(String.valueOf(object[1]));
			prospective.setSecondName(String.valueOf(object[2]));
			prospective.setSurname(String.valueOf(object[3]));
			prospective.setSecondSurname(String.valueOf(object[4]));
			prospective.setDocumentNumber(Long.parseLong(String.valueOf(object[5])));
			prospective.setBirthdate(LocalDate.parse(String.valueOf(object[6])));
			prospective.setDocumentType(Integer.parseInt(String.valueOf(object[7])));
			prospective.setEmail(String.valueOf(object[8]));
			prospective.setCellphone(String.valueOf(object[9]));
			prospective.setSchoolGrade(Integer.parseInt(String.valueOf(object[10])));
			prospective.setTermsConditions(Integer.parseInt(String.valueOf(object[11])));
			prospective.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(object[12])));
			prospective.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(object[13])));
			prospectiveStatus.setId(Integer.parseInt(String.valueOf(object[14])));
			prospective.setProspectiveStatusId(prospectiveStatus);
		}
		return prospective;
	}

	@Override
	public Prospective save( Prospective entity ) throws Exception {	
		return null;
	}

	@Override
	public Prospective getFindByDocument(Integer documentType, Long documentNumber) throws Exception {
		Prospective prospective = new Prospective();
		prospective = generateDtoFindDocument(documentType,documentNumber);
		return prospective;
	}

	@Override
	public String sendInstructionsInterviewAssessment(String email, String program) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Optional<Prospective> findById(Integer id) {
		return null; // INACTIVE METHOD
	}

	@Override
	public Prospective update(Prospective entity) throws Exception {
		return null; // INACTIVE METHOD
	}

	@Override
	public void delete(Prospective entity) throws Exception {
		// INACTIVE METHOD
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		// INACTIVE METHOD
	}
}