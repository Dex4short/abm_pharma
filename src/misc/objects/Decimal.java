package misc.objects;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Decimal {
	private BigDecimal bigdecimal_value;
	
	public Decimal(BigDecimal bigdecimal_value) {
		setBigDecimalValue(bigdecimal_value);
	}
	@Override
	public String toString() {
		return new DecimalFormat("#,##0.00").format(bigdecimal_value.doubleValue());
	}
	public void setBigDecimalValue(BigDecimal decimal_value) {	
		this.bigdecimal_value = decimal_value;
	}
	public BigDecimal getBigDecimalValue() {
		return bigdecimal_value;
	}
}
