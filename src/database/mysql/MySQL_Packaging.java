package database.mysql;

import misc.objects.Quantity;

public class MySQL_Packaging {
	public static String PackagingColumns[] = {"pack_id", "qty", "size", "uom_id"};
	
	public static int insertPackaging(Quantity qty, int uom_id) {
		int pack_id = MySQL.nextUID("packaging");
		
		Object values[] = {
				pack_id,
				qty.getQuantity(),
				qty.getSize(),
				uom_id
		};
		MySQL.insert("packaging", PackagingColumns, values);
		
		return pack_id;
	}
}
