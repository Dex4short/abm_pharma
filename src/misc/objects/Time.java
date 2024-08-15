package misc.objects;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {
	private String time;
	private LocalTime local_time;

	public Time() { 
		local_time = LocalTime.now();
		
		DateTimeFormatter 
		formatter = DateTimeFormatter.ofPattern("hh:mm a");
		
		time = local_time.format(formatter);
	}
	public Time(int HH, int MM, int SS) {
		local_time = LocalTime.of(HH, MM, SS);

		DateTimeFormatter 
		formatter = DateTimeFormatter.ofPattern("hh:mm a");
		
		time = local_time.format(formatter);
	}
	@Override
	public String toString() {
		return time;
	}
	public java.sql.Time toSQLTime(){
		return java.sql.Time.valueOf(local_time);
	}
	public static Time parseTime(java.sql.Time time) {
		LocalTime local_time = time.toLocalTime();
		
		int
		hh = local_time.getHour(),
		mm = local_time.getMinute(),
		ss = local_time.getSecond();
		
		return new Time(hh, mm, ss);
	}
}
