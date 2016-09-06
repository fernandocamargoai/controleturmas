package br.com.ufg.model.helper;


import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import br.com.ufg.database.InterfacePool;
import br.com.ufg.database.Pool;
import br.com.ufg.controller.command.CommandAlterarPesos;
import br.com.ufg.controller.command.CommandCadastrarAlunos;
import br.com.ufg.controller.command.CommandCadastrarConteudo;
import br.com.ufg.controller.command.CommandCadastrarDatas;
import br.com.ufg.controller.command.CommandCadastrarDiasSemana;
import br.com.ufg.controller.command.CommandCadastrarDisciplina;
import br.com.ufg.controller.command.CommandCadastrarNota;
import br.com.ufg.controller.command.CommandCadastrarPresenca;
import br.com.ufg.controller.command.CommandCadastrarProfessor;
import br.com.ufg.controller.command.Command;
import br.com.ufg.controller.command.CommandCadastrarTurma;
import br.com.ufg.controller.command.CommandConteudo;
import br.com.ufg.controller.command.CommandEditarAluno;
import br.com.ufg.controller.command.CommandEditarDisciplina;
import br.com.ufg.controller.command.CommandEditarTurma;
import br.com.ufg.controller.command.CommandExcluirAluno;
import br.com.ufg.controller.command.CommandExcluirDisciplina;
import br.com.ufg.controller.command.CommandExcluirTurma;
import br.com.ufg.controller.command.CommandGerarDatas;
import br.com.ufg.controller.command.CommandLogin;
import br.com.ufg.controller.command.CommandLogout;
import br.com.ufg.controller.command.CommandDisciplinas;
import br.com.ufg.controller.command.CommandNota;
import br.com.ufg.controller.command.CommandPresenca;
import br.com.ufg.controller.command.CommandRelatorioAnexo;
import br.com.ufg.controller.command.CommandRelatorioFrequencia;
import br.com.ufg.controller.command.CommandRelatorioNotas;
import br.com.ufg.controller.command.CommandTurma;
import br.com.ufg.controller.command.CommandTurmas;
import br.com.ufg.model.dao.AlunoDAO;
import br.com.ufg.model.dao.DataDAO;
import br.com.ufg.model.dao.DisciplinaDAO;
import br.com.ufg.model.dao.NotaDAO;
import br.com.ufg.model.dao.PresencaDAO;
import br.com.ufg.model.dao.ProfessorDAO;
import br.com.ufg.model.dao.RelacaoAlunoNotasDAO;
import br.com.ufg.model.dao.TurmaDAO;

public class Helper {
	private HashMap<String, Command> mapaComandos;
	private HttpServletRequest request;
	private InterfacePool pool;
	private ServletContext context;
	
	public Helper(ServletContext context){
		this.pool = new Pool();
		this.context = context;
		mapaComandos = new HashMap<String, Command>();
		mapaComandos.put("cadastrarProfessor", new CommandCadastrarProfessor(new ProfessorDAO(pool)));
		mapaComandos.put("login", new CommandLogin(new ProfessorDAO(pool)));
		mapaComandos.put("/logout", new CommandLogout());
		mapaComandos.put("cadastrarDisciplina", new CommandCadastrarDisciplina(new DisciplinaDAO(pool)));
		mapaComandos.put("/user/disciplinas", new CommandDisciplinas(new DisciplinaDAO(pool)));
		mapaComandos.put("editarDisciplina", new CommandEditarDisciplina(new DisciplinaDAO(pool)));
		mapaComandos.put("excluirDisciplina", new CommandExcluirDisciplina(new DisciplinaDAO(pool)));
		mapaComandos.put("cadastrarTurma", new CommandCadastrarTurma(new TurmaDAO(pool)));
		mapaComandos.put("excluirTurma", new CommandExcluirTurma(new DisciplinaDAO(pool), new TurmaDAO(pool)));
		mapaComandos.put("editarTurma", new CommandEditarTurma(new DisciplinaDAO(pool), new TurmaDAO(pool)));
		mapaComandos.put("/user/turmas", new CommandTurmas(new DisciplinaDAO(pool), new TurmaDAO(pool)));
		mapaComandos.put("/user/turma", new CommandTurma(new DisciplinaDAO(pool), new TurmaDAO(pool), new AlunoDAO(pool), new DataDAO(pool)));
		mapaComandos.put("/user/cadastrar_alunos", new CommandCadastrarAlunos(new AlunoDAO(pool)));
		mapaComandos.put("/user/cadastrar_diasSemana", new CommandCadastrarDiasSemana(new DisciplinaDAO(pool), new TurmaDAO(pool)));
		mapaComandos.put("/user/cadastrar_datas", new CommandCadastrarDatas(new TurmaDAO(pool)));
		mapaComandos.put("editarAluno", new CommandEditarAluno(new DisciplinaDAO(pool), new TurmaDAO(pool), new AlunoDAO(pool)));
		mapaComandos.put("excluirAluno", new CommandExcluirAluno(new DisciplinaDAO(pool), new TurmaDAO(pool), new AlunoDAO(pool)));
		mapaComandos.put("/user/gerar_datas", new CommandGerarDatas(new DisciplinaDAO(pool), new TurmaDAO(pool), new DataDAO(pool)));
		mapaComandos.put("/user/conteudo", new CommandConteudo(new DisciplinaDAO(pool), new TurmaDAO(pool), new AlunoDAO(pool), new DataDAO(pool)));
		mapaComandos.put("/user/cadastrar_conteudo", new CommandCadastrarConteudo(new DisciplinaDAO(pool), new TurmaDAO(pool), new DataDAO(pool)));
		mapaComandos.put("/user/presenca", new CommandPresenca(new DisciplinaDAO(pool), new TurmaDAO(pool), new AlunoDAO(pool), new DataDAO(pool), new PresencaDAO(pool)));
		mapaComandos.put("/user/cadastrar_presenca", new CommandCadastrarPresenca(new DisciplinaDAO(pool), new TurmaDAO(pool), new AlunoDAO(pool), new DataDAO(pool), new PresencaDAO(pool)));
		mapaComandos.put("/user/nota", new CommandNota(new DisciplinaDAO(pool), new TurmaDAO(pool), new AlunoDAO(pool), new NotaDAO(pool)));
		mapaComandos.put("/user/cadastrar_nota", new CommandCadastrarNota(new DisciplinaDAO(pool), new TurmaDAO(pool), new AlunoDAO(pool), new NotaDAO(pool)));
		mapaComandos.put("/user/alterar_pesos", new CommandAlterarPesos(new DisciplinaDAO(pool), new TurmaDAO(pool), new AlunoDAO(pool), new NotaDAO(pool)));
		mapaComandos.put("/user/anexo", new CommandRelatorioAnexo(context, pool, new DisciplinaDAO(pool), new TurmaDAO(pool), new DataDAO(pool)));
		mapaComandos.put("/user/frequencia", new CommandRelatorioFrequencia(context, pool, new DisciplinaDAO(pool), new TurmaDAO(pool), new DataDAO(pool), new AlunoDAO(pool)));
		mapaComandos.put("/user/relacao_notas", new CommandRelatorioNotas(context, pool, new DisciplinaDAO(pool), new TurmaDAO(pool), new DataDAO(pool), new AlunoDAO(pool), new RelacaoAlunoNotasDAO(pool)));
	}
	
	public Command getCommand(){
		String comando = request.getParameter("cmd");
		Object nocmd = request.getAttribute("nocmd");
		if(request.getParameter("cmd") == null || nocmd != null){
			comando = request.getServletPath();
		}
		return mapaComandos.get(comando);
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public InterfacePool getPool() {
		return pool;
	}
	
}
