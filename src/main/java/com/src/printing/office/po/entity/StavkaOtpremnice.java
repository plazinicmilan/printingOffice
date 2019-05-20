package com.src.printing.office.po.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the stavka_otpremnice database table.
 * 
 */
@Entity
@Table(name = "stavka_otpremnice")
@NamedQuery(name = "StavkaOtpremnice.findAll", query = "SELECT s FROM StavkaOtpremnice s")
public class StavkaOtpremnice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cena_po_jedinici_mere")
	private Double cenaPoJediniciMere;

	// bi-directional many-to-one association to Jedinica mere
//	@ManyToOne
//	@JoinColumn(name = "id_jedinica_mere")
//	private JedinicaMere jedinicaMere;

	@Column(name = "kolicina")
	private Integer kolicina;

	@Column(name = "gratis")
	private Integer gratis;

	@Column(name = "naziv_artikla")
	private String nazivArtikla;

	@Column(name = "redni_broj")
	private int redniBroj;

	@Column(name = "ukupno")
	private Double ukupno;

	@Column(name = "zajednicki_naziv")
	private String zajednickiNaziv;

	// bi-directional many-to-one association to Otpremnica
	@ManyToOne
	@JoinColumn(name = "id_otpremnica")
	private Otpremnica otpremnica;

	// bi-directional many-to-one association to Gotov Proizvod
	@ManyToOne
	@JoinColumn(name = "id_gotov_proizvod")
	private GotovProizvod gotovProizvod;

	// bi-directional many-to-one association to Materijal
	@ManyToOne
	@JoinColumn(name = "id_materijal")
	private Materijal materijal;

	public StavkaOtpremnice() {
	}

	public StavkaOtpremnice(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCenaPoJediniciMere() {
		return cenaPoJediniciMere;
	}

	public void setCenaPoJediniciMere(Double cenaPoJediniciMere) {
		this.cenaPoJediniciMere = cenaPoJediniciMere;
	}

	public GotovProizvod getGotovProizvod() {
		return gotovProizvod;
	}

	public void setGotovProizvod(GotovProizvod gotovProizvod) {
		this.gotovProizvod = gotovProizvod;
	}

	public Integer getKolicina() {
		return kolicina;
	}

	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}

	public String getNazivArtikla() {
		return nazivArtikla;
	}

	public void setNazivArtikla(String nazivArtikla) {
		this.nazivArtikla = nazivArtikla;
	}

	public int getRedniBroj() {
		return redniBroj;
	}

	public void setRedniBroj(int redniBroj) {
		this.redniBroj = redniBroj;
	}

	public Double getUkupno() {
		return ukupno;
	}

	public void setUkupno(Double ukupno) {
		this.ukupno = ukupno;
	}

	public Otpremnica getOtpremnica() {
		return otpremnica;
	}

	public void setOtpremnica(Otpremnica otpremnica) {
		this.otpremnica = otpremnica;
	}

	public Materijal getMaterijal() {
		return materijal;
	}

	public void setMaterijal(Materijal materijal) {
		this.materijal = materijal;
	}

	public Integer getGratis() {
		return gratis;
	}

	public void setGratis(Integer gratis) {
		this.gratis = gratis;
	}

	public String getZajednickiNaziv() {
		return zajednickiNaziv;
	}

	public void setZajednickiNaziv(String zajednickiNaziv) {
		this.zajednickiNaziv = zajednickiNaziv;
	}

}