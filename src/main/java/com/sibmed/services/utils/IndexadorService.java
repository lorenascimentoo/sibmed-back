package com.sibmed.services.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexadorService {
	public static final String diretorioParaIndexar = System.getProperty("user.dir") + "/arquivosDir/";
	public static final String diretorioDosIndices = System.getProperty("user.dir") + "/indexDir/";

	private IndexWriter writer;

	@Autowired
	private ArquivoService arqService;

	private static final Logger logger = LoggerFactory.getLogger(IndexadorService.class);

	public void indexaArquivosDoDiretorio() {
		try {

			IndexWriter writer = criarEscritor();
			this.writer = writer;
			long inicio = System.currentTimeMillis();
			indexaArquivosDoDiretorio(new File(diretorioParaIndexar));

			writer.commit();
			writer.close();
			long fim = System.currentTimeMillis();
			logger.info("Tempo para indexar: " + ((fim - inicio) / 1000) + "s");
		} catch (IOException e) {
			logger.error("Erro ao indexar arquivo.");
		}
	}

	public void indexaArquivosDoDiretorio(File raiz) {
		FilenameFilter filtro = new FilenameFilter() {
			public boolean accept(File arquivo, String nome) {
				if (nome.toLowerCase().endsWith(".pdf")) {
					return true;
				}
				return false;
			}
		};
		for (File arquivo : raiz.listFiles(filtro)) {
			if (arquivo.isFile()) {
				StringBuffer msg = new StringBuffer();
				msg.append("Indexando o arquivo ");
				msg.append(arquivo.getAbsoluteFile());
				msg.append(", ");
				msg.append(arquivo.length() / 1000);
				msg.append("kb");
				logger.info(msg.toString());

				try {
					arqService.ExtrairPDF(arquivo);
					logger.info(arqService.getNomeComercial());
					logger.info(arqService.getPrincipioAtivo());
					indexaArquivo(arquivo, arqService.getTextoExtraido().toLowerCase(), arqService.getNomeComercial(),
							arqService.getPrincipioAtivo(), arqService.getFabricante(), arqService.getIndicacoes().toString().toLowerCase(),
							arqService.getContraIndicacoes().toString().toLowerCase(), arqService.getReacoesAdversas().toString().toLowerCase());

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				indexaArquivosDoDiretorio(arquivo);
				System.out.println("O arquivo " + raiz + " foi indexado!");
			}
		}
	}

	private void indexaArquivo(File arquivo, String textoExtraido, String nomeComercial, String principioAtivo,
			String fabricante, String indicacoes, String contraIndicacoes, String reacoesAdversas) {
		SimpleDateFormat formatador = new SimpleDateFormat("DDMMYYYY");
		String ultimaModificacao = formatador.format(arquivo.lastModified());
		
		Document documento = new Document();
		documento.add(new StringField("UltimaModificacao", ultimaModificacao, Field.Store.YES));
		documento.add(new StringField("Caminho", arquivo.getAbsolutePath(), Field.Store.YES));
		documento.add(new TextField("nomeComercial", nomeComercial, Field.Store.YES));
		documento.add(new TextField("principioAtivo", principioAtivo, Field.Store.YES));
		documento.add(new TextField("fabricante", fabricante, Field.Store.YES));
		documento.add(new TextField("indicacoes", indicacoes, Field.Store.YES));
		documento.add(new TextField("contraIndicacoes", contraIndicacoes, Field.Store.YES));
		documento.add(new TextField("reacoesAdversas", reacoesAdversas, Field.Store.YES));
		documento.add(new TextField("Texto", textoExtraido, Field.Store.YES));
		try {
			this.writer.addDocument(documento);
		} catch (IOException e) {
			logger.error(e.toString());
		}
	}

	private static IndexWriter criarEscritor() throws IOException {
		File diretorio = new File(diretorioDosIndices);
		apagaIndices(diretorio);
		logger.info("Diretório do índice: " + diretorioDosIndices);
		FSDirectory dir = FSDirectory.open(Paths.get(diretorioDosIndices));
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		IndexWriter writer = new IndexWriter(dir, config);
		return writer;
	}
	
	private static void apagaIndices(File diretorio) {
	    if (diretorio.exists()) {
	      File arquivos[] = diretorio.listFiles();
	      for (File arquivo : arquivos) {
	        arquivo.delete();
	      }
	    }
	}

}
