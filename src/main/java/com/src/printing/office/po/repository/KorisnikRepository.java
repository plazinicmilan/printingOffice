package com.src.printing.office.po.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.src.printing.office.po.entity.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

	@Query("SELECT k FROM Korisnik k where korisnicko_ime = ?1")
	Korisnik findByUsername(String username);

	Korisnik findByKorisnickoImeAndIdNot(String korisnickoIme, Long id);

	@Override
	@Query("SELECT k FROM Korisnik k ORDER BY k.ime, k.prezime")
	List<Korisnik> findAll();

	@Query("SELECT k FROM Korisnik k where (?1 is null or k.id = ?1) ORDER BY k.ime, k.prezime")
	List<Korisnik> search(Long id);
}
