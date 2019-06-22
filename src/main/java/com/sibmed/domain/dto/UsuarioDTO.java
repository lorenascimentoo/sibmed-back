package com.sibmed.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.sibmed.domain.Usuario;
import com.sibmed.validation.UsuarioUpdate;

@UsuarioUpdate
public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String email;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String senha;

	public UsuarioDTO(Usuario obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
