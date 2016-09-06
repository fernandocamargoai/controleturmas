package br.com.ufg.controller.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;


import br.com.ufg.database.InterfacePool;
import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Professor;
import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.AlunoDAO;
import br.com.ufg.model.dao.DataDAO;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.RelacaoAlunoNotasDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class CommandRelatorioNotas implements Command {

	private ServletContext context;
	private InterfacePool pool;
	private DisciplinaDAO daoDisciplina;
	private TurmaDAO daoTurma;
	private DataDAO daoData;
	private AlunoDAO daoAluno;
	private RelacaoAlunoNotasDAO daoRelacao;
	
	public CommandRelatorioNotas(ServletContext context, InterfacePool pool, DisciplinaDAO daoDisciplina, TurmaDAO daoTurma, DataDAO daoData, AlunoDAO daoAluno, RelacaoAlunoNotasDAO daoRelacao){
		this.context = context;
		this.pool = pool;
		this.daoDisciplina = daoDisciplina;
		this.daoTurma = daoTurma;
		this.daoData = daoData;
		this.daoAluno = daoAluno;
		this.daoRelacao = daoRelacao;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Professor professor = (Professor)request.getSession().getAttribute("professor");
		Disciplina disciplina = (Disciplina)request.getSession().getAttribute("disciplina");
		Turma turma = (Turma)request.getSession().getAttribute("turma");
		
		if(disciplina == null){
			request.setAttribute("mensagem", "Nenhuma disciplina selecionada para gerar o relatório!");
			return "disciplinas";
		}
		if(turma == null){
			request.setAttribute("mensagem", "Nenhuma turma selecionada para gerar o relatório!");
			return "turmas?codigoDisciplina="+disciplina.getCodigo();
		}
		try {
			if(daoDisciplina.pertenceProfessor(disciplina.getCodigo(), professor) && daoTurma.pertenceDisciplina(turma.getCodigo(), disciplina.getCodigo())){
				//Ajustando os parâmetros
				Map parametros = new HashMap();
				parametros.put("codigoTurma", turma.getCodigo());
				parametros.put("matriculaProfessor", professor.getMatricula());
				parametros.put("nomeProfessor", professor.getNome());
				parametros.put("nomeDisciplina", disciplina.getNome());
				parametros.put("cargaHoraria", disciplina.getCargaHoraria());
				parametros.put("ano", turma.getAno());
				parametros.put("semestre", turma.getSemestre());
				parametros.put("curso", turma.getCurso());
				parametros.put("turma", turma.getDescricao());
				parametros.put("absolutePathImageUfg", context.getRealPath("/WEB-INF/reports/ufg.png"));
				parametros.put("numeroAlunos", daoAluno.count(turma.getCodigo()));
				
				byte[] bytes = null;
				try{
					// Carrega o arquivo jasper  
		            JasperReport relatorioJasper = (JasperReport)JRLoader.loadObjectFromFile(context.getRealPath("/WEB-INF/reports/Notas.jasper"));
		            // Roda o relatório
		            bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros, new JRBeanCollectionDataSource(daoRelacao.listarRelacaoAlunoNotas(turma)));
				}
				catch(JRException e){
					e.printStackTrace();
				}
				if(bytes != null && bytes.length > 0){
					// Envia o relatório em formato PDF para o browser
					response.setContentType("application/pdf");
					response.setContentLength(bytes.length);
					try {
						ServletOutputStream ouputStream = response.getOutputStream();
						ouputStream.write(bytes, 0, bytes.length);  
				        ouputStream.flush();  
				        ouputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}           
				}
				return null;
			}
			else{
				request.setAttribute("mensagem", "Essa turma não te pertence ou não existe!");
				return "disciplinas";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("mensagem", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
			return "disciplinas";
		}
		
	}

}
