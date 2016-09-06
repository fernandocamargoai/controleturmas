package br.com.ufg.controller.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.dao.ProfessorDAO;

public class CommandLogin implements Command {

	private ProfessorDAO dao;
	
	public CommandLogin(ProfessorDAO dao){
		this.dao = dao;
	}
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		if(request.getAttribute("mensagem") != null){
			return "login.jsp";
		}
		Professor professor = null;
		try {
			professor = dao.getProfessor(Integer.parseInt((String)request.getAttribute("matricula")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("mensagem", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			return "login.jsp";
		}
		if(professor != null){
			if(professor.getSenha().equals((String)request.getParameter("senha"))){
				request.getSession().setAttribute("professor", professor);
				return "user/paineldecontrole.jsp";
			}
		}
		request.getSession().setAttribute("mensagem", "Matrícula/senha incorreta!");
		return "login.jsp";
	}

}
