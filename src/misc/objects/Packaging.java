package misc.objects;

public class Packaging {
	private Quantity qty;
	private Uom uom;
	
	public Packaging(Quantity qty, Uom uom) {
		setQty(qty);
		setUom(uom);
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
