package co.edu.colomboamericano.caelassessment.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class ProspectiveRepositoryCustom {
	

	@Autowired
    private EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	public List<Object> findByDocument(Integer documentType,Long documentNumber) throws Exception {
	
		List<Object> resultQuery = new ArrayList<>();
	
			StringBuilder buildQuery = new StringBuilder();
			buildQuery.append("select max(id)  as id , firstName, secondName , surname,");
			buildQuery.append("secondSurname, documentNumber, birthdate,");
			buildQuery.append("documentType ,email, cellphone, schoolGrade, termsConditions,");
			buildQuery.append("createdAt, updatedAt, prospectiveStatusId from prospective where documentType = ? and documentNumber = ?");
			//buildQuery.append("order by id desc");
			resultQuery = entityManager.createNativeQuery(buildQuery.toString())
			  .setParameter(1, documentType)
			  .setParameter(2, documentNumber)
			  .getResultList();
			/*
			object = entityManager.createQuery("SELECT MAX(id) from Prospective WHERE documentType = ?1 AND documentNumber = ?2")
				.setParameter(1, documentType)
				.setParameter(2, documentNumber)
				.getResultList();*/
		return resultQuery;
	}
	

}
