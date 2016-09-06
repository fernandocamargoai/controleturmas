package br.com.ufg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.ufg.database.InterfacePool;
import br.com.ufg.model.bean.Professor;

public class ProfessorDAO {
	private InterfacePool pool;

	public ProfessorDAO(InterfacePool pool) {
		super();
		this.pool = pool;
	}
	
	public void cadastrar(Professor professor) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("INSERT INTO Professores(matricula, senha, nome) VALUES (?,?,?)");
			stm.setInt(1, professor.getMatricula());
			stm.setString(2, professor.getSenha());
			stm.setString(3, professor.getNome());
			
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
	
	public Professor getProfessor(int matricula) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		Professor professor = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Professores WHERE matricula = ?");
			stm.setInt(1, matricula);
			rs = stm.executeQuery();
			
			if(rs.next() != false){
				professor = new Professor();
				professor.setMatricula(rs.getInt("matricula"));
				professor.setNome(rs.getString("nome"));
				professor.setSenha(rs.getString("senha"));
			}			
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
		return professor;
	}
	
}
