package br.com.ufg.model.bean;

public class Presenca {
	private int idPresenca;
	private int idData;
	private int codigoAluno;
	private boolean presenca;
	
	public int getIdPresenca() {
		return idPresenca;
	}
	public void setIdPresenca(int idPresenca) {
		this.idPresenca = idPresenca;
	}
	public int getIdData() {
		return idData;
	}
	public void setIdData(int idData) {
		this.idData = idData;
	}
	public int getCodigoAluno() {
		return codigoAluno;
	}
	public void setCodigoAluno(int matriculaAluno) {
		this.codigoAluno = matriculaAluno;
	}
	public boolean isPresenca() {
		return presenca;
	}
	public void setPresenca(boolean presenca) {
		this.presenca = presenca;
	}
}
