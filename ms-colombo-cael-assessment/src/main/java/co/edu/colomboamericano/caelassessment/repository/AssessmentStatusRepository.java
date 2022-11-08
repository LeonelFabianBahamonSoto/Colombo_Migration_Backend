package co.edu.colomboamericano.caelassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.colomboamericano.caelassessment.entity.AssessmentStatus;

@Repository
public interface AssessmentStatusRepository extends JpaRepository<AssessmentStatus, Integer>
{

}