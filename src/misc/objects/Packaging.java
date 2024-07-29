package misc.objects;

public class Packaging {
	private int pack_id;
	private Quantity qty;
	private Uom uom;
	
	public Packaging(int pack_id, Quantity qty, Uom uom) {
		setPackId(pack_id);
		setQty(qty);
		setUom(uom);
	}
	public int getPackId() {
		return pack_id;
	}
	public void setPackId(int pack_id) {
		this.pack_id = pack_id;
	}
	public Quantity getQty() {
		return qty;
	}
	public void setQty(Quantity qty) {
		this.qty = qty;
	}
	public Uom getUom() {
		return uom;
	}
	public void setUom(Uom uom) {
		this.uom = uom;
	}
}
