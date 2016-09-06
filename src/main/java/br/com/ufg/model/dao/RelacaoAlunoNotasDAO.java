package br.com.ufg.model.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.com.ufg.database.InterfacePool;
import br.com.ufg.model.bean.RelacaoAlunoNotas;
import br.com.ufg.model.bean.Turma;

public class RelacaoAlunoNotasDAO {
	private InterfacePool pool;
	
	public RelacaoAlunoNotasDAO(InterfacePool pool){
		super();
		this.pool = pool;
	}

	public Vector<RelacaoAlunoNotas> listarRelacaoAlunoNotas(Turma turma) throws SQLException {
		Vector<RelacaoAlunoNotas> relacoes = new Vector<RelacaoAlunoNotas>();
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT\n");
			buffer.append("a.matricula matriculaAluno,\n");
			buffer.append("a.nome nomeAluno,\n");
			buffer.append("n.nota,\n");
			buffer.append("n.peso,\n");
			buffer.append("CASE WHEN (SELECT SUM(nnn.peso) FROM Notas nnn WHERE nnn.codigoAluno = a.codigo) = 0 THEN 0");
			buffer.append("ELSE (SELECT SUM(nn.nota * nn.peso) FROM Notas nn WHERE nn.codigoAluno = a.codigo)/(SELECT SUM(nnn.peso) FROM Notas nnn WHERE nnn.codigoAluno = a.codigo) END media,\n");
			buffer.append("CASE WHEN (SELECT Count(presenca) FROM Presencas p WHERE p.codigoAluno = a.codigo) = 0 THEN 0");
			buffer.append("ELSE ((SELECT Count(presenca) FROM Presencas p WHERE p.codigoAluno = a.codigo AND presenca = true)/(SELECT Count(presenca) FROM Presencas p WHERE p.codigoAluno = a.codigo)::float)*100 END frequencia,\n");
			buffer.append("(SELECT Count(presenca) FROM Presencas p WHERE p.codigoAluno = a.codigo AND presenca = true) presencas\n");
			buffer.append("FROM Alunos a JOIN Notas n ON a.codigo = n.codigoAluno\n");
			buffer.append("WHERE a.codigoTurma = ? AND n.peso != 0\n");
			buffer.append("ORDER BY a.nome, n.idNota;");
			
			stm = con.prepareStatement(buffer.toString());
			stm.setInt(1, turma.getCodigo());
			
			rs = stm.executeQuery();
			
			int contador = 1;
			int matriculaAtual = -1;
			RelacaoAlunoNotas relacaoAtual = null;
			while(rs.next()){
				if(matriculaAtual != -1 && matriculaAtual != rs.getInt("matriculaAluno")){
					relacoes.add(relacaoAtual);
					contador = 1;
				}
				if(contador == 1){
					relacaoAtual = new RelacaoAlunoNotas();
					relacaoAtual.setMatricula(rs.getInt("matriculaAluno"));
					relacaoAtual.setNome(rs.getString("nomeAluno"));
					relacaoAtual.setMedia((new BigDecimal(String.valueOf(rs.getFloat("media"))).setScale(2, BigDecimal.ROUND_HALF_UP)).floatValue());
					relacaoAtual.setFrequencia((new BigDecimal(String.valueOf(rs.getFloat("frequencia"))).setScale(2, BigDecimal.ROUND_HALF_UP)).floatValue());
					relacaoAtual.setPresencas(rs.getInt("presencas"));
					relacaoAtual.setN1(rs.getFloat("nota"));
				}
				if(contador == 2){
					relacaoAtual.setN2(rs.getFloat("nota"));
				}
				if(contador == 3){
					relacaoAtual.setN3(rs.getFloat("nota"));
				}
				if(contador == 4){
					relacaoAtual.setN4(rs.getFloat("nota"));
				}
				if(contador == 5){
					relacaoAtual.setN5(rs.getFloat("nota"));
				}
				if(contador == 6){
					relacaoAtual.setN6(rs.getFloat("nota"));
				}
				if(contador == 7){
					relacaoAtual.setN7(rs.getFloat("nota"));
				}
				if(contador == 8){
					relacaoAtual.setN8(rs.getFloat("nota"));
				}
				if(contador == 9){
					relacaoAtual.setN9(rs.getFloat("nota"));
				}
				if(contador == 10){
					relacaoAtual.setN10(rs.getFloat("nota"));
				}
				matriculaAtual = rs.getInt("matriculaAluno");
				contador++;
			}
			relacoes.add(relacaoAtual);
			return relacoes;
		}
		finally{
			try{
				rs.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			catch(NullPointerException e){
				e.printStackTrace();
			}
			try{
				stm.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			catch(NullPointerException e){
				e.printStackTrace();
			}
			pool.liberarConnection(con);
		}
	}
	
}
