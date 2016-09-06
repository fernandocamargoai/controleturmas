package br.com.ufg.model.bean;

import java.sql.Date;

public class Turma {
	private int codigo;
	private int codigoDisciplina;
	private String descricao;
	private int semestre;
	private int ano;
	private String curso;
	private Date dataInicio;
	private Date dataFim;
	
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date diaInicio) {
		this.dataInicio = diaInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date diaFim) {
		this.dataFim = diaFim;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getCodigoDisciplina() {
		return codigoDisciplina;
	}
	public void setCodigoDisciplina(int codigoDisciplina) {
		this.codigoDisciplina = codigoDisciplina;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getSemestre() {
		return semestre;
	}
	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
}
