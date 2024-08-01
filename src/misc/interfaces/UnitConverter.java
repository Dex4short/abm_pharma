package misc.interfaces;

import java.math.BigDecimal;
import java.util.ArrayList;

import misc.enums.UomType;
import misc.objects.Decimal;
import misc.objects.Item;
import misc.objects.Packaging;
import misc.objects.Percentage;
import misc.objects.Pricing;
import misc.objects.Product;
import misc.objects.Quantity;
import misc.objects.Uom;

public interface UnitConverter {

	public default Product[] subtractUnits(Product product, Uom uom1, int qty1, Uom uom2, int qty2) {
		Item      item      = product.getItem();
		Packaging packaging = product.getPackaging();
		Pricing   pricing   = product.getPricing();
		
		ArrayList<Product> byproducts = new ArrayList<Product>();
		
		BigDecimal
		a = new BigDecimal(qty1).divide(new BigDecimal(uom1.getUnitSize())),
		b = new BigDecimal(qty2).divide(new BigDecimal(uom2.getUnitSize())),
		c = a.subtract(b),
		d = new BigDecimal(0),
		e;
		
		Uom uom = packaging.getUom();
		Quantity qty;
		
		Decimal    cost, unit_price, unit_amount; 
		Percentage discount;
		
		UomType
		uom_a = packaging.getUom().getUnitType(), 
		uom_b;
		
		int uom_size;
		
		while(uom != null) {
			uom_size = uom.getUnitSize();
			c = c.subtract(d).multiply(new BigDecimal(uom_size));
			
			qty = new Quantity(0, packaging.getQty().getQuantity());
			qty.setQuantity(c.intValue());
			
			uom_b = uom.getUnitType();
			if(uom_a != uom_b) {
				qty.setSize(c.intValue());
				discount = new Percentage("%0");
			}
			else {
				qty.setSize(packaging.getQty().getQuantity());
				discount = pricing.getDiscount();
			}
			
			e = new BigDecimal(uom_size);
			cost = new Decimal(pricing.getCost().toBigDecimal().divide(e));
			unit_price = new Decimal(pricing.getUnitPrice().toBigDecimal().divide(e));
			unit_amount = new Decimal(pricing.getUnitAmount().toBigDecimal().divide(e));
			
			byproducts.add(
				new Product(
					product.getInvId(),
					new Item(
							item.getItemId(), //TODO
							item.getItemNo(), 
							item.getDescription(), 
							item.getLotNo(), 
							item.getDateAdded(), 
							item.getExpDate(), 
							item.getBrand()
					),
					new Packaging(
							packaging.getPackId(),//TODO
							qty,
							uom,
							packaging.getParentPackId()//TODO
					), 
					pricing = new Pricing(
							pricing.getPriceId(),//TODO
							cost, //TODO
							unit_price, //TODO
							discount,//TODO
							unit_amount//TODO
					),
					product.getRemarks()//TODO
			));
			d = new BigDecimal(c.intValue());
			
			uom = uom.getSubUom();
		}
		
		return byproducts.toArray(new Product[byproducts.size()]);
	}
}
