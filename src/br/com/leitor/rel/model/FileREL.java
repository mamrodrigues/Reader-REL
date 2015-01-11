package br.com.leitor.rel.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileREL {
	
	private File file;
	private List<String> content = new ArrayList<String>();
	
	private String mesInicial;
	private String mesFinal;
	private int anoInicial;
	private int anoFinal;

	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public List<String> getContent() {
		return content;
	}
	public void setContent(List<String> content) {
		this.content = content;
	}
	public int getAnoInicial() {
		return anoInicial;
	}
	public void setAnoInicial(int anoInicial) {
		this.anoInicial = anoInicial;
	}
	public int getAnoFinal() {
		return anoFinal;
	}
	public void setAnoFinal(int anoFinal) {
		this.anoFinal = anoFinal;
	}
	public String getMesInicial() {
		return mesInicial;
	}
	public void setMesInicial(String mesInicial) {
		this.mesInicial = mesInicial;
	}
	public String getMesFinal() {
		return mesFinal;
	}
	public void setMesFinal(String mesFinal) {
		this.mesFinal = mesFinal;
	}
}
