package database.mysql;

import misc.enums.Role;

public final class MySQL_Security{

	private MySQL_Security() {
		
	}
	public static Role getAccess(char password[]) {
		String pass="";
		for(char p: password) {
			pass += p;
		}
		
		Object data[][] = MySQL.select(new String[]{"role"}, "security", " where password='" + pass + "' ;");
		
		if(data.length > 0) {
			String role = (String)data[0][0];
			if(role.equals("adm")) {
				return Role.ADMIN;
			}
			else if(role.equals("emp")){
				return Role.EMPLOYEE;
			}
			else {
				return Role.NONE;
			}
		}
		else {
			return Role.NONE;
		}
	}
}
