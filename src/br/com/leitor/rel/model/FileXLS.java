package br.com.leitor.rel.model;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileXLS {
	
	private String path;
	private String sheetName;
	private List<String[]> listContent = new ArrayList<String[]>();

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String[]> getListContent() {
		return listContent;
	}

	public void setListContent(List<String[]> listContent) {
		this.listContent = listContent;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
}
