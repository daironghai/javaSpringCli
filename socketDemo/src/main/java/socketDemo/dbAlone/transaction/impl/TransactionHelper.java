package socketDemo.dbAlone.transaction.impl;

import java.sql.ResultSet;
import java.util.List;

public interface TransactionHelper {

	
	public int executeTransaction(String sql);
	

	public int executeTransaction(List<String> sqlList);
	

	public int executeTransaction(String[] sqlList);
	
	
	public ResultSet select(String sql);
	
	
	
	
	
}
