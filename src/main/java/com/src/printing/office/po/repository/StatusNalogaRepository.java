package com.src.printing.office.po.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.src.printing.office.po.entity.StatusNaloga;

public interface StatusNalogaRepository extends CrudRepository<StatusNaloga, Long> {

	@Override
	Set<StatusNaloga> findAll();
}
