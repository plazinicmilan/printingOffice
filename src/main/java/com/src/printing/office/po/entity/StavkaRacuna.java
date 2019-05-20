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
 * The persistent class for the stavka_racuna database table.
 * 
 */
@Entity
@Table(name = "stavka_racuna")
@NamedQuery(name = "StavkaRacuna.findAll", query = "SELECT s FROM StavkaRacuna s")
public class StavkaRacuna implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cena_pdv")
	private Double cenaPDV;

	@Column(name = "cena_po_jedinici_mere")
	private Double cenaPoJediniciMere;

	@Column(name = "cena_sa_pdv")
	private Double cenaSaPdv;

	@Column(name = "iznos")
	private Double iznos;

	@Column(name = "jedinica_mere")
	private String jedinicaMere;

	@Column(name = "kolicina")
	private Double kolicina;

	@Column(name = "opis")
	private String opis;

	@Column(name = "redni_broj")
	private int redniBroj;

	@Column(name = "pdv_stopa")
	private Double pdvStopa;

	// uni-directional many-to-one association to Otpremnica
	@ManyToOne
	@JoinColumn(name = "id_otpremnica")
	private Otpremnica otpremnica;

	// bi-directional many-to-one association to Racun
	@ManyToOne
	@JoinColumn(name = "id_racun")
	private Racun racun;

	public StavkaRacuna() {
	}

	public StavkaRacuna(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCenaPDV() {
		return this.cenaPDV;
	}

	public void setCenaPDV(Double cenaPDV) {
		this.cenaPDV = cenaPDV;
	}

	public Double getCenaPoJediniciMere() {
		return this.cenaPoJediniciMere;
	}

	public void setCenaPoJediniciMere(Double cenaPoJediniciMere) {
		this.cenaPoJediniciMere = cenaPoJediniciMere;
	}

	public Double getCenaSaPdv() {
		return this.cenaSaPdv;
	}

	public void setCenaSaPdv(Double cenaSaPdv) {
		this.cenaSaPdv = cenaSaPdv;
	}

	public Double getIznos() {
		return this.iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}

	public String getJedinicaMere() {
		return this.jedinicaMere;
	}

	public void setJedinicaMere(String jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}

	public Double getKolicina() {
		return this.kolicina;
	}

	public void setKolicina(Double kolicina) {
		this.kolicina = kolicina;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getRedniBroj() {
		return this.redniBroj;
	}

	public void setRedniBroj(int redniBroj) {
		this.redniBroj = redniBroj;
	}

	public Racun getRacun() {
		return this.racun;
	}

	public void setRacun(Racun racun) {
		this.racun = racun;
	}

	public Otpremnica getOtpremnica() {
		return otpremnica;
	}

	public void setOtpremnica(Otpremnica otpremnica) {
		this.otpremnica = otpremnica;
	}

	public Double getPdvStopa() {
		return pdvStopa;
	}

	public void setPdvStopa(Double pdvStopa) {
		this.pdvStopa = pdvStopa;
	}

}