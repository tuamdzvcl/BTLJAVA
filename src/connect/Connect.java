package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

	private static String conn = "jdbc:ucanaccess://./6667458_PhamNgocTuanAnh.accdb";
	
	public Connection connect() {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(conn);
			return connect;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
