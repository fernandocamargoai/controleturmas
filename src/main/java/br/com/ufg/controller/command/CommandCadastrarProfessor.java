package br.com.ufg.controller.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.dao.ProfessorDAO;

public class CommandCadastrarProfessor implements Command {

	private ProfessorDAO dao;
	
	public CommandCadastrarProfessor(ProfessorDAO dao) {
		super();
		this.dao = dao;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if(request.getAttribute("mensagem") != null){
			return "cadastro.jsp";
		}
		Professor professor = new Professor();
		professor.setNome((String)request.getAttribute("nome"));
		int matricula = Integer.parseInt((String)request.getAttribute("matricula"));
		professor.setMatricula(matricula);
		professor.setSenha((String)request.getParameter("senha1"));
		try{
			if(dao.getProfessor(matricula) == null){
				dao.cadastrar(professor);
			}
			else{
				request.setAttribute("mensagem", "A matrícula inserida já foi cadastrada!");
				return "cadastro.jsp";
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			request.setAttribute("mensagem", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			return "cadastro.jsp";
		}
		request.setAttribute("mensagem", "Cadastro concluído com sucesso! Você já pode se logar!");
		request.setAttribute("matricula", null);
		request.setAttribute("nome", null);
		return "cadastro.jsp";
	}

}
