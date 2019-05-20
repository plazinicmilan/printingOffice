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
 * The persistent class for the stavka_nabavke database table.
 * 
 */
@Entity
@Table(name="stavka_nabavke")
@NamedQuery(name="StavkaNabavke.findAll", query="SELECT s FROM StavkaNabavke s")
public class StavkaNabavke implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="cena")
	private Double cena;

	@Column(name="cena_po_kg")
	private Double cenaPoKg;

	@Column(name="kolicina")
	private Double kolicina;

	@Column(name="redni_broj")
	private int redniBroj;

	//uni-directional many-to-one association to Materijal
	@ManyToOne
	@JoinColumn(name="id_materijal")
	private Materijal materijal;

	//bi-directional many-to-one association to NabavkaMaterijala
	@ManyToOne
	@JoinColumn(name="id_nabavka_materijala")
	private NabavkaMaterijala nabavkaMaterijala;

	public StavkaNabavke() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getCena() {
		return this.cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public Double getCenaPoKg() {
		return this.cenaPoKg;
	}

	public void setCenaPoKg(Double cenaPoKg) {
		this.cenaPoKg = cenaPoKg;
	}

	public Double getKolicina() {
		return this.kolicina;
	}

	public void setKolicina(Double kolicina) {
		this.kolicina = kolicina;
	}

	public int getRedniBroj() {
		return this.redniBroj;
	}

	public void setRedniBroj(int redniBroj) {
		this.redniBroj = redniBroj;
	}

	public Materijal getMaterijal() {
		return this.materijal;
	}

	public void setMaterijal(Materijal materijal) {
		this.materijal = materijal;
	}

	public NabavkaMaterijala getNabavkaMaterijala() {
		return this.nabavkaMaterijala;
	}

	public void setNabavkaMaterijala(NabavkaMaterijala nabavkaMaterijala) {
		this.nabavkaMaterijala = nabavkaMaterijala;
	}

}