package misc.objects;

import java.time.LocalDate;

public class Date{
	private static final String months[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	private int YYYY, MM, DD;
	private String Month, Day;

	public Date() {
		setCurrentDate();
	}
	public Date(int yyyy, int mm, int dd) {
		setYear(yyyy);
		setMonth(mm);
		setDay(dd);
	}
	public Date(String date) {
		setDate(date);
	}
	@Override
	public String toString() {
		int
		YYYY = getYear(),
		MM = getMonth(),
		DD = getDay();
		
		return YYYY + "-" + (MM<10 ? "0"+MM : ""+MM) + "-" + (DD<10 ? "0"+DD : ""+DD);
	}
	public int getYear() {
		return YYYY;
	}
	public int getMonth() {
		return MM;
	}
	public String getMonthName() {
		return Month;
	}
	public int getDay() {
		return DD;
	}
	public String getDayName() {
		return Day;
	}
	public String getDayName(int dd) {
		return getDayName(getYear(), getMonth(), dd);
	}
	public void setYear(int yyyy) {
		YYYY = yyyy;
	}
	public void setMonth(int mm) {
		MM = mm;
		Month = getMonthName(mm);
	}
	public void setDay(int dd) {
		DD = dd;
		Day = getDayName(dd);
	}
	public void setDate(int yyyy, int mm, int dd) {
		setYear(yyyy);
		setMonth(mm);
		setDay(dd);
	}
	public void setDate(String date) {//yyyy-mm-dd
		setYear(extractYear(date));
		setMonth(extractMonth(date));
		setDay(extractDay(date));
	}
	public void setCurrentDate() {
		setYear(getCurrentYear());
		setMonth(getCurrentMonth());
		setDay(getCurrentDay());
	}
	public java.sql.Date toSQLDate(){
		return java.sql.Date.valueOf(toString());
	}
	
	public static int getCurrentYear() {
		return LocalDate.now().getYear();
	}
	public static String getCurrentMonthName() {
		return LocalDate.now().getMonth().toString().substring(0, 3);
	}
	public static int getCurrentMonth() {
		return LocalDate.now().getMonthValue();
	}
	public static String getMonthName(int m) {
		return months[m-1];
	}
	public static int getMonthDays(int yyyy, int mm) {
		return LocalDate.of(yyyy, mm, 1).lengthOfMonth();
	}
	public static String getCurrentDayName() {
		return LocalDate.now().getDayOfWeek().toString().substring(0, 3);
	}
	public static int getCurrentDay() {
		return LocalDate.now().getDayOfMonth();
	}
	public static String getDayName(int yyyy, int mm, int dd) {
		return LocalDate.of(yyyy, mm, dd).getDayOfWeek().toString().substring(0, 3);
	}
	public static String getCurrentDate() {
		return LocalDate.now().toString();
	}
	public static int extractYear(String date) {
		return Integer.parseInt(date.substring(0, 4));
	}
	public static int extractMonth(String date) {
		return Integer.parseInt(date.substring(5, 7));
	}
	public static int extractDay(String date) {
		return Integer.parseInt(date.substring(8, 10));
	}
	public static Date parseDate(java.sql.Date date) {
		return new Date(date.toString());
	}
}
