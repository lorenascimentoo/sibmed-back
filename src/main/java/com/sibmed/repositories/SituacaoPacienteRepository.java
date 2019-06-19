package com.sibmed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sibmed.domain.SituacaoPaciente;

@Repository
public interface SituacaoPacienteRepository extends JpaRepository<SituacaoPaciente, Integer>{
	
}
