package br.com.ufg.controller.command;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Aluno;
import br.com.ufg.model.bean.Data;
import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Presenca;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.AlunoDAO;
import br.com.ufg.model.dao.DataDAO;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.PresencaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandPresenca implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO daoTurma;
	private AlunoDAO daoAluno;
	private DataDAO daoData;
	private PresencaDAO dao;

	public CommandPresenca(DisciplinaDAO daoDisciplina, TurmaDAO daoTurma, AlunoDAO daoAluno, DataDAO daoData, PresencaDAO dao){
		super();
		this.daoDisciplina = daoDisciplina;
		this.daoTurma = daoTurma;
		this.daoAluno = daoAluno;
		this.daoData = daoData;
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
				List<Data> datas = daoData.mostrarDatasAulaNormal(turma);				
				HashMap<String, Presenca> mapaPresencas = new HashMap<String, Presenca>();
				for(int i=0; i<alunos.size(); i++){
					for(int j=0; j<datas.size(); j++){
						Presenca presenca = dao.get(alunos.get(i), datas.get(j));
						if(presenca == null){
							dao.cadastrar(alunos.get(i), datas.get(j));
							presenca = dao.get(alunos.get(i), datas.get(j));
						}
						mapaPresencas.put(alunos.get(i).getCodigo() + "$" + datas.get(j).getIdData(), presenca);
					}
				}
				request.setAttribute("alunos", alunos);
				request.setAttribute("datas", datas);
				request.setAttribute("mapaPresencas", mapaPresencas);
			}
			else{
				request.setAttribute("mensagem", "Essa turma não te pertence ou não existe!");
				return "disciplinas";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("mensagem", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
		}

		return "presenca.jsp";
	}

}
