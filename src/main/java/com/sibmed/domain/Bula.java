package com.sibmed.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Bula implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nomeComercial;
	private String principioAtivo;
	private String fabricante;
	@Lob
	@Column(nullable = true, columnDefinition = "TEXT")
	private String indicacao;
	
	@Lob
	@Column(nullable = true, columnDefinition = "TEXT")
	private String contraIndicacao;
	
	@Lob
	@Column(nullable = true, columnDefinition = "TEXT")
	private String reacaoAdversa;
	private String dir;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="evidencia_bula_id")
	private Evidencia evidencia;
	
	public Bula() {	
	}
	
	public Bula(Integer id,String nomeComercial, String principioAtivo, String fabricante, String indicacao, String contraIndicacao,String reacaoAdversa, String dir,Evidencia evidencia) {
		this.id = id;
		this.nomeComercial = nomeComercial;
		this.principioAtivo = principioAtivo;
		this.fabricante = fabricante;
		this.indicacao = indicacao;
		this.contraIndicacao = contraIndicacao;
		this.reacaoAdversa = reacaoAdversa;
		this.dir = dir;
		this.evidencia = evidencia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeComercial() {
		return nomeComercial;
	}

	public void setNomeComercial(String nomeComercial) {
		this.nomeComercial = nomeComercial;
	}

	public String getPrincipioAtivo() {
		return principioAtivo;
	}

	public void setPrincipioAtivo(String principioAtivo) {
		this.principioAtivo = principioAtivo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(String indicacao) {
		this.indicacao = indicacao;
	}

	public String getContraIndicacao() {
		return contraIndicacao;
	}

	public void setContraIndicacao(String contraIndicacao) {
		this.contraIndicacao = contraIndicacao;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getReacaoAdversa() {
		return reacaoAdversa;
	}

	public void setReacaoAdversa(String reacaoAdversa) {
		this.reacaoAdversa = reacaoAdversa;
	}

	public Evidencia getEvidencia() {
		return evidencia;
	}

	public void setEvidencia(Evidencia evidencia) {
		this.evidencia = evidencia;
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
		Bula other = (Bula) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
