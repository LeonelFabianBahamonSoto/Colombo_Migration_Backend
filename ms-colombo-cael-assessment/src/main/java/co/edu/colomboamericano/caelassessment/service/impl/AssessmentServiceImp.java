package co.edu.colomboamericano.caelassessment.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.colomboamericano.caelassessment.entity.Assessment;
import co.edu.colomboamericano.caelassessment.repository.AssessmentRepository;
import co.edu.colomboamericano.caelassessment.service.AssessmentService;

@Service
@Scope("singleton")
public class AssessmentServiceImp implements AssessmentService
{
	@Autowired
	AssessmentRepository assessmentRepositrory;
	
	/**
	 * @param Numero documento 'documentNumber', tipo del documento 'documentType', nivel 'level',
	 * programa 'program', sede 'headquarter', fecha nacimiento 'birthdate'.
	 * @return Assessment created.
	 * @throws Exception: Si el prospective no existe.
	 */
	@Override
	public Assessment save(Assessment entity) throws Exception
	{
//		ArrayList<String> miJsonArray = new ArrayList<>();

		//Consulta el prospective si no existe lanza exception (Espera a la DB)
		// ???? es el prospective que crea un paso anterior a este EVALUAR!
		
		//Consulta el assessmentConfig ese cuando se crea?????
		
		//CREA EL ASSESSMENT CON EL PROSPECTIVE Y EL ASSESSMENTCONFIG.
		
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param Numero documento 'documentNumber', tipo del documento 'documentType', estado del assessment 'ssessmentStatus'.
	 * @return Consulta si un usuario o estudiante ya realizo la nivelacion.
	 * @throws Exception cuando el usuario ya realizo la nivelacion.
	 */
	@Override
	@Transactional( readOnly = true )
	public Integer getAssessmentBy( String documentNumber, String documentType ) throws Exception
	{
//		Integer isAssessment = 0;
//		
//		isAssessment = assessmentRepositrory.findByDocumentNumberAndDocumentTypeAndAssessmentStatus(documentNumber, documentType, "1");
//		
//		if( isAssessment == 0 ) {
//			isAssessment = assessmentRepositrory.findByDocumentNumberAndDocumentTypeAndAssessmentStatus(documentNumber, documentType, "2");
//		};
//
//		return isAssessment;
		return 0;
	}
	
	@Override
	public Optional<Assessment> findByProspectiveId(Integer prospectiveId) {
		return assessmentRepositrory.findByProspectiveId(prospectiveId);
	}

	@Override
	public List<Assessment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Assessment> findById(Integer id) {
		return assessmentRepositrory.findById(id);
	}	

	@Override
	public Assessment update(Assessment entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Assessment entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
