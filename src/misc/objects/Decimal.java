package misc.objects;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Decimal {
	private BigDecimal bigdecimal_value;
	
	public Decimal(BigDecimal bigdecimal_value) {
		this.bigdecimal_value = bigdecimal_value;
	}
	@Override
	public String toString() {
		return new DecimalFormat("#,##0.00").format(bigdecimal_value.doubleValue());
	}
	public BigDecimal toBigDecimal() {
		return bigdecimal_value;
	}
}
