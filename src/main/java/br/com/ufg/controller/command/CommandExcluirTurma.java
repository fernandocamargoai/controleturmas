package br.com.ufg.controller.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandExcluirTurma implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO dao;

	public CommandExcluirTurma(DisciplinaDAO daoDisciplina, TurmaDAO dao) {
		super();
		this.daoDisciplina = daoDisciplina;
		this.dao = dao;
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		int codigo = 0;
		boolean erroCodigo = false;
		try {
			codigo = Integer.parseInt(request.getParameter("codigo"));
		} catch (NumberFormatException e1) {
			erroCodigo = true;
			e1.printStackTrace();
		}
		Professor professor = (Professor) request.getSession().getAttribute("professor");
		int codigoDisciplina = ((Disciplina)request.getSession().getAttribute("disciplina")).getCodigo();

		PrintWriter out = null;
		StringBuffer xmlBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlBuffer.append("<resposta>");
		try{
			try {
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(erroCodigo){
				xmlBuffer.append("<tipo>erro</tipo>");
				xmlBuffer.append("<mensagem>");
				xmlBuffer.append("<![CDATA[");
				xmlBuffer.append("Código inválido!");
				xmlBuffer.append("]]>");
				xmlBuffer.append("</mensagem></resposta>");
				out.print(xmlBuffer.toString());
				out.flush();
				return null;
			}
			if(daoDisciplina.pertenceProfessor(codigoDisciplina, professor) && dao.pertenceDisciplina(codigo, codigoDisciplina)){				
				dao.excluir(codigo);
				xmlBuffer.append("<tipo>aviso</tipo>");
				xmlBuffer.append("<mensagem>");
				xmlBuffer.append("<![CDATA[");
				xmlBuffer.append("Exclusão de turma concluída com sucesso!");
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
		}
		catch(SQLException e){
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
