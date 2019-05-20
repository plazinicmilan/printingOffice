package com.src.printing.office.po.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the otpremnica database table.
 * 
 */
@Entity
@Table(name = "otpremnica")
@NamedQuery(name = "Otpremnica.findAll", query = "SELECT o FROM Otpremnica o")
public class Otpremnica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "broj_otpremnice")
	private String brojOtpremnice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datum")
	private Calendar datum;

	@Column(name = "fakturisano")
	private Boolean fakturisano;

	// uni-directional many-to-one association to Korisnik
	@ManyToOne
	@JoinColumn(name = "id_korisnik")
	private Korisnik korisnik;

	// uni-directional many-to-one association to Korisnik
	@ManyToOne
	@JoinColumn(name = "id_korisnik_zavrsio")
	private Korisnik korisnikZavrsio;

	// uni-directional many-to-one association to Kupac
	@ManyToOne
	@JoinColumn(name = "id_kupac")
	private Kupac kupac;

	@Column(name = "stornirana")
	private Boolean stornirana;

	@Column(name = "interna")
	private Boolean interna;

	@Column(name = "opis")
	private String opis;

	// bi-directional many-to-one association to StavkaOtpremnice
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "otpremnica")
	private List<StavkaOtpremnice> stavkaOtpremniceList;

	// uni-directional many-to-many association to RadniNalog
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "otpremnica_radni_nalog", joinColumns = @JoinColumn(name = "id_otpremnica", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_radni_nalog", referencedColumnName = "id"))
	private Set<RadniNalog> radniNalogList;

	public Otpremnica() {
	}

	public Otpremnica(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBrojOtpremnice() {
		return this.brojOtpremnice;
	}

	public void setBrojOtpremnice(String brojOtpremnice) {
		this.brojOtpremnice = brojOtpremnice;
	}

	public Calendar getDatum() {
		return datum;
	}

	public void setDatum(Calendar datum) {
		this.datum = datum;
	}

	public Boolean getFakturisano() {
		return fakturisano;
	}

	public void setFakturisano(Boolean fakturisano) {
		this.fakturisano = fakturisano;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Korisnik getKorisnikZavrsio() {
		return korisnikZavrsio;
	}

	public void setKorisnikZavrsio(Korisnik korisnikZavrsio) {
		this.korisnikZavrsio = korisnikZavrsio;
	}

	public Kupac getKupac() {
		return this.kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public Boolean getStornirana() {
		return stornirana;
	}

	public void setStornirana(Boolean stornirana) {
		this.stornirana = stornirana;
	}

	public List<StavkaOtpremnice> getStavkaOtpremniceList() {
		return stavkaOtpremniceList;
	}

	public void setStavkaOtpremniceList(List<StavkaOtpremnice> stavkaOtpremniceList) {
		this.stavkaOtpremniceList = stavkaOtpremniceList;
	}

	public Boolean getInterna() {
		return interna;
	}

	public void setInterna(Boolean interna) {
		this.interna = interna;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Set<RadniNalog> getRadniNalogList() {
		return radniNalogList;
	}

	public void setRadniNalogList(Set<RadniNalog> radniNalogList) {
		this.radniNalogList = radniNalogList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Otpremnica other = (Otpremnica) obj;
		if (id != other.id)
			return false;
		return true;
	}

}