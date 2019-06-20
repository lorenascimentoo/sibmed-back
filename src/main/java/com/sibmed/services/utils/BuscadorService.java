package com.sibmed.services.utils;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BuscadorService {

	public static final String diretorioDosIndices = System.getProperty("user.dir") + "/indexDir/";
	private static final Logger logger = LoggerFactory.getLogger(BuscadorService.class);
	
	public void buscaComParser(String indicacao) {
		
			Directory diretorio;
			try {
				diretorio = new SimpleFSDirectory(new File(diretorioDosIndices));
				IndexReader leitor = IndexReader.open(diretorio);
				// {2}
				IndexSearcher buscador = new IndexSearcher(leitor);
				Analyzer analisador = new StandardAnalyzer(Version.LUCENE_36);
				buscaIndicacao(indicacao, buscador, analisador);
			} catch (Exception e) {
				logger.error(e.toString());
			}
	}
	
	public void buscaIndicacao(String parametro, IndexSearcher buscador, Analyzer analisador){
		try {
		QueryParser parser = new QueryParser(Version.LUCENE_36, "principioAtivo", analisador);
		Query consulta = parser.parse(parametro);
		long inicio = System.currentTimeMillis();
		// {4}
		TopDocs resultado = buscador.search(consulta, 100);			
		
		long fim = System.currentTimeMillis();
		int totalDeOcorrencias = resultado.totalHits;
		logger.info("Total de documentos encontrados:" + totalDeOcorrencias);
		logger.info("Tempo total para busca: " + (fim - inicio) + "ms");
		// {5}
		for (ScoreDoc sd : resultado.scoreDocs) {
			Document documento = buscador.doc(sd.doc);
			logger.info("Caminho: " + documento.get("Caminho"));
			logger.info("Última modificação: " + documento.get("UltimaModificacao"));
			logger.info("Nome Comercial: " + documento.get("nomeComercial"));
			logger.info("Principio Ativo: " + documento.get("principioAtivo"));
			String princAtivo = documento.get("principioAtivo");
			logger.info("Resgatando principio ativo:" + princAtivo);
			logger.info("Fabricante: " + documento.get("fabricante"));
			logger.info("Score:" + sd.score);
			logger.info("--------");
		}
		buscador.close();
	} catch (Exception e) {
		logger.error(e.toString());
	}
	}
	
	
	
	
}
