package misc.interfaces;

import java.math.BigDecimal;

import misc.objects.Decimal;
import misc.objects.Packaging;
import misc.objects.Percentage;
import misc.objects.Pricing;

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
	public default Decimal calculateNetAmount(Packaging packaging, Pricing pricing) {
		int quantity = packaging.getQty().getQuantity();
		
		Decimal 
		unit_amount = calculateUnitAmount(pricing.getUnitPrice(), pricing.getDiscount()),
		net_amount = new Decimal(unit_amount.toBigDecimal().multiply(new BigDecimal(quantity)));
		
		return net_amount;
	}
}
