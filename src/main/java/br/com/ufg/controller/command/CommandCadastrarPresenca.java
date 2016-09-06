package br.com.ufg.controller.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.AlunoDAO;
import br.com.ufg.model.dao.DataDAO;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.PresencaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandCadastrarPresenca implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO daoTurma;
	private AlunoDAO daoAluno;
	private DataDAO daoData;
	private PresencaDAO dao;
	
	
	public CommandCadastrarPresenca(DisciplinaDAO daoDisciplina, TurmaDAO daoTurma, AlunoDAO daoAluno, DataDAO daoData, PresencaDAO dao) {
		super();
		this.daoDisciplina = daoDisciplina;
		this.daoTurma = daoTurma;
		this.daoAluno = daoAluno;
		this.daoData = daoData;
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
				Enumeration<String> paramNames = request.getParameterNames();
				List<String> listaId = new ArrayList<String>();
				while(paramNames.hasMoreElements()){
					String param = paramNames.nextElement();
					if(param.contains("id")){
						listaId.add(param);
					}
				}
				for(int i=0; i<listaId.size(); i++){
					int idPresenca = Integer.parseInt(request.getParameter(listaId.get(i)));
					String presenca = request.getParameter("presenca"+idPresenca);
					dao.atualizar(idPresenca, presenca != null);
				}
				request.setAttribute("mensagem", "Registros de presen&ccedil;as incluídos com sucesso!");
			}
			else{
				request.setAttribute("mensagem", "Essa turma não te pertence ou não existe!");
				return "disciplinas";
			}
		} catch (SQLException e) {
			request.setAttribute("mensagemData", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			e.printStackTrace();
		}
		
		return "presenca?codigoTurma="+codigoTurma;
	}

}
