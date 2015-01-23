package br.com.leitor.rel.model;

import java.util.List;

import br.com.leitor.rel.util.EnumPAT;

public class CMO {

	private int mes;
	private EnumPAT enumPAT;
	private PAT pat;
	private List<String> earmi;
	private List<String> cmo;


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
	public List<String> getEarmi() {
		return earmi;
	}
	public void setEarmi(List<String> earmi) {
		this.earmi = earmi;
	}
	public List<String> getCmo() {
		return cmo;
	}
	public void setCmo(List<String> cmo) {
		this.cmo = cmo;
	}
	
	
	
	
}
