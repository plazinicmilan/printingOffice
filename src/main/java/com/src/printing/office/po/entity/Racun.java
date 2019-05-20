package com.src.printing.office.po.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
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
 * The persistent class for the racun database table.
 * 
 */
@Entity
@Table(name = "racun")
@NamedQuery(name = "Racun.findAll", query = "SELECT r FROM Racun r")
public class Racun implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "broj_racuna")
	private String brojRacuna;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datum")
	private Calendar datum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datum_dospeca_placanja")
	private Calendar datumDospecaPlacanja;

	@Column(name = "iznos_bez_pdv")
	private Double iznosBezPdv;

	@Column(name = "iznos_pdv")
	private Double iznosPdv;

	@Column(name = "iznos_ukupno")
	private Double iznosUkupno;

	@Column(name = "storniran")
	private Boolean storniran;

	@Column(name = "ukljuci_pdv")
	private Boolean ukljuciPDV;

	@Column(name = "tip")
	private String tip;

	@Column(name = "napomena")
	private String napomena;

	// uni-directional many-to-one association to Korisnik
	@ManyToOne
	@JoinColumn(name = "id_korisnik")
	private Korisnik korisnik;

	// uni-directional many-to-one association to Kupac
	@ManyToOne
	@JoinColumn(name = "id_kupac")
	private Kupac kupac;

	// bi-directional many-to-one association to StavkaRacuna
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "racun")
	private List<StavkaRacuna> stavkaRacunaList;

	// uni-directional many-to-many association to RadniNalog
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "radni_nalog_racun", joinColumns = @JoinColumn(name = "id_racun", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_radni_nalog", referencedColumnName = "id"))
	private Set<RadniNalog> radniNalogList;

	public Racun() {
		radniNalogList = new HashSet<>();
	}

	public Racun(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBrojRacuna() {
		return this.brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public Calendar getDatum() {
		return this.datum;
	}

	public void setDatum(Calendar datum) {
		this.datum = datum;
	}

	public Calendar getDatumDospecaPlacanja() {
		return this.datumDospecaPlacanja;
	}

	public void setDatumDospecaPlacanja(Calendar datumDospecaPlacanja) {
		this.datumDospecaPlacanja = datumDospecaPlacanja;
	}

	public Double getIznosBezPdv() {
		return this.iznosBezPdv;
	}

	public void setIznosBezPdv(Double iznosBezPdv) {
		this.iznosBezPdv = iznosBezPdv;
	}

	public Double getIznosPdv() {
		return this.iznosPdv;
	}

	public void setIznosPdv(Double iznosPdv) {
		this.iznosPdv = iznosPdv;
	}

	public Double getIznosUkupno() {
		return this.iznosUkupno;
	}

	public void setIznosUkupno(Double iznosUkupno) {
		this.iznosUkupno = iznosUkupno;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Kupac getKupac() {
		return this.kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public Boolean getStorniran() {
		return storniran;
	}

	public void setStorniran(Boolean storniran) {
		this.storniran = storniran;
	}

	public Boolean getUkljuciPDV() {
		return ukljuciPDV;
	}

	public void setUkljuciPDV(Boolean ukljuciPDV) {
		this.ukljuciPDV = ukljuciPDV;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}

	public List<StavkaRacuna> getStavkaRacunaList() {
		return stavkaRacunaList;
	}

	public void setStavkaRacunaList(List<StavkaRacuna> stavkaRacunaList) {
		this.stavkaRacunaList = stavkaRacunaList;
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
		Racun other = (Racun) obj;
		if (id != other.id)
			return false;
		return true;
	}

}