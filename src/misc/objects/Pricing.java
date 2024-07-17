package misc.objects;

public class Pricing {
	private Decimal cost, unit_price, unit_amount;
	private Percentage discount;

	public Pricing(Decimal cost, Decimal unit_price, Percentage discount, Decimal unit_amount) {
		setCost(cost);
		setUnitPrice(unit_price);
		setDiscount(discount);
		setUnitAmount(unit_amount);
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
