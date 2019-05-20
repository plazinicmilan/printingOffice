package com.src.printing.office.po.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.src.printing.office.po.entity.Rola;

public interface RolaRepository extends CrudRepository<Rola, Long> {

	@Override
	@Query("SELECT r FROM Rola r where r.punNaziv is not null ORDER BY r.punNaziv")
	List<Rola> findAll();
}
