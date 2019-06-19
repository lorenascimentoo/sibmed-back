package com.sibmed.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sibmed.domain.Evidencia;
import com.sibmed.services.EvidenciaService;

@RestController
@RequestMapping(value = "/evidencias")
public class EvidenciaResource {
	@Autowired
	private EvidenciaService evidService;

	@RequestMapping(value="/{string}",method=RequestMethod.GET)
	public ResponseEntity<Evidencia> findPrincAtivo(@PathVariable String string) {
		Evidencia obj = evidService.findPrincAtivo(string);
		return ResponseEntity.ok().body(obj);
	}
}
