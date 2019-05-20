package com.src.printing.office.po.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.src.printing.office.po.entity.GotovProizvod;
import com.src.printing.office.po.entity.Kupac;

@Repository
public interface GotovProizvodRepository extends CrudRepository<GotovProizvod, Long> {

	@Override
	List<GotovProizvod> findAllById(Iterable<Long> ids);

	GotovProizvod findByKupacAndNazivAndKolicinaGreaterThan(Kupac kupac, String naziv, int kolicina);

	GotovProizvod findByIdNotAndKupacAndNazivAndKolicinaGreaterThan(Long id, Kupac kupac, String naziv, int kolicina);

	@Query("SELECT gp FROM GotovProizvod gp where (?1 is null or gp.kupac = ?1) AND gp.kolicina > 0 "
			+ "ORDER BY gp.id DESC")
	List<GotovProizvod> search(Kupac kupac);

	@Query("SELECT gp FROM GotovProizvod gp where gp.kolicina > 0 " + "ORDER BY gp.id DESC")
	List<GotovProizvod> findAllActive();

	@Query("SELECT gp FROM GotovProizvod gp where (?1 is null or ?1 <= gp.datumOtpisa) AND (?2 is null or ?2 > gp.datumOtpisa) "
			+ "AND gp.otpisano > 0 ORDER BY gp.datumOtpisa")
	List<GotovProizvod> searchWrittenOff(Calendar datumOd, Calendar datumDo);
}
