package br.com.ufg.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface InterfaceDataSource {
	public abstract Connection getConnection() throws SQLException;
}
