package com.sibmed.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.sibmed.services.validation.UsuarioInsert;

@UsuarioInsert
public class UsuarioNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String email;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String senha;

	public UsuarioNewDTO(String nome,String email, String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
