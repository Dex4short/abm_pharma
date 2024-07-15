package database.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import misc.enums.Role;

public class Postgres {
	private Connection c;
	private Statement s;
	private ResultSet rs;
	
	public Postgres() {
		try {
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/system_pharma", "postgres", "241989");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Role getAccess(char password[]) {
		String pass="";
		for(char p: password) {
			pass += p;
		}
		
		try {
			s = c.createStatement();
			rs = s.executeQuery("select role from security where password='" + pass + "';");
			if(rs.next()) {
				if(rs.getString(1).equals("adm")) {
					return Role.ADMIN;
				}
				else {
					return Role.EMPLOYEE;
				}
			}
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Role.NONE;
	}
}
