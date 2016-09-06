package br.com.ufg.controller.command;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Aluno;
import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.AlunoDAO;
import br.com.ufg.model.dao.DataDAO;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandTurma implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO dao;
	private AlunoDAO daoAluno;
	private DataDAO daoData;

	public CommandTurma(DisciplinaDAO daoDisciplina, TurmaDAO dao, AlunoDAO daoAluno, DataDAO daoData){
		this.daoDisciplina = daoDisciplina;
		this.dao = dao;
		this.daoAluno = daoAluno;
		this.daoData = daoData;
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int codigo = 0;
			boolean erroCodigo = false;
			try {
				codigo = Integer.parseInt(request.getParameter("codigoTurma"));
			} catch (NumberFormatException e1) {
				erroCodigo = true;
				e1.printStackTrace();
			}
			Professor professor = (Professor) request.getSession().getAttribute("professor");
			int codigoDisciplina = ((Disciplina)request.getSession().getAttribute("disciplina")).getCodigo();


			if(erroCodigo){
				request.setAttribute("mensagem", "Código inválido!");
				return "disciplinas";
			}
			if(daoDisciplina.pertenceProfessor(codigoDisciplina, professor) && dao.pertenceDisciplina(codigo, codigoDisciplina)){
				Turma turma = dao.get(codigo);
				request.getSession().setAttribute("turma", turma);
				List<String> diasSemana = dao.listarDiasSemana(codigo);
				if(diasSemana.contains("dom")){
					request.setAttribute("domingo", true);
				}
				if(diasSemana.contains("seg")){
					request.setAttribute("segunda", true);
				}
				if(diasSemana.contains("ter")){
					request.setAttribute("terca", true);
				}
				if(diasSemana.contains("qua")){
					request.setAttribute("quarta", true);
				}
				if(diasSemana.contains("qui")){
					request.setAttribute("quinta", true);
				}
				if(diasSemana.contains("sex")){
					request.setAttribute("sexta", true);
				}
				if(diasSemana.contains("sab")){
					request.setAttribute("sabado", true);
				}
				Date dataInicio = turma.getDataInicio();
				Date dataFim = turma.getDataFim();
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				if(dataInicio != null){
					request.setAttribute("dataInicio", format.format(dataInicio));
				}
				if(dataFim != null){
					request.setAttribute("dataFim", format.format(dataFim));
				}
				request.setAttribute("temDatas", daoData.temDatas(turma.getCodigo()));
				List<Aluno> alunos = daoAluno.mostrarAlunos(turma);
				if(alunos.size() > 0){
					request.setAttribute("alunos", alunos);
				}
			}
			else{
				request.setAttribute("mensagem", "Essa turma não te pertence ou não existe!");
				return "disciplinas";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("mensagem2", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
		}

		return "turma.jsp";
	}

}
