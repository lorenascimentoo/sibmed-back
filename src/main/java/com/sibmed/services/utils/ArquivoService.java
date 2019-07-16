package com.sibmed.services.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.stereotype.Service;

@Service
public class ArquivoService {

	private String nomeComercial=null;
	private String principioAtivo=null;
	private String fabricante=null;
	private String indicacoes=null;
	private String contraIndicacoes=null;
	private String reacoesAdversas=null;
	private String textoExtraido=null;
	
	public void ExtrairPDF(File arquivo) throws IOException{
		
		try (PDDocument document = PDDocument.load(arquivo)){
			if(!document.isEncrypted()) {
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				
				//Cria o leitor e faz a leitura do TEXTO para uma String;
				PDFTextStripper tStripper = new PDFTextStripper();
				
				String[] filename = arquivo.getName().split("_");
				
				String pdfFileInText = tStripper.getText(document);
				this.textoExtraido = pdfFileInText;
				
				
				List<String> dados = getBulasInfo(pdfFileInText);
			   	
				this.nomeComercial = filename[1].toUpperCase();
				this.principioAtivo = filename[0].toUpperCase();
				this.fabricante = dados.get(0).toUpperCase();
				this.indicacoes = dados.get(1);
				this.contraIndicacoes = dados.get(2);
				this.reacoesAdversas = dados.get(3);
				
			}
		}
	}

	private static List<String> getBulasInfo(String pdfFileInText) {
		// Faz a varredura linha a linha do texto extraído
		String fabricante = null;
		Scanner scn = null;
		scn = new Scanner(pdfFileInText);
		List<String> line = new ArrayList<String>();
		while (scn.hasNextLine()) {
			String analisar = scn.nextLine().trim();
			if (analisar.length() > 0) {
				if (analisar.contains("Ltda") || analisar.contains("LTDA")) {
					fabricante = analisar;
				} else {
					line.add(analisar);
				}
			}
		}
		
		List<String> dados = new ArrayList<>();
		
		dados.add(fabricante);
		
		StringBuffer indicacao = new StringBuffer();
		StringBuffer contraIndicacao = new StringBuffer();
		StringBuffer reacaoAdversa = new StringBuffer();
		for (int i = 0; i < line.size(); i++) {
			String linha = line.get(i);
			if (linha.contains("PARA")) {
				int j = i;
				while (!line.get(j).contains("NÃO")) {
					indicacao.append(line.get(j));
					j++;
				}
			} else if (linha.contains("NÃO")) {
				int j = i;
				while (!line.get(j).contains("SABER")) {
					contraIndicacao.append(line.get(j));
					j++;
				}

			} else if (linha.contains("MALES")) {
				int j = i;
				while (!line.get(j).contains("QUANTIDADE")) {
					reacaoAdversa.append(line.get(j));
					j++;
				}
			}

		}

		dados.add(getString(indicacao));
		dados.add(getString(contraIndicacao));
		dados.add(getString(reacaoAdversa));
		scn.close();
		return dados;
	}

	private static String getString(StringBuffer lista) {
		String dado = lista.toString();
		dado = dado.replaceAll("\\d.", "").replace("?", "?\n").replace(":", ":\n").replace(";", ";\n");
		return dado;
	}

	public String getNomeComercial() {
		return nomeComercial;
	}

	public String getPrincipioAtivo() {
		return principioAtivo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public String getIndicacoes() {
		return indicacoes;
	}

	public String getContraIndicacoes() {
		return contraIndicacoes;
	}

	public String getReacoesAdversas() {
		return reacoesAdversas;
	}

	public String getTextoExtraido() {
		return textoExtraido;
	}
	
	public void apagaArquivo(String dir) {
		File arquivo = new File(dir);
		arquivo.delete();
	}
}