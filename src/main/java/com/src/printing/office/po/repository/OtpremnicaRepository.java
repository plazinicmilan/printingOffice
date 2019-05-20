package com.src.printing.office.po.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.Otpremnica;

public interface OtpremnicaRepository extends CrudRepository<Otpremnica, Long> {

	@Query("SELECT o FROM Otpremnica o where (?1 is null or o.brojOtpremnice = ?1) AND "
			+ "(?2 is null or o.kupac = ?2) " + " AND (?3 is null or ?3 <= o.datum) AND (?4 is null or ?4 > o.datum) "
			+ " AND (o.interna = ?5) AND (o.stornirana is null or o.stornirana = 0) ORDER BY o.id DESC")
	List<Otpremnica> search(String brojOtpremnice, Kupac kupacID, Calendar datumOd, Calendar datumDo, Boolean interna);

	@Query("SELECT o FROM Otpremnica o where (o.stornirana is null or o.stornirana = 0) AND "
			+ "(o.fakturisano is null or o.fakturisano = 0) AND (o.interna is null or o.interna = 0) ORDER BY o.id DESC")
	List<Otpremnica> findAllUnreceipted();

}
