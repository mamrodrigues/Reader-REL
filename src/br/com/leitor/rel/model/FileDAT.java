package br.com.leitor.rel.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileDAT {
	
	private File file;
	private List<String> content = new ArrayList<String>();
	
	private int ano;
	private String mes;

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
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	
}
