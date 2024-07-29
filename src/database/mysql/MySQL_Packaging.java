package database.mysql;

import misc.objects.Packaging;
import misc.objects.Quantity;
import misc.objects.Uom;

public class MySQL_Packaging {
	public static String PackagingColumns[] = {"pack_id", "qty", "size", "uom_id"};
	
	public static void insertPackaging(Packaging packaging) {
		MySQL_Uom.insertUom(packaging.getUom());
		
		Object values[] = {
				packaging.getPackId(),
				packaging.getQty().getQuantity(),
				packaging.getQty().getSize(),
				packaging.getUom().getUomId()
		};
		MySQL.insert("packaging", PackagingColumns, values);
		
	}
	public static Packaging selectPackaging(int pack_id) {
		Object packaging_result[] = MySQL.select(PackagingColumns, "packaging", "where pack_id=" + pack_id)[0];
		
		int
		count  = (int)packaging_result[1],
		size   = (int)packaging_result[2],
		uom_id = (int)packaging_result[3];
		
		Quantity
		qty = new Quantity(count, size);
		
		Uom
		uom = MySQL_Uom.selectUom(uom_id);
		
		return new Packaging(pack_id, qty, uom);
		
	}
	public static void updatePackaging(Packaging packaging) {
		MySQL_Uom.updateUom(packaging.getUom());
		
		Object values[] = {
				packaging.getPackId(),
				packaging.getQty().getQuantity(),
				packaging.getQty().getSize(),
				packaging.getUom().getUomId()
		};
		MySQL.update("packaging", PackagingColumns, values, "where pack_id=" + packaging.getPackId());
	}
}
