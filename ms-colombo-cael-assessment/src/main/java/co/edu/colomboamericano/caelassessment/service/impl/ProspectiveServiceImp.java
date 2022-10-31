package co.edu.colomboamericano.caelassessment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import co.edu.colomboamericano.caelassessment.dto.FindByFilterDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveByDateRangeFilterDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveDto;
import co.edu.colomboamericano.caelassessment.dto.ProspectiveToSaveDto;
import co.edu.colomboamericano.caelassessment.entity.Prospective;
import co.edu.colomboamericano.caelassessment.mapper.ProspectiveMapper;
import co.edu.colomboamericano.caelassessment.repository.ProspectiveRepository;
import co.edu.colomboamericano.caelassessment.service.ProspectiveService;
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
	private RestTemplate restTemplate;
	
	@Value("${url.SOFI_GATEWAY_API}")
	private String sofiGatewayService;

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
		
		if( prospective.isEmpty() ) {
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

	@Override
	@Transactional( readOnly = true )
	public List<ProspectiveByDateRangeFilterDto> findByDateRangeFilter( Date startDate, Date endDate ) throws Exception 
	{
		List<ProspectiveByDateRangeFilterDto> listOfProspectives = prospectiveRepository.findByDateRangeFilterQuery(startDate, endDate);
		List<Object> datos = new ArrayList<>();
		
		for( ProspectiveByDateRangeFilterDto prospective : listOfProspectives )
		{
			ResponseEntity<Object> registerProspective = restTemplate.getForEntity( sofiGatewayService + "/sofi-students?documentNumber=" + prospective.getDocumentNumber(), Object.class );
			
			if(  registerProspective.getStatusCode() == HttpStatus.OK )
			{
				datos.add(listOfProspectives);
			};
			
		};
		
		return listOfProspectives;
	}

	@Override
	public Optional<Prospective> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}


	@Override
	public Prospective update(Prospective entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Prospective entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @author Smarthink
	 * @param new prospective entity
	 * @return new prospective
	 * @throws Exception if prospective couldnt be created.
	 */
	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Prospective save( Prospective entity ) throws Exception {	
		return null;
	}
}