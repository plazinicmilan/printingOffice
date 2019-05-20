package com.src.printing.office.po.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.Porudzbina;

public interface PorudzbinaRepository extends CrudRepository<Porudzbina, Long> {

	@Override
	@Query("SELECT p FROM Porudzbina p ORDER BY p.id DESC")
	List<Porudzbina> findAll();

	@Query("SELECT p FROM Porudzbina p WHERE p.korisnikIzvrsilac is null ORDER BY p.id DESC")
	List<Porudzbina> findAllUnaccepted();

	@Query("SELECT p FROM Porudzbina p where (?1 is null or p.kupac = ?1) AND "
			+ "(?2 is null or ?2 <= p.datum) AND (?3 is null or ?3 > p.datum) ORDER BY p.id DESC")
	List<Porudzbina> search(Kupac kupac, Calendar datumOd, Calendar datumDo);

}
