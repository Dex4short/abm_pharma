package database.mysql;

import misc.objects.Uom;

public class MySQL_Uom {
	public static String UomColumns[] = {"uom_id", "name", "size", "subUom_id"};
	
	public static int insertUom(Uom uom) {
		if(uom != null) {
			int uom_id = MySQL.nextUID("uom");
			
			Object values[] = {
					uom_id,
					uom.getUnitName(),
					uom.getUnitSize(),
					insertUom(uom.getSubUom())
			};
			MySQL.insert("uom", UomColumns, values);
			
			return uom_id;
		}
		else {
			return -1;
		}
	}
}
