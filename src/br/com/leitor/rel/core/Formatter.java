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
import br.com.leitor.rel.model.PATAMAR;

public class Formatter {

	private FileREL fileREL;
	private FileDAT fileDAT;
	private List<CMO> listCMO = new ArrayList<CMO>();

	public Formatter(FileREL fileREL, FileDAT fileDAT) {
		this.fileREL = fileREL;
		this.fileDAT = fileDAT;
	}

	public List<String[]> FormatterXLS(FileREL fileREL, FileDAT fileDAT) {
		List<String[]> dataList = new ArrayList<String[]>();
		dataList.addAll(getTop());
		dataList.addAll(getHeader());
		dataList.addAll(getData());
		//dataList.addAll(getFooter());
		return dataList;
	}

	private List<String[]> getTop() {
		List<String[]> dataList = new ArrayList<String[]>();

		List<String> Meses = getMeses();

		String mesInicial = Meses.get(0);
		String mesFinal = Meses.get(1);

		String[][] dataArray = new String[][] {
				{ " ", mesInicial.substring(0, 13),
						mesInicial.substring(14, 21) },
				{ " ", mesFinal.substring(0, 13), mesFinal.substring(14, 21) } };
		dataList = Arrays.asList(dataArray);
		return dataList;
	}

	private int getQtdMeses() {
		int qtdMeses = 0;
		List<String> Meses = getMeses();

		String mesInicial = Meses.get(0);
		String mesFinal = Meses.get(1);

		String teste = mesInicial.substring(13, 16).trim();

		int mesInicio = Integer.parseInt(mesInicial.substring(13, 16).trim());
		int mesFim = Integer.parseInt(mesFinal.substring(13, 16).trim());
		int anoInicio = Integer.parseInt(mesInicial.substring(19, 21).trim());
		int anoFim = Integer.parseInt(mesFinal.substring(19, 21).trim());

		if (anoInicio < anoFim || anoInicio != anoFim) {
			int anos = anoFim - anoInicio;
			anos = anos * 12;
			qtdMeses = (mesFim + anos) - mesInicio;

		} else {
			qtdMeses = mesFim - mesInicio;
		}

		return qtdMeses;
	}

	private List<String> getMeses() {
		String mesInicial = null;
		String mesFinal = null;
		List<String> meses = new ArrayList<String>();

		for (int index = 0; index < fileREL.getContent().size(); index++) {
			String search = fileREL.getContent().get(index);
			if (search.contains("MES INICIAL")
					|| search.contains("mes inicial")) {
				int indexDataInicial = index;
				search = fileREL.getContent().get(indexDataInicial).trim();
				mesInicial = search;
				String[] arrayMesAnoInicial = search.split("/");
				arrayMesAnoInicial[0] = arrayMesAnoInicial[0].substring(
						arrayMesAnoInicial[0].length() - 2,
						arrayMesAnoInicial[0].length()).trim();
				fileREL.setMesInicial(arrayMesAnoInicial[0]);
				fileREL.setAnoInicial(Integer.parseInt(arrayMesAnoInicial[1]
						.trim()));
			}
			if (search.contains("MES FINAL") || search.contains("mes final")) {
				int indexDataInicial = index;
				search = fileREL.getContent().get(indexDataInicial).trim();
				mesFinal = search;
				String[] arrayMesAnoFinal = search.split("/");
				arrayMesAnoFinal[0] = arrayMesAnoFinal[0].substring(
						arrayMesAnoFinal[0].length() - 2,
						arrayMesAnoFinal[0].length()).trim();
				fileREL.setMesFinal(arrayMesAnoFinal[0]);
				fileREL.setAnoFinal(Integer.parseInt(arrayMesAnoFinal[1].trim()));
			}
			search = "";
		}

		meses.add(mesInicial);
		meses.add(mesFinal);

		return meses;
	}

	private void getPATDados() {

		for (int index = 0; index < fileREL.getContent().size(); index++) {

			String search = fileREL.getContent().get(index);
			if ((search.contains("C.MARG.AGUA") || search
					.contains("c.marg.agua"))) {
				CMO cmo = new CMO();
				List<String> earmiToMorto = getEarmiToEmortoDados(index);
				List<String> pat = getCMOPATDados(index);
				cmo.setEarmi(earmiToMorto);
				cmo.setCmo(pat);
				listCMO.add(cmo);
			}

		}
	}

	// TODO - Verificar se o ultimo é o EMORTO(getEarmiToEmortoDados) e
	// verificar se o ultimo é o PAT3(getPATDados)
	private List<String> getEarmiToEmortoDados(int indice) {

		List<String> earmiToEmorto = new ArrayList<String>();

		for (; indice < fileREL.getContent().size(); indice++) {
			String search = fileREL.getContent().get(indice);
			if ((search.contains("EARMI") || search.contains("earmi"))) {
				for (int i = indice; i < indice + 23; i++) {
					search = fileREL.getContent().get(i).trim();
					earmiToEmorto.add(search);
					search = "";
				}
				break;
			}
		}
		return earmiToEmorto;
	}

	private List<String> getCMOPATDados(int indice) {

		List<String> cmoPat = new ArrayList<String>();

		for (; indice < fileREL.getContent().size(); indice++) {
		
				for (int i = indice; i < indice + 8; i++) {
					String search = fileREL.getContent().get(indice);
					if (!search.isEmpty()
							&& (search.contains("C.MARG.AGUA") || search
									.contains("PAT"))) {
						search = fileREL.getContent().get(i).trim();
						cmoPat.add(search);
					}
					search = "";
				}
				break;
			}
		
		return cmoPat;
	}

	private List<String[]> getCMODados() {
		List<String[]> retornoCMO = new ArrayList<String[]>();
		for (int i = 0; i < listCMO.size(); i++) {
			for (int j = 0; j < listCMO.get(i).getCmo().size(); j++) {
				String[] tempString = getLineArrayClean(listCMO.get(i).getCmo().get(j));
				if(tempString.length > 0){
					retornoCMO.add(tempString);
				}
			}
		}
		return retornoCMO;
	}

	private List<String[]> getEARMIDados() {
		List<String[]> retornoEARMI = new ArrayList<String[]>();
		for (int i = 0; i < listCMO.size(); i++) {
			for (int j = 0; j < listCMO.get(i).getEarmi().size(); j++) {
				String[] tempString = getLineArrayClean(listCMO.get(i).getEarmi().get(j));
				if(tempString.length > 0){
					retornoEARMI.add(tempString);
				}
			}
		}
		return retornoEARMI;
	}

	// private List<String[]> getEarmiDados(){
	// int i = 0;
	// List<String> EarmiList = getEarmiToEmortoDados(i);
	// List<String[]> retorno = new ArrayList<String[]>();
	// for(int i=0; i<EarmiList.size(); i++)
	// {
	// retorno.add(getLineArrayClean(EarmiList.get(i)));
	// }
	//
	// return retorno;
	// }

	private List<String> getDocumentoEARMItoEMORTODados() {

		List<String> earmiToEmorto = new ArrayList<String>();

		for (int index = 0; index < fileREL.getContent().size(); index++) {
			String search = fileREL.getContent().get(index);
			int validador = 0;
			if ((search.contains("C.MARG.AGUA") || search
					.contains("c.marg.agua")) && validador == 0) {
				validador = 1;
				for (int i = index; i < index + 9; i++) {
					search = fileREL.getContent().get(i).trim();
					if (!search.isEmpty()
							&& (search.contains("C.MARG.AGUA") || search
									.contains("PAT"))) {
						earmiToEmorto.add(search);
					}
					search = "";
				}
			}
		}
		return earmiToEmorto;
	}

	// private List<String[]> getEARMItoEMORTODados(){
	// List<String> earmiToEmorto = getDocumentoEARMItoEMORTODados();
	// List<String[]> retorno = new ArrayList<String[]>();
	// for(int i=0; i<earmiToEmorto.size(); i++)
	// {
	// retorno.add(getLineArrayClean(earmiToEmorto.get(i)));
	// }
	//
	// return retorno;
	// }

	private List<String[]> getHeader() {

		List<EAF> listEAF = mapHeader();
		List<String[]> dataList = new ArrayList<String[]>();

		dataList.add(new String[] { " ", " ", "DATA", "SE", "SU", "NE", "NO" });

		for (int i = 0; i < listEAF.size(); i++) {
			EAF eaf = listEAF.get(i);
			if (i == 0) {
				dataList.add(new String[] { " ", "EARMI", eaf.getData(),
						eaf.getSe(), eaf.getSu(), eaf.getNe(), eaf.getNo() });
			} else {
				dataList.add(new String[] { "1", "EAF", eaf.getData(),
						eaf.getSe(), eaf.getSu(), eaf.getNe(), eaf.getNo() });
			}
		}

		dataList.add(new String[] { " ", " ", " ", "SE", "SU", "NE", "NO" });

		return dataList;
	}

	private List<EAF> mapHeader() {

		List<String> listStringEAF = new ArrayList<String>();

		/**
		 * NESTE FOR, EU PERCORRO O ARQUIVO ATÉ ENCONTRAR A PRIMEIRA LINHA QUE
		 * CONTÉM O TRECHO "EARMI" QUANDO ENCONTRADA, EU PERCORRO OS DADOS
		 * PREENCHENDO UMA LISTA DE STRING COM OS DADOS DO EAF QUE DEVEM SER
		 * CONTIDOS NO HEADER
		 **/
		for (int index = 0; index < fileREL.getContent().size(); index++) {
			String search = fileREL.getContent().get(index);
			if (search.contains("EARMI") || search.contains("earmi")) {
				int indexEAF = index;
				while (!fileREL.getContent().get(indexEAF).isEmpty()
						&& (indexEAF < index + 12)) {
					search = fileREL.getContent().get(indexEAF).trim();
					listStringEAF.add(search);
					search = "";
					indexEAF++;
				}
				break;
			}
			search = "";
		}

		/**
		 * APÓS O PREENCHIMENTO DA STRING EU PREENCHO O OBJETO PARA QUE OS DADOS
		 * RETORNEM UMA LISTA DO OBJETO NECESSÁRIO
		 **/
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
				eafMAP.put(eaf, eaf.getIndex());
			}
		}

		listEAF.clear();
		listEAF.addAll(eafMAP.keySet());

		return listEAF;
	}

	private List<String[]> getData() {
		List<String[]> dataList = new ArrayList<String[]>();

		int meses = getQtdMeses();

		getPATDados();
		List<String[]> cmo = getCMODados();
		List<String[]> earmi = getEARMIDados();
		String[][] dataArray = new String[][] {
				{ "", "C.MARG.AGUA", "", cmo.get(0)[1], cmo.get(0)[2],cmo.get(0)[3], cmo.get(0)[4] },
				{ "", "CMO", "PAT1", cmo.get(1)[3], cmo.get(1)[4],cmo.get(1)[5], cmo.get(1)[6] },
				{ "", "CMO", "PAT2", cmo.get(2)[2], cmo.get(2)[3],cmo.get(2)[4], cmo.get(2)[5] },
				{ "", "CMO", "PAT3", cmo.get(3)[2], cmo.get(3)[3],cmo.get(3)[4], cmo.get(3)[5] },
				{ "", "EARMI" ,earmi.get(0)[1], earmi.get(0)[2], earmi.get(0)[3], earmi.get(0)[4]},
				{ "", "EARMF" ,earmi.get(1)[1], earmi.get(1)[2], earmi.get(1)[3], earmi.get(1)[4]},
				{ "", "EVERT" ,earmi.get(2)[1], earmi.get(2)[2], earmi.get(2)[3], earmi.get(2)[4]},
				{ "", "ECONT" ,earmi.get(3)[1], earmi.get(3)[2], earmi.get(3)[3], earmi.get(3)[4]},
				{ "", "ECONTC",earmi.get(4)[1], earmi.get(4)[2], earmi.get(4)[3], earmi.get(4)[4] },
				{ "", "EFIOB" ,earmi.get(5)[1], earmi.get(5)[2], earmi.get(5)[3], earmi.get(5)[4]},
				{ "", "EFIOL" ,earmi.get(6)[1], earmi.get(6)[2], earmi.get(6)[3], earmi.get(6)[4]},
				{ "", "GFIOL" ,earmi.get(7)[1], earmi.get(7)[2], earmi.get(7)[3], earmi.get(7)[4]},
				{ "", "PFIONTURB" ,earmi.get(8)[1], earmi.get(8)[2], earmi.get(8)[3], earmi.get(8)[4]},
				{ "", "PERDA FIO" ,earmi.get(9)[1], earmi.get(9)[2], earmi.get(9)[3], earmi.get(9)[4]},
				{ "", "META EVMIN" ,earmi.get(10)[1], earmi.get(10)[2], earmi.get(10)[3], earmi.get(10)[4]},
				{ "", "EVMIN" ,earmi.get(11)[1], earmi.get(11)[2], earmi.get(11)[3], earmi.get(11)[4]},
				{ "", "META DSVC" ,earmi.get(12)[1], earmi.get(12)[2], earmi.get(12)[3], earmi.get(12)[4]},
				{ "", "DSVAGUA" ,earmi.get(13)[1], earmi.get(13)[2], earmi.get(13)[3], earmi.get(13)[4]},
				{ "", "META DSVF" ,earmi.get(14)[1], earmi.get(14)[2], earmi.get(14)[3], earmi.get(14)[4]},
				{ "", "DSVAGUA FIO" ,earmi.get(15)[1], earmi.get(15)[2], earmi.get(15)[3], earmi.get(15)[4]},
				{ "", "EVAPORACAO" ,earmi.get(16)[1], earmi.get(16)[2], earmi.get(16)[3], earmi.get(16)[4]},
				{ "", "EMORTO" ,earmi.get(17)[1], earmi.get(17)[2], earmi.get(17)[3], earmi.get(17)[4]}
				};
		
//		String[][] earmiList = null;
//		String[][] cmoList = null;
//		for (CMO cmoTemp : listCMO) {
//			for (int i=0; i<cmoTemp.getCmo().size(); i++) {
//				cmoList = new String[][] {{ "", cmo.get(i)[0], cmo.get(i)[1], cmo.get(i)[2], cmo.get(i)[3],cmo.get(i)[4]}};
//			}
//			for (int i=0; i<cmoTemp.getEarmi().size(); i++) {
//				earmiList = new String[][] {{ "", earmi.get(i)[0], earmi.get(i)[1], earmi.get(i)[2], earmi.get(i)[3],earmi.get(i)[4]}};
//			}
//		}
//		
//		dataList.addAll(Arrays.asList(cmoList));
//		dataList.addAll(Arrays.asList(earmiList));

		dataList.addAll(Arrays.asList(dataArray));

		int size = (cmo.size() / 4);
		for (int numeroCMO = 0; numeroCMO < size; numeroCMO++) {
			if (numeroCMO != 0) {
				int agua = (0 + 4 * numeroCMO);
				int pat1 = (1 + 4 * numeroCMO);
				int pat2 = (2 + 4 * numeroCMO);
				int pat3 = (3 + 4 * numeroCMO);
				int earmiToEmortoValue1 = (0 + 18 * numeroCMO);
				int earmiToEmortoValue2 = (1 + 18 * numeroCMO);
				int earmiToEmortoValue3 = (2 + 18 * numeroCMO);
				int earmiToEmortoValue4 = (3 + 18 * numeroCMO);
				int earmiToEmortoValue5 = (4 + 18 * numeroCMO);
				int earmiToEmortoValue6 = (5 + 18 * numeroCMO);
				int earmiToEmortoValue7 = (6 + 18 * numeroCMO);
				int earmiToEmortoValue8 = (7 + 18 * numeroCMO);
				int earmiToEmortoValue9 = (8 + 18 * numeroCMO);
				int earmiToEmortoValue10 = (9 + 18 * numeroCMO);
				int earmiToEmortoValue11 = (10 + 18 * numeroCMO);
				int earmiToEmortoValue12 = (11 + 18 * numeroCMO);
				int earmiToEmortoValue13 = (12 + 18 * numeroCMO);
				int earmiToEmortoValue14 = (13 + 18 * numeroCMO);
				int earmiToEmortoValue15 = (14 + 18 * numeroCMO);
				int earmiToEmortoValue16 = (15 + 18 * numeroCMO);
				int earmiToEmortoValue17 = (16 + 18 * numeroCMO);
				int earmiToEmortoValue18 = (17 + 18 * numeroCMO);
				String[][] dataArray2 = new String[][] {
						{ "", "C.MARG.AGUA", "", cmo.get(agua)[1],
								cmo.get(agua)[2], cmo.get(agua)[3],
								cmo.get(agua)[4] },
						{ "", "CMO", "PAT1", cmo.get(pat1)[3],
								cmo.get(pat1)[4], cmo.get(pat1)[5],
								cmo.get(pat1)[6] },
						{ "", "CMO", "PAT2", cmo.get(pat2)[2],
								cmo.get(pat2)[3], cmo.get(pat2)[4],
								cmo.get(pat2)[5] },
						{ "", "CMO", "PAT3", cmo.get(pat3)[2],
								cmo.get(pat3)[3], cmo.get(pat3)[4],
								cmo.get(pat3)[5] }, 
								{ "", "EARMI" ,earmi.get(earmiToEmortoValue1)[1], earmi.get(earmiToEmortoValue1)[2], earmi.get(earmiToEmortoValue1)[3], earmi.get(earmiToEmortoValue1)[4]},
								{ "", "EARMF" ,earmi.get(earmiToEmortoValue2)[1], earmi.get(earmiToEmortoValue2)[2], earmi.get(earmiToEmortoValue2)[3], earmi.get(earmiToEmortoValue2)[4]},
								{ "", "EVERT" ,earmi.get(earmiToEmortoValue3)[1], earmi.get(earmiToEmortoValue3)[2], earmi.get(earmiToEmortoValue3)[3], earmi.get(earmiToEmortoValue3)[4]},
								{ "", "ECONT" ,earmi.get(earmiToEmortoValue4)[1], earmi.get(earmiToEmortoValue4)[2], earmi.get(earmiToEmortoValue4)[3], earmi.get(earmiToEmortoValue4)[4]},
								{ "", "ECONTC",earmi.get(earmiToEmortoValue5)[1], earmi.get(earmiToEmortoValue5)[2], earmi.get(earmiToEmortoValue5)[3], earmi.get(earmiToEmortoValue5)[4]},
								{ "", "EFIOB" ,earmi.get(earmiToEmortoValue6)[1], earmi.get(earmiToEmortoValue6)[2], earmi.get(earmiToEmortoValue6)[3], earmi.get(earmiToEmortoValue6)[4]},
								{ "", "EFIOL" ,earmi.get(earmiToEmortoValue7)[1], earmi.get(earmiToEmortoValue7)[2], earmi.get(earmiToEmortoValue7)[3], earmi.get(earmiToEmortoValue7)[4]},
								{ "", "GFIOL" ,earmi.get(earmiToEmortoValue8)[1], earmi.get(earmiToEmortoValue8)[2], earmi.get(earmiToEmortoValue8)[3], earmi.get(earmiToEmortoValue8)[4]},
								{ "", "PFIONTURB" ,earmi.get(earmiToEmortoValue9)[1], earmi.get(earmiToEmortoValue9)[2], earmi.get(earmiToEmortoValue9)[3], earmi.get(earmiToEmortoValue9)[4]},
								{ "", "PERDA FIO" ,earmi.get(earmiToEmortoValue10)[1], earmi.get(earmiToEmortoValue10)[2], earmi.get(earmiToEmortoValue10)[3], earmi.get(earmiToEmortoValue10)[4]},
								{ "", "META EVMIN" ,earmi.get(earmiToEmortoValue11)[1], earmi.get(earmiToEmortoValue11)[2], earmi.get(earmiToEmortoValue11)[3], earmi.get(earmiToEmortoValue11)[4]},
								{ "", "EVMIN" ,earmi.get(earmiToEmortoValue12)[1], earmi.get(earmiToEmortoValue12)[2], earmi.get(earmiToEmortoValue12)[3], earmi.get(earmiToEmortoValue12)[4]},
								{ "", "META DSVC" ,earmi.get(earmiToEmortoValue13)[1], earmi.get(earmiToEmortoValue13)[2], earmi.get(earmiToEmortoValue13)[3], earmi.get(earmiToEmortoValue13)[4]},
								{ "", "DSVAGUA" ,earmi.get(earmiToEmortoValue14)[1], earmi.get(earmiToEmortoValue14)[2], earmi.get(earmiToEmortoValue14)[3], earmi.get(earmiToEmortoValue14)[4]},
								{ "", "META DSVF" ,earmi.get(earmiToEmortoValue15)[1], earmi.get(earmiToEmortoValue15)[2], earmi.get(earmiToEmortoValue15)[3], earmi.get(earmiToEmortoValue15)[4]},
								{ "", "DSVAGUA FIO" ,earmi.get(earmiToEmortoValue16)[1], earmi.get(earmiToEmortoValue16)[2], earmi.get(earmiToEmortoValue16)[3], earmi.get(earmiToEmortoValue16)[4]},
								{ "", "EVAPORACAO" ,earmi.get(earmiToEmortoValue17)[1], earmi.get(earmiToEmortoValue17)[2], earmi.get(earmiToEmortoValue17)[3], earmi.get(earmiToEmortoValue17)[4]},
								{ "", "EMORTO" ,earmi.get(earmiToEmortoValue18)[1], earmi.get(earmiToEmortoValue18)[2], earmi.get(earmiToEmortoValue18)[3], earmi.get(earmiToEmortoValue18)[4]}
				};
				dataList.addAll(Arrays.asList(dataArray2));
			}
		}

		return dataList;
	}

	private List<String[]> getFooter() {
		List<String[]> dataList = new ArrayList<String[]>();
		
		String[][] indiceDataArray = new String[][] { { " ", " ", " ", "SE",
				"SU", "NE", "NO" } };

		List<PATAMAR> listPatamares = getDadosPatamar(fileREL.getAnoInicial(),
				fileREL.getMesInicial(), fileREL.getAnoFinal(),fileREL.getMesFinal());
		//calculaCMOFooter(listPatamares);



		dataList.addAll(Arrays.asList(indiceDataArray));
//		dataList.addAll(Arrays.asList(dataArray));

		return null;
	}

	public List<PATAMAR> getDadosPatamar(int anoInicial, String mesInicial,
			int anoFinal, String mesFinal) {
		List<PATAMAR> listPATAMAR = new ArrayList<PATAMAR>();
		
		/** RECUPERANDO TODOS OS DADOS DO PATAMAR DO ANO **/
		for (int index = 0; index < fileDAT.getContent().size(); index++) {
			String search = fileDAT.getContent().get(index).trim();
			if (search.startsWith(Integer.toString(anoInicial))) {

				for (int ano = anoInicial; ano <= anoFinal; ano++) {
					search = fileDAT.getContent().get(index).trim();
					PATAMAR patamar = new PATAMAR();
					patamar.setAno(ano);
					for (int aux = index; aux < index+3; aux++) {
						search = fileDAT.getContent().get(aux).trim();
						if (search.startsWith(Integer.toString(ano))) {
							search = search.substring(4, search.length()).trim();
						}
						patamar.getListaNumero().add(search);
						search = "";
					}
					listPATAMAR.add(patamar);
					index = index+3;
				}
				
				/** COM O BREAK NESSE PONTO A EXECUÇÃO PARA QUANDO REALIZA O PRIMEIRO FOR
					ISSO FAZ COM QUE SEJAM CARREGADOS APENAS PRIMEIROS DADOS DO ANO E MESES DOS PATAMARES **/
				break; 
			}
			search = "";
		}
		
		/** FILTRO DE DADOS QUE SEPARA TODOS OS MESES PARA FACILITAR O CALCULO **/
		for (int indicePatamar = 0; indicePatamar < listPATAMAR.size(); indicePatamar++) {
			List<String[]> valores = new ArrayList<String[]>();
			for (int indiceStringPatamar = 0; indiceStringPatamar < 3; indiceStringPatamar++) {
				String[] teste = getLineArrayClean(listPATAMAR.get(indicePatamar).getListaNumero().get(indiceStringPatamar));
				valores.add(teste);
			}
			listPATAMAR.get(indicePatamar).setValorMeses(valores);
		}
		
		calculaCMOFooter(listPATAMAR,Integer.parseInt(mesInicial), Integer.parseInt(mesFinal),anoInicial,anoFinal);
		
		return listPATAMAR;
	}
	
	public void calculaCMOFooter(List<PATAMAR> listPatamares, int mesInicial, int mesFinal,int anoInicial,int anoFinal){
		//List<String[]> cmo = getCMODados();
		//String[][] dataArray = null;
		
		for (int indicePatamar = 0; indicePatamar < listPatamares.size(); indicePatamar++) {
			if (listPatamares.get(indicePatamar).getAno()==anoInicial){
				for (int i = 0; i < listPatamares.get(indicePatamar).getValorMeses().size(); i++) { 
					//PARA CADA UMA DAS 3 LINHAS
					while(mesInicial < 12){
						listCMO.size(); // VERIFICAR ESTE TAMANHO, POIS DEVE SER A DIFERENÇA ENTRE OS MESES DO ANO
						listCMO.get(indicePatamar);	// PEGAR OS DADOS DO CMO DO MES E MULTIPLICAR PELOS DADOS DO PATAMAR
						mesInicial++;
					}
					
				}
			} else if (listPatamares.get(indicePatamar).getAno()==anoFinal){
				for (int i = 0; i < listPatamares.get(indicePatamar).getValorMeses().size(); i++) { 
					//PARA CADA UMA DAS 3 LINHAS
					for (int mes = 0; mes < mesFinal; mes++) {
						listCMO.size(); // VERIFICAR ESTE TAMANHO, POIS DEVE SER A DIFERENÇA ENTRE OS MESES DO ANO
						listCMO.get(indicePatamar);	// PEGAR OS DADOS DO CMO DO MES E MULTIPLICAR PELOS DADOS DO PATAMAR
					}
					
				}
			} else {
				for (int i = 0; i < listPatamares.get(indicePatamar).getValorMeses().size(); i++) { 
					//PARA CADA UMA DAS 3 LINHAS
					for (int mes = 0; mes < 12; mes++) {
						listCMO.size(); // VERIFICAR ESTE TAMANHO, POIS DEVE SER A DIFERENÇA ENTRE OS MESES DO ANO
						listCMO.get(indicePatamar);	// PEGAR OS DADOS DO CMO DO MES E MULTIPLICAR PELOS DADOS DO PATAMAR
					}
				}
			}
			
		}
		
//		
//		for (int i = 0; i < meses; i++) {
//			int pat = 1 + 4 * i;
//			int mes = Integer.parseInt(fileREL.getMesInicial()) + i;
//
//		}
//		String[][] dataArray = new String[][] {
//				 {String.valueOf(i),"CMO","MÊS INICIAL "+mes,cmo.get(pat)[3],cmo.get(pat)[4],cmo.get(pat)[5],cmo.get(pat)[6]}
//				};
		
	}
	
	

	public String[] getLineArrayClean(String string) {
		String[] list = string.split(" ");
		List<String> lineClean = new ArrayList<String>();
		for (String st : list) {
			if (st != null && !st.isEmpty()) {
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
