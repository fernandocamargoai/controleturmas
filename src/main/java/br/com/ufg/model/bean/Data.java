package br.com.ufg.model.bean;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Data {
	private int idData;
	private int codigoTurma;
	private String conteudo;
	private Date dia;
	private boolean aulaNormal;
	
	public int getIdData() {
		return idData;
	}
	public void setIdData(int idData) {
		this.idData = idData;
	}
	public int getCodigoTurma() {
		return codigoTurma;
	}
	public void setCodigoTurma(int codigoTurma) {
		this.codigoTurma = codigoTurma;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public void setDia(Date dia) {
		this.dia = dia;
	}
	public Date getDia() {
		return dia;
	}
	public String getData(){
		return new SimpleDateFormat("dd/MM/yyyy").format(dia);
	}
	public void setAulaNormal(boolean aulaNormal) {
		this.aulaNormal = aulaNormal;
	}
	public boolean isAulaNormal() {
		return aulaNormal;
	}	
}
