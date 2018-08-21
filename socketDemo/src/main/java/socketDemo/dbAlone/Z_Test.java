package dai.developTest.dbAlone;

import java.sql.ResultSet;

import dai.developTest.dbAlone.transaction.impl.TransactionHelper;
import dai.developTest.dbAlone.utils.DBUtils;

public class Z_Test {

	
	
	public static void main(String[] args) {
		
		
		TransactionHelper tx = MyJdbcFactory.getTransactionHelper();
		
//		int ret = tx.executeTransaction("insert into customer(number) values('123')");
//		System.out.println(ret);

		ResultSet rs = tx.select("select * from customer");
		DBUtils.printResultSet(rs);
		
//		UUID uuid = UUID.randomUUID();
//		String id = uuid.toString().replace("-", "");
//		System.out.println(id);
//		System.out.println(id.length());
		
		
		
		
	}
	
	
	
	
	
}
