package database.mysql;

import misc.enums.ItemCondition;
import misc.objects.Packaging;
import misc.objects.Quantity;
import misc.objects.Uom;

public class MySQL_Packaging {
	public static String PackagingColumns[] = {"pack_id", "qty", "size", "uom_id", "parentPack_id"};
	
	public static void insertPackaging(Packaging packaging) {
		MySQL_Uom.insertUom(packaging.getUom());
		
		packaging.setPackId(MySQL.nextUID("pack_id", "packaging"));
		Object values[] = {
				packaging.getPackId(),
				packaging.getQty().getQuantity(),
				packaging.getQty().getSize(),
				packaging.getUom().getUomId(),
				packaging.getParentPackId() //no parent pack id yet 
		};
		MySQL.insert("packaging", PackagingColumns, values);
		
	}
	public static Packaging selectPackaging(int pack_id) {
		Object packaging_result[][] = MySQL.select(PackagingColumns, "packaging", "where pack_id=" + pack_id);
		
		int
		count  = (int)packaging_result[0][1],
		size   = (int)packaging_result[0][2],
		uom_id = (int)packaging_result[0][3],
		parentPack_id = (int)packaging_result[0][4];
		
		Quantity
		qty = new Quantity(count, size);
		
		Uom
		uom = MySQL_Uom.selectUom(uom_id);
		
		return new Packaging(pack_id, qty, uom, parentPack_id);
	}
	public static Packaging[] selectPackagings(String condition) {
		Object packaging_result[][] = MySQL.select(PackagingColumns, "packaging", condition);
		Packaging packagings[] = new Packaging[packaging_result.length];
		
		int	pack_id, count, size, uom_id, parentPack_id;
		
		Quantity qty;
		Uom uom;
		
		for(int i=0; i<packaging_result.length; i++) {
			pack_id = (int)packaging_result[i][0];
			count = (int)packaging_result[i][1];
			size = (int)packaging_result[i][2];
			uom_id = (int)packaging_result[i][3];
			parentPack_id = (int)packaging_result[i][4];
			
			qty = new Quantity(count, size);
			uom = MySQL_Uom.selectUom(uom_id);
			
			packagings[i] = new Packaging(pack_id, qty, uom, parentPack_id);
		}

		return packagings;
	}	
	public static Packaging selectPackagingStoredArchived(int uom_id, int parentPack_id) {	
		String
		columns[] = {"p.pack_id", "p.qty", "p.size", "p.uom_id", "p.parentPack_id"},
		joins = "inventory i join packaging p",
		
		condition1 = "p.uom_id=" + uom_id,
		condition2 = "p.parentPack_id=" + parentPack_id,
		condition3 = "i.item_condition='" + ItemCondition.STORED.toString() + "'",
		condition4 = "i.item_condition='" + ItemCondition.ARCHIVED.toString() + "'",
		
		conditions = "where (" + condition1 + " and " + condition2 + ") and (" + condition3 + " or " + condition4 + ")";
		
		Object pack_result[][] = MySQL.select(columns, joins, conditions);
		
		int
		pack_id= (int)pack_result[0][0],
		count  = (int)pack_result[0][1],
		size   = (int)pack_result[0][2];
		
		Quantity
		qty = new Quantity(count, size);
		
		Uom
		uom = MySQL_Uom.selectUom(uom_id);
		
		return new Packaging(pack_id, qty, uom, parentPack_id);
	}
	public static Packaging[] selectPackagingChildrenStoredArchived(int parentPack_id) {		
		String 
		joins = "inventory i join packaging p",
		
		condition1 = "p.parentPack_id=" + parentPack_id,
		condition2 = "i.item_condition='" + ItemCondition.STORED.toString() + "'",
		condition3 = "i.item_condition='" + ItemCondition.ARCHIVED.toString() + "'",
		
		conditions = "where " + condition1 + " and (" + condition2 + " or " + condition3 + ")";
		
		return selectPackagings(joins + conditions);
	}
	public static void updatePackaging(Packaging packaging) {
		MySQL_Uom.updateUom(packaging.getUom());
		Object values[] = {
				packaging.getPackId(),
				packaging.getQty().getQuantity(),
				packaging.getQty().getSize(),
				packaging.getUom().getUomId(),
				packaging.getParentPackId()
		};
		MySQL.update("packaging", PackagingColumns, values, "where pack_id=" + packaging.getPackId());
	}
	public static void updatePackagingQuantity(Packaging packaging) {
		MySQL.update(
			"packaging",
			new String[]{
				"qty", "size"
			},
			new Object[] {
				packaging.getQty().getQuantity(), 
				packaging.getQty().getSize()
			}, 
			"where pack_id=" + packaging.getPackId()
		);
	}
	public static void updateByPackagingQuantities(Packaging bypackagings[]) {
		updatePackagingQuantity(bypackagings[0]);
		
		Packaging packaging;
		int qty1,qty2,qty,size;
		ItemCondition item_conditon;
		
		for(int i=1; i<bypackagings.length; i++) {
			packaging = selectPackagingStoredArchived(
					bypackagings[i].getUom().getUomId(),
					bypackagings[i].getParentPackId()
			);
			bypackagings[i].setPackId(packaging.getPackId());
			
			qty1 = packaging.getQty().getQuantity();
			qty2 = bypackagings[i].getQty().getQuantity();
			
			qty  = qty1 + qty2;
			size = packaging.getQty().getSize();
			if(qty > size) {
				size = qty;
			}
			
			packaging.getQty().setQuantity(qty);
			packaging.getQty().setSize(size);
			updatePackagingQuantity(packaging);
			
			if(qty > 0) {
				item_conditon = ItemCondition.STORED;
			}
			else {
				item_conditon = ItemCondition.ARCHIVED;
			}
			MySQL_Inventory.updateProductItemConditionByPackaging(packaging.getPackId(), item_conditon);
		}
	}
	public static void deletePackaging(int pack_id) {
		MySQL.delete("packaging", "where pack_id=" + pack_id);
	}
}
