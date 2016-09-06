package br.com.ufg.controller.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandCadastrarTurma implements Command {

	private TurmaDAO dao;
	
	public CommandCadastrarTurma(TurmaDAO dao){
		super();
		this.dao = dao;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("nocmd", true);
		if(request.getAttribute("mensagem") != null){
			return "turmas?codigoDisciplina="+((Disciplina)request.getSession().getAttribute("disciplina")).getCodigo();
		}
		Turma turma = new Turma();
		turma.setDescricao((String)request.getAttribute("descricao"));
		turma.setCurso((String)request.getAttribute("curso"));
		turma.setAno(Integer.parseInt((String)request.getAttribute("ano")));
		turma.setSemestre(Integer.parseInt((String)request.getAttribute("semestre")));
		turma.setCodigoDisciplina(((Disciplina)request.getSession().getAttribute("disciplina")).getCodigo());
		try{
			dao.cadastrar(turma);
		}
		catch(SQLException e){
			e.printStackTrace();
			request.setAttribute("mensagem", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			return "turmas?codigoDisciplina="+((Disciplina)request.getSession().getAttribute("disciplina")).getCodigo();
		}
		request.setAttribute("mensagem", "Cadastro de turma concluído com sucesso!");
		request.setAttribute("descricao", null);
		request.setAttribute("curso", null);
		request.setAttribute("ano", null);
		request.setAttribute("semestre", null);
		return "turmas?codigoDisciplina="+((Disciplina)request.getSession().getAttribute("disciplina")).getCodigo();
	}


}
