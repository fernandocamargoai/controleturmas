package br.com.ufg.controller.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandEditarTurma implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO dao;
	
	public CommandEditarTurma(DisciplinaDAO daoDisciplina, TurmaDAO dao) {
		super();
		this.daoDisciplina = daoDisciplina;
		this.dao = dao;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Enumeration<String> paramNames = request.getParameterNames();
		String descricao = null;
		String curso = null;
		String anoStr = null;
		String semestreStr = null;
		String codigoStr = null;
		String query = request.getQueryString();
		while(paramNames.hasMoreElements()){
			String param = paramNames.nextElement();
			if(param.contains("descricao")){
					descricao = query.substring(query.indexOf(param) + param.length() + 1);
					descricao = descricao.substring(0, descricao.indexOf('&') != -1 ? descricao.indexOf('&') : descricao.length());
					codigoStr = param.substring(param.indexOf('$') +1);				
			}
			if(param.contains("curso")){
				curso = query.substring(query.indexOf(param) + param.length() + 1);
				curso = curso.substring(0, curso.indexOf('&') != -1 ? curso.indexOf('&') : curso.length());
			}
			if(param.contains("ano")){
				anoStr = query.substring(query.indexOf(param) + param.length() + 1);
				anoStr = anoStr.substring(0, anoStr.indexOf('&') != -1 ? anoStr.indexOf('&') : anoStr.length());
			}
			if(param.contains("semestre")){
				semestreStr = query.substring(query.indexOf(param) + param.length() + 1);
				semestreStr = semestreStr.substring(0, semestreStr.indexOf('&') != -1 ? semestreStr.indexOf('&') : semestreStr.length());
			}
		}
		try {
			descricao = URLDecoder.decode(descricao, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			curso = URLDecoder.decode(curso, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		PrintWriter out = null;
		StringBuffer xmlBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlBuffer.append("<resposta>");
		try {
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int codigo = 0;
		try{
			codigo = Integer.parseInt(codigoStr);
		}
		catch(NumberFormatException e){
			e.printStackTrace();
			xmlBuffer.append("<tipo>erro</tipo>");
			xmlBuffer.append("<mensagem>");
			xmlBuffer.append("<![CDATA[");
			xmlBuffer.append("Problema ao salvar! Recarregue a página e tente novamente.");
			xmlBuffer.append("]]>");
			xmlBuffer.append("</mensagem></resposta>");
			out.print(xmlBuffer.toString());
			out.flush();
			return null;
		}
		
		int ano = 0;
		int semestre = 0;
		
		if (descricao.equals("")) {
			xmlBuffer.append("<tipo>erro</tipo>");
			xmlBuffer.append("<mensagem>");
			xmlBuffer.append("<![CDATA[");
			xmlBuffer.append("A turma é obrigatório!");
			xmlBuffer.append("]]>");
			xmlBuffer.append("</mensagem></resposta>");
			out.print(xmlBuffer.toString());
			out.flush();
			return null;
		} else {
			if (curso.equals("")) {
				xmlBuffer.append("<tipo>erro</tipo>");
				xmlBuffer.append("<mensagem>");
				xmlBuffer.append("<![CDATA[");
				xmlBuffer.append("O curso horária é obrigatório!");
				xmlBuffer.append("]]>");
				xmlBuffer.append("</mensagem></resposta>");
				out.print(xmlBuffer.toString());
				out.flush();
				return null;
			}
			else {
				if(anoStr.equals("")){
					xmlBuffer.append("<tipo>erro</tipo>");
					xmlBuffer.append("<mensagem>");
					xmlBuffer.append("<![CDATA[");
					xmlBuffer.append("O ano é obrigatório!");
					xmlBuffer.append("]]>");
					xmlBuffer.append("</mensagem></resposta>");
					out.print(xmlBuffer.toString());
					out.flush();
					return null;
				}
				else{
					if(semestreStr.equals("")){
						xmlBuffer.append("<tipo>erro</tipo>");
						xmlBuffer.append("<mensagem>");
						xmlBuffer.append("<![CDATA[");
						xmlBuffer.append("O semestre é obrigatório!");
						xmlBuffer.append("]]>");
						xmlBuffer.append("</mensagem></resposta>");
						out.print(xmlBuffer.toString());
						out.flush();
						return null;
					}
					else{
						if(descricao.length() > 3){
							xmlBuffer.append("<tipo>erro</tipo>");
							xmlBuffer.append("<mensagem>");
							xmlBuffer.append("<![CDATA[");
							xmlBuffer.append("A turma deve conter no máximo 3 caracteres!");
							xmlBuffer.append("]]>");
							xmlBuffer.append("</mensagem></resposta>");
							out.print(xmlBuffer.toString());
							out.flush();
							return null;
						}
						else{
							if(curso.length() > 30){
								xmlBuffer.append("<tipo>erro</tipo>");
								xmlBuffer.append("<mensagem>");
								xmlBuffer.append("<![CDATA[");
								xmlBuffer.append("O curso deve conter no máximo 30 caracteres!");
								xmlBuffer.append("]]>");
								xmlBuffer.append("</mensagem></resposta>");
								out.print(xmlBuffer.toString());
								out.flush();
								return null;
							}
							else{
								if(anoStr.length() != 4){
									xmlBuffer.append("<tipo>erro</tipo>");
									xmlBuffer.append("<mensagem>");
									xmlBuffer.append("<![CDATA[");
									xmlBuffer.append("O ano deve conter 4 caracteres!");
									xmlBuffer.append("]]>");
									xmlBuffer.append("</mensagem></resposta>");
									out.print(xmlBuffer.toString());
									out.flush();
									return null;
								}
								else{
									if(semestreStr.length() != 1){
										xmlBuffer.append("<tipo>erro</tipo>");
										xmlBuffer.append("<mensagem>");
										xmlBuffer.append("<![CDATA[");
										xmlBuffer.append("O semestre deve conter 1 caracter (1 ou 2)!");
										xmlBuffer.append("]]>");
										xmlBuffer.append("</mensagem></resposta>");
										out.print(xmlBuffer.toString());
										out.flush();
										return null;
									}
									else{
										try{
											ano = Integer.parseInt(anoStr);
											try{
												semestre = Integer.parseInt(semestreStr);
												if(semestre != 1 && semestre != 2){
													xmlBuffer.append("<tipo>erro</tipo>");
													xmlBuffer.append("<mensagem>");
													xmlBuffer.append("<![CDATA[");
													xmlBuffer.append("O semestre deve conter 1 ou 2!");
													xmlBuffer.append("]]>");
													xmlBuffer.append("</mensagem></resposta>");
													out.print(xmlBuffer.toString());
													out.flush();
													return null;
												}
											}
											catch(NumberFormatException e){
												xmlBuffer.append("<tipo>erro</tipo>");
												xmlBuffer.append("<mensagem>");
												xmlBuffer.append("<![CDATA[");
												xmlBuffer.append("O semestre deve conter apenas números!");
												xmlBuffer.append("]]>");
												xmlBuffer.append("</mensagem></resposta>");
												out.print(xmlBuffer.toString());
												out.flush();
												return null;
											}
										}
										catch(NumberFormatException e){
											xmlBuffer.append("<tipo>erro</tipo>");
											xmlBuffer.append("<mensagem>");
											xmlBuffer.append("<![CDATA[");
											xmlBuffer.append("O ano deve conter apenas números!");
											xmlBuffer.append("]]>");
											xmlBuffer.append("</mensagem></resposta>");
											out.print(xmlBuffer.toString());
											out.flush();
											return null;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		Professor professor = (Professor) request.getSession().getAttribute("professor");
		int codigoDisciplina = ((Disciplina)request.getSession().getAttribute("disciplina")).getCodigo();
		try {
			if(daoDisciplina.pertenceProfessor(codigoDisciplina, professor) && dao.pertenceDisciplina(codigo, codigoDisciplina)){
				dao.editar(codigo, descricao, curso, ano, semestre);
				xmlBuffer.append("<tipo>aviso</tipo>");
				xmlBuffer.append("<mensagem>");
				xmlBuffer.append("<![CDATA[");
				xmlBuffer.append("Edi&ccedil;ão de turma concluída com sucesso!");
				xmlBuffer.append("]]>");
				xmlBuffer.append("</mensagem></resposta>");
				out.print(xmlBuffer.toString());
				out.flush();
			}
			else{
				xmlBuffer.append("<tipo>erro</tipo>");
				xmlBuffer.append("<mensagem>");
				xmlBuffer.append("<![CDATA[");
				xmlBuffer.append("Essa turma não te pertence ou não existe!");
				xmlBuffer.append("]]>");
				xmlBuffer.append("</mensagem></resposta>");
				out.print(xmlBuffer.toString());
				out.flush();
			}
		} catch (SQLException e) {
			xmlBuffer.append("<tipo>erro</tipo>");
			xmlBuffer.append("<mensagem>");
			xmlBuffer.append("<![CDATA[");
			xmlBuffer.append("Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			xmlBuffer.append("]]>");
			xmlBuffer.append("</mensagem></resposta>");
			out.print(xmlBuffer.toString());
			out.flush();
			e.printStackTrace();
		}
		return null;
	}

}
