package com.src.printing.office.po.repository;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.RadniNalog;
import com.src.printing.office.po.entity.StatusNaloga;

public interface RadniNalogRepository extends CrudRepository<RadniNalog, Long> {

	Set<RadniNalog> findAllById(Set<Long> ids);

	@Query("SELECT rn FROM RadniNalog rn where (?1 is null or rn.brojRadnogNaloga = ?1) AND "
			+ "(?2 is null or rn.kupac = ?2) AND rn.statusNaloga in ?3 "
			+ " AND (?4 is null or ?4 <= rn.datum) AND (?5 is null or ?5 > rn.datum) ORDER BY rn.id DESC")
	List<RadniNalog> search(String brojNaloga, Kupac kupacID, List<StatusNaloga> statusID, Calendar datumOd,
			Calendar datumDo);

	@Query("SELECT rn FROM RadniNalog rn where (?1 is null or rn.brojRadnogNaloga = ?1) AND "
			+ "((?2 is null or rn.kupac = ?2 or rn.kupacFaktura = ?2) OR (rn.kupac = ?3 or rn.kupacFaktura = ?3))"
			+ " AND (?4 is null or ?4 <= rn.datum) AND (?5 is null or ?5 > rn.datum) AND (rn.stariNalog = ?6) ORDER BY rn.id DESC")
	List<RadniNalog> searchWithoutStatus(String brojNaloga, Kupac kupac, Kupac kupacPripadnost, Calendar datumOd,
			Calendar datumDo, boolean stariNalog);

	RadniNalog findByBrojRadnogNaloga(String brojNaloga);

	@Query("SELECT rn FROM RadniNalog rn where rn.stariNalog = 0 AND rn.korisnikZavrsio is null ORDER BY rn.id DESC")
	List<RadniNalog> findAllActive();

}
