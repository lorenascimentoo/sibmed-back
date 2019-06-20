package com.sibmed.services.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexadorService  {
	public static final String diretorioParaIndexar = System.getProperty("user.dir") + "/arquivosDir/";
	public static final String diretorioDosIndices = System.getProperty("user.dir") + "/indexDir/";
	
	private IndexWriter writer;
	
	@Autowired
	private ArquivoService arqService;
	
	private static final Logger logger = LoggerFactory.getLogger(IndexadorService.class);
	
	public void indexaArquivosDoDiretorio(){
	    try {
	      File diretorio = new File(diretorioDosIndices);
	      apagaIndices(diretorio);

	      Directory d = new SimpleFSDirectory(diretorio);
	      logger.info("Diretório do índice: " + diretorioDosIndices);
	   
	      Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
	    
	      IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36,
	          analyzer);
	   
	      writer = new IndexWriter(d, config);
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

	  private void apagaIndices(File diretorio) {
	    if (diretorio.exists()) {
	      File arquivos[] = diretorio.listFiles();
	      for (File arquivo : arquivos) {
	        arquivo.delete();
	      }
	    }
	  }

	public void indexaArquivosDoDiretorio(File raiz) {
	    FilenameFilter filtro = new FilenameFilter() {
	      public boolean accept(File arquivo, String nome) {
	        if (nome.toLowerCase().endsWith(".pdf")){
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

	        try
			{
	        	arqService.ExtrairPDF(arquivo);
	        	logger.info(arqService.getNomeComercial());
	        	logger.info(arqService.getPrincipioAtivo());
				indexaArquivo(arquivo, arqService.getTextoExtraido(), arqService.getNomeComercial(), arqService.getPrincipioAtivo(), arqService.getFabricante(), arqService.getIndicacoes(), arqService.getContraIndicacoes(), arqService.getReacoesAdversas());
				
			}catch (Exception e) 
			{
				e.printStackTrace();
			}
			
	      } else {
	        indexaArquivosDoDiretorio(arquivo);
	        System.out.println("O arquivo "+raiz+ " foi indexado!");
	      }
	    }
	  }

	  private void indexaArquivo(File arquivo, String textoExtraido, String nomeComercial, String principioAtivo,String fabricante, String indicacoes, String contraIndicacoes, String reacoesAdversas) {
	    SimpleDateFormat formatador = new SimpleDateFormat("");
	    String ultimaModificacao = formatador.format(arquivo.lastModified());
	    //{10}
	    Document documento = new Document();
	    documento.add(new Field("UltimaModificacao", ultimaModificacao,
	        Field.Store.YES, Field.Index.NOT_ANALYZED));
	    documento.add(new Field("Caminho", arquivo.getAbsolutePath(),
	        Field.Store.YES, Field.Index.NOT_ANALYZED));
	    documento.add(new Field("nomeComercial", nomeComercial, Field.Store.YES,
	            Field.Index.ANALYZED));
	    documento.add(new Field("principioAtivo", principioAtivo, Field.Store.YES,
	            Field.Index.ANALYZED));
	    documento.add(new Field("fabricante", fabricante, Field.Store.YES,
	            Field.Index.ANALYZED));
	    documento.add(new Field("indicacoes", indicacoes, Field.Store.YES,
	            Field.Index.ANALYZED));
	    documento.add(new Field("contraIndicacoes", contraIndicacoes, Field.Store.YES,
	            Field.Index.ANALYZED));
	    documento.add(new Field("reacoesAdversas", reacoesAdversas, Field.Store.YES,
	            Field.Index.ANALYZED));
	    documento.add(new Field("Texto", textoExtraido, Field.Store.YES,
	        Field.Index.ANALYZED));
	    try {
	      //{11}
	      getWriter().addDocument(documento);
	    } catch (IOException e) {
	      logger.error(e.toString());
	    }
	  }

	  public IndexWriter getWriter() {
	    return writer;
	  }
}
