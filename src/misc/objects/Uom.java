package misc.objects;

import misc.enums.UomType;

public class Uom {
	private int uom_id;
	private UomType unit_type;
	private int unit_size;
	private Uom sub_uom;
	
	public Uom(int uom_id, UomType uomType, int unitSize, Uom subUom) {
		setUomId(uom_id);
		setUnitType(uomType);
		setUnitSize(unitSize);
		setSubUom(subUom);
	}
	@Override
	public String toString() {
		String str = "[" + getUnitType() + " : " + getUnitSize() + "]";
		Uom sub_uom = getSubUom();
		while(sub_uom != null) {
			str += " -> [" + sub_uom.getUnitType() + " : " + sub_uom.getUnitSize() + "]";
			sub_uom = sub_uom.getSubUom();
		}
		return str;
	}
	public int getUomId() {
		return uom_id;
	}
	public void setUomId(int uom_id) {
		this.uom_id = uom_id;
	}
	public UomType getUnitType() {
		return unit_type;
	}
	public int getUnitSize() {
		return unit_size;
	}
	public Uom getSubUom() {
		return sub_uom;
	}
	public void setUnitType(UomType unitType) {
		unit_type = unitType;
	}
	public void setUnitSize(int unitSize) {
		unit_size = unitSize;
	}
	public void setSubUom(Uom subUom) {
		sub_uom = subUom;
	}
}
