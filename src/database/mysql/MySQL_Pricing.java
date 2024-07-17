package database.mysql;

import java.math.BigDecimal;

import misc.interfaces.UID;
import misc.objects.Decimal;
import misc.objects.Percentage;
import misc.objects.Pricing;

public class MySQL_Pricing {
	public static String PricingColumns[] = {"price_id", "cost", "unit_price", "discount", "unit_amount"};
	
	public static int insertPricing(Pricing pricing) {
		int price_id = MySQL.nextUID("pricing");
		
		Object values[] = {
				price_id,
				pricing.getCost().getBigDecimalValue(),
				pricing.getUnitPrice().getBigDecimalValue(),
				pricing.getDiscount().getPercentValue(),
				pricing.getUnitAmount().getBigDecimalValue()
		};
		MySQL.insert("pricing", PricingColumns, values);
		
		return price_id;
	}
	public static Pricing selectPricing(int price_id) {
		Object pricing_result[][] = MySQL.select(PricingColumns, "pricing", "where price_id=" + price_id);
		
		Decimal
		cost        = new Decimal((BigDecimal)pricing_result[0][1]),
		unit_price  = new Decimal((BigDecimal)pricing_result[0][2]),
		unit_amount = new Decimal((BigDecimal)pricing_result[0][4]);
		
		Percentage
		percentage  = new Percentage((String)pricing_result[0][3]);
		
		return new PricingRetrieved(cost, unit_price, percentage, unit_amount) {
			@Override
			public int getId() {
				return (int)pricing_result[0][0];
			}
		};
	}
	
	public static abstract class PricingRetrieved extends Pricing implements UID{
		public PricingRetrieved(Decimal cost, Decimal unit_price, Percentage discount, Decimal unit_amount) {
			super(cost, unit_price, discount, unit_amount);
			// TODO Auto-generated constructor stub
		}
		
	}
}
