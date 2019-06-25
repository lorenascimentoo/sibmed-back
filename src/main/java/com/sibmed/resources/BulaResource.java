package com.sibmed.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sibmed.domain.Bula;
import com.sibmed.domain.dto.BulaDTO;
import com.sibmed.domain.dto.BulaNewDTO;
import com.sibmed.services.BulaService;
import com.sibmed.services.exception.JdbcSQLException;
import com.sibmed.services.exception.ObjectNotFoundException;
import com.sibmed.services.utils.ArquivoService;
import com.sibmed.services.utils.BuscadorService;
import com.sibmed.services.utils.IndexadorService;

@RestController
@RequestMapping(value = "/bulas")
public class BulaResource {
	public static final String uploadingDir = System.getProperty("user.dir") + "/arquivosDir/";

	@Autowired
	private BulaService bulaService;

	@Autowired
	private ArquivoService arqService;

	@Autowired
	private IndexadorService indexService;

	@Autowired
	private BuscadorService buscaService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<BulaDTO>> findAll() throws ObjectNotFoundException {
		List<Bula> list = bulaService.findAll();
		List<BulaDTO> listDTO = list.stream().map(obj -> new BulaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PreAuthorize("hasAnyRole('USUARIO')")
	@RequestMapping(value = "/usuario",method = RequestMethod.GET)
	public ResponseEntity<List<BulaDTO>> findUser() throws ObjectNotFoundException {
		List<Bula> list = bulaService.findUser();
		List<BulaDTO> listDTO = list.stream().map(obj -> new BulaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Bula> find(@PathVariable Integer id) {
		Bula obj = bulaService.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/busca", method = RequestMethod.GET)
	public ResponseEntity<List<BulaDTO>> findBula(
			@RequestParam(value = "indicacao", defaultValue = "null") String indicacao,
			@RequestParam(value = "contraIndicacao", defaultValue = "null") String contraIndicacao,
			@RequestParam(value = "reacaoAdversa", defaultValue = "null") String reacaoAdversa){
		buscaService.buscaComParser(indicacao, contraIndicacao, reacaoAdversa);
		List<Bula> list = buscaService.getList();
		List<BulaDTO> listDTO = list.stream().map(obj -> new BulaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PreAuthorize("hasAnyRole('USUARIO')")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<List<String>> uploadingPost(@RequestParam("file") MultipartFile[] uploadingFiles)
			throws IOException, JdbcSQLException {
		List<String>resultado = new ArrayList<>();
		for (MultipartFile uploadedFile : uploadingFiles) {
			File file = new File(uploadingDir + uploadedFile.getOriginalFilename());
			uploadedFile.transferTo(file);
			arqService.ExtrairPDF(file);
			BulaNewDTO bulaDTO = new BulaNewDTO(null, arqService.getNomeComercial(), arqService.getPrincipioAtivo(),
					arqService.getFabricante(), arqService.getIndicacoes(), arqService.getContraIndicacoes(),
					arqService.getReacoesAdversas(), file.getPath(), null, null);
			Bula b = bulaService.fromDTO(bulaDTO);
			b=bulaService.insert(b);
			if (b != null) {
				resultado.add("Upload realizado com sucesso! Arquivo: " + file.getPath());
			} else {
				resultado.add("Bula já inserida no sistema!Arquivo: " + file.getPath());
			}	
		}
		
		indexService.indexaArquivosDoDiretorio();
		return ResponseEntity.ok().body(resultado);
	}
	
	@PreAuthorize("hasAnyRole('USUARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		bulaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
