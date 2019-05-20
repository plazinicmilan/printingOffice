package com.src.printing.office.po.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the materijal database table.
 * 
 */
@Entity
@Table(name = "gotov_proizvod")
public class GotovProizvod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// bi-directional many-to-one association to Kupac
	@ManyToOne
	@JoinColumn(name = "id_kupac")
	private Kupac kupac;

	// bi-directional many-to-one association to Dobavljac
	@ManyToOne
	@JoinColumn(name = "id_dobavljac")
	private Dobavljac dobavljac;

	// bi-directional many-to-one association to JedinicaMere
	@ManyToOne
	@JoinColumn(name = "id_jedinica_mere")
	private JedinicaMere jedinicaMere;

	@Column(name = "naziv")
	private String naziv;

	@Column(name = "kolicina")
	private Integer kolicina;

	@Column(name = "otpisano")
	private Integer otpisano;

	@Column(name = "cena_po_jedinici")
	private Double cenaPoJedinici;

	@Column(name = "razlog_otpisa")
	private String razlogOtpisa;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datum_otpisa")
	private Calendar datumOtpisa;

	// bi-directional many-to-one association to Korisnik
	@ManyToOne
	@JoinColumn(name = "id_korisnik_otpisao")
	private Korisnik korisnikOtpisao;

	// uni-directional many-to-many association to RadniNalog
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "radni_nalog_gotov_proizvod", joinColumns = @JoinColumn(name = "id_gotov_proizvod", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_radni_nalog", referencedColumnName = "id"))
	private Set<RadniNalog> radniNalogList;

	public GotovProizvod() {
		radniNalogList = new HashSet<>();
	}

	public GotovProizvod(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public Integer getKolicina() {
		return kolicina;
	}

	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Dobavljac getDobavljac() {
		return dobavljac;
	}

	public void setDobavljac(Dobavljac dobavljac) {
		this.dobavljac = dobavljac;
	}

	public JedinicaMere getJedinicaMere() {
		return jedinicaMere;
	}

	public void setJedinicaMere(JedinicaMere jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}

	public Double getCenaPoJedinici() {
		return cenaPoJedinici;
	}

	public void setCenaPoJedinici(Double cenaPoJedinici) {
		this.cenaPoJedinici = cenaPoJedinici;
	}

	public Integer getOtpisano() {
		return otpisano;
	}

	public void setOtpisano(Integer otpisano) {
		this.otpisano = otpisano;
	}

	public String getRazlogOtpisa() {
		return razlogOtpisa;
	}

	public void setRazlogOtpisa(String razlogOtpisa) {
		this.razlogOtpisa = razlogOtpisa;
	}

	public Calendar getDatumOtpisa() {
		return datumOtpisa;
	}

	public void setDatumOtpisa(Calendar datumOtpisa) {
		this.datumOtpisa = datumOtpisa;
	}

	public Korisnik getKorisnikOtpisao() {
		return korisnikOtpisao;
	}

	public void setKorisnikOtpisao(Korisnik korisnikOtpisao) {
		this.korisnikOtpisao = korisnikOtpisao;
	}

	public Set<RadniNalog> getRadniNalogList() {
		return radniNalogList;
	}

	public void setRadniNalogList(Set<RadniNalog> radniNalogList) {
		this.radniNalogList = radniNalogList;
	}

}