package co.edu.colomboamericano.caelassessment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.colomboamericano.caelassessment.entity.Prospective;

@Repository
public interface ProspectiveRepository extends JpaRepository<Prospective, Integer>
{
	Optional<Prospective> findByDocumentNumber( Long documentNumber );
	
	@Query(value = "SELECT * FROM miniveldeingles.prospective limit 10", nativeQuery = true)
	List<Prospective> findTen();
}