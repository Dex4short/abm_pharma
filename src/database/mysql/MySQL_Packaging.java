package database.mysql;

import misc.interfaces.UID;
import misc.objects.Packaging;
import misc.objects.Quantity;
import misc.objects.Uom;

public class MySQL_Packaging {
	public static String PackagingColumns[] = {"pack_id", "qty", "size", "uom_id"};
	
	public static int insertPackaging(Packaging packaging) {
		int
		pack_id = MySQL.nextUID("packaging"),
		uom_id = MySQL_Uom.insertUom(packaging.getUom());
		
		Object values[] = {
				pack_id,
				packaging.getQty().getQuantity(),
				packaging.getQty().getSize(),
				uom_id
		};
		MySQL.insert("packaging", PackagingColumns, values);
		
		return pack_id;
	}
	public static Packaging selectPackaging(int pack_id) {
		Object packaging_result[][] = MySQL.select(PackagingColumns, "packaging", "where pack_id=" + pack_id);
		
		int
		count  = (int)packaging_result[0][1],
		size   = (int)packaging_result[0][2],
		uom_id = (int)packaging_result[0][3];
		
		Quantity
		qty = new Quantity(count, size);
		
		Uom
		uom = MySQL_Uom.selectUom(uom_id);
		
		return new PackagingRetrieved(qty, uom) {
			@Override
			public int getId() {
				return (int)packaging_result[0][0];
			}
		};
		
	}
	
	public static abstract class PackagingRetrieved extends Packaging implements UID{
		public PackagingRetrieved(Quantity qty, Uom uom) {
			super(qty, uom);
			// TODO Auto-generated constructor stub
		}
	}
}
