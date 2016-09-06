package br.com.ufg.model.bean;

public class RelacaoAlunoNotas {
	private int matricula;
	private String nome;
	private float n1;
	private float n2;
	private float n3;
	private float n4;
	private float n5;
	private float n6;
	private float n7;
	private float n8;
	private float n9;
	private float n10;
	private float media;
	private float frequencia;
	private int presencas;
	
	public RelacaoAlunoNotas(){
		this.n1 = -1;
		this.n2 = -1;
		this.n3 = -1;
		this.n4 = -1;
		this.n5 = -1;
		this.n6 = -1;
		this.n7 = -1;
		this.n8 = -1;
		this.n9 = -1;
		this.n10 = -1;
	}
	
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public float getN1() {
		return n1;
	}
	public void setN1(float n1) {
		this.n1 = n1;
	}
	public float getN2() {
		return n2;
	}
	public void setN2(float n2) {
		this.n2 = n2;
	}
	public float getN3() {
		return n3;
	}
	public void setN3(float n3) {
		this.n3 = n3;
	}
	public float getN4() {
		return n4;
	}
	public void setN4(float n4) {
		this.n4 = n4;
	}
	public float getN5() {
		return n5;
	}
	public void setN5(float n5) {
		this.n5 = n5;
	}
	public float getN6() {
		return n6;
	}
	public void setN6(float n6) {
		this.n6 = n6;
	}
	public float getN7() {
		return n7;
	}
	public void setN7(float n7) {
		this.n7 = n7;
	}
	public float getN8() {
		return n8;
	}
	public void setN8(float n8) {
		this.n8 = n8;
	}
	public float getN9() {
		return n9;
	}
	public void setN9(float n9) {
		this.n9 = n9;
	}
	public float getN10() {
		return n10;
	}
	public void setN10(float n10) {
		this.n10 = n10;
	}
	public float getMedia() {
		return media;
	}
	public void setMedia(float media) {
		this.media = media;
	}
	public float getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(float frequencia) {
		this.frequencia = frequencia;
	}

	public void setPresencas(int presencas) {
		this.presencas = presencas;
	}

	public int getPresencas() {
		return presencas;
	}
}
