package com.src.printing.office.po.entity;

import java.io.Serializable;
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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the korisnik database table.
 * 
 */
@Entity
@Table(name = "korisnik")
@NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k")
public class Korisnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "ime")
	private String ime;

	@Column(name = "korisnicka_sifra")
	private String korisnickaSifra;

	@Column(name = "korisnicko_ime")
	private String korisnickoIme;

	@Column(name = "prezime")
	private String prezime;

	// uni-directional many-to-many association to Rola
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "korisnik_rola", joinColumns = @JoinColumn(name = "id_korisnik", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_rola", referencedColumnName = "id"))
	private Set<Rola> rolaList;

	public Korisnik() {
	}

	public Korisnik(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getKorisnickaSifra() {
		return this.korisnickaSifra;
	}

	public void setKorisnickaSifra(String korisnickaSifra) {
		this.korisnickaSifra = korisnickaSifra;
	}

	public String getKorisnickoIme() {
		return this.korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getPrezime() {
		return this.prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Set<Rola> getRolaList() {
		return this.rolaList;
	}

	public void setRolaList(Set<Rola> rolaList) {
		this.rolaList = rolaList;
	}

	@Override
	public String toString() {
		return "Korisnik [id=" + id + ", ime=" + ime + ", korisnickaSifra=" + korisnickaSifra + ", korisnickoIme="
				+ korisnickoIme + ", prezime=" + prezime + ", rolaList=" + rolaList + "]";
	}

}