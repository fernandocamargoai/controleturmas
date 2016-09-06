package br.com.ufg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.ufg.database.InterfacePool;
import br.com.ufg.model.bean.Aluno;
import br.com.ufg.model.bean.Data;
import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Presenca;
import br.com.ufg.model.bean.Turma;

public class PresencaDAO {

	private InterfacePool pool;
	
	public PresencaDAO(InterfacePool pool){
		super();
		this.pool = pool;
	}

	public Presenca get(Aluno aluno, Data data) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Presencas WHERE codigoAluno = ? AND idData = ?");
			stm.setInt(1, aluno.getCodigo());
			stm.setInt(2, data.getIdData());
			
			rs = stm.executeQuery();
			
			if(rs.next()){
				Presenca presenca = new Presenca();
				presenca.setCodigoAluno(rs.getInt("codigoAluno"));
				presenca.setIdData(rs.getInt("idData"));
				presenca.setIdPresenca(rs.getInt("idPresenca"));
				presenca.setPresenca(rs.getBoolean("presenca"));
				return presenca;
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

	public void cadastrar(Aluno aluno, Data data) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		try{
			stm = con.prepareStatement("INSERT INTO Presencas(codigoAluno, idData, presenca) VALUES (?,?,?)");
			stm.setInt(1, aluno.getCodigo());
			stm.setInt(2, data.getIdData());
			stm.setBoolean(3, true);
			
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

	public void atualizar(int idPresenca, boolean presenca) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("UPDATE Presencas SET presenca=? WHERE idPresenca = ?");
			stm.setBoolean(1, presenca);
			stm.setInt(2, idPresenca);
			
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
