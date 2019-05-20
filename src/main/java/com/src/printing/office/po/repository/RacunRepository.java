package com.src.printing.office.po.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.Racun;

public interface RacunRepository extends CrudRepository<Racun, Long> {

	@Query("SELECT r FROM Racun r where (?1 is null or r.brojRacuna = ?1) AND " + "(?2 is null or r.kupac = ?2) "
			+ " AND (?3 is null or ?3 <= r.datum) AND (?4 is null or ?4 > r.datum) AND r.tip = ?5 "
			+ " AND (r.storniran is null or r.storniran = 0) ORDER BY r.id DESC")
	List<Racun> search(String brojRacuna, Kupac kupacID, Calendar datumOd, Calendar datumDo, String tip);
}
