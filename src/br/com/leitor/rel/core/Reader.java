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

	public static List<FileREL> getListFileREL(String path) {
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

	public static FileDAT getFileDAT(String path) {
		File directory = new File(path);
		FileDAT fileDAT = new FileDAT();

		if (directory.getName().endsWith(".dat")
				|| directory.getName().endsWith(".DAT")) {
			fileDAT.setFile(directory);
			fileDAT.setContent(getListContent(directory));
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
			for(int i=0; i< 1419; i++){ 
				/*TODO - Marcos verificar como pegar o numero de linhas do documento antes de ler e inserir no array, 
				pois com o while verificando enquanto não for null ele pulava varias linhas.*/
				listContent.add(buffReader.readLine());
			}
			buffReader.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
		return listContent;
	}

}
