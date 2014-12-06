package br.com.leitor.rel.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.leitor.rel.model.FileDAT;
import br.com.leitor.rel.model.FileREL;

public class Reader {

	public String pathDAT;
	public String pathREL;

	public List<FileREL> getListFileREL(String path) {
		File directory = new File(path);
		List<FileREL> listaFileREL = new ArrayList<FileREL>();
		File[] files = directory.listFiles();

		for (File file : files) {
			FileREL fileREL = new FileREL();
			fileREL.setFile(file);
			fileREL.setContent(getListContent(file));
			listaFileREL.add(fileREL);
		}
		return listaFileREL;
	}

	public FileDAT getFileDAT(String path) {
		File directory = new File(path);
		File[] files = directory.listFiles();
		FileDAT fileDAT = new FileDAT();

		for (File file : files) {
			if (file.getName().endsWith(".dat")
					|| file.getName().endsWith(".DAT")) {
				fileDAT.setFile(file);
				fileDAT.setContent(getListContent(file));
			}
		}
		return fileDAT;
	}

	public File getFile(String dir) {
		File file = new File(dir);
		return file;
	}

	public static List<String> getListContent(File file) {
		List<String> listContent = new ArrayList<String>();

		try {
			FileReader reader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(reader);
			while (buffReader.readLine() != null) {
				listContent.add(buffReader.readLine());
			}
			buffReader.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
		return listContent;
	}

}
