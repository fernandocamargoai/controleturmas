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
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.NotaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandCadastrarNota implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO daoTurma;
	private AlunoDAO daoAluno;
	private NotaDAO dao;	
	
	public CommandCadastrarNota(DisciplinaDAO daoDisciplina, TurmaDAO daoTurma, AlunoDAO daoAluno, NotaDAO dao) {
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
				Enumeration<String> paramNames = request.getParameterNames();
				List<String> listaNota = new ArrayList<String>();
				while(paramNames.hasMoreElements()){
					String param = paramNames.nextElement();
					if(param.contains("nota")){
						listaNota.add(param);
					}
				}
				boolean tudoOk = true;
				for(int i=0; i<listaNota.size(); i++){
					int idNota = Integer.parseInt(listaNota.get(i).substring(4));
					String notaString = request.getParameter(listaNota.get(i));
					try {
						float nota = Float.parseFloat(notaString.replace(',', '.'));
						if(nota >= 0 && nota <= 10){
							dao.atualizar(idNota, nota);
						}
						else{
							tudoOk = false;
						}						
					} catch (NumberFormatException e) {
						tudoOk = false;
					}
				}
				if(tudoOk){
					request.setAttribute("mensagem", "Registros de notas incluídos com sucesso!");
				}
				else{
					request.setAttribute("mensagem", "Houve erros na inclusão dos registros! As notas em formato errado foram ignoradas e as corretas foram registradas!");
				}				
			}
			else{
				request.setAttribute("mensagem", "Essa turma não te pertence ou não existe!");
				return "disciplinas";
			}
		} catch (SQLException e) {
			request.setAttribute("mensagem", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			e.printStackTrace();
		}
		
		return "nota?codigoTurma="+codigoTurma;
	}

}
