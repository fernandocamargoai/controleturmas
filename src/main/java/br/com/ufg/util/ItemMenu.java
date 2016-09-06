package br.com.ufg.util;

public class ItemMenu {
	
	private String nome;
	private String url;
	private String titulo;
	
	public ItemMenu(){
		
	}

	public ItemMenu(String nome, String url, String titulo) {
		super();
		this.nome = nome;
		this.url = url;
		this.setTitulo(titulo);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}
	
	
}
