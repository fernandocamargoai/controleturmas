package br.com.ufg.controller.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.dao.DisciplinaDAO;

public class CommandEditarDisciplina implements Command {

	private DisciplinaDAO dao;
	
	public CommandEditarDisciplina(DisciplinaDAO dao) {
		super();
		this.dao = dao;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Enumeration<String> paramNames = request.getParameterNames();
		String nome = null;
		String codigoStr = null;
		String cargaHorariaStr = null;
		String query = request.getQueryString();
		while(paramNames.hasMoreElements()){
			String param = paramNames.nextElement();
			if(param.contains("nome")){
					nome = query.substring(query.indexOf(param) + param.length() + 1);
					nome = nome.substring(0, nome.indexOf('&') != -1 ? nome.indexOf('&') : nome.length());
					codigoStr = param.substring(param.indexOf('$') +1);				
			}
			if(param.contains("cargaHoraria")){
				cargaHorariaStr = query.substring(query.indexOf(param) + param.length() + 1);
				cargaHorariaStr = cargaHorariaStr.substring(0, cargaHorariaStr.indexOf('&') != -1 ? cargaHorariaStr.indexOf('&') : cargaHorariaStr.length());
			}
		}
		try {
			nome = URLDecoder.decode(nome, "UTF-8");
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
		int cargaHoraria = 0;
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
		if(nome.equals("")){
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
		if(cargaHorariaStr.equals("")){
			xmlBuffer.append("<tipo>erro</tipo>");
			xmlBuffer.append("<mensagem>");
			xmlBuffer.append("<![CDATA[");
			xmlBuffer.append("A carga horária é obrigatória!");
			xmlBuffer.append("]]>");
			xmlBuffer.append("</mensagem></resposta>");
			out.print(xmlBuffer.toString());
			out.flush();
			return null;
		}
		if(nome.length() > 50){
			xmlBuffer.append("<tipo>erro</tipo>");
			xmlBuffer.append("<mensagem>");
			xmlBuffer.append("<![CDATA[");
			xmlBuffer.append("O nome deve ter no máximo 50 caractéres!");
			xmlBuffer.append("]]>");
			xmlBuffer.append("</mensagem></resposta>");
			out.print(xmlBuffer.toString());
			out.flush();
			return null;
		}
		try{
			cargaHoraria = Integer.parseInt(cargaHorariaStr);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			xmlBuffer.append("<tipo>erro</tipo>");
			xmlBuffer.append("<mensagem>");
			xmlBuffer.append("<![CDATA[");
			xmlBuffer.append("A carga horária deve conter apenas números!");
			xmlBuffer.append("]]>");
			xmlBuffer.append("</mensagem></resposta>");
			out.print(xmlBuffer.toString());
			out.flush();
			return null;
		}
		try {
			if(dao.pertenceProfessor(codigo,(Professor) request.getSession().getAttribute("professor"))){
				dao.editar(codigo, nome, cargaHoraria);
				xmlBuffer.append("<tipo>aviso</tipo>");
				xmlBuffer.append("<mensagem>");
				xmlBuffer.append("<![CDATA[");
				xmlBuffer.append("Edi&ccedil;ão de disciplina concluída com sucesso!");
				xmlBuffer.append("]]>");
				xmlBuffer.append("</mensagem></resposta>");
				out.print(xmlBuffer.toString());
				out.flush();
			}
			else{
				xmlBuffer.append("<tipo>erro</tipo>");
				xmlBuffer.append("<mensagem>");
				xmlBuffer.append("<![CDATA[");
				xmlBuffer.append("Essa disciplina não te pertence ou não existe!");
				xmlBuffer.append("]]>");
				xmlBuffer.append("</mensagem></resposta>");
				xmlBuffer.append(xmlBuffer.toString());
				xmlBuffer.append("]]>");
				xmlBuffer.append("</mensagem></resposta>");
				out.print(xmlBuffer.toString());
				out.flush();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			xmlBuffer.append("<tipo>erro</tipo>");
			xmlBuffer.append("<mensagem>");
			xmlBuffer.append("<![CDATA[");
			xmlBuffer.append("Problema ao salvar! Recarregue a página e tente novamente.");
			xmlBuffer.append("]]>");
			xmlBuffer.append("</mensagem></resposta>");
			out.print(xmlBuffer.toString());
			out.flush();
		}
		return null;
	}

}
