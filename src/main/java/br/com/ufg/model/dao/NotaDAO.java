package br.com.ufg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ufg.database.InterfacePool;
import br.com.ufg.model.bean.Aluno;
import br.com.ufg.model.bean.Nota;

public class NotaDAO {
	private InterfacePool pool;
	
	public NotaDAO(InterfacePool pool){
		super();
		this.pool = pool;
	}

	public void cadastrar(Aluno aluno) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		try{
			stm = con.prepareStatement("INSERT INTO Notas(nota, peso, codigoAluno) VALUES (?,?,?)");
			stm.setFloat(1, (float)0.0);
			stm.setInt(2, 0);
			stm.setInt(3, aluno.getCodigo());
			
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

	public List<Nota> listarNotas(Aluno aluno) throws SQLException {
		List<Nota> notas = new ArrayList<Nota>();
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Notas WHERE codigoAluno = ? ORDER BY idNota");
			stm.setInt(1, aluno.getCodigo());
			
			rs = stm.executeQuery();
			
			while(rs.next()){
				Nota nota = new Nota();
				nota.setIdNota(rs.getInt("idNota"));
				nota.setNota(rs.getFloat("nota"));
				nota.setPeso(rs.getInt("peso"));
				nota.setCodigoAluno(rs.getInt("codigoAluno"));
				notas.add(nota);
			}
			return notas;
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

	public void atualizar(int idNota, float nota) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("UPDATE Notas SET nota=? WHERE idNota = ?");
			stm.setFloat(1, nota);
			stm.setInt(2, idNota);
			
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

	public void atualizarPeso(int peso, int idNota) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("UPDATE Notas SET peso=? WHERE idNota = ?");
			stm.setInt(1, peso);
			stm.setInt(2, idNota);
			
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
