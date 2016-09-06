package br.com.ufg.controller.command;

import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandCadastrarDatas implements Command {

	private TurmaDAO dao;
	
	
	public CommandCadastrarDatas(TurmaDAO dao) {
		super();
		this.dao = dao;
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		Turma turma = (Turma)request.getSession().getAttribute("turma");
		int codigoTurma = turma.getCodigo();
		if(request.getAttribute("mensagemData") != null){
			return "turma?codigoTurma="+codigoTurma;
		}
				
		Date dataInicio = (Date)request.getAttribute("dataInicioDate");
		Date dataFim = (Date)request.getAttribute("dataFimDate");
		
		try {
			dao.cadastrarDatas(codigoTurma, dataInicio, dataFim);
			turma.setDataInicio(dataInicio);
			turma.setDataFim(dataFim);
			request.setAttribute("mensagemData", "Registro incluído com sucesso!");
		} catch (SQLException e) {
			request.setAttribute("mensagemData", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			e.printStackTrace();
		}
		return "turma?codigoTurma="+codigoTurma;
	}

}
