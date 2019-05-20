package com.src.printing.office.po.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.src.printing.office.po.entity.JedinicaMere;

public interface JedinicaMereRepository extends CrudRepository<JedinicaMere, Long> {

	@Override
	Set<JedinicaMere> findAll();

	@Query("SELECT jm FROM JedinicaMere jm where naziv = ?1")
	JedinicaMere findByName(String naziv);
}
