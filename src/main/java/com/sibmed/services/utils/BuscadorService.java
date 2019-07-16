package com.sibmed.services.utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sibmed.domain.Bula;
import com.sibmed.services.BulaService;

@Service
public class BuscadorService {

	public static final String diretorioDosIndices = System.getProperty("user.dir") + "/indexDir/";
	private static final Logger logger = LoggerFactory.getLogger(BuscadorService.class);

	private List<Bula> list = new ArrayList<>();

	public List<Bula> getList() {
		return list;
	}

	public void setList(List<Bula> list) {
		this.list = list;
	}

	@Autowired
	private BulaService bulaService;

	public void buscaComParser(String indicacao, String contraIndicacao, String reacaoAdversa) {

		try {
			list.clear();
			IndexSearcher buscador = criarLeitor();
			logger.info("Dados recebidos: " + indicacao + ", " + contraIndicacao + ", " + reacaoAdversa);

			if (!indicacao.contains("null") && contraIndicacao.contains("null") && reacaoAdversa.contains("null")) {
				logger.info("Iniciando a procura por indicacao: " + indicacao);
				buscaIndicacao(indicacao, buscador);

			} else if (indicacao.contains("null") && !contraIndicacao.contains("null")
					&& reacaoAdversa.contains("null")) {
				logger.info("Iniciando a procura por contra indicacao: " + contraIndicacao);
				buscaContraIndicacao(contraIndicacao, buscador);

			} else if (indicacao.contains("null") && contraIndicacao.contains("null")
					&& !reacaoAdversa.contains("null")) {
				logger.info("Iniciando a procura por reação adversa: " + reacaoAdversa);
				buscaReacaoAdversa(reacaoAdversa, buscador);

			} else if (!indicacao.contains("null") && !contraIndicacao.contains("null")
					&& !reacaoAdversa.contains("null")) {
				logger.info("Iniciando a procura por dados recebidos: " + indicacao + ", " + contraIndicacao + ", "
						+ reacaoAdversa);
				buscaBooleana(indicacao, contraIndicacao, reacaoAdversa, buscador);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	public void buscaPrincAtivo(String principioAt) throws IOException {
		list.clear();
		IndexSearcher buscador = criarLeitor();

		try {
			QueryParser parser = new QueryParser("principioAtivo", new StandardAnalyzer());
			Query consulta = parser.parse(principioAt);
			long inicio = System.currentTimeMillis();
			TopDocs resultado = buscador.search(consulta, 100);
			long fim = System.currentTimeMillis();
			int totalDeOcorrencias = resultado.totalHits;
			logger.info("Executando buscaIndicacao");
			logger.info("Total de documentos encontrados:" + totalDeOcorrencias);
			logger.info("Tempo total para busca: " + (fim - inicio) + "ms");

			for (ScoreDoc sd : resultado.scoreDocs) {
				Document documento = buscador.doc(sd.doc);
				logger.info("Caminho: " + documento.get("Caminho"));
				logger.info("Última modificação: " + documento.get("UltimaModificacao"));
				logger.info("Nome Comercial: " + documento.get("nomeComercial"));
				logger.info("Principio Ativo: " + documento.get("principioAtivo"));
				logger.info("Fabricante: " + documento.get("fabricante"));
				logger.info("Score:" + sd.score);
				logger.info("--------");

				logger.info("Iniciando a busca por bula a partir do diretório");
				logger.info("Item: " + documento.get("Caminho"));
				Bula obj = bulaService.findDiretorio(documento.get("Caminho"));
				logger.info("Bula encontrada! Id: " + obj.getId());
				list.add(obj);
			}

		} catch (Exception e) {
			logger.error(e.toString());
		}

	}

	private void buscaIndicacao(String parametro, IndexSearcher buscador) throws Exception {
		try {

			QueryParser parser = new QueryParser("indicacoes", new StandardAnalyzer());
			Query consulta = parser.parse(parametro);
			long inicio = System.currentTimeMillis();

			TopDocs resultado = buscador.search(consulta, 100);

			long fim = System.currentTimeMillis();
			int totalDeOcorrencias = resultado.totalHits;
			logger.info("Executando buscaIndicacao");
			logger.info("Total de documentos encontrados:" + totalDeOcorrencias);
			logger.info("Tempo total para busca: " + (fim - inicio) + "ms");

			for (ScoreDoc sd : resultado.scoreDocs) {
				Document documento = buscador.doc(sd.doc);
				logger.info("Caminho: " + documento.get("Caminho"));
				logger.info("Última modificação: " + documento.get("UltimaModificacao"));
				logger.info("Nome Comercial: " + documento.get("nomeComercial"));
				logger.info("Principio Ativo: " + documento.get("principioAtivo"));
				logger.info("Fabricante: " + documento.get("fabricante"));
				logger.info("Score:" + sd.score);
				logger.info("--------");

				logger.info("Iniciando a busca por bula a partir do diretório");
				logger.info("Item: " + documento.get("Caminho"));
				Bula obj = bulaService.findDiretorio(documento.get("Caminho"));
				logger.info("Bula encontrada! Id: " + obj.getId());
				list.add(obj);

			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	private void buscaContraIndicacao(String parametro, IndexSearcher buscador) throws Exception {
		try {
			QueryParser parser = new QueryParser("contraIndicacoes", new StandardAnalyzer());
			Query consulta = parser.parse(parametro);
			long inicio = System.currentTimeMillis();

			TopDocs resultado = buscador.search(consulta, 100);

			long fim = System.currentTimeMillis();
			int totalDeOcorrencias = resultado.totalHits;
			logger.info("Executando buscaContraIndicacao");
			logger.info("Total de documentos encontrados:" + totalDeOcorrencias);
			logger.info("Tempo total para busca: " + (fim - inicio) + "ms");
			// {5}
			for (ScoreDoc sd : resultado.scoreDocs) {
				Document documento = buscador.doc(sd.doc);
				logger.info("Caminho: " + documento.get("Caminho"));
				logger.info("Última modificação: " + documento.get("UltimaModificacao"));
				logger.info("Nome Comercial: " + documento.get("nomeComercial"));
				logger.info("Principio Ativo: " + documento.get("principioAtivo"));
				logger.info("Fabricante: " + documento.get("fabricante"));
				logger.info("Score:" + sd.score);
				logger.info("--------");

				logger.info("Iniciando a busca por bula a partir do diretório");
				logger.info("Item: " + documento.get("Caminho"));
				Bula obj = bulaService.findDiretorio(documento.get("Caminho"));
				logger.info("Bula encontrada! Id: " + obj.getId());
				list.add(obj);

			}
		} catch (Exception e) {
			logger.error(e.toString());
		}

	}

	private void buscaReacaoAdversa(String parametro, IndexSearcher buscador) throws Exception {
		try {
			QueryParser parser = new QueryParser("reacoesAdversas", new StandardAnalyzer());
			Query consulta = parser.parse(parametro);
			long inicio = System.currentTimeMillis();

			TopDocs resultado = buscador.search(consulta, 100);

			long fim = System.currentTimeMillis();
			int totalDeOcorrencias = resultado.totalHits;
			logger.info("Executando buscaReacaoAdversa");
			logger.info("Total de documentos encontrados:" + totalDeOcorrencias);
			logger.info("Tempo total para busca: " + (fim - inicio) + "ms");
			// {5}
			for (ScoreDoc sd : resultado.scoreDocs) {
				Document documento = buscador.doc(sd.doc);
				logger.info("Caminho: " + documento.get("Caminho"));
				logger.info("Última modificação: " + documento.get("UltimaModificacao"));
				logger.info("Nome Comercial: " + documento.get("nomeComercial"));
				logger.info("Principio Ativo: " + documento.get("principioAtivo"));
				logger.info("Fabricante: " + documento.get("fabricante"));
				logger.info("Score:" + sd.score);
				logger.info("--------");

				logger.info("Iniciando a busca por bula a partir do diretório");
				logger.info("Item: " + documento.get("Caminho"));
				Bula obj = bulaService.findDiretorio(documento.get("Caminho"));
				logger.info("Bula encontrada! Id: " + obj.getId());
				list.add(obj);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	private void buscaBooleana(String par1, String par2, String par3, IndexSearcher buscador) throws Exception {
		try {

			String[] query = {par1, par2, par3};
			String[] fields = { "indicacoes", "contraIndicacoes", "reacoesAdversas" };
			BooleanClause.Occur[] flags = { BooleanClause.Occur.MUST, BooleanClause.Occur.MUST_NOT,
					BooleanClause.Occur.MUST };
			Query consulta = MultiFieldQueryParser.parse(query, fields, flags, new StandardAnalyzer());

			long inicio = System.currentTimeMillis();

			TopDocs resultado = buscador.search(consulta, 100);

			long fim = System.currentTimeMillis();
			int totalDeOcorrencias = resultado.totalHits;
			logger.info("Executando buscaBooleana");
			logger.info("Total de documentos encontrados:" + totalDeOcorrencias);
			logger.info("Tempo total para busca: " + (fim - inicio) + "ms");
			// {5}
			for (ScoreDoc sd : resultado.scoreDocs) {
				Document documento = buscador.doc(sd.doc);
				logger.info("Caminho: " + documento.get("Caminho"));
				logger.info("Última modificação: " + documento.get("UltimaModificacao"));
				logger.info("Nome Comercial: " + documento.get("nomeComercial"));
				logger.info("Principio Ativo: " + documento.get("principioAtivo"));
				logger.info("Fabricante: " + documento.get("fabricante"));
				logger.info("Score:" + sd.score);
				logger.info("--------");

				logger.info("Iniciando a busca por bula a partir do diretório");
				logger.info("Item: " + documento.get("Caminho"));
				Bula obj = bulaService.findDiretorio(documento.get("Caminho"));
				logger.info("Bula encontrada! Id: " + obj.getId());
				list.add(obj);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}

	}

	private static IndexSearcher criarLeitor() throws IOException {
		Directory dir = FSDirectory.open(Paths.get(diretorioDosIndices));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		return searcher;
	}

}
