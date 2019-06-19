package com.sibmed.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sibmed.domain.Evidencia;

@Repository
public interface EvidenciaRepository extends JpaRepository<Evidencia, Integer>{
	
	Optional<Evidencia> findById(Integer id);
	
	Optional<Evidencia> findByPrincipioAtivo(String principioAtivo);

}
