package com.sibmed.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.sibmed.domain.Evidencia;
import com.sibmed.domain.Usuario;
import com.sibmed.services.validation.BulaInsert;


@BulaInsert
public class BulaNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Integer id;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String nomeComercial;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String principioAtivo;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String fabricante;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String indicacao;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String contraIndicacao;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String reacaoAdversa;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String dir;
	@NotEmpty(message = "Preenchimento obrigatório")
	private Evidencia evidencia;
	@NotEmpty(message = "Preenchimento obrigatório")
	private Usuario usuario;
	
	public BulaNewDTO(@NotEmpty(message = "Preenchimento obrigatório") Integer id,
			@NotEmpty(message = "Preenchimento obrigatório") String nomeComercial,
			@NotEmpty(message = "Preenchimento obrigatório") String principioAtivo,
			@NotEmpty(message = "Preenchimento obrigatório") String fabricante,
			@NotEmpty(message = "Preenchimento obrigatório") String indicacao,
			@NotEmpty(message = "Preenchimento obrigatório") String contraIndicacao,
			@NotEmpty(message = "Preenchimento obrigatório") String reacaoAdversa,
			@NotEmpty(message = "Preenchimento obrigatório") String dir,
			@NotEmpty(message = "Preenchimento obrigatório") Evidencia evidencia,
			@NotEmpty(message = "Preenchimento obrigatório") Usuario usuario) {
		super();
		this.id = id;
		this.nomeComercial = nomeComercial;
		this.principioAtivo = principioAtivo;
		this.fabricante = fabricante;
		this.indicacao = indicacao;
		this.contraIndicacao = contraIndicacao;
		this.reacaoAdversa = reacaoAdversa;
		this.dir = dir;
		this.evidencia = evidencia;
		this.usuario = usuario;
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

	public String getReacaoAdversa() {
		return reacaoAdversa;
	}

	public void setReacaoAdversa(String reacaoAdversa) {
		this.reacaoAdversa = reacaoAdversa;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Evidencia getEvidencia() {
		return evidencia;
	}

	public void setEvidencia(Evidencia evidencia) {
		this.evidencia = evidencia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}	
	
}