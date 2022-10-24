package co.edu.colomboamericano.caelassessment.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.colomboamericano.caelassessment.entity.Prospective;
import co.edu.colomboamericano.caelassessment.repository.ProspectiveRepository;
import co.edu.colomboamericano.caelassessment.service.ProspectiveService;

@Service
@Scope("singleton")
public class ProspectiveServiceImp implements ProspectiveService
{
	@Autowired
	private ProspectiveRepository prospectiveRepository;

	/**
	 * @author Smarthink
	 * @param new prospective entity
	 * @return new prospective
	 * @throws Exception if prospective couldnt be created.
	 */
	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Prospective save( Prospective entity ) throws Exception
	{
		Optional<Prospective> isProspective = findByDocumentNumber( entity.getDocumentNumber() );
		
		if( isProspective.isPresent() ) {
			throw new Exception("EL usuario ya se encuentra registrado");
		};
		
		Date currentDate = new Date();
		entity.setCreatedAt( currentDate );
		entity.setUpdatedAt( currentDate );
		
		return prospectiveRepository.save(entity);
	}
	
	/**
	 * @author Smarthink
	 * @param document number 
	 * @return prospective entity
	 * @throws Exception if prospective doesnt exist
	 */
	@Override
	public Optional<Prospective> findByDocumentNumber( Long documentNumber ) throws Exception
	{		
		Optional<Prospective> prospective = prospectiveRepository.findByDocumentNumber(documentNumber);
		
		Date currentDate = new Date();
		System.out.println("FECHA ACTUAL: " + currentDate);
		
		if( prospective.isEmpty() ) {
			throw new Exception("No se encontro el prospective por el numero de documento indicado");
		};
		
		return prospective;
	}

//	@Override
//	public List<Prospective> findTen()
//	{
//		List<Prospective> prospective = prospectiveRepository.findTen();
//		System.out.println("DOCUMEBT NUMBER: " + prospective);
//		return prospective;
//	}

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
}