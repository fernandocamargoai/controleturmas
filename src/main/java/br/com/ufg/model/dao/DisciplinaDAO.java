package br.com.ufg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ufg.database.InterfacePool;
import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Professor;

public class DisciplinaDAO {
	private InterfacePool pool;

	public DisciplinaDAO(InterfacePool pool) {
		super();
		this.pool = pool;
	}

	public boolean notExists(Disciplina disciplina, Professor professor) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Disciplinas WHERE nome = ? AND matriculaProfessor = ?");
			stm.setString(1, disciplina.getNome());
			stm.setInt(2, professor.getMatricula());
			
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
	public boolean pertenceProfessor(int codigo, Professor professor) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Disciplinas WHERE codigo = ? AND matriculaProfessor = ?");
			stm.setInt(1, codigo);
			stm.setInt(2, professor.getMatricula());
			
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

	public void cadastrar(Disciplina disciplina) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("INSERT INTO Disciplinas(nome, cargaHoraria, matriculaProfessor) VALUES (?,?,?)");
			stm.setString(1, disciplina.getNome());
			stm.setInt(2, disciplina.getCargaHoraria());
			stm.setInt(3, disciplina.getMatriculaProfessor());			
			
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
	
	public List<Disciplina> mostrarDisciplinas(Professor professor) throws SQLException{
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Disciplinas WHERE matriculaProfessor = ? ORDER BY nome");
			stm.setInt(1, professor.getMatricula());
			
			rs = stm.executeQuery();
			
			while(rs.next()){
				Disciplina disciplina = new Disciplina();
				disciplina.setCodigo(rs.getInt("codigo"));
				disciplina.setNome(rs.getString("nome"));
				disciplina.setCargaHoraria(rs.getInt("cargahoraria"));
				disciplina.setMatriculaProfessor(rs.getInt("matriculaprofessor"));
				disciplinas.add(disciplina);
			}
			return disciplinas;
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
	
	public void editar(int codigo, String nome, int cargaHoraria) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("UPDATE Disciplinas SET nome=?,cargaHoraria=? WHERE codigo=?");
			stm.setString(1, nome);
			stm.setInt(2, cargaHoraria);
			stm.setInt(3, codigo);
			
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
	
	public void excluir(int codigo) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("DELETE FROM Disciplinas WHERE codigo=?");
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

	public Disciplina get(int codigoDisciplina) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Disciplinas WHERE codigo = ?");
			stm.setInt(1, codigoDisciplina);
			
			rs = stm.executeQuery();
			
			if(rs.next()){
				Disciplina disciplina = new Disciplina();
				disciplina.setCodigo(rs.getInt("codigo"));
				disciplina.setNome(rs.getString("nome"));
				disciplina.setCargaHoraria(rs.getInt("cargahoraria"));
				disciplina.setMatriculaProfessor(rs.getInt("matriculaprofessor"));
				return disciplina;
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
}
