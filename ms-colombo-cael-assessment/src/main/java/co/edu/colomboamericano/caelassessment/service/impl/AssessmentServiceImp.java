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
	
	@Override
	public Assessment save(Assessment entity) throws Exception {
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
		Integer isAssessment = 0;
		
		isAssessment = assessmentRepositrory.findByDocumentNumberAndDocumentTypeAndAssessmentStatus(documentNumber, documentType, "1");
		
		if( isAssessment == 0 ) {
			isAssessment = assessmentRepositrory.findByDocumentNumberAndDocumentTypeAndAssessmentStatus(documentNumber, documentType, "2");
		};

		return isAssessment;
	}

	@Override
	public List<Assessment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Assessment> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
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
