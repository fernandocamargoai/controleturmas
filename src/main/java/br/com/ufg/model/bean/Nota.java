package br.com.ufg.model.bean;

public class Nota {
	private int idNota;
	private int codigoAluno;
	private float nota;
	private int peso;
	
	public int getIdNota() {
		return idNota;
	}
	public void setIdNota(int idNota) {
		this.idNota = idNota;
	}
	public int getCodigoAluno() {
		return codigoAluno;
	}
	public void setCodigoAluno(int codigoAluno) {
		this.codigoAluno = codigoAluno;
	}
	public float getNota() {
		return nota;
	}
	public void setNota(float nota) {
		this.nota = nota;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	public int getPeso() {
		return peso;
	}	
}
