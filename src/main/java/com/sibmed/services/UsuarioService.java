package com.sibmed.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sibmed.domain.Usuario;
import com.sibmed.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repo;

	@Autowired
	private BCryptPasswordEncoder pe;

	public Usuario find(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElse(null);
	}

	public Usuario insert(Usuario obj) {
		obj.setId(null);
		obj.setSenha(pe.encode(obj.getSenha()));
		return repo.save(obj);
	}

	public void update(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
		repo.save(newObj);
	}

	private void updateData(Usuario newObj, Usuario obj) {

		if (obj.getNome() != null || !obj.getNome().isEmpty()) {
			newObj.setNome(obj.getNome());
		} else if (obj.getEmail() != null || !obj.getEmail().isEmpty()) {
			newObj.setEmail(obj.getEmail());
		} else if (obj.getSenha() != null || !obj.getSenha().isEmpty()) {
			newObj.setSenha(obj.getSenha());
		}
	}

	public void updateBula(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateDataBula(newObj, obj);
		repo.save(newObj);
	}

	private void updateDataBula(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setBulas(obj.getBulas());
	}

}
