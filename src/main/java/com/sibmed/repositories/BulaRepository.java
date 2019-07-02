package com.sibmed.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sibmed.domain.Bula;
import com.sibmed.domain.Usuario;

@Repository
public interface BulaRepository extends JpaRepository<Bula, Integer>{
	
	Optional<Bula> findById(Integer id);
	List<Bula> findByPrincipioAtivo(String principioAtivo);
	List<Bula> findByUsuario(Usuario usuario);
	
	@Transactional(readOnly=true)
	Optional<Bula> findByDir(String dir);
}
