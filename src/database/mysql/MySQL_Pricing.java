package database.mysql;

import java.math.BigDecimal;

import misc.objects.Decimal;
import misc.objects.Percentage;
import misc.objects.Pricing;

public class MySQL_Pricing {
	public static String PricingColumns[] = {"price_id", "cost", "unit_price", "discount", "unit_amount"};
	
	public static void insertPricing(Pricing pricing) {
		
		Object values[] = {
				pricing.getPriceId(),
				pricing.getCost().toBigDecimal(),
				pricing.getUnitPrice().toBigDecimal(),
				pricing.getDiscount().toString(),
				pricing.getUnitAmount().toBigDecimal()
		};
		MySQL.insert("pricing", PricingColumns, values);
	}
	public static Pricing selectPricing(int price_id) {
		Object pricing_result[][] = MySQL.select(PricingColumns, "pricing", "where price_id=" + price_id);
		
		Decimal
		cost        = new Decimal((BigDecimal)pricing_result[0][1]),
		unit_price  = new Decimal((BigDecimal)pricing_result[0][2]),
		unit_amount = new Decimal((BigDecimal)pricing_result[0][4]);
		
		Percentage
		percentage  = new Percentage((String)pricing_result[0][3]);
		
		return new Pricing(price_id, cost, unit_price, percentage, unit_amount);
	}
	public static void updatePricing(Pricing pricing) {
		Object values[] = {
				pricing.getPriceId(),
				pricing.getCost().toBigDecimal(),
				pricing.getUnitPrice().toBigDecimal(),
				pricing.getDiscount().toString(),
				pricing.getUnitAmount().toBigDecimal()
		};
		MySQL.update("pricing", PricingColumns, values, "where price_id=" + pricing.getPriceId());
	}
}
