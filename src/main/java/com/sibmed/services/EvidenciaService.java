package com.sibmed.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sibmed.domain.Evidencia;
import com.sibmed.repositories.EvidenciaRepository;

@Service
public class EvidenciaService {
	
	@Autowired
	private EvidenciaRepository repo;
		
	public Evidencia findID(Integer id) {
		Optional<Evidencia> obj = repo.findById(id);
		return obj.orElse(null);
	}
	
	public Evidencia findPrincAtivo(String princAtivo) {
		Optional<Evidencia> obj = repo.findByPrincipioAtivo(princAtivo);
		return obj.orElse(null);
	}
	
	public Evidencia insert(Evidencia obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public void update(Evidencia obj) {
		Evidencia newObj= findID(obj.getId());
		updateData(newObj, obj);
		repo.save(newObj);
	}
	
	private void updateData(Evidencia newObj, Evidencia obj) {
		newObj.setId(obj.getId());
		newObj.setCategoria(obj.getCategoria());
		newObj.setPrincipioAtivo(obj.getPrincipioAtivo());
		newObj.setBulas(obj.getBulas());
		newObj.setSituacaoPaciente(obj.getSituacaoPaciente());
	}
}
