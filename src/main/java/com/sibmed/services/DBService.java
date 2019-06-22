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
import com.sibmed.domain.enums.TipoCategoria;
import com.sibmed.domain.enums.TipoEstadoPaciente;
import com.sibmed.repositories.BulaRepository;
import com.sibmed.repositories.EvidenciaRepository;
import com.sibmed.repositories.SituacaoPacienteRepository;
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
	private UsuarioService userService;

	public void instantiateTestDatabase() throws ParseException {
		
		Usuario u1 = new Usuario(null, "Lorena", "email", pe.encode("123"));
				
		Evidencia e1 = new Evidencia(null, "principioAtivo", TipoCategoria.RISCO_A);
		SituacaoPaciente sp1 = new SituacaoPaciente(null, TipoEstadoPaciente.GRAVIDEZ, "Liberado a todos os periodos",
				e1);
		SituacaoPaciente sp2 = new SituacaoPaciente(null, TipoEstadoPaciente.LACTANTE, "Liberado a todos os periodos",
				e1);
		e1.getSituacaoPaciente().addAll(Arrays.asList(sp1, sp2));
		
		Bula b1 = new Bula(null, "nome", "principioAtivo", "fabricante", "indicacao", "contraIndicacao",
				"reacaoAdversa", "dir", e1,u1);

		Bula b2 = new Bula(null, "nome", "principioAtivo", "fabricante", "indicacao", "contraIndicacao",
				"reacaoAdversa", "dir2", e1,u1);
		
		e1.getBulas().addAll(Arrays.asList(b1, b2));
		u1.getBulas().addAll(Arrays.asList(b1, b2));
		
		Evidencia e2 = new Evidencia(null, "ACICLOVIR", TipoCategoria.RISCO_C);
		SituacaoPaciente e2_sp1 = new SituacaoPaciente(null, TipoEstadoPaciente.GRAVIDEZ, "Não há estudos controlados",
				e2);
		SituacaoPaciente e2_sp2 = new SituacaoPaciente(null, TipoEstadoPaciente.LACTANTE,
				"Contra-indicada para evitar transmissão vertical do vírus para o RN.", e2);
		e2.getSituacaoPaciente().addAll(Arrays.asList(e2_sp1, e2_sp2));
		
		
		
		userService.insert(u1);
		bulaRepository.saveAll(Arrays.asList(b1, b2));
		evidenciaRepository.saveAll(Arrays.asList(e1, e2));
		sitPacienteRepository.saveAll(Arrays.asList(sp1, sp2, e2_sp1, e2_sp2));

		indexService.indexaArquivosDoDiretorio();
	}
}
