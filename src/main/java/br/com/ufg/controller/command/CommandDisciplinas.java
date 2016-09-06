package br.com.ufg.controller.command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.dao.DisciplinaDAO;

public class CommandDisciplinas implements Command {

	private DisciplinaDAO dao;
	
	
	public CommandDisciplinas(DisciplinaDAO dao) {
		super();
		this.dao = dao;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Disciplina> disciplinas = dao.mostrarDisciplinas((Professor)request.getSession().getAttribute("professor"));
			if(disciplinas.size() > 0){
				request.setAttribute("disciplinas", disciplinas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("mensagem2", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
		}
		
		return "disciplinas.jsp";
	}

}
