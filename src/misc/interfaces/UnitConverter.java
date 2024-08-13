package misc.interfaces;

import java.math.BigDecimal;
import java.util.ArrayList;

import misc.enums.UomType;
import misc.objects.Packaging;
import misc.objects.Quantity;
import misc.objects.Uom;

public interface UnitConverter {

	public default BigDecimal getUnitScale(Uom main_uom, UomType uom_type) {
		BigDecimal 
		scale = new BigDecimal(main_uom.getUnitSize());
		
		if(main_uom.getUnitType() == uom_type) {
			return scale;
		}
		else {
			boolean found = false;
			Uom sub_uom = main_uom.getSubUom();
			while(sub_uom != null) {
				scale = scale.divide(new BigDecimal(sub_uom.getUnitSize()));
				
				if(sub_uom.getUnitType() == uom_type) {
					found = true;
					break;
				}
				
				sub_uom = sub_uom.getSubUom();
			}
			
			if(found) {
				return scale;
			}
			else {
				return null;
			}
		}
	}
	public default Packaging[] subtractUnits(Packaging packaging, Uom uom1, int qty1, Uom uom2, int qty2) {		
		ArrayList<Packaging> bypackagings = new ArrayList<Packaging>();

		Uom uom = packaging.getUom();		
		Quantity qty;
		
		BigDecimal
		a = new BigDecimal(qty1).multiply(getUnitScale(uom, uom1.getUnitType())),
		b = new BigDecimal(qty2).multiply(getUnitScale(uom, uom2.getUnitType())),
		c = a.subtract(b),
		d = new BigDecimal(0);
		
		UomType
		uom_a = packaging.getUom().getUnitType(), 
		uom_b;
		
		int
		pack_id = packaging.getPackId(),
		uom_size,
		quantity,
		parentPack_id = packaging.getParentPackId();
		
		while(uom != null) {
			uom_size = uom.getUnitSize();
			c = c.subtract(d).multiply(new BigDecimal(uom_size));
			
			quantity = c.intValue();
			if(quantity < 0) {
				return null;
			}

			qty = new Quantity(0, packaging.getQty().getQuantity());
			qty.setQuantity(quantity);
			
			uom_b = uom.getUnitType();
			if(uom_a != uom_b) {
				qty.setSize(quantity);
			}
			else {
				qty.setSize(packaging.getQty().getQuantity());
			}
			
			bypackagings.add(
				new Packaging(
					pack_id,
					qty,
					uom,
					parentPack_id
				)
			);
			
			pack_id = -2;//temporary value
			parentPack_id = packaging.getPackId();
			
			d = new BigDecimal(quantity);
			uom = uom.getSubUom();
		}
		
		return bypackagings.toArray(new Packaging[bypackagings.size()]);
	}
}
