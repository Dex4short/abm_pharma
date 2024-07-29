package database.mysql;

import misc.objects.Date;
import misc.objects.Remarks;
import misc.objects.Time;

public class MySQL_Remarks {
	public static final String RemarksColumns[] = {"rem_id", "rem_date", "rem_time", "details"};
	
	public static void insertRemarks(Remarks remarks) {		
		Object values[] = {
			remarks.getRemId(), 
			remarks.getDate().toSQLDate(), 
			remarks.getTime().toSQLTime(), 
			remarks.getDetails()
		};
		MySQL.insert("remarks", RemarksColumns, values);
	}
	public static Remarks selectRemarks(int rem_id) {
		Object rem_result[][] = MySQL.select(RemarksColumns, "remarks", "where rem_id=" + rem_id);
		
		if(rem_result.length > 0) {
			Date date = Date.parseDate((java.sql.Date)rem_result[0][1]);
			Time time = Time.parseTime((java.sql.Time)rem_result[0][2]);
			String details = (String)rem_result[0][3];
			
			return new Remarks(rem_id, date, time, details);
		}
		else {
			return null;
		}
	}
	public static void deleteRemarks(int rem_id) {
		MySQL.delete("remarks", "where rem_id=" + rem_id);
	}
}
