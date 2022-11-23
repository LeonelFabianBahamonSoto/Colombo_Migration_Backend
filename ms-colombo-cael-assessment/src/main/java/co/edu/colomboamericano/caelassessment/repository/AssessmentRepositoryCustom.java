package co.edu.colomboamericano.caelassessment.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AssessmentRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	/*
	 * 
	 * Gets an assessment given a prospective and status.
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAssesments(Long idProspective, Integer assessmentStatusId) throws Exception {
		List<Object> resultQuery = new ArrayList<>();
		StringBuilder buildQuery = new StringBuilder();
		buildQuery.append("SELECT a.id as idPropective, a.createdAt , a.updatedAt,");
		buildQuery.append("a.course , a.remainingTime, a.program , a.headquarter,");
		buildQuery.append("ass.id as idAssessmentStatus , ass.createdAt as createdAtStatus,");
		buildQuery.append("ass.updatedAt as updatedAtStatus , ass.name, ass.key,");
		buildQuery.append("p.prospectiveStatusId , p.id as idProspective,");
		buildQuery.append("p.createdAt as createdAtProspective , p.updatedAt as updatedAtProspective,");
		buildQuery.append("p.firstName , p.secondName, p.surname , p.secondSurname,");
		buildQuery.append("p.documentType, p.documentNumber, p.birthdate, p.email,");
		buildQuery.append("p.cellphone , p.schoolGrade , p.termsConditions ");
		buildQuery.append("FROM assessment a INNER JOIN prospective p ON p.id = a.prospectiveId ");
		buildQuery.append("INNER JOIN assessmentStatus ass ON ass.id = a.assessmentStatusId ");
		buildQuery.append("WHERE p.id = ? AND a.assessmentStatusId = ?");
		resultQuery = entityManager.createNativeQuery(buildQuery.toString())
				.setParameter(1, idProspective)
				.setParameter(2, assessmentStatusId)
				.getResultList();
		return resultQuery;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object>  getAssementAndQuestionsStepper(Integer id) throws Exception {
		List<Object> resultQuery = new ArrayList<>();
		resultQuery = entityManager.createNativeQuery("select assessments ,questionsStepper,id from assessment where id = ?")
				.setParameter(1, id)
				.getResultList();
		return resultQuery;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object>  getAssementForValidateQuestion(Integer id) throws Exception {
		List<Object> resultQuery = new ArrayList<>();
		resultQuery = entityManager.createNativeQuery("select assessments ,questionsStepper from assessment where id = ?")
				.setParameter(1, id)
				.getResultList();
		return resultQuery;
	}


}
