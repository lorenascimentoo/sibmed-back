package com.sibmed.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sibmed.domain.Bula;

@Repository
public interface BulaRepository extends JpaRepository<Bula, Integer>{
	
	Optional<Bula> findById(Integer id);
}
