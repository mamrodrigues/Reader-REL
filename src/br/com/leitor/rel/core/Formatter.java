package br.com.leitor.rel.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	public List<String[]> FormatterXLS(FileREL fileREL, FileDAT fileDAT){
		List<String[]> dataList = new ArrayList<String[]>();
        dataList.addAll(getTop());
        dataList.addAll(getHeader());
        dataList.addAll(getData());
        dataList.addAll(getFooter());
		return dataList;
	}
	
	private List<String[]> getTop() {
		List<String[]> dataList = new ArrayList<String[]>();
		
		List<String> Meses = getMeses();
				
		String mesInicial = Meses.get(0);
		String mesFinal = Meses.get(1);
		//TODO Marcos temos que melhorar isso. Pois está executando o getMeses 2 vezes
				
        String[][] dataArray = new String[][]{
             {" ",mesInicial.substring(0, 13),mesInicial.substring(14, 21)},
             {" ",mesFinal.substring(0, 13),mesFinal.substring(14, 21)}
             //TODO Tirar os dados fixos e pegar do arquivo
        };
        dataList = Arrays.asList(dataArray);
		return dataList;
	}
	
	private int getQtdMeses(){
		int qtdMeses = 0;
		List<String> Meses = getMeses();
		
		String mesInicial = Meses.get(0);
		String mesFinal = Meses.get(1);
		
		int mesInicio = Integer.parseInt(mesInicial.substring(14,16));
		int mesFim = Integer.parseInt(mesFinal.substring(14,16));
		int anoInicio = Integer.parseInt(mesInicial.substring(19,21));
		int anoFim = Integer.parseInt(mesFinal.substring(19,21));

		
		
		if(anoInicio < anoFim || anoInicio != anoFim){
			int anos = anoFim - anoInicio;
			anos = anos * 12;
			qtdMeses = (mesFim+anos) - mesInicio;
			
		}
		else{
			qtdMeses = mesFim - mesInicio;
		}
		
		
		return qtdMeses;
	}
	
	private List<String> getMeses(){
		String mesInicial = null;
		String mesFinal = null;
		List<String> Meses = new ArrayList<String>();
		
		for (int index = 0; index < fileREL.getContent().size(); index++) {
			String search = fileREL.getContent().get(index);
			if (search.contains("MES INICIAL") || search.contains("mes inicial")) {
				int indexDataInicial =  index;
					search = fileREL.getContent().get(indexDataInicial).trim();
					mesInicial = search;	
			}
			if(search.contains("MES FINAL") || search.contains("mes final")) {
				int indexDataInicial =  index;
					search = fileREL.getContent().get(indexDataInicial).trim();
					mesFinal = search;	
			}
			search = "";
		}
		
		Meses.add(mesInicial);
		Meses.add(mesFinal);
		
		return Meses;
	}
	
	private List<String> getPATDados(){
		
		List<String> PAT = new ArrayList<String>();


		for (int index = 0; index < fileREL.getContent().size(); index++) {
			String search = fileREL.getContent().get(index);
			int validador = 0;
			if ((search.contains("C.MARG.AGUA") || search.contains("c.marg.agua")) && validador==0) {
				validador = 1;
				for(int i=index; i<index+9; i++){
					search = fileREL.getContent().get(i).trim();
					if(!search.isEmpty() && (search.contains("C.MARG.AGUA") || search.contains("PAT"))){
					PAT.add(search);
					}
					search = "";
				}
				
			}
			
		
		}
		return PAT;
	}
	
	private List<String[]> getCMODados(){
		List<String> pat = getPATDados();
		List<String[]> retorno = new ArrayList<String[]>();
		for(int i=0; i<pat.size(); i++)
		{
			retorno.add(getLineArrayClean(pat.get(i)));
		}

		return retorno;
	}
	
	private List<String[]> getHeader() {
		
		List<EAF> listEAF = mapHeader();
		List<String[]> dataList = new ArrayList<String[]>();
		

		dataList.add(new String[]
             {" "," ","DATA","SE","SU","NE","NO"});
       
        for (int i = 0; i < listEAF.size(); i++) {
        	EAF eaf = listEAF.get(i);
        	if(i == 0){
        		dataList.add(new String[]
        	        	{" ","EARMI",eaf.getData(),eaf.getSe(),eaf.getSu(),eaf.getNe(),eaf.getNo()});
        	}else{
        		dataList.add(new String[]
        	        	{"1","EAF",eaf.getData(),eaf.getSe(),eaf.getSu(),eaf.getNe(),eaf.getNo()});
        	}
		}
        
        dataList.add(new String[]
             {" "," "," ","SE","SU","NE","NO"});
        
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
			String[] dadosEAF = getLineArrayClean(stringEAF);
			for (int j = 0; j < dadosEAF.length; j++) {
				EAF eaf = new EAF();
				eaf.setData(dadosEAF[1]);
				eaf.setSe(dadosEAF[2]);
				eaf.setSu(dadosEAF[3]);
				eaf.setNe(dadosEAF[4]);
				eaf.setNo(dadosEAF[5]);
				listEAF.add(eaf);
			}
		}
		
		Map<EAF, Integer> eafMAP = new LinkedHashMap<>();
		for (EAF eaf : listEAF) {
		   if (!eafMAP.containsKey(eaf)) {
			   eafMAP.put(eaf,eaf.getIndex());
		   }
		}
		
		listEAF.clear();
		listEAF.addAll(eafMAP.keySet());
		
		return listEAF;
	}
	
	// Mano //TODO o cmo ai embaixo está trazendo uma lista de array já com todos os pat que existe no documento, este é o erro. Depois te explico!!!
	private List<String[]> getData(){
		List<String[]> dataList = new ArrayList<String[]>();
		int meses = getQtdMeses();
		for (int i = 0; i<meses ;i++){
			
			List<String[]> cmo = getCMODados();
			if(i == 0){
	        String[][] dataArray = new String[][]{
	        		{""+i,"C.MARG.AGUA","",cmo.get(0)[1],cmo.get(0)[2],cmo.get(0)[3],cmo.get(0)[4]},
	                {""+i,"CMO","PAT1",cmo.get(1)[3],cmo.get(1)[4],cmo.get(1)[5],cmo.get(1)[6]},
	                {""+i,"CMO","PAT2",cmo.get(2)[2],cmo.get(2)[3],cmo.get(2)[4],cmo.get(2)[5]},
	                {""+i,"CMO","PAT3",cmo.get(3)[2],cmo.get(3)[3],cmo.get(3)[4],cmo.get(3)[5]},
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
			else{
				int agua = 0 + 4*i;
				int pat1 = 1 + 4*i;
				int pat2 = 2 + 4*i;
				int pat3 = 3 + 4*i;
				String[][] dataArray = new String[][]{
		        		{""+i,"C.MARG.AGUA","",cmo.get(agua)[1],cmo.get(agua)[2],cmo.get(agua)[3],cmo.get(agua)[4]},
		                {""+i,"CMO","PAT1",cmo.get(pat1)[3],cmo.get(pat1)[4],cmo.get(pat1)[5],cmo.get(pat1)[6]},
		                {""+i,"CMO","PAT2",cmo.get(pat2)[2],cmo.get(pat2)[3],cmo.get(pat2)[4],cmo.get(pat2)[5]},
		                {""+i,"CMO","PAT3",cmo.get(pat3)[2],cmo.get(pat3)[3],cmo.get(pat3)[4],cmo.get(pat3)[5]},
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
	
//	public List<String[]> cleanList(List<String[]> listString){
//		List<String[]> allLineClean = new ArrayList<String[]>();
//		for (String[] strings : listString) {
//			List<String> line = new ArrayList<String>();
//			List<String> lineClean = new ArrayList<String>();
//			line = Arrays.asList(strings);
//			for (String string : line) {
//				if(string != null && string != ""){
//					lineClean.add(string);
//				}
//			}
//			allLineClean.add((String[]) lineClean.toArray());
//		}
//		return allLineClean;
//	}
	
	public String[] getLineArrayClean(String string){
		String[] list = string.split(" ");
		List<String> lineClean = new ArrayList<String>();
		for (String st : list) {
			if(st != null && !st.isEmpty()){
				lineClean.add(st);
			}
		}
		
		return lineClean.toArray(new String[lineClean.size()]);
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
