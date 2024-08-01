package misc.objects;

public class Product {
	private int inv_id;
	private Item item;
	private Packaging packaging;
	private Pricing pricing;
	private Remarks remarks;
	
	public Product(int inv_id, Item item, Packaging packaging, Pricing pricing, Remarks remarks) {
		setInvId(inv_id);
		setItem(item);
		setPackaging(packaging);
		setPricing(pricing);
		setRemarks(remarks);
	}
	@Override
	public String toString() {
		String str = 
			"Product( inv_id:" + getInvId() + " )\n" +
			"\t" + getItem().toString() + "\n" +
			"\t" + getPackaging().toString() + "\n" +
			"\t" + getPricing().toString() + "\n"
		;
		
		if(getRemarks() != null) {
			str += "\t" + getRemarks().toString() + "\n";
		}else {
			str += "\tRemarks( n/a )\n" +
			"\tno remarks \n";
		}
		
		return str;
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
