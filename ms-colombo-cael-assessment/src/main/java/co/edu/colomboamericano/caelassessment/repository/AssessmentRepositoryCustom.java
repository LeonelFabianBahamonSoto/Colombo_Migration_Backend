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
	public List<Object> getAssesment(Integer prospectiveId,Integer assessmentStatusId) throws Exception {
		List<Object> resultQuery = new ArrayList<>();
		StringBuilder buildQuery = new StringBuilder();
		buildQuery.append("SELECT a.id, a.course, a.assessments, a.questionsStepper, a.remainingTime,");
		buildQuery.append("a.assessmentStatusId, a.prospectiveId, a.createdAt, a.updatedAt,");
		buildQuery.append("a.program, a.headquarter, p.id as idProspective, p.firstName ,");
		buildQuery.append("p.secondName, p.surname, p.secondSurname , p.documentNumber,");
		buildQuery.append("p.birthdate, p.email, p.cellphone, p.schoolGrade, p.termsConditions,");
		buildQuery.append("p.prospectiveStatusId, p.createdAt as createdAtPropective,");
		buildQuery.append("p.updatedAt as updatedAtPropective, p.documentType , ast.id as idProspectiveStatus,");
		buildQuery.append("ast.name, ast.key, ast.createdAt as createdAtProspectiveStatus,");
		buildQuery.append("ast.updatedAt as updatedAtProspectiveStatus ");
		buildQuery.append("FROM assessment a INNER JOIN prospective p ON p.id = a.prospectiveId INNER JOIN assessmentStatus ast ON ast.id = a.assessmentStatusId  ");
		buildQuery.append("WHERE p.id = ? AND a.assessmentStatusId = ?");
		resultQuery = entityManager.createNativeQuery(buildQuery.toString())
				.setParameter(1, prospectiveId)
				.setParameter(2, assessmentStatusId)
				.getResultList();
		return resultQuery;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object>  getAssementAndQuestionsStepper(Integer id) throws Exception {
		List<Object> resultQuery = new ArrayList<>();
		resultQuery = entityManager.createNativeQuery("select assessments ,questionsStepper from assessment where id = ?")
				.setParameter(1, id)
				.getResultList();
		return resultQuery;
	}

}
