package br.com.leitor.rel.util;
import java.util.HashMap;
import java.util.Map;

public enum EnumMesArquivo {

	JANEIRO("1", "jan"),
	FEVEREIRO("2", "fev"),
	MARCO("3", "mar"),
	ABRIL("4", "abr"),
	MAIO("5", "mai"),
	JUNHO("6", "jun"),
	JULHO("7", "jul"),
	AGOSTO("8", "ago"),
	SETEMBRO("9", "set"),
	OUTUBRO("10", "out"),
	NOVEMBRO("11", "nov"),
	DEZEMBRO("12","dez");

	private final String valor;
	private final String sigla;
	private static Map<String, String> map;

	private EnumMesArquivo(String valor, String sigla) {
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
	    Map<String, String> enumMA = new HashMap<String, String>();
	    for (EnumMesArquivo ea : EnumMesArquivo.values()) {
	    	enumMA.put(ea.valor, ea.sigla);
	    }
	    return enumMA;
	}

}
