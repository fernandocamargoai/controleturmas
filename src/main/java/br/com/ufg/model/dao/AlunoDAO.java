package br.com.ufg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ufg.database.InterfacePool;
import br.com.ufg.model.bean.Aluno;
import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Turma;

public class AlunoDAO {
	private InterfacePool pool;
	
	public AlunoDAO(InterfacePool pool){
		super();
		this.pool = pool;
	}

	public void cadastrar(Aluno aluno) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		try{
			stm = con.prepareStatement("INSERT INTO Alunos(matricula, nome, email, codigoTurma) VALUES (?,?,?,?)");
			stm.setInt(1, aluno.getMatricula());
			stm.setString(2, aluno.getNome());
			stm.setString(3, aluno.getEmail());
			stm.setInt(4, aluno.getCodigoTurma());
			
			stm.executeUpdate();
		}
		finally {
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
	
	public List<Aluno> mostrarAlunos(Turma turma) throws SQLException {
		List<Aluno> alunos = new ArrayList<Aluno>();
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Alunos WHERE codigoTurma = ? ORDER BY nome");
			stm.setInt(1, turma.getCodigo());
			
			rs = stm.executeQuery();
			
			while(rs.next()){
				Aluno aluno = new Aluno();
				aluno.setNome(rs.getString("nome"));
				aluno.setEmail(rs.getString("email"));
				aluno.setMatricula(rs.getInt("matricula"));
				aluno.setCodigo(rs.getInt("codigo"));
				aluno.setCodigoTurma(rs.getInt("codigoTurma"));
				alunos.add(aluno);
			}
			return alunos;
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

	public boolean pertenceTurma(int codigo, int codigoTurma) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Alunos WHERE codigo = ? AND codigoTurma = ?");
			stm.setInt(1, codigo);
			stm.setInt(2, codigoTurma);
			
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

	public void editar(int codigo, int matricula, String nome, String email) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("UPDATE Alunos SET matricula=?,nome=?,email=? WHERE codigo=?");
			stm.setInt(1, matricula);
			stm.setString(2, nome);
			stm.setString(3, email);
			stm.setInt(4, codigo);
			
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
			stm = con.prepareStatement("DELETE FROM Alunos WHERE codigo=?");
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

	public int count(int codigoTurma) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT Count(matricula) count FROM Alunos WHERE codigoTurma = ?;");
			stm.setInt(1, codigoTurma);
			
			rs = stm.executeQuery();
			
			if(rs.next()){
				return rs.getInt("count");
			}
			
			return 0;
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
	
	public boolean notExists(Turma turma, String matricula) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Alunos WHERE codigoTurma = ? AND matricula = ?");
			stm.setInt(1, turma.getCodigo());
			stm.setInt(2, Integer.parseInt(matricula));
			
			rs = stm.executeQuery();
			
			return !rs.next();
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
