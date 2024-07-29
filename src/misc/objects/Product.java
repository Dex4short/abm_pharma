package misc.objects;

public class Product {
	private int inv_id;
	private Item item;
	private Pricing pricing;
	private Packaging packaging;
	private Remarks remarks;
	
	public Product(int inv_id, Item item, Packaging packaging, Pricing pricing, Remarks remarks) {
		setInvId(inv_id);
		setItem(item);
		setPackaging(packaging);
		setPricing(pricing);
		setRemarks(remarks);
	}
	public void setInvId(int inv_id) {
		this.inv_id = inv_id;
	}
	public int getInvId() {
		return inv_id;
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
	public void setRemarks(Remarks remarks) {
		this.remarks = remarks;
	}
	public Remarks getRemarks() {
		return remarks;
	}
}
