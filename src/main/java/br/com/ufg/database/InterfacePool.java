package br.com.ufg.database;

import java.sql.Connection;

public interface InterfacePool {

	public abstract Connection getConnection();
	public void liberarConnection(Connection con);
	public void fecharConnections();
}
