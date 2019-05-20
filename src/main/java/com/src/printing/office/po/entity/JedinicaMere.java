package com.src.printing.office.po.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the dobavljac database table.
 * 
 */
@Entity
@Table(name = "jedinica_mere")
public class JedinicaMere implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "naziv")
	private String naziv;

	public JedinicaMere() {
	}

	public JedinicaMere(long id) {
		super();
		this.id = id;
	}

	public JedinicaMere(String naziv) {
		super();
		this.naziv = naziv;
	}

	public JedinicaMere(long id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

}