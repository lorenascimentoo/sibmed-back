package com.sibmed.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sibmed.domain.Usuario;
import com.sibmed.domain.dto.UsuarioDTO;
import com.sibmed.domain.dto.UsuarioNewDTO;
import com.sibmed.repositories.UsuarioRepository;
import com.sibmed.services.exception.DataIntegrityException;
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
	public Usuario findByEmail(String email) {
		Usuario obj = repo.findByEmail(email);
		return obj;
	}
	
	public Usuario insert(Usuario obj) {
		obj.setId(null);
		obj.setSenha(pe.encode(obj.getSenha()));
		return repo.save(obj);
	}
	
	public Usuario fromDTO(UsuarioDTO objDto) {
		return new Usuario(objDto.getId(), objDto.getNome(), objDto.getEmail(), objDto.getSenha());
	}
	
	public Usuario fromDTO(UsuarioNewDTO objDTO) {
		return new Usuario(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getSenha());		
	}
	
	
	public Usuario update(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setSenha(obj.getSenha());
	}

	public void updateBula(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateDataBula(newObj, obj);
		repo.save(newObj);
	}

	private void updateDataBula(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setSenha(obj.getSenha());
		newObj.setBulas(obj.getBulas());
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir pois há bulas relacionadas");
		}
	}
}
