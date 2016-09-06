package br.com.ufg.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ufg.database.InterfacePool;
import br.com.ufg.model.bean.Aluno;
import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Turma;

public class TurmaDAO {
	private InterfacePool pool;

	public TurmaDAO(InterfacePool pool) {
		super();
		this.pool = pool;
	}

	public List<Turma> mostrarTurmas(Disciplina disciplina) throws SQLException {
		List<Turma> turmas = new ArrayList<Turma>();
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Turmas WHERE codigoDisciplina = ? ORDER BY descricao");
			stm.setInt(1, disciplina.getCodigo());
			
			rs = stm.executeQuery();
			
			while(rs.next()){
				Turma turma = new Turma();
				turma.setCodigo(rs.getInt("codigo"));
				turma.setDescricao(rs.getString("descricao"));
				turma.setCurso(rs.getString("curso"));
				turma.setAno(rs.getInt("ano"));
				turma.setSemestre(rs.getInt("semestre"));
				turma.setCodigoDisciplina(rs.getInt("codigoDisciplina"));
				turmas.add(turma);
			}
			return turmas;
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

	public void cadastrar(Turma turma) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("INSERT INTO Turmas(descricao, curso, ano, semestre, codigoDisciplina) VALUES (?,?,?,?,?)");
			stm.setString(1, turma.getDescricao());
			stm.setString(2, turma.getCurso());
			stm.setInt(3, turma.getAno());
			stm.setInt(4, turma.getSemestre());
			stm.setInt(5, turma.getCodigoDisciplina());
			
			stm.executeUpdate();
		}
		finally {
			try{
				stm.close();
			}
			catch(SQLException e){
				
			}
			pool.liberarConnection(con);
		}
		
	}

	public void excluir(int codigo) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("DELETE FROM Turmas WHERE codigo=?");
			stm.setInt(1, codigo);
			
			stm.executeUpdate();
		}
		finally {
			try{
				stm.close();
			}
			catch(SQLException e){
				
			}
			pool.liberarConnection(con);
		}		
	}
	
	public boolean pertenceDisciplina(int codigo, int codigoDisciplina) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Turmas WHERE codigo = ? AND codigoDisciplina = ?");
			stm.setInt(1, codigo);
			stm.setInt(2, codigoDisciplina);
			
			rs = stm.executeQuery();
			
			return rs.next();
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

	public void editar(int codigo, String descricao, String curso, int ano,
			int semestre) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("UPDATE Turmas SET descricao=?,curso=?,ano=?,semestre=? WHERE codigo=?");
			stm.setString(1, descricao);
			stm.setString(2, curso);
			stm.setInt(3, ano);
			stm.setInt(4, semestre);
			stm.setInt(5, codigo);
			
			stm.executeUpdate();
		}
		finally {
			try{
				stm.close();
			}
			catch(SQLException e){
				
			}
			pool.liberarConnection(con);
		}
		
	}

	public Turma get(int codigo) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Turmas WHERE codigo = ?");
			stm.setInt(1, codigo);
			
			rs = stm.executeQuery();
			
			if(rs.next()){
				Turma turma = new Turma();
				turma.setCodigo(codigo);
				turma.setDescricao(rs.getString("descricao"));
				turma.setCurso(rs.getString("curso"));
				turma.setAno(rs.getInt("ano"));
				turma.setSemestre(rs.getInt("semestre"));
				turma.setCodigoDisciplina(rs.getInt("codigoDisciplina"));
				turma.setDataInicio(rs.getDate("dataInicio"));
				turma.setDataFim(rs.getDate("dataFim"));
				return turma;
			}
			return null;
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

	public void limparDiasSemana(int codigoTurma) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("DELETE FROM DiasSemana WHERE codigoTurma=?");
			stm.setInt(1, codigoTurma);
			
			stm.executeUpdate();
		}
		finally {
			try{
				stm.close();
			}
			catch(SQLException e){
				
			}
			pool.liberarConnection(con);
		}		
	}

	public void cadastrarDiaSemana(int codigoTurma, String diaSemana) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("INSERT INTO DiasSemana(codigoTurma, diaSemana) VALUES (?,?)");
			stm.setInt(1, codigoTurma);
			stm.setString(2, diaSemana);			
			
			stm.executeUpdate();
		}
		finally {
			try{
				stm.close();
			}
			catch(SQLException e){
				
			}
			pool.liberarConnection(con);
		}		
	}
	
	public List<String> listarDiasSemana(int codigoTurma) throws SQLException{
		List<String> dias = new ArrayList<String>();
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM DiasSemana WHERE codigoTurma = ?");
			stm.setInt(1, codigoTurma);
			
			rs = stm.executeQuery();
			
			while(rs.next()){
				dias.add(rs.getString("diaSemana"));
			}
			return dias;
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

	public void cadastrarDatas(int codigoTurma, Date dataInicio, Date dataFim) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("UPDATE Turmas SET dataInicio=?,dataFim=? WHERE codigo=?");
			stm.setDate(1, dataInicio);
			stm.setDate(2, dataFim);
			stm.setInt(3, codigoTurma);
			
			stm.executeUpdate();
		}
		finally {
			try{
				stm.close();
			}
			catch(SQLException e){
				
			}
			pool.liberarConnection(con);
		}
		
	}

	
}
