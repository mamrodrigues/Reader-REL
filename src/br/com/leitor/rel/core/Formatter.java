package br.com.leitor.rel.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.leitor.rel.model.CMO;
import br.com.leitor.rel.model.EAF;
import br.com.leitor.rel.model.FileDAT;
import br.com.leitor.rel.model.FileREL;

public class Formatter {
	
	private FileREL fileREL;
	private FileDAT fileDAT;
	private List<CMO> listCMO;
	
	public Formatter(FileREL fileREL, FileDAT fileDAT) {
		this.fileREL = fileREL;
		this.fileDAT = fileDAT;
	}

	public List<String[]> FormatterXLS(FileREL fileREL, FileDAT fileDAT){
		List<String[]> dataList = new ArrayList<String[]>();
        dataList.addAll(getTop());
        dataList.addAll(getHeader());
        dataList.addAll(getData());
       // dataList.addAll(getFooter());
		return dataList;
	}
	
	private List<String[]> getTop() {
		List<String[]> dataList = new ArrayList<String[]>();
		
		List<String> Meses = getMeses();
				
		String mesInicial = Meses.get(0);
		String mesFinal = Meses.get(1);
				
        String[][] dataArray = new String[][]{
             {" ",mesInicial.substring(0, 13),mesInicial.substring(14, 21)},
             {" ",mesFinal.substring(0, 13),mesFinal.substring(14, 21)}
        };
        dataList = Arrays.asList(dataArray);
		return dataList;
	}
	
	private int getQtdMeses(){
		int qtdMeses = 0;
		List<String> Meses = getMeses();
		
		String mesInicial = Meses.get(0);
		String mesFinal = Meses.get(1);
		
		String teste = mesInicial.substring(13,16).trim();
		
		int mesInicio = Integer.parseInt(mesInicial.substring(13,16).trim());
		int mesFim = Integer.parseInt(mesFinal.substring(13,16).trim());
		int anoInicio = Integer.parseInt(mesInicial.substring(19,21).trim());
		int anoFim = Integer.parseInt(mesFinal.substring(19,21).trim());
		
		if(anoInicio < anoFim || anoInicio != anoFim){
			int anos = anoFim - anoInicio;
			anos = anos * 12;
			qtdMeses = (mesFim+anos) - mesInicio;
			
		}else{
			qtdMeses = mesFim - mesInicio;
		}
		
		return qtdMeses;
	}
	
	private List<String> getMeses(){
		String mesInicial = null;
		String mesFinal = null;
		List<String> meses = new ArrayList<String>();
		
		for (int index = 0; index < fileREL.getContent().size(); index++) {
			String search = fileREL.getContent().get(index);
			if (search.contains("MES INICIAL") || search.contains("mes inicial")) {
				int indexDataInicial =  index;
					search = fileREL.getContent().get(indexDataInicial).trim();
					mesInicial = search;
					String[] arrayMesAnoInicial = search.split("/");
					arrayMesAnoInicial[0] = arrayMesAnoInicial[0].substring(arrayMesAnoInicial[0].length()-2, arrayMesAnoInicial[0].length()).trim();	
					fileREL.setMesInicial(arrayMesAnoInicial[0]);
					fileREL.setAnoInicial(Integer.parseInt(arrayMesAnoInicial[1].trim()));
			}
			if(search.contains("MES FINAL") || search.contains("mes final")) {
				int indexDataInicial =  index;
					search = fileREL.getContent().get(indexDataInicial).trim();
					mesFinal = search;
					String[] arrayMesAnoFinal = search.split("/");
					arrayMesAnoFinal[0] = arrayMesAnoFinal[0].substring(arrayMesAnoFinal[0].length()-2, arrayMesAnoFinal[0].length()).trim();
					fileREL.setMesFinal(arrayMesAnoFinal[0]);	
					fileREL.setAnoFinal(Integer.parseInt(arrayMesAnoFinal[1].trim()));
			}
			search = "";
		}
		
		meses.add(mesInicial);
		meses.add(mesFinal);
		
		return meses;
	}
	
	private List<String> getPATDados(){
		
		List<String> pat = new ArrayList<String>();

		for (int index = 0; index < fileREL.getContent().size(); index++) {
			String search = fileREL.getContent().get(index);
			int validador = 0;
			if ((search.contains("C.MARG.AGUA") || search.contains("c.marg.agua")) && validador==0) {
				validador = 1;
				for(int i=index; i<index+9; i++){
					search = fileREL.getContent().get(i).trim();
					if(!search.isEmpty() && (search.contains("C.MARG.AGUA") || search.contains("PAT"))){
					pat.add(search);
					}
					search = "";
				}
			}
		}
		return pat;
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
	
	private List<String> getDocumentoEARMItoEMORTODados(){
		
		List<String> earmiToEmorto = new ArrayList<String>();

		for (int index = 0; index < fileREL.getContent().size(); index++) {
			String search = fileREL.getContent().get(index);
			int validador = 0;
			if ((search.contains("C.MARG.AGUA") || search.contains("c.marg.agua")) && validador==0) {
				validador = 1;
				for(int i=index; i<index+9; i++){
					search = fileREL.getContent().get(i).trim();
					if(!search.isEmpty() && (search.contains("C.MARG.AGUA") || search.contains("PAT"))){
					earmiToEmorto.add(search);
					}
					search = "";
				}
			}
		}
		return earmiToEmorto;
	}
	
	private List<String[]> getEARMItoEMORTODados(){
		List<String> earmiToEmorto = getDocumentoEARMItoEMORTODados();
		List<String[]> retorno = new ArrayList<String[]>();
		for(int i=0; i<earmiToEmorto.size(); i++)
		{
			retorno.add(getLineArrayClean(earmiToEmorto.get(i)));
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
				while(!fileREL.getContent().get(indexEAF).isEmpty() && (indexEAF<index+12)){
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
	
	private List<String[]> getData(){
		List<String[]> dataList = new ArrayList<String[]>();
		
		int meses = getQtdMeses();
			
			List<String[]> cmo = getCMODados();
						
	        String[][] dataArray = new String[][]{
	        		{"","C.MARG.AGUA","",cmo.get(0)[1],cmo.get(0)[2],cmo.get(0)[3],cmo.get(0)[4]},
	                {"","CMO","PAT1",cmo.get(1)[3],cmo.get(1)[4],cmo.get(1)[5],cmo.get(1)[6]},
	                {"","CMO","PAT2",cmo.get(2)[2],cmo.get(2)[3],cmo.get(2)[4],cmo.get(2)[5]},
	                {"","CMO","PAT3",cmo.get(3)[2],cmo.get(3)[3],cmo.get(3)[4],cmo.get(3)[5]},
	                {"","EARMI"},
	                {"","EARMF"},
	                {"","EVERT"},
	                {"","ECONT"},
	                {"","ECONTC"},
	                {"","EFIOB"},
	                {"","EFIOL"},
	                {"","GFIOL"},
	                {"","PFIONTURB"},
	                {"","PERDA FIO"},
	                {"","META EVMIN"},
	                {"","EVMIN"},
	                {"","META DSVC"},
	                {"","DSVAGUA"},
	                {"","META DSVF"},
	                {"","DSVAGUA FIO"},
	                {"","EVAPORACAO"},
	                {"","EMORTO"}
	           };
			
	        
	        dataList.addAll(Arrays.asList(dataArray));
			
				int size = (cmo.size()/4);
				for(int numeroCMO = 0; numeroCMO<size; numeroCMO++){
				if(numeroCMO != 0){
				int agua = (0+4*numeroCMO);
				int pat1 = (1+4*numeroCMO);
				int pat2 = (2+4*numeroCMO);
				int pat3 = (3+4*numeroCMO);
				String[][] dataArray2 = new String[][]{
		        		{"","C.MARG.AGUA","",cmo.get(agua)[1],cmo.get(agua)[2],cmo.get(agua)[3],cmo.get(agua)[4]},
		                {"","CMO","PAT1",cmo.get(pat1)[3],cmo.get(pat1)[4],cmo.get(pat1)[5],cmo.get(pat1)[6]},
		                {"","CMO","PAT2",cmo.get(pat2)[2],cmo.get(pat2)[3],cmo.get(pat2)[4],cmo.get(pat2)[5]},
		                {"","CMO","PAT3",cmo.get(pat3)[2],cmo.get(pat3)[3],cmo.get(pat3)[4],cmo.get(pat3)[5]},
		                {"","EARMI"},
		                {"","EARMF"},
		                {"","EVERT"},
		                {"","ECONT"},
		                {"","ECONTC"},
		                {"","EFIOB"},
		                {"","EFIOL"},
		                {"","GFIOL"},
		                {"","PFIONTURB"},
		                {"","PERDA FIO"},
		                {"","META EVMIN"},
		                {"","EVMIN"},
		                {"","META DSVC"},
		                {"","DSVAGUA"},
		                {"","META DSVF"},
		                {"","DSVAGUA FIO"},
		                {"","EVAPORACAO"},
		                {"","EMORTO"}
		           };
		        
		        dataList.addAll(Arrays.asList(dataArray2));
					}
				}
			
		
		return dataList;
}
	
	private List<String[]> getFooter() {
		List<String[]> dataList = new ArrayList<String[]>();
		int meses = getQtdMeses();
		List<String[]> cmo = getCMODados();
		
		String[][] indiceDataArray = new String[][]{{" "," "," ","SE","SU","NE","NO"}};
		
		List<String> listStringDAT = getDadosPatamar(fileREL.getAnoInicial(),fileREL.getMesInicial(),fileREL.getAnoFinal(),fileREL.getMesFinal());
		for (String string : listStringDAT) {
			System.out.println(string);
		}
		
		String[][] dataArray = null;
		for (int i = 0; i<meses ;i++){
			int pat = 1 + 4*i;
			int mes = Integer.parseInt(fileREL.getMesInicial())+i;
			dataArray = new String[][]{
           //     {String.valueOf(i),"CMO","MÊS INICIAL "+mes,cmo.get(pat)[3],cmo.get(pat)[4],cmo.get(pat)[5],cmo.get(pat)[6]}
			};
		}
		
		dataList.addAll(Arrays.asList(indiceDataArray));
		dataList.addAll(Arrays.asList(dataArray));
		
        return null;
	}
	
	public List<String> getDadosPatamar(int anoInicial, String mesInicial, int anoFinal, String mesFinal){
		List<String> listStringDAT = new ArrayList<String>();
		for (int index = 0; index < fileDAT.getContent().size(); index++) {
			String search = fileDAT.getContent().get(index);
			if(anoInicial==anoFinal){
				if (search.contains(""+anoInicial+"")) {
					int indexDAT =  index;
					while(!fileDAT.getContent().get(indexDAT).isEmpty() && (indexDAT < index+3)){
						search = fileDAT.getContent().get(indexDAT).trim();
						if (search.startsWith("2014")) {
							search = search.substring(4, search.length()).trim();
							listStringDAT.add(search);
						}else{
							listStringDAT.add(search);
						}
						search = "";
						indexDAT++;
					}
					break;
				}
				search = "";
			}else{
				if(search.contains(""+anoInicial+"")) {
					int indexDAT =  index;
					while(!fileDAT.getContent().get(indexDAT).isEmpty() && (indexDAT < index+3)){
						search = fileDAT.getContent().get(indexDAT).trim();
						if (search.startsWith("2014")) {
							search = search.substring(4, search.length()).trim();
							listStringDAT.add(search);
						}else{
							listStringDAT.add(search);
						}
						search = "";
						indexDAT++;
					}
				}
				if(search.contains(""+anoFinal+"")) {
					int indexDAT =  index;
					while(!fileDAT.getContent().get(indexDAT).isEmpty() && (indexDAT < index+3)){
						search = fileDAT.getContent().get(indexDAT).trim();
						if (search.startsWith("2014")) {
							search = search.substring(4, search.length()).trim();
							listStringDAT.add(search);
						}else{
							listStringDAT.add(search);
						}
						search = "";
						indexDAT++;
					}
				}
				break;
			}
			search = "";
		}
		
		for (String string : listStringDAT) {
			//TODO CALCULO DOS 3 CMOS DOS MESES
			String[] list = string.split(" ");
		}
		
		return listStringDAT;
	}
	
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

	public List<CMO> getListCMO() {
		return listCMO;
	}

	public void setListCMO(List<CMO> listCMO) {
		this.listCMO = listCMO;
	}

}
