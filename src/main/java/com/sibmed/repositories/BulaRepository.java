package com.sibmed.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sibmed.domain.Bula;

@Repository
public interface BulaRepository extends JpaRepository<Bula, Integer>{
	
	Optional<Bula> findById(Integer id);
	Optional<Bula> findByPrincipioAtivo(String principioAtivo);
	
	@Transactional(readOnly=true)
	Optional<Bula> findByDir(String dir);
}
