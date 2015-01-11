package br.com.leitor.rel.model;

import br.com.leitor.rel.util.EnumPAT;

public class CMO {

	private int mes;
	private EnumPAT enumPAT;
	private PAT pat;

	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public EnumPAT getEnumPAT() {
		return enumPAT;
	}
	public void setEnumPAT(EnumPAT enumPAT) {
		this.enumPAT = enumPAT;
	}
	public PAT getPat() {
		return pat;
	}
	public void setPat(PAT pat) {
		this.pat = pat;
	}
	
}
