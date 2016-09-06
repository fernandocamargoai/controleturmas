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
import br.com.ufg.model.dao.DataDAO;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandCadastrarConteudo implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO daoTurma;
	private DataDAO dao;
	
	
	public CommandCadastrarConteudo(DisciplinaDAO daoDisciplina, TurmaDAO daoTurma, DataDAO dao) {
		super();
		this.daoDisciplina = daoDisciplina;
		this.daoTurma = daoTurma;
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
				List<String> listaConteudo = new ArrayList<String>();
				while(paramNames.hasMoreElements()){
					String param = paramNames.nextElement();
					if(param.contains("conteudo")){
						listaConteudo.add(param);
					}
				}
				for(int i=0; i<listaConteudo.size(); i++){
					int idData = Integer.parseInt(listaConteudo.get(i).substring(8));
					if(!dao.pertenceTurma(idData, turma)){
						request.setAttribute("mensagem", "Esse registro de conteúdo não te pertence ou não existe!");
						return "disciplinas";
					}
					String aulaNormal = request.getParameter("aulaNormal"+idData);
					String conteudo = request.getParameter(listaConteudo.get(i));
					dao.atualizar(idData, aulaNormal != null, conteudo);
				}
				request.setAttribute("mensagem", "Registros de conteúdo incluídos com sucesso!");
			}
			else{
				request.setAttribute("mensagem", "Essa turma não te pertence ou não existe!");
				return "disciplinas";
			}
		} catch (SQLException e) {
			request.setAttribute("mensagemData", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			e.printStackTrace();
		}
		
		return "conteudo?codigoTurma="+codigoTurma;
	}

}
