package com.src.printing.office.po.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the radni_nalog database table.
 * 
 */
@Entity
@Table(name = "porudzbina")
public class Porudzbina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datum")
	private Calendar datum;

	@Column(name = "naziv_posla")
	private String nazivPosla;

	@Column(name = "tiraz")
	private Integer tiraz;

	@Column(name = "opis")
	private String opis;

	// bi-directional many-to-one association to Korisnik
	@ManyToOne
	@JoinColumn(name = "id_korisnik")
	private Korisnik korisnik;

	// bi-directional many-to-one association to Korisnik
	@ManyToOne
	@JoinColumn(name = "id_korisnik_izvrsilac")
	private Korisnik korisnikIzvrsilac;

	// bi-directional many-to-one association to Kupac
	@ManyToOne
	@JoinColumn(name = "id_kupac")
	private Kupac kupac;

	public Porudzbina() {
	}

	public Porudzbina(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDatum() {
		return datum;
	}

	public void setDatum(Calendar datum) {
		this.datum = datum;
	}

	public String getNazivPosla() {
		return nazivPosla;
	}

	public void setNazivPosla(String nazivPosla) {
		this.nazivPosla = nazivPosla;
	}

	public Integer getTiraz() {
		return tiraz;
	}

	public void setTiraz(Integer tiraz) {
		this.tiraz = tiraz;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Korisnik getKorisnikIzvrsilac() {
		return korisnikIzvrsilac;
	}

	public void setKorisnikIzvrsilac(Korisnik korisnikIzvrsilac) {
		this.korisnikIzvrsilac = korisnikIzvrsilac;
	}

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

}