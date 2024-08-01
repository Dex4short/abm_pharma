package misc.interfaces;

import misc.objects.Decimal;
import misc.objects.Percentage;

public interface Accountancy {

	public default Decimal calculateUnitAmount(Decimal unit_price, Percentage discount) {
		return new Decimal(
				unit_price.toBigDecimal().subtract(
						unit_price.toBigDecimal().multiply(
								discount.toBigDecimal()
						)
				)
		);
	}
}
