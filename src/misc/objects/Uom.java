package misc.objects;

public class Uom {
	private String unit_name;
	private int unit_size;
	private Uom sub_uom;
	
	public Uom(String unitName, int unitSize, Uom subUom) {
		setUnitName(unitName);
		setUnitSize(unitSize);
		setSubUom(subUom);
	}
	public String getUnitName() {
		return unit_name;
	}
	public int getUnitSize() {
		return unit_size;
	}
	public Uom getSubUom() {
		return sub_uom;
	}
	public void setUnitName(String unitName) {
		unit_name = unitName;
	}
	public void setUnitSize(int unitSize) {
		unit_size = unitSize;
	}
	public void setSubUom(Uom subUom) {
		sub_uom = subUom;
	}
}
