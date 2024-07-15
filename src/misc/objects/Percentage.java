package misc.objects;

public class Percentage {
	private String percent_value;
	/**
	 * 
	 * @param percent_value with % sign, "%0"~"%100".
	 */
	public Percentage(String percent_value) {
		setPercentValue(percent_value);
	}
	public String getPercentValue() {
		return percent_value;
	}
	public void setPercentValue(String percent_value){
		if(percent_value.charAt(0) != '%') {
			System.err.println("percent value must have a percent symbol");
		}
		else {
			this.percent_value = percent_value;
		}
	}
	public int parseInt() {
		return Integer.parseInt(percent_value.substring(1, percent_value.length()));
	}
}
