package br.com.ufg.controller.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.dao.DisciplinaDAO;

public class CommandExcluirDisciplina implements Command {

	private DisciplinaDAO dao;
	
	public CommandExcluirDisciplina(DisciplinaDAO dao) {
		super();
		this.dao = dao;
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		int codigo = Integer.parseInt(request.getParameter("codigo"));

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
		try {
			if(dao.pertenceProfessor(codigo,(Professor) request.getSession().getAttribute("professor"))){
				dao.excluir(codigo);
				xmlBuffer.append("<tipo>aviso</tipo>");
				xmlBuffer.append("<mensagem>");
				xmlBuffer.append("<![CDATA[");
				xmlBuffer.append("Exclusão de disciplina concluída com sucesso!");
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
