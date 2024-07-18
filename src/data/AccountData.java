package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beean.Account;
import connect.Connect;

public class AccountData {

	public boolean checkLogin(Account account) {
		String login = "select * from login where username = ? and password = ?";
		Connect con = new Connect();
		try {
			Connection connect = con.connect();
			PreparedStatement p = connect.prepareStatement(login);
			p.setString(1, account.getUsername());
			p.setString(2, account.getPassword());
			ResultSet resultSet = p.executeQuery();
			if(resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
