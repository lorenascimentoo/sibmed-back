package com.sibmed.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sibmed.domain.Bula;
import com.sibmed.domain.Evidencia;
import com.sibmed.domain.SituacaoPaciente;
import com.sibmed.domain.Usuario;
import com.sibmed.repositories.BulaRepository;
import com.sibmed.repositories.EvidenciaRepository;
import com.sibmed.repositories.SituacaoPacienteRepository;
import com.sibmed.repositories.UsuarioRepository;
import com.sibmed.services.utils.IndexadorService;

@Service
public class DBService {

	@Autowired
	private BulaRepository bulaRepository;

	@Autowired
	private EvidenciaRepository evidenciaRepository;

	@Autowired
	private SituacaoPacienteRepository sitPacienteRepository;

	@Autowired
	private IndexadorService indexService;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private UsuarioRepository usuarioRepostiry;

	public void instantiateTestDatabase() throws ParseException {
		
		Usuario u1 = new Usuario(null, "Lorena", "email@gmail.com", pe.encode("123"));
				
		Evidencia e1 = new Evidencia(null, "ABACAVIR", "RISCO C");
		SituacaoPaciente e1_sp1 = new SituacaoPaciente(null, "GRAVIDEZ", 
				"Não há estudos controlados",	e1);
		SituacaoPaciente e1_sp2 = new SituacaoPaciente(null, "LACTANTE", 
				"Contra-indicada para evitar transmissão vertical do vírus para o RN.",e1);
		e1.getSituacaoPaciente().addAll(Arrays.asList(e1_sp1, e1_sp2));
		
		
		Evidencia e2 = new Evidencia(null, "ACETAMINOFENO", "RISCO B/D");
		SituacaoPaciente e2_sp1 = new SituacaoPaciente(null, "GRAVIDEZ",
				"Doses terapêuticas de acetaminofeno são compatíveis; consumo elevado por tempo prolongado pode provocar lesões hepáticas e renais nos organismos materno e fetal.",
				e2);
		SituacaoPaciente e2_sp2 = new SituacaoPaciente(null, "LACTANTE",
				"Compatível em doses habituais.", e2);
		e2.getSituacaoPaciente().addAll(Arrays.asList(e2_sp1, e2_sp2));
		
		
		Evidencia e3 = new Evidencia(null, "ACICLOVIR", "RISCO C");
		SituacaoPaciente e3_sp1 = new SituacaoPaciente(null, "GRAVIDEZ",
				"Não há estudos controlados.", e3);
		SituacaoPaciente e3_sp2 = new SituacaoPaciente(null, "LACTANTE",
				"contra-indicada para evitar transmissão vertical do vírus para o RN.", e3);
		e3.getSituacaoPaciente().addAll(Arrays.asList(e3_sp1, e3_sp2));
		
		
		Evidencia e4 = new Evidencia(null, "ATENOLOL", "RISCO D");
		SituacaoPaciente e4_sp1 = new SituacaoPaciente(null, "GRAVIDEZ",
				"Aumento da resistência vascular no binônio materno-fetal proporcional ao tempo de exposição. Segundo trimestre, redução do peso da placenta e crescimento intra-uterino restrito; terceiro trimestre, redução do peso da placenta. Avaliar risco/benefício.",
				e4);
		SituacaoPaciente e4_sp2 = new SituacaoPaciente(null, "LACTANTE",
				"Compatível em doses habituais.", e4);
		e4.getSituacaoPaciente().addAll(Arrays.asList(e4_sp1, e4_sp2));
		
		
		Bula b1 = new Bula(null, "nome", "principioAtivo", "fabricante", "indicacao", "contraIndicacao",
				"reacaoAdversa", "dir", e1,u1);

		Bula b2 = new Bula(null, "nome", "principioAtivo", "fabricante", "indicacao", "contraIndicacao",
				"reacaoAdversa", "dir2", e1,u1);
		
		e1.getBulas().addAll(Arrays.asList(b1, b2));
		u1.getBulas().addAll(Arrays.asList(b1, b2));
		
		
		
		
		usuarioRepostiry.save(u1);
		bulaRepository.saveAll(Arrays.asList(b1, b2));
		evidenciaRepository.saveAll(Arrays.asList(e1,e2,e3,e4));
		sitPacienteRepository.saveAll(Arrays.asList(e1_sp1, e1_sp2, e2_sp1, e2_sp2, e3_sp1,e3_sp2,e4_sp1,e4_sp2));

		indexService.indexaArquivosDoDiretorio();
	}
}
