package misc.objects;

public class Pricing {
	private int price_id;
	private Decimal cost, unit_price, unit_amount;
	private Percentage discount;

	public Pricing(int price_id, Decimal cost, Decimal unit_price, Percentage discount, Decimal unit_amount) {
		setPriceId(price_id);
		setCost(cost);
		setUnitPrice(unit_price);
		setDiscount(discount);
		setUnitAmount(unit_amount);
	}
	@Override
	public String toString() {
		String str = 
			"Pricing( price_id:" + getPriceId() + " )\n" +
			"\t" + getCost().toString() + "\n" +
			"\t" + getUnitPrice().toString() + "\n" +
			"\t" + getDiscount().toString() + "\n" +
			"\t" + getUnitAmount().toString() + "\n"
		;	
		return str;
	}
	public int getPriceId() {
		return price_id;
	}
	public void setPriceId(int price_id) {
		this.price_id = price_id;
	}
	public Decimal getCost() {
		return cost;
	}
	public void setCost(Decimal cost) {
		this.cost = cost;
	}
	public Decimal getUnitPrice() {
		return unit_price;
	}
	public void setUnitPrice(Decimal unit_price) {
		this.unit_price = unit_price;
	}
	public Decimal getUnitAmount() {
		return unit_amount;
	}
	public void setUnitAmount(Decimal unit_amount) {
		this.unit_amount = unit_amount;
	}
	public Percentage getDiscount() {
		return discount;
	}
	public void setDiscount(Percentage discount) {
		this.discount = discount;
	}
}
