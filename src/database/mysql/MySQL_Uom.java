package database.mysql;

import misc.enums.UomType;
import misc.objects.Uom;

public class MySQL_Uom {
	public static String UomColumns[] = {"uom_id", "name", "size", "subUom_id"};
	
	public static void insertUom(Uom uom) {
		if(uom != null) {
			Uom sub_uom = uom.getSubUom();
			insertUom(sub_uom);
			
			int sub_uomId;
			if(sub_uom != null) {
				sub_uomId = sub_uom.getUomId();
			}
			else {
				sub_uomId = -1;
			}
			
			Object 
			values[] = {
					uom.getUomId(),
					uom.getUnitType().toString(),
					uom.getUnitSize(),
					sub_uomId
			},
			uom_result[][] = MySQL.select(new String[] {"uom_id"}, "uom", "where uom_id=" + uom.getUomId());
			
			if(uom_result.length == 0) {
				MySQL.insert("uom", UomColumns, values);
			}
		}
	}
	public static Uom selectUom(int uom_id) {
		if(uom_id != -1) {
			Object uom_result[][] = MySQL.select(UomColumns, "uom", "where uom_id=" + uom_id);
			
			String
			unit_name = (String)uom_result[0][1];
			
			int
			unit_size = (int)uom_result[0][2],
			subUom_id = (int)uom_result[0][3];
			
			Uom
			sub_uom	  = selectUom(subUom_id);
			
			return new Uom(uom_id, UomType.valueOf(unit_name), unit_size, sub_uom);
		}
		else {
			return null;
		}
	}
	public static void updateUom(Uom uom) {
		insertUom(uom);
	}
}
