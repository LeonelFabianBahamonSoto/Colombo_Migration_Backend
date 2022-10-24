package co.edu.colomboamericano.caelassessment.service;

import java.util.Optional;

public interface GenericService<T, ID>
{
	Optional<T> findById(ID id);
	
	T save(T entity) throws Exception;
	
	T update(T entity) throws Exception;
	
	void delete(T entity) throws Exception;
	
	void deleteById(ID id) throws Exception;
}
