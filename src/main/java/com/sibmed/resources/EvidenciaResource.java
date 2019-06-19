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
@RequestMapping(value="/evidencias")
public class EvidenciaResource {
	@Autowired
	private EvidenciaService evidService;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		Evidencia obj = evidService.findID(id);
		return ResponseEntity.ok().body(obj);
	}
}
