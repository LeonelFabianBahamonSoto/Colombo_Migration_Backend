package co.edu.colomboamericano.caelassessment.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import co.edu.colomboamericano.caelassessment.entity.AssessmentStatus;
import co.edu.colomboamericano.caelassessment.repository.AssessmentStatusRepository;
import co.edu.colomboamericano.caelassessment.service.AssessmentStatusService;

@Service
@Scope("singleton")
public class AssessmentStatusServiceImp implements AssessmentStatusService
{
	@Autowired
	private AssessmentStatusRepository assessmentStatusRepository;

	@Override
	public Optional<AssessmentStatus> findById(Integer id) {
		return assessmentStatusRepository.findById( id );
	};

	@Override
	public AssessmentStatus save(AssessmentStatus entity) throws Exception {
		return null; // INACTIVE METHOD
	};

	@Override
	public AssessmentStatus update(AssessmentStatus entity) throws Exception {
		return null; // INACTIVE METHOD
	};

	@Override
	public void delete(AssessmentStatus entity) throws Exception {
		// INACTIVE METHOD
	};

	@Override
	public void deleteById(Integer id) throws Exception {
		// INACTIVE METHOD
	}

}
