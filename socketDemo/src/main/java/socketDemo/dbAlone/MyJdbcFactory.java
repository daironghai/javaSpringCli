package socketDemo.dbAlone;

import java.sql.Connection;
import java.sql.SQLException;

import socketDemo.dbAlone.connect.Mssql_ConnectHelper;
import socketDemo.dbAlone.table.Mssql_TableHelperImpl;
import socketDemo.dbAlone.table.impl.TableHelper;
import socketDemo.dbAlone.transaction.Mssql_TransactionHelper;
import socketDemo.dbAlone.transaction.impl.TransactionHelper;

public class MyJdbcFactory {

	
	public static Connection getConnection() throws SQLException{
		return Mssql_ConnectHelper.getConnection();
	}
	
	
	private static TransactionHelper transactionHelper;
	public static TransactionHelper getTransactionHelper(){
		if(null==transactionHelper){
			transactionHelper = new Mssql_TransactionHelper();
		}
		return transactionHelper;
	}
	
	
	private static TableHelper tableHelper;
	public static TableHelper getTableHelper(){
		if(null==tableHelper){
			tableHelper = new Mssql_TableHelperImpl();
		}
		return tableHelper;
	}
	
	
	
}
