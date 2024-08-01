package misc.objects;

public class Remarks {
	private int rem_id;
	private Date date;
	private Time time;
	private String details;
	
	public Remarks(int rem_id, Date date, Time time, String details) {
		setRemId(rem_id);
		setDate(date);
		setTime(time);
		setDetails(details);
	}
	@Override
	public String toString() {
		String str = 
			"Remarks( rem_id:" + getRemId() + " )\n" +
			"\t" + getDate() + "\n" +
			"\t" + getTime() + "\n" +
			"\t" + getDetails() + "\n"
		;	
		return str;
	}
	public int getRemId() {
		return rem_id;
	}
	public void setRemId(int rem_id) {
		this.rem_id = rem_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}
