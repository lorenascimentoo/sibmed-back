package com.sibmed.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sibmed.domain.Bula;
import com.sibmed.domain.Evidencia;
import com.sibmed.domain.Usuario;
import com.sibmed.domain.dto.BulaNewDTO;
import com.sibmed.repositories.BulaRepository;
import com.sibmed.services.exception.DataIntegrityException;
import com.sibmed.services.exception.ObjectNotFoundException;
import com.sibmed.services.utils.ArquivoService;

@Service
public class BulaService {
	private static final Logger logger = LoggerFactory.getLogger(BulaService.class);
	@Autowired
	private EvidenciaService eService;

	@Autowired
	private ArquivoService arqService;

	@Autowired
	private UsuarioService userService;

	@Autowired
	private BulaRepository repo;

	public Bula find(Integer id) {
		Optional<Bula> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Bula não encontrada! Id:" + id + ", Tipo: " + Bula.class.getName()));
	}
	
	public Bula insert(Bula obj) {
		obj.setId(null);
		Usuario objUsuario = obj.getUsuario();
		Evidencia objEvidencia = obj.getEvidencia();
		repo.save(obj);
		objEvidencia.getBulas().add(obj);
		objUsuario.getBulas().add(obj);
		eService.update(objEvidencia);
		userService.updateBula(objUsuario);
		return obj;
	}
	
	public Bula fromDTO(BulaNewDTO obj, Integer id) {
		logger.info("Iniciando inserção");
		Usuario objUsuario = userService.find(id);
		Evidencia objEvidencia = eService.findPrincAtivo(obj.getPrincipioAtivo());
		logger.info("Evidencia: "+ objEvidencia);
		logger.info("Evidencia_id: "+ objEvidencia.getId());
		logger.info("Evidencia_principioAtivo: "+ objEvidencia.getPrincipioAtivo());
		logger.info("Usuário encontrado! id:"+ objUsuario.getId());
		if (objEvidencia != null && objUsuario != null){
			obj.setEvidencia(objEvidencia);
			obj.setUsuario(objUsuario);	
		}
		return new Bula(null,
				obj.getNomeComercial(),
				obj.getPrincipioAtivo(),
				obj.getFabricante(), obj.getIndicacao(),
				obj.getContraIndicacao(), obj.getReacaoAdversa(), obj.getDir(),
				obj.getEvidencia(), obj.getUsuario());
	}

	public void delete(Integer id) {
		Bula obj = find(id);
		try {
			Evidencia objEvidencia = eService.findPrincAtivo(obj.getPrincipioAtivo());
			objEvidencia.getBulas().remove(obj);
			Usuario objUsuario = userService.find(obj.getUsuario().getId());
			objUsuario.getBulas().remove(obj);
			arqService.apagaArquivo(obj.getDir());
			userService.update(objUsuario);
			eService.update(objEvidencia);
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível excluir a bula");
		}
	}

	public List<Bula> findAll() {
		return repo.findAll();
	}

	public Bula findPrincAtivo(String princAtivo) {
		Optional<Bula> obj = repo.findByPrincipioAtivo(princAtivo);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Bula não encontrada! Principio Ativo:" + princAtivo + ", Tipo: " + Bula.class.getName()));
	}

	public Bula findDiretorio(String dir) {
		Optional<Bula> obj = repo.findByDir(dir);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Arquivo não encontrado!"));
	}

	public void update(Bula obj) {
		Bula newObj = find(obj.getId());
		updateData(newObj, obj);
		repo.save(newObj);
	}

	private void updateData(Bula newObj, Bula obj) {
		newObj.setEvidencia(obj.getEvidencia());
	}
}
