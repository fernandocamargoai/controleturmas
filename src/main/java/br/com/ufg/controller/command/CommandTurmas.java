package br.com.ufg.controller.command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandTurmas implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO dao;

	public CommandTurmas(DisciplinaDAO daoDisciplina, TurmaDAO dao){
		this.daoDisciplina = daoDisciplina;
		this.dao = dao;
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String codigoDisciplinaStr = request.getParameter("codigoDisciplina");
			int codigoDisciplina = 0;
			try {
				codigoDisciplina = Integer.parseInt(codigoDisciplinaStr);
			} catch (NumberFormatException e) {
				request.setAttribute("mensagem", "Código de disciplina inválido!");
				e.printStackTrace();
				return "disciplinas";
			}
			Professor professor = (Professor) request.getSession().getAttribute("professor");

			if(daoDisciplina.pertenceProfessor(codigoDisciplina, professor)){
				Disciplina disciplina = daoDisciplina.get(codigoDisciplina);
				List<Turma> turmas = dao.mostrarTurmas(disciplina);
				request.getSession().setAttribute("disciplina", disciplina);
				if(turmas.size() > 0){
					request.setAttribute("turmas", turmas);
				}
			}
			else{
				request.setAttribute("mensagem", "Essa disciplina não te pertence ou não existe!");
				return "disciplinas";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("mensagem2", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
		}
		
		return "turmas.jsp";
	}

}
