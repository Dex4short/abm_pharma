package database.mysql;

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
	public static Packaging[] selectPackagingChildren(int parentPackId) {
		return selectPackagings("where parentPack_id=" + parentPackId);
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
}
