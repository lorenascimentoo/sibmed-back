package com.sibmed.resources;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sibmed.domain.Bula;
import com.sibmed.domain.dto.BulaDTO;
import com.sibmed.services.BulaService;
import com.sibmed.services.exception.ObjectNotFoundException;
import com.sibmed.services.utils.ArquivoService;
import com.sibmed.services.utils.BuscadorService;
import com.sibmed.services.utils.IndexadorService;

@RestController
@RequestMapping(value="/bulas")
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

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<BulaDTO>> findAll() throws ObjectNotFoundException {
		List<Bula> list = bulaService.findAll();
		List<BulaDTO> listDTO = list.stream().map(obj -> new BulaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Bula> find(@PathVariable Integer id){
		Bula obj = bulaService.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/busca/{string}",method=RequestMethod.GET)
	public String findBula(@PathVariable String string) {
		buscaService.buscaComParser(string);
		return "ok";//ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadingPost(@RequestParam("file") MultipartFile[] uploadingFiles) throws IOException {
		for(MultipartFile uploadedFile : uploadingFiles) {
            File file = new File(uploadingDir + uploadedFile.getOriginalFilename());
            uploadedFile.transferTo(file);
            arqService.ExtrairPDF(file);                      
            Bula b = new Bula(null,
                		arqService.getNomeComercial(),
                		arqService.getPrincipioAtivo(),
                		arqService.getFabricante(),
                		arqService.getIndicacoes(),
                		arqService.getContraIndicacoes(),
                		arqService.getReacoesAdversas(),
                		file.getPath(),null);
           bulaService.insert(b); 
        }
		
		indexService.indexaArquivosDoDiretorio();
        return "Upload feito com sucesso!";
 }
}
