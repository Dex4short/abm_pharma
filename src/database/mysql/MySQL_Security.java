package database.mysql;

import misc.enums.SecurityRole;

public final class MySQL_Security{

	private MySQL_Security() {
		
	}
	public static SecurityRole getAccess(char password[]) {
		String pass="";
		for(char p: password) {
			pass += p;
		}
		
		Object data[][] = MySQL.select(new String[]{"role"}, "security", " where password='" + pass + "' ;");
		
		if(data.length > 0) {
			String role = (String)data[0][0];
			if(role.equals("adm")) {
				return SecurityRole.ADMIN;
			}
			else if(role.equals("emp")){
				return SecurityRole.EMPLOYEE;
			}
			else {
				return SecurityRole.NONE;
			}
		}
		else {
			return SecurityRole.NONE;
		}
	}
}
