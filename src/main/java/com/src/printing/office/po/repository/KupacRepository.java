package com.src.printing.office.po.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.src.printing.office.po.entity.Kupac;

public interface KupacRepository extends CrudRepository<Kupac, Long> {

	@Override
	Set<Kupac> findAll();

	@Query("SELECT k FROM Kupac k where naziv = ?1")
	Kupac findByName(String naziv);

	@Query("SELECT k FROM Kupac k where (?1 is null or k.naziv = ?1)")
	List<Kupac> search(String kupac);

	List<Kupac> findByNeaktivanIsNullOrNeaktivan(Boolean inactive);
}
