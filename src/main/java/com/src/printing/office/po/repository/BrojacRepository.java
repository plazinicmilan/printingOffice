package com.src.printing.office.po.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.src.printing.office.po.entity.Brojac;

public interface BrojacRepository extends JpaRepository<Brojac, Long> {

	@Query("SELECT b FROM Brojac b where godina = ?1 and tip_dokumenta = ?2")
	Brojac findByTypeAndYear(Integer year, String typeOfDocument);
}
