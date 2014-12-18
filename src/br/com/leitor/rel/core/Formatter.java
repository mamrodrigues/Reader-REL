package br.com.leitor.rel.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.rowset.serial.SerialArray;

import br.com.leitor.rel.model.FileDAT;
import br.com.leitor.rel.model.FileREL;
import br.com.leitor.rel.model.EAF;

public class Formatter {
	
	private FileREL fileREL;
	private FileDAT fileDAT;
	
	public Formatter(FileREL fileREL, FileDAT fileDAT) {
		this.fileREL = fileREL;
		this.fileDAT = fileDAT;
	}

	public List<String[]> FormatterXLS(FileREL fileREL, FileDAT fileDAT) {
		List<String[]> dataList = new ArrayList<String[]>();
        dataList.addAll(getTop());
        dataList.addAll(getHeader());
        dataList.addAll(getData());
        dataList.addAll(getFooter());
		return dataList;
	}
	
	private List<String[]> getTop() {
		List<String[]> dataList = new ArrayList<String[]>();
        String[][] dataArray = new String[][]{
             {" ","MES INICIAL"," 10/2014","1"},
             {" ","MES FINAL"," 11/2014","2"}
             //TODO Tirar os dados fixos e pegar do arquivo
        };
        dataList = Arrays.asList(dataArray);
		return dataList;
	}
	
	private List<String[]> getHeader() {
		
		List<String[]> dataList = new ArrayList<String[]>();
        String[][] dataArray1 = new String[][]{
             {" "," ","DATA","SE","SU","NE","NO"}
        };
        
        List<EAF> listEAF = mapHeader();
        String[][] dataArray2 =  null;
        for (int i = 0; i < listEAF.size(); i++) {
        	EAF eaf = listEAF.get(i);
        	if(i == 0){
            	dataArray2 = new String[][]{
        	        	{" ","EARMI",eaf.getData(),eaf.getSe(),eaf.getSu(),eaf.getNe(),eaf.getNo()}
        	        };
        	}else{
            	dataArray2 = new String[][]{
        	        	{"1","EAF",eaf.getData(),eaf.getSe(),eaf.getSu(),eaf.getNe(),eaf.getNo()}
        	    };
        	}
		}
        
        String[][] dataArray3 = new String[][]{
             {" "," "," ","SE","SU","NE","NO"}
        };
        
        dataList.addAll(Arrays.asList(dataArray1));
        dataList.addAll(Arrays.asList(dataArray2));
        dataList.addAll(Arrays.asList(dataArray3));
        
		return dataList;
	}
	
	private List<EAF> mapHeader() {
		
		List<String> listStringEAF = new ArrayList<String>();
		
		/** NESTE FOR, EU PERCORRO O ARQUIVO ATÉ ENCONTRAR A PRIMEIRA LINHA QUE CONTÉM O TRECHO "EARMI"
		 *  QUANDO ENCONTRADA, EU PERCORRO OS DADOS PREENCHENDO UMA LISTA DE STRING COM OS DADOS DO EAF
		 *  QUE DEVEM SER CONTIDOS NO HEADER **/
		for (int index = 0; index < fileREL.getContent().size(); index++) {
			String search = fileREL.getContent().get(index);
			if (search.contains("EARMI") || search.contains("earmi")) {
				int indexEAF =  index;
				while(!fileREL.getContent().get(indexEAF).isEmpty()){
					search = fileREL.getContent().get(indexEAF).trim();
					listStringEAF.add(search);
					search = "";
					indexEAF++;
				}
				break;
			}
			search = "";
		}
		
		/** APÓS O PREENCHIMENTO DA STRING EU PREENCHO O OBJETO PARA QUE OS DADOS RETORNEM UMA LISTA DO OBJETO NECESSÁRIO **/
		List<EAF> listEAF = new ArrayList<EAF>();
		for (String stringEAF : listStringEAF) {
			String[] dadosEAF = stringEAF.split(" ");
			if(dadosEAF.length >= 4){
				EAF eaf = new EAF();
				eaf.setData(dadosEAF[0]);
				eaf.setSe(dadosEAF[1]);
				eaf.setSu(dadosEAF[2]);
				eaf.setNe(dadosEAF[3]);
				eaf.setNo(dadosEAF[4]);
			}
			dadosEAF = null;
		}
		return listEAF;
	}
	
	
	private List<String[]> getData() {
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
	
	private List<String[]> getFooter() {
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

	public FileREL getFileREL() {
		return fileREL;
	}

	public void setFileREL(FileREL fileREL) {
		this.fileREL = fileREL;
	}

	public FileDAT getFileDAT() {
		return fileDAT;
	}

	public void setFileDAT(FileDAT fileDAT) {
		this.fileDAT = fileDAT;
	}

}
