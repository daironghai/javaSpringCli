package dai.developTest.dbAlone;

import java.sql.Connection;
import java.sql.SQLException;

import dai.developTest.dbAlone.connect.Mssql_ConnectHelper;
import dai.developTest.dbAlone.table.Mssql_TableHelperImpl;
import dai.developTest.dbAlone.table.impl.TableHelper;
import dai.developTest.dbAlone.transaction.Mssql_TransactionHelper;
import dai.developTest.dbAlone.transaction.impl.TransactionHelper;

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
