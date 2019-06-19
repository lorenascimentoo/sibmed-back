package com.sibmed.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sibmed.domain.Bula;
import com.sibmed.domain.Evidencia;
import com.sibmed.repositories.BulaRepository;

@Service
public class BulaService {
	
	@Autowired
	private BulaRepository repo;
	
	@Autowired
	private EvidenciaService evidenciaService;
	
	public Bula find(Integer id) {
		Optional<Bula> obj = repo.findById(id);
		return obj.orElse(null);
	}
	
	public Bula insert(Bula obj){
		obj.setId(null);
		Evidencia e = evidenciaService.findPrincAtivo(obj.getPrincipioAtivo());
		Evidencia b= new Evidencia(e.getId(), e.getPrincipioAtivo(), e.getCategoria());
		obj.setEvidencia(e);
		obj.s
		return repo.save(obj);
	}
	
	public List<Bula> findAll(){
		return repo.findAll();
	}
}
