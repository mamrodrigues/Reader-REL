package br.com.leitor.rel.util;

import java.util.HashMap;
import java.util.Map;

public enum EnumPAT {
	
	PAT1("1", "PAT1"),
	PAT2("2", "PAT2"),
	PAT3("3", "PAT3");

	private final String valor;
	private final String sigla;
	private static Map<String, String> map;

	private EnumPAT(String valor, String sigla) {
		this.valor = valor;
		this.sigla = sigla;
	}
	
	public String getValor() {
		return valor;
	}

	public static String getSigla(String valor) {
		if (map == null) {
			map = initializeMapping();
		}
		if (map.containsKey(valor)) {
			return map.get(valor);
		}
		return null;
	}

	private static Map<String, String> initializeMapping() {
	    Map<String, String> enumPAT = new HashMap<String, String>();
	    for (EnumPAT ep : EnumPAT.values()) {
	    	enumPAT.put(ep.valor, ep.sigla);
	    }
	    return enumPAT;
	}

}
