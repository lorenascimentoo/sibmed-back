package com.sibmed.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sibmed.domain.Bula;
import com.sibmed.repositories.BulaRepository;

@Service
public class BulaService {
	
	
	
	@Autowired
	private BulaRepository repo;
	
	
	public Bula find(Integer id) {
		Optional<Bula> obj = repo.findById(id);
		return obj.orElse(null);
	}
	
	public Bula insert(Bula obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	
}
