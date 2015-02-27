package br.com.leitor.rel.model;

import java.util.ArrayList;
import java.util.List;

public class PATAMAR {
	
	private int ano;
	private List<String> listaNumero = new ArrayList<String>();
	List<String[]> valorMeses = new ArrayList<String[]>();
	
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public List<String> getListaNumero() {
		return listaNumero;
	}
	public void setListaNumero(List<String> listaNumero) {
		this.listaNumero = listaNumero;
	}
	public List<String[]> getValorMeses() {
		return valorMeses;
	}
	public void setValorMeses(List<String[]> valorMeses) {
		this.valorMeses = valorMeses;
	}

}
