package com.sibmed.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sibmed.domain.Bula;
import com.sibmed.domain.Evidencia;

@Service
public class Associacao{
	
	@Autowired
	private BulaService bService;
	
	@Autowired
	private EvidenciaService eService;
	
	public void associar(String princAtivo) {
		Bula objBula = bService.findPrincAtivo(princAtivo);
		Evidencia objEvidencia = eService.findPrincAtivo(princAtivo);
		
		try {
			if(objBula.getPrincipioAtivo()!=null && objEvidencia.getPrincipioAtivo()!=null) {
						updateData(objEvidencia, objBula);
			}
		} catch (Exception e) {
			System.out.println("Não foi possível associar.");
		}
	}
	
	private void updateData(Evidencia newObj, Bula b) {
		b.setEvidencia(newObj);
		newObj.getBulas().add(b);
		
		bService.update(b);
		eService.update(newObj);	
	}

}

