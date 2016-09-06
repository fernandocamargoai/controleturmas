package br.com.ufg.controller.command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Aluno;
import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Nota;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.AlunoDAO;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.NotaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandAlterarPesos implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO daoTurma;
	private AlunoDAO daoAluno;
	private NotaDAO dao;	
	
	public CommandAlterarPesos(DisciplinaDAO daoDisciplina, TurmaDAO daoTurma, AlunoDAO daoAluno, NotaDAO dao) {
		super();
		this.daoDisciplina = daoDisciplina;
		this.daoTurma = daoTurma;
		this.daoAluno = daoAluno;
		this.dao = dao;
	}


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Turma turma = (Turma)request.getSession().getAttribute("turma");
		int codigoTurma = turma.getCodigo();
		Professor professor = (Professor) request.getSession().getAttribute("professor");
		int codigoDisciplina = ((Disciplina)request.getSession().getAttribute("disciplina")).getCodigo();
		try {
			if(daoDisciplina.pertenceProfessor(codigoDisciplina, professor) && daoTurma.pertenceDisciplina(codigoTurma, codigoDisciplina)){
				String pesoN1String = request.getParameter("pesoN1").replace("-", "");
				String pesoN2String = request.getParameter("pesoN2").replace("-", "");
				String pesoN3String = request.getParameter("pesoN3").replace("-", "");
				String pesoN4String = request.getParameter("pesoN4").replace("-", "");
				String pesoN5String = request.getParameter("pesoN5").replace("-", "");
				String pesoN6String = request.getParameter("pesoN6").replace("-", "");
				String pesoN7String = request.getParameter("pesoN7").replace("-", "");
				String pesoN8String = request.getParameter("pesoN8").replace("-", "");
				String pesoN9String = request.getParameter("pesoN9").replace("-", "");
				String pesoN10String = request.getParameter("pesoN10").replace("-", "");
				request.setAttribute("pesoN1", pesoN1String);
				request.setAttribute("pesoN2", pesoN2String);
				request.setAttribute("pesoN3", pesoN3String);
				request.setAttribute("pesoN4", pesoN4String);
				request.setAttribute("pesoN5", pesoN5String);
				request.setAttribute("pesoN6", pesoN6String);
				request.setAttribute("pesoN7", pesoN7String);
				request.setAttribute("pesoN8", pesoN8String);
				request.setAttribute("pesoN9", pesoN9String);
				request.setAttribute("pesoN10", pesoN10String);
				int pesoN1 = 0;
				int pesoN2 = 0;
				int pesoN3 = 0;
				int pesoN4 = 0;
				int pesoN5 = 0;
				int pesoN6 = 0;
				int pesoN7 = 0;
				int pesoN8 = 0;
				int pesoN9 = 0;
				int pesoN10 = 0;
				try {
					pesoN1 = Integer.parseInt(pesoN1String);
				} catch (NumberFormatException e) {
					request.setAttribute("mensagem", "A N1 deve conter apenas números!");
					return "nota?codigoTurma="+codigoTurma;
				}
				try {
					pesoN2 = Integer.parseInt(pesoN2String);
				} catch (NumberFormatException e) {
					request.setAttribute("mensagem", "A N2 deve conter apenas números!");
					return "nota?codigoTurma="+codigoTurma;
				}
				try {
					pesoN3 = Integer.parseInt(pesoN3String);
				} catch (NumberFormatException e) {
					request.setAttribute("mensagem", "A N3 deve conter apenas números!");
					return "nota?codigoTurma="+codigoTurma;
				}
				try {
					pesoN4 = Integer.parseInt(pesoN4String);
				} catch (NumberFormatException e) {
					request.setAttribute("mensagem", "A N4 deve conter apenas números!");
					return "nota?codigoTurma="+codigoTurma;
				}
				try {
					pesoN5 = Integer.parseInt(pesoN5String);
				} catch (NumberFormatException e) {
					request.setAttribute("mensagem", "A N5 deve conter apenas números!");
					return "nota?codigoTurma="+codigoTurma;
				}
				try {
					pesoN6 = Integer.parseInt(pesoN6String);
				} catch (NumberFormatException e) {
					request.setAttribute("mensagem", "A N6 deve conter apenas números!");
					return "nota?codigoTurma="+codigoTurma;
				}
				try {
					pesoN7 = Integer.parseInt(pesoN7String);
				} catch (NumberFormatException e) {
					request.setAttribute("mensagem", "A N7 deve conter apenas números!");
					return "nota?codigoTurma="+codigoTurma;
				}
				try {
					pesoN8 = Integer.parseInt(pesoN8String);
				} catch (NumberFormatException e) {
					request.setAttribute("mensagem", "A N8 deve conter apenas números!");
					return "nota?codigoTurma="+codigoTurma;
				}
				try {
					pesoN9 = Integer.parseInt(pesoN9String);
				} catch (NumberFormatException e) {
					request.setAttribute("mensagem", "A N9 deve conter apenas números!");
					return "nota?codigoTurma="+codigoTurma;
				}
				try {
					pesoN10 = Integer.parseInt(pesoN10String);
				} catch (NumberFormatException e) {
					request.setAttribute("mensagem", "A N10 deve conter apenas números!");
					return "nota?codigoTurma="+codigoTurma;
				}
				
				List<Aluno> alunos = daoAluno.mostrarAlunos(turma);
				for(int i=0; i<alunos.size(); i++){
					List<Nota> notas = dao.listarNotas(alunos.get(i));					
					dao.atualizarPeso(pesoN1, notas.get(0).getIdNota());
					dao.atualizarPeso(pesoN2, notas.get(1).getIdNota());
					dao.atualizarPeso(pesoN3, notas.get(2).getIdNota());
					dao.atualizarPeso(pesoN4, notas.get(3).getIdNota());
					dao.atualizarPeso(pesoN5, notas.get(4).getIdNota());
					dao.atualizarPeso(pesoN6, notas.get(5).getIdNota());
					dao.atualizarPeso(pesoN7, notas.get(6).getIdNota());
					dao.atualizarPeso(pesoN8, notas.get(7).getIdNota());
					dao.atualizarPeso(pesoN9, notas.get(8).getIdNota());
					dao.atualizarPeso(pesoN10, notas.get(9).getIdNota());
				}
				request.setAttribute("mensagemPeso", "Registros de pesos atualizados com sucesso!");
			}
			else{
				request.setAttribute("mensagem", "Essa turma não te pertence ou não existe!");
				return "disciplinas";
			}
		} catch (SQLException e) {
			request.setAttribute("mensagemPeso", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			e.printStackTrace();
		}
		
		return "nota?codigoTurma="+codigoTurma;
	}

}
