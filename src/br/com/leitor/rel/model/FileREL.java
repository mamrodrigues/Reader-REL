package br.com.leitor.rel.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileREL {
	
	private File file;
	private List<String> content = new ArrayList<String>();

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

}
