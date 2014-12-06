package br.com.leitor.rel.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.leitor.rel.model.FileDAT;
import br.com.leitor.rel.model.FileREL;

public class Formatter {
	
	FileREL fileREL;
	FileDAT fileDAT;
	
	public Formatter(FileREL fileREL, FileDAT fileDAT) {
		this.fileREL = fileREL;
		this.fileDAT = fileDAT;
	}

	public static List<String[]> FormatterXLS(FileREL fileREL, FileDAT fileDAT) {
		List<String[]> dataList = new ArrayList<String[]>();
        dataList.addAll(getTop());
        dataList.addAll(getHeader());
        dataList.addAll(getData());
        dataList.addAll(getFooter());
		return dataList;
	}
	
	private static List<String[]> getTop() {
		List<String[]> dataList = new ArrayList<String[]>();
        String[][] dataArray = new String[][]{
             {" ","MES INICIAL"," 10/2014","1"},
             {" ","MES FINAL"," 11/2014","2"}
             //TODO Tirar os dados fixos e pegar do arquivo
        };
        dataList = Arrays.asList(dataArray);
		return dataList;
	}
	
	private static List<String[]> getHeader() {
		List<String[]> dataList = new ArrayList<String[]>();
        String[][] dataArray = new String[][]{
             {" "," ","DATA","SE","SU","NE","NO"},
             {" ","EARMI"},
             {"1","EAF"},
             {"2","EAF"},
             {"3","EAF"},
             {"4","EAF"},
             {"5","EAF"},
             {"6","EAF"},
             {"7","EAF"},
             {"8","EAF"},
             {"9","EAF"},
             {"10","EAF"},
             {"11","EAF"},
             {" "," "," ","SE","SU","NE","NO"}
             //TODO Tirar os dados fixos e pegar do arquivo
        };
        dataList = Arrays.asList(dataArray);
		return dataList;
	}
	
	private static List<String[]> getData() {
		List<String[]> dataList = new ArrayList<String[]>();
		for (int i = 1; i<12 ;i++){
	        String[][] dataArray = new String[][]{
	        		{""+i,"C.MARG.AGUA"},
	                {""+i,"CMO","PAT1"},
	                {""+i,"CMO","PAT2"},
	                {""+i,"CMO","PAT3"},
	                {""+i,"EARMI"},
	                {""+i,"EARMF"},
	                {""+i,"EVERT"},
	                {""+i,"ECONT"},
	                {""+i,"ECONTC"},
	                {""+i,"EFIOB"},
	                {""+i,"EFIOL"},
	                {""+i,"GFIOL"},
	                {""+i,"PFIONTURB"},
	                {""+i,"PERDA FIO"},
	                {""+i,"META EVMIN"},
	                {""+i,"EVMIN"},
	                {""+i,"META DSVC"},
	                {""+i,"DSVAGUA"},
	                {""+i,"META DSVF"},
	                {""+i,"DSVAGUA FIO"},
	                {""+i,"EVAPORACAO"},
	                {""+i,"EMORTO"}
	                //TODO Tirar os dados fixos e pegar do arquivo
	           };
	        dataList.addAll(Arrays.asList(dataArray));
		}
		return dataList;
	}
	
	private static List<String[]> getFooter() {
		List<String[]> dataList = new ArrayList<String[]>();
        String[][] dataArray = new String[][]{
        		{" "," "," ","SE","SU","NE","NO"},
                {"1","CMO","MÊS INICIAL1"},
                {"2","CMO","MÊS INICIAL2"},
                {"3","CMO","MÊS INICIAL3"},
                {"4","CMO","MÊS INICIAL4"},
                {"5","CMO","MÊS INICIAL5"},
                {"6","CMO","MÊS INICIAL6"},
                {"7","CMO","MÊS INICIAL7"},
                {"8","CMO","MÊS INICIAL8"},
                {"9","CMO","MÊS INICIAL9"},
                {"10","CMO","MÊS INICIAL10"},
                {"11","CMO","MÊS INICIAL11"},
                {"12","CMO","MÊS INICIAL12"}
                //TODO Tirar os dados fixos e pegar do arquivo
        };
        dataList = Arrays.asList(dataArray);
        return dataList;
	}
	

}
