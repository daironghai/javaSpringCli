package socketDemo.dbAlone.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

	
	public static void printResultSet(ResultSet rs){
		if(null==rs){
			return;
		}
		try {
			if(!rs.wasNull()){
				String showLimit = "\t\t\t\t";
				//打印列头
				int colCount = rs.getMetaData().getColumnCount();
				for (int i = 1; i <= colCount; i++) {
					System.out.print(rs.getMetaData().getColumnLabel(i) + showLimit);
				}
				System.out.println();
				while(rs.next()){
					for (int i = 1; i <= colCount; i++) {
						System.out.print(rs.getObject(i) + showLimit);
					}
					System.out.println();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
