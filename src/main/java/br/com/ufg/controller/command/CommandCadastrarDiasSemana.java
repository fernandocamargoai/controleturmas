package br.com.ufg.controller.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandCadastrarDiasSemana implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO dao;

	public CommandCadastrarDiasSemana(DisciplinaDAO daoDisciplina, TurmaDAO dao) {
		super();
		this.daoDisciplina = daoDisciplina;
		this.dao = dao;
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String domingo = request.getParameter("domingo");
		String segunda = request.getParameter("segunda");
		String terca = request.getParameter("terca");
		String quarta = request.getParameter("quarta");
		String quinta = request.getParameter("quinta");
		String sexta = request.getParameter("sexta");
		String sabado = request.getParameter("sabado");
		int codigoTurma = ((Turma)request.getSession().getAttribute("turma")).getCodigo();
		try {
			dao.limparDiasSemana(codigoTurma);
			if(domingo != null){
				dao.cadastrarDiaSemana(codigoTurma, domingo);
			}
			if(segunda != null){
				dao.cadastrarDiaSemana(codigoTurma, segunda);
			}
			if(terca != null){
				dao.cadastrarDiaSemana(codigoTurma, terca);
			}
			if(quarta != null){
				dao.cadastrarDiaSemana(codigoTurma, quarta);
			}
			if(quinta != null){
				dao.cadastrarDiaSemana(codigoTurma, quinta);
			}
			if(sexta != null){
				dao.cadastrarDiaSemana(codigoTurma, sexta);
			}
			if(sabado != null){
				dao.cadastrarDiaSemana(codigoTurma, sabado);
			}
			request.setAttribute("mensagemSemana", "Registro incluído com sucesso!");
		} catch (SQLException e) {
			request.setAttribute("mensagem", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			e.printStackTrace();
		}
		return "turma?codigoTurma="+codigoTurma;
	}

}
