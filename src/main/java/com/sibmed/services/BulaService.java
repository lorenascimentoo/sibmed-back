package com.sibmed.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sibmed.domain.Bula;
import com.sibmed.domain.Evidencia;
import com.sibmed.repositories.BulaRepository;

@Service
public class BulaService {
	private static final Logger logger = LoggerFactory.getLogger(BulaService.class);
	@Autowired
	private EvidenciaService eService;
	
	@Autowired
	private BulaRepository repo;
		
	public Bula find(Integer id) {
		Optional<Bula> obj = repo.findById(id);
		return obj.orElse(null);
	}
	
	public Bula insert(Bula obj){
		logger.info("Iniciando inserção");
		obj.setId(null);
		Evidencia objEvidencia = eService.findPrincAtivo(obj.getPrincipioAtivo());
		logger.info("Evidencia: ", objEvidencia);
		logger.info("Evidencia_id: ", objEvidencia.getId());
		logger.info("Evidencia_principioAtivo: ", objEvidencia.getPrincipioAtivo());
		if (objEvidencia != null) {
			obj.setEvidencia(objEvidencia);
		}
		repo.save(obj);
		
		objEvidencia.getBulas().add(obj);
		eService.update(objEvidencia);
		
		return obj;
	}
	
	public List<Bula> findAll(){
		return repo.findAll();
	}
	
	public Bula findPrincAtivo(String princAtivo) {
		Optional<Bula> obj = repo.findByPrincipioAtivo(princAtivo);
		return obj.orElse(null);
	}
	
	public void update(Bula obj) {
		Bula newObj= find(obj.getId());
		updateData(newObj, obj);
		repo.save(newObj);
	}
	
	private void updateData(Bula newObj, Bula obj) {
		newObj.setEvidencia(obj.getEvidencia());
	}
}
