package dai.developTest.dbAlone.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//maven包
//<dependency>
//<groupId>com.microsoft.sqlserver</groupId>
//<artifactId>mssql-jdbc</artifactId>
//<version>6.1.7.jre8-preview</version>
//</dependency>

public final class Mssql_ConnectHelper {
	
	private Mssql_ConnectHelper(){
		
	}
	
	public static final String dbPort = "1433";
	public static final String dbName = "daiTest";
	public static final String dbUsername = "sa";
	public static final String dbPassword = "123456";
	
	public static final String url = "jdbc:sqlserver://"
			+ "localhost:" + Mssql_ConnectHelper.dbPort + ";"
			+ "DatabaseName=" + Mssql_ConnectHelper.dbName + ";";
	
	
	private static Connection conn;  
	
	
	static{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(Mssql_ConnectHelper.url, Mssql_ConnectHelper.dbUsername, Mssql_ConnectHelper.dbPassword);
		} catch (Exception e) {
			System.err.println("数据库驱动加载失败");
			e.printStackTrace();
		}   
	}
	

	public static Connection getConnection() throws SQLException{
		if(null==conn){
			synchronized (Mssql_ConnectHelper.class){
				if(null==conn){
					conn = DriverManager.getConnection(Mssql_ConnectHelper.url, Mssql_ConnectHelper.dbUsername, Mssql_ConnectHelper.dbPassword);
				}
			};
		}
		return conn;
	}
	
	
	
}
