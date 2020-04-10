package com.json;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseInfo {

	private static final String myDriver = "org.postgresql.Driver";
	private static final String myUrl = "jdbc:postgresql:sensordb";
	private static final String usernameDB = "test";
	private static final String passwordDB = "test";

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DataBaseInfo.myDriver);
		Connection conn = DriverManager.getConnection(DataBaseInfo.myUrl, DataBaseInfo.usernameDB, DataBaseInfo.passwordDB);

		return conn;
	}
	
}
