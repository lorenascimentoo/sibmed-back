package com.sibmed.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sibmed.domain.enums.TipoCategoria;

@Entity
public class Evidencia implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String principioAtivo;
	
	private Integer categoria;
	
	@JsonIgnore
	@OneToMany(mappedBy="evidencia", cascade = CascadeType.ALL)
	@Column(name = "evidencia_id")
	private List<Bula> bulas = new ArrayList<>();
	
	
	@OneToMany(mappedBy="evidencia", cascade = CascadeType.ALL)
	@Column(name = "evidencia_id")
	private List<SituacaoPaciente> situacaoPaciente = new ArrayList<>();
	
	public Evidencia() {
	}
	
	public Evidencia(Integer id, String principioAtivo, TipoCategoria categoria) {
		super();
		this.id = id;
		this.principioAtivo = principioAtivo;
		this.categoria = (categoria == null) ? null : categoria.getCod();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPrincipioAtivo() {
		return principioAtivo;
	}
	public void setPrincipioAtivo(String principioAtivo) {
		this.principioAtivo = principioAtivo;
	}
	public Integer getCategoria() {
		return categoria;
	}
	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}
	
	public List<SituacaoPaciente> getSituacaoPaciente() {
		return situacaoPaciente;
	}

	public void setSituacaoPaciente(List<SituacaoPaciente> situacaoPaciente) {
		this.situacaoPaciente = situacaoPaciente;
	}

	public List<Bula> getBulas() {
		return bulas;
	}

	public void setBulas(List<Bula> bulas) {
		this.bulas = bulas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Evidencia other = (Evidencia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
