package br.com.ufg.controller.command;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
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

public class CommandNota implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO daoTurma;
	private AlunoDAO daoAluno;
	private NotaDAO dao;

	public CommandNota(DisciplinaDAO daoDisciplina, TurmaDAO daoTurma, AlunoDAO daoAluno, NotaDAO dao){
		super();
		this.daoDisciplina = daoDisciplina;
		this.daoTurma = daoTurma;
		this.daoAluno = daoAluno;
		this.dao = dao;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
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
			if(daoDisciplina.pertenceProfessor(codigoDisciplina, professor) && daoTurma.pertenceDisciplina(codigo, codigoDisciplina)){
				Turma turma = daoTurma.get(codigo);
				request.getSession().setAttribute("turma", turma);
				List<Aluno> alunos = daoAluno.mostrarAlunos(turma);
				HashMap<Integer, List<Nota>> mapaNotas = new HashMap<Integer, List<Nota>>();
				HashMap<Integer, Float> mapaMedia = new HashMap<Integer, Float>();
				for(int i=0; i<alunos.size(); i++){
					List<Nota> notas = dao.listarNotas(alunos.get(i));
					if(notas.isEmpty()){
						for(int j=0; j<10; j++){
							dao.cadastrar(alunos.get(i));
						}
						notas = dao.listarNotas(alunos.get(i));
					}					
					mapaNotas.put(alunos.get(i).getCodigo(), notas);
					mapaMedia.put(alunos.get(i).getCodigo(), calcularMedia(notas));
				}
				request.setAttribute("alunos", alunos);
				request.setAttribute("mapaNotas", mapaNotas);
				request.setAttribute("mapaMedia", mapaMedia);
			}
			else{
				request.setAttribute("mensagem", "Essa turma não te pertence ou não existe!");
				return "disciplinas";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("mensagem", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
		}

		return "nota.jsp";
	}

	private float calcularMedia(List<Nota> notas) {
		float media = 0;
		int somaPesos = 0;
		for(Nota nota: notas){
			media += nota.getNota() * nota.getPeso();
			somaPesos += nota.getPeso();
		}
		if(somaPesos == 0){
			return 0;
		}
		return (new BigDecimal(String.valueOf(media/somaPesos)).setScale(2, BigDecimal.ROUND_HALF_UP)).floatValue();
	}

}
