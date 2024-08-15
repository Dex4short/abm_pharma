package misc.objects;

public class Packaging {
	private int pack_id, parentPack_id;
	private Quantity qty;
	private Uom uom;
	
	public Packaging(int pack_id, Quantity qty, Uom uom, int parentPack_id) {
		setPackId(pack_id);
		setQty(qty);
		setUom(uom);
		setParentPackId(parentPack_id);
	}
	@Override
	public String toString() {
		String str = 
			"Packaging( pack_id:" + getPackId() + " )\n" +
			"\t" + getQty().toString() + "\n" +
			"\t" + getUom().toString() + "\n" +
			"\t" + getParentPackId() + "\n"
		;
				
		return str;
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
	public int getParentPackId() {
		return parentPack_id;
	}
	public void setParentPackId(int parentPack_id) {
		this.parentPack_id = parentPack_id;
	}
}
