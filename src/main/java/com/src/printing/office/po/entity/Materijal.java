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
 * The persistent class for the materijal database table.
 * 
 */
@Entity
@Table(name = "materijal")
@NamedQuery(name = "Materijal.findAll", query = "SELECT m FROM Materijal m")
public class Materijal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "dozvoljeni_limit")
	private Double dozvoljeniLimit;

	@Column(name = "kolicina")
	private Double kolicina;

	@Column(name = "naziv")
	private String naziv;

	// uni-directional many-to-one association to JedinicaMere
	@ManyToOne
	@JoinColumn(name = "id_jedinica_mere")
	private JedinicaMere jedinicaMere;

	// uni-directional many-to-one association to Dobavljac
	@ManyToOne
	@JoinColumn(name = "id_dobavljac")
	private Dobavljac dobavljac;

	@Column(name = "proizvodjac")
	private String proizvodjac;

	@Column(name = "cena")
	private Double cena;

	// uni-directional many-to-one association to JedinicaMere
	@ManyToOne
	@JoinColumn(name = "id_pakovanje_jm")
	private JedinicaMere pakovanjeJM;

	@Column(name = "pakovanje_kolicina")
	private Double pakovanjeKolicina;

	@Column(name = "tip")
	private String tip;

	@Column(name = "neaktivan")
	private Boolean neaktivan;

	public Materijal() {
	}

	public Materijal(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getDozvoljeniLimit() {
		return this.dozvoljeniLimit;
	}

	public void setDozvoljeniLimit(Double dozvoljeniLimit) {
		this.dozvoljeniLimit = dozvoljeniLimit;
	}

	public Double getKolicina() {
		return this.kolicina;
	}

	public void setKolicina(Double kolicina) {
		this.kolicina = kolicina;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public JedinicaMere getJedinicaMere() {
		return jedinicaMere;
	}

	public void setJedinicaMere(JedinicaMere jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}

	public Dobavljac getDobavljac() {
		return dobavljac;
	}

	public void setDobavljac(Dobavljac dobavljac) {
		this.dobavljac = dobavljac;
	}

	public String getProizvodjac() {
		return proizvodjac;
	}

	public void setProizvodjac(String proizvodjac) {
		this.proizvodjac = proizvodjac;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public JedinicaMere getPakovanjeJM() {
		return pakovanjeJM;
	}

	public void setPakovanjeJM(JedinicaMere pakovanjeJM) {
		this.pakovanjeJM = pakovanjeJM;
	}

	public Double getPakovanjeKolicina() {
		return pakovanjeKolicina;
	}

	public void setPakovanjeKolicina(Double pakovanjeKolicina) {
		this.pakovanjeKolicina = pakovanjeKolicina;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public Boolean getNeaktivan() {
		return neaktivan;
	}

	public void setNeaktivan(Boolean neaktivan) {
		this.neaktivan = neaktivan;
	}

}