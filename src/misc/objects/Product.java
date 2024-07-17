package misc.objects;

public class Product {
	private Item item;
	private Pricing pricing;
	private Packaging packaging;
	
	public Product(Item item, Packaging packaging, Pricing pricing) {
		setItem(item);
		setPackaging(packaging);
		setPricing(pricing);
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Item getItem() {
		return item;
	}
	public Packaging getPackaging() {
		return packaging;
	}
	public void setPackaging(Packaging packaging) {
		this.packaging = packaging;
	}
	public Pricing getPricing() {
		return pricing;
	}
	public void setPricing(Pricing pricing) {
		this.pricing = pricing;
	}
}
