package com.src.printing.office.po.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.src.printing.office.po.entity.JedinicaMere;
import com.src.printing.office.po.entity.Materijal;

public interface MaterijalRepository extends CrudRepository<Materijal, Long> {

	@Override
	@Query("SELECT m FROM Materijal m ORDER BY m.naziv, m.proizvodjac")
	List<Materijal> findAll();

	Materijal findByNazivAndProizvodjacAndPakovanjeKolicinaAndPakovanjeJMAndJedinicaMere(String naziv,
			String proizvodjac, Double pakovanjeKolicina, JedinicaMere pakovanjeJM, JedinicaMere jedinicaMere);

	Materijal findByNazivAndProizvodjacAndPakovanjeKolicinaAndPakovanjeJMAndJedinicaMereAndIdNot(String naziv,
			String proizvodjac, Double pakovanjeKolicina, JedinicaMere pakovanjeJM, JedinicaMere jedinicaMere, Long id);

	@Query("SELECT m FROM Materijal m where (?1 is null or m.id = ?1) ORDER BY m.naziv")
	List<Materijal> search(Long id);

	@Query("SELECT m FROM Materijal m where (?1 is null or m.id = ?1) AND m.tip in ?2 ORDER BY m.naziv")
	List<Materijal> searchWithType(Long id, List<String> tipLista);

	@Query("SELECT m FROM Materijal m where (?1 is null or m.id = ?1) AND (m.neaktivan is null or m.neaktivan = 0) ORDER BY m.tip, m.naziv")
	List<Materijal> searchActive(Long id);

	@Query("SELECT m FROM Materijal m where (?1 is null or m.id = ?1) AND m.tip in ?2 AND (m.neaktivan is null or m.neaktivan = 0) ORDER BY m.tip, m.naziv")
	List<Materijal> searchActiveWithType(Long id, List<String> tipLista);

	@Query("SELECT m FROM Materijal m where (m.neaktivan is null or m.neaktivan = 0) "
			+ "and m.kolicina <= m.dozvoljeniLimit ORDER BY m.naziv")
	List<Materijal> findCritical();

	@Query("SELECT m FROM Materijal m where (m.neaktivan is null or m.neaktivan = 0) " + "and m.tip = ?1"
			+ " ORDER BY m.naziv, m.proizvodjac")
	List<Materijal> findActivePapers(String papir);

	Materijal findByNaziv(String naziv);

	@Query("SELECT m FROM Materijal m where (m.neaktivan is null or m.neaktivan = 0) "
			+ "ORDER BY m.naziv, m.proizvodjac")
	List<Materijal> findAllActive();

	@Query("SELECT m FROM Materijal m where (m.neaktivan is null or m.neaktivan = 0) and m.tip = ?1"
			+ " ORDER BY m.naziv")
	List<Materijal> findAllSpirals(String spirala);

	@Query("SELECT m.id, m.naziv, m.proizvodjac, jm.naziv, "
			+ " SUM(CEILING(((sn.mnozilac * sn.tabaka + sn.visak) / sn.izTabaka)))" + "  FROM StavkaNaloga sn"
			+ " left join RadniNalog rn on rn.id = sn.radniNalog " + " left join Materijal m on m.id = sn.materijal"
			+ " left join JedinicaMere jm on m.jedinicaMere = jm.id"
			+ " WHERE (?1 is null or ?1 <= rn.datum) AND (?2 is null or ?2 > rn.datum)"
			+ " group by m.id, m.naziv, m.proizvodjac, jm.naziv, m.tip")
	List<Object[]> findConsumptionFromWO(Calendar datumOd, Calendar datumDo);

	@Query("SELECT m.id, m.naziv, m.proizvodjac, jm.naziv,"
			+ " SUM(so.kolicina + CASE WHEN so.gratis is null THEN 0 ELSE so.gratis END)" + " FROM StavkaOtpremnice so"
			+ "  left join Otpremnica o on o.id = so.otpremnica " + " left join Materijal m on m.id = so.materijal"
			+ "  left join JedinicaMere jm on m.jedinicaMere = jm.id"
			+ "  WHERE so.materijal is not null AND (?1 is null or ?1 <= o.datum) AND (?2 is null or ?2 > o.datum)"
			+ "  group by m.id, m.naziv, m.proizvodjac, jm.naziv, m.tip")
	List<Object[]> findConsumptionFromDN(Calendar datumOd, Calendar datumDo);
}
