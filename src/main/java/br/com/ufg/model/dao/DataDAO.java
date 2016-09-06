package br.com.ufg.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ufg.database.InterfacePool;
import br.com.ufg.model.bean.Data;
import br.com.ufg.model.bean.Disciplina;
import br.com.ufg.model.bean.Turma;

public class DataDAO {
	
	private InterfacePool pool;

	public DataDAO(InterfacePool pool) {
		super();
		this.pool = pool;
	}

	public void cadastrar(Date data, int codigoTurma) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		try{
			stm = con.prepareStatement("INSERT INTO Datas(dia, aulaNormal, codigoTurma, conteudo) VALUES (?,?,?,'')");
			stm.setDate(1, data);
			stm.setBoolean(2, true);
			stm.setInt(3, codigoTurma);
			
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

	public boolean temDatas(int codigoTurma) throws SQLException{
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Datas WHERE codigoTurma = ?");
			stm.setInt(1, codigoTurma);
			
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
	
	public List<Data> mostrarDatas(Turma turma) throws SQLException {
		List<Data> datas = new ArrayList<Data>();
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Datas WHERE codigoTurma = ? ORDER BY dia");
			stm.setInt(1, turma.getCodigo());
			
			rs = stm.executeQuery();
			
			while(rs.next()){
				Data data = new Data();
				data.setIdData(rs.getInt("idData"));
				data.setDia(rs.getDate("dia"));
				data.setAulaNormal(rs.getBoolean("aulaNormal"));
				data.setConteudo(rs.getString("conteudo"));
				data.setCodigoTurma(rs.getInt("codigoTurma"));
				datas.add(data);
			}
			return datas;
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

	public boolean pertenceTurma(int idData, Turma turma) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Datas WHERE idData = ? AND codigoTurma = ?");
			stm.setInt(1, idData);
			stm.setInt(2, turma.getCodigo());
			
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

	public void atualizar(int idData, boolean aulaNormal, String conteudo) throws SQLException {
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		
		try{
			stm = con.prepareStatement("UPDATE Datas SET aulaNormal=?,conteudo=? WHERE idData = ?");
			stm.setBoolean(1, aulaNormal);
			stm.setString(2, conteudo);
			stm.setInt(3, idData);
			
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

	public List<Data> mostrarDatasAulaNormal(Turma turma) throws SQLException {
		List<Data> datas = new ArrayList<Data>();
		Connection con = pool.getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			stm = con.prepareStatement("SELECT * FROM Datas WHERE codigoTurma = ? AND aulaNormal = ? ORDER BY dia");
			stm.setInt(1, turma.getCodigo());
			stm.setBoolean(2, true);
			
			rs = stm.executeQuery();
			
			while(rs.next()){
				Data data = new Data();
				data.setIdData(rs.getInt("idData"));
				data.setDia(rs.getDate("dia"));
				data.setAulaNormal(rs.getBoolean("aulaNormal"));
				data.setConteudo(rs.getString("conteudo"));
				data.setCodigoTurma(rs.getInt("codigoTurma"));
				datas.add(data);
			}
			return datas;
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
