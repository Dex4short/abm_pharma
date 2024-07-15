package database.mysql;

import misc.objects.Decimal;
import misc.objects.Percentage;

public class MySQL_Pricing {
	public static String PricingColumns[] = {"price_id", "cost", "unit_price", "discount", "unit_amount"};
	
	public static int insertPricing(Decimal cost, Decimal unit_price, Percentage discount, Decimal unit_amount) {
		int price_id = MySQL.nextUID("pricing");
		
		Object values[] = {
				price_id,
				cost.getBigDecimalValue(),
				unit_price.getBigDecimalValue(),
				discount.getPercentValue(),
				unit_amount.getBigDecimalValue()
		};
		MySQL.insert("pricing", PricingColumns, values);
		
		return price_id;
	}
}
