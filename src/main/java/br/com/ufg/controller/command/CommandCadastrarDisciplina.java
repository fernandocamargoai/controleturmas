package br.com.ufg.controller.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.dao.DisciplinaDAO;

public class CommandCadastrarDisciplina implements Command {

	private DisciplinaDAO dao;
	
	public CommandCadastrarDisciplina(DisciplinaDAO dao){
		super();
		this.dao = dao;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("nocmd", true);
		if(request.getAttribute("mensagem") != null){
			return "disciplinas";
		}
		Disciplina disciplina = new Disciplina();
		disciplina.setNome((String) request.getAttribute("nome"));
		disciplina.setCargaHoraria(Integer.parseInt((String) request.getAttribute("cargaHoraria")));
		Professor professor = (Professor)request.getSession().getAttribute("professor");
		disciplina.setMatriculaProfessor(professor.getMatricula());
		try{
			if(dao.notExists(disciplina, professor)){
				dao.cadastrar(disciplina);
			}
			else{
				request.setAttribute("mensagem", "Já existe uma disciplina com esse nome!");
				return "disciplinas";
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			request.setAttribute("mensagem", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			return "disciplinas";
		}
		request.setAttribute("mensagem", "Cadastro de disciplina concluído com sucesso!");
		request.setAttribute("nome", null);
		request.setAttribute("cargaHoraria", null);
		return "disciplinas";
	}


}
