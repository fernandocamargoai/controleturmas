package br.com.ufg.controller.command;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.DataDAO;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandGerarDatas implements Command {

	private DisciplinaDAO daoDisciplina;
	private TurmaDAO daoTurma;
	private DataDAO dao;
	
	
	public CommandGerarDatas(DisciplinaDAO daoDisciplina, TurmaDAO daoTurma, DataDAO dao) {
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
				Date dataInicio = turma.getDataInicio();
				Date dataFim = turma.getDataFim();
				
				if(dataInicio == null || dataFim == null){
					request.setAttribute("mensagemGerar", "As datas de início e fim do semestre devem ser registradas antes de gerar as datas!");
					return "turma?codigoTurma="+codigoTurma;
				}
				
				List<String> listaDiasSemanaString = daoTurma.listarDiasSemana(codigoTurma);
				
				if(listaDiasSemanaString.size() == 0){
					request.setAttribute("mensagemGerar", "Os dias da semana devem ser registrados antes de gerar as datas!");
					return "turma?codigoTurma="+codigoTurma;
				}
				
				List<Integer> listaDiasSemana = new ArrayList<Integer>();
				Iterator<String> iterator = listaDiasSemanaString.iterator();
				while(iterator.hasNext()){
					String aux = iterator.next();
					if(aux.equals("dom")){
						listaDiasSemana.add(Calendar.SUNDAY);
					}
					if(aux.equals("seg")){
						listaDiasSemana.add(Calendar.MONDAY);
					}
					if(aux.equals("ter")){
						listaDiasSemana.add(Calendar.TUESDAY);
					}
					if(aux.equals("qua")){
						listaDiasSemana.add(Calendar.WEDNESDAY);
					}
					if(aux.equals("qui")){
						listaDiasSemana.add(Calendar.THURSDAY);
					}
					if(aux.equals("sex")){
						listaDiasSemana.add(Calendar.FRIDAY);
					}
					if(aux.equals("sab")){
						listaDiasSemana.add(Calendar.SATURDAY);
					}
				}
				
				Calendar calendarInicio = new GregorianCalendar();
				calendarInicio.setTime(dataInicio);
				Calendar calendarFim = new GregorianCalendar();
				calendarFim.setTime(dataFim);
				
				while(calendarInicio.compareTo(calendarFim) <= 0){
					if(listaDiasSemana.contains(calendarInicio.get(Calendar.DAY_OF_WEEK))){
						dao.cadastrar(new Date(calendarInicio.getTime().getTime()), codigoTurma);
					}
					calendarInicio.add(Calendar.DATE, 1);
				}
				request.setAttribute("mensagemGerar", "Datas geradas com sucesso!");
			}
			else{
				request.setAttribute("mensagem", "Essa turma não te pertence ou não existe!");
				return "disciplinas";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "conteudo?codigoTurma="+codigoTurma;
	}

}
