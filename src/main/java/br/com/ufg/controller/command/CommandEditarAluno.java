package br.com.ufg.controller.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.AlunoDAO;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandEditarAluno implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO daoTurma;
	private AlunoDAO dao;
	
	
	public CommandEditarAluno(DisciplinaDAO daoDisciplina, TurmaDAO daoTurma, AlunoDAO dao) {
		super();		
		this.daoDisciplina = daoDisciplina;
		this.daoTurma = daoTurma;
		this.dao = dao;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Enumeration<String> paramNames = request.getParameterNames();
		String matricula = null;
		String nome = null;
		String email = null;		
		String codigoStr = null;
		String query = request.getQueryString();
		while(paramNames.hasMoreElements()){
			String param = paramNames.nextElement();
			if(param.contains("matricula")){
				matricula = query.substring(query.indexOf(param) + param.length() + 1);
				matricula = matricula.substring(0, matricula.indexOf('&') != -1 ? matricula.indexOf('&') : matricula.length());
				codigoStr = param.substring(param.indexOf('$') +1);				
			}
			if(param.contains("nome")){
				nome = query.substring(query.indexOf(param) + param.length() + 1);
				nome = nome.substring(0, nome.indexOf('&') != -1 ? nome.indexOf('&') : nome.length());
			}
			if(param.contains("email")){
				email = query.substring(query.indexOf(param) + param.length() + 1);
				email = email.substring(0, email.indexOf('&') != -1 ? email.indexOf('&') : email.length());
			}
		}
		try {
			nome = URLDecoder.decode(nome, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			email = URLDecoder.decode(email, "UTF-8");
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

		if (matricula.equals("")) {
			xmlBuffer.append("<tipo>erro</tipo>");
			xmlBuffer.append("<mensagem>");
			xmlBuffer.append("<![CDATA[");
			xmlBuffer.append("A matrí é obrigatória!");
			xmlBuffer.append("]]>");
			xmlBuffer.append("</mensagem></resposta>");
			out.print(xmlBuffer.toString());
			out.flush();
			return null;
		} else {
			if (nome.equals("")) {
				xmlBuffer.append("<tipo>erro</tipo>");
				xmlBuffer.append("<mensagem>");
				xmlBuffer.append("<![CDATA[");
				xmlBuffer.append("O nome é obrigatório!");
				xmlBuffer.append("]]>");
				xmlBuffer.append("</mensagem></resposta>");
				out.print(xmlBuffer.toString());
				out.flush();
				return null;
			}
			else {
				if(email.equals("")){
					xmlBuffer.append("<tipo>erro</tipo>");
					xmlBuffer.append("<mensagem>");
					xmlBuffer.append("<![CDATA[");
					xmlBuffer.append("O e-mail é obrigatório!");
					xmlBuffer.append("]]>");
					xmlBuffer.append("</mensagem></resposta>");
					out.print(xmlBuffer.toString());
					out.flush();
					return null;
				}
				else{					
					if(matricula.length() > 6){
						xmlBuffer.append("<tipo>erro</tipo>");
						xmlBuffer.append("<mensagem>");
						xmlBuffer.append("<![CDATA[");
						xmlBuffer.append("A matrícula deve conter no máximo 6 caracteres!");
						xmlBuffer.append("]]>");
						xmlBuffer.append("</mensagem></resposta>");
						out.print(xmlBuffer.toString());
						out.flush();
						return null;
					}
					else{
						if(nome.length() > 60){
							xmlBuffer.append("<tipo>erro</tipo>");
							xmlBuffer.append("<mensagem>");
							xmlBuffer.append("<![CDATA[");
							xmlBuffer.append("O nome deve conter no máximo 60 caracteres!");
							xmlBuffer.append("]]>");
							xmlBuffer.append("</mensagem></resposta>");
							out.print(xmlBuffer.toString());
							out.flush();
							return null;
						}
						else{
							if(email.length() > 60){
								xmlBuffer.append("<tipo>erro</tipo>");
								xmlBuffer.append("<mensagem>");
								xmlBuffer.append("<![CDATA[");
								xmlBuffer.append("O e-mail deve conter no máximo 60 caracteres!");
								xmlBuffer.append("]]>");
								xmlBuffer.append("</mensagem></resposta>");
								out.print(xmlBuffer.toString());
								out.flush();
								return null;
							}
							else{
								Pattern pattern = Pattern.compile("^[0-9]+$");
								Matcher matcher = pattern.matcher(matricula);
								if(!matcher.matches()){
									xmlBuffer.append("<tipo>erro</tipo>");
									xmlBuffer.append("<mensagem>");
									xmlBuffer.append("<![CDATA[");
									xmlBuffer.append("A matrí deve conter apenas números!");
									xmlBuffer.append("]]>");
									xmlBuffer.append("</mensagem></resposta>");
									out.print(xmlBuffer.toString());
									out.flush();
									return null;
								}
								else{
									pattern = Pattern.compile("^[\\w\\-\\.\\+]+\\@[a-zA-Z0-9\\.\\-]+\\.[a-zA-z0-9]{2,4}$");
									matcher = pattern.matcher(email);
									if(!matcher.matches()){
										xmlBuffer.append("<tipo>erro</tipo>");
										xmlBuffer.append("<mensagem>");
										xmlBuffer.append("<![CDATA[");
										xmlBuffer.append("O e-mail está em formato inválido!");
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
		
		Professor professor = (Professor) request.getSession().getAttribute("professor");
		int codigoDisciplina = ((Disciplina)request.getSession().getAttribute("disciplina")).getCodigo();
		int codigoTurma = ((Turma)request.getSession().getAttribute("turma")).getCodigo();
		try {
			if(daoDisciplina.pertenceProfessor(codigoDisciplina, professor) && daoTurma.pertenceDisciplina(codigoTurma, codigoDisciplina) && dao.pertenceTurma(codigo, codigoTurma)){
				dao.editar(codigo, Integer.parseInt(matricula), nome, email);
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
				xmlBuffer.append("Esse aluno não te pertence ou não existe!");
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
