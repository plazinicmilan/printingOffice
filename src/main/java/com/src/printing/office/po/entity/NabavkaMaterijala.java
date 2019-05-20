package com.src.printing.office.po.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the nabavka_materijala database table.
 * 
 */
@Entity
@Table(name = "nabavka_materijala")
@NamedQuery(name = "NabavkaMaterijala.findAll", query = "SELECT n FROM NabavkaMaterijala n")
public class NabavkaMaterijala implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datum_nabavke")
	private Calendar datumNabavke;

	@Column(name = "broj_nabavke")
	private String brojNabavke;

	@Column(name = "ukupna_suma")
	private Double ukupnaSuma;

	// bi-directional many-to-one association to Dobavljac
	@ManyToOne
	@JoinColumn(name = "id_dobavljac")
	private Dobavljac dobavljac;

	// uni-directional many-to-one association to Korisnik
	@ManyToOne
	@JoinColumn(name = "id_korisnik")
	private Korisnik korisnik;

	// bi-directional many-to-one association to StavkaNabavke
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "nabavkaMaterijala")
	private List<StavkaNabavke> stavkaNabavkeList;

	public NabavkaMaterijala() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getDatumNabavke() {
		return datumNabavke;
	}

	public void setDatumNabavke(Calendar datumNabavke) {
		this.datumNabavke = datumNabavke;
	}

	public String getBrojNabavke() {
		return brojNabavke;
	}

	public void setBrojNabavke(String brojNabavke) {
		this.brojNabavke = brojNabavke;
	}

	public Double getUkupnaSuma() {
		return this.ukupnaSuma;
	}

	public void setUkupnaSuma(Double ukupnaSuma) {
		this.ukupnaSuma = ukupnaSuma;
	}

	public Dobavljac getDobavljac() {
		return this.dobavljac;
	}

	public void setDobavljac(Dobavljac dobavljac) {
		this.dobavljac = dobavljac;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public List<StavkaNabavke> getStavkaNabavkeList() {
		return stavkaNabavkeList;
	}

	public void setStavkaNabavkeList(List<StavkaNabavke> stavkaNabavkeList) {
		this.stavkaNabavkeList = stavkaNabavkeList;
	}

}