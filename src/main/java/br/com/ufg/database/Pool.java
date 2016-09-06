package br.com.ufg.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

public class Pool implements InterfacePool {

	private InterfaceDataSource ds;
	private ArrayBlockingQueue<Connection> conexoesLivres;
	private HashMap<String, Connection> conexoesUtilizadas;
	private Integer numeroMaximoConexoes;
	private ResourceBundle config;

	public Pool(){
		config = ResourceBundle.getBundle("br.com.ufg.database.bancodedados");
		ds = new DataSource(config.getString("url"), config.getString("driver"), config.getString("usuario"), config.getString("senha"));
		numeroMaximoConexoes = Integer.parseInt(config.getString("numeroMaximoConexoes"));
		conexoesLivres = new ArrayBlockingQueue<Connection>(numeroMaximoConexoes, true);
		conexoesUtilizadas = new HashMap<String, Connection>();
	}
	
	@Override
	public Connection getConnection() {
		Connection con = null;
		try {
			if(conexoesUtilizadas.size() < numeroMaximoConexoes){
				con = conexoesLivres.poll();
				if(con == null){
					con = ds.getConnection();
				}
				else{
					if(con.isClosed()){
						this.getConnection();
					}
				}
				conexoesUtilizadas.put(con.toString(), con);
			}
		} catch (SQLException e) {
			System.out.println("Problemas com o pool!");
			e.printStackTrace();
		}
		return con;
	}

	@Override
	public void liberarConnection(Connection con) {
		conexoesLivres.add(con);
		conexoesUtilizadas.remove(con.toString());
	}
	
	public void fecharConnections(){
		Iterator<Connection> i = conexoesLivres.iterator();
		while(i.hasNext()){
			try {
				i.next().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
