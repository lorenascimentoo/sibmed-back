package com.sibmed.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.sibmed.domain.Bula;

public class BulaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Prenchimento obrigatório")
	@Length(min=5,max=80,message="O tamanho deve ser entre 5 e 80 caracteres")
	private String nomeComercial;
	private String fabricante;
		
	public BulaDTO(){
	}
	public BulaDTO(Bula obj) {
		this.id = obj.getId();
		this.nomeComercial =obj.getNomeComercial();
		this.fabricante = obj.getFabricante();
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
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
	
}