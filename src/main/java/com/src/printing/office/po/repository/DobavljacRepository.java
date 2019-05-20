package com.src.printing.office.po.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.src.printing.office.po.entity.Dobavljac;

public interface DobavljacRepository extends CrudRepository<Dobavljac, Long> {

	@Override
	Set<Dobavljac> findAll();

	@Query("SELECT d FROM Dobavljac d where naziv = ?1")
	Dobavljac findByName(String naziv);

	@Query("SELECT d FROM Dobavljac d where (?1 is null or d.naziv = ?1)")
	List<Dobavljac> search(String dobavljac);

}
