package com.json;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseInfo {

	private static final String myDriver = "com.mysql.cj.jdbc.Driver";
	private static final String myUrl = "jdbc:mysql://localhost/test?autoReconnect=true&useSSL=false";
	private static final String usernameDB = "test";
	private static final String passwordDB = "test";

	protected static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DataBaseInfo.myDriver);
		Connection conn = DriverManager.getConnection(DataBaseInfo.myUrl, DataBaseInfo.usernameDB, DataBaseInfo.passwordDB);

		return conn;
	}
	
}
