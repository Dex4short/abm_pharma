package database.mysql;

import misc.interfaces.UID;
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
			
			return new UomRetrieved(unit_name, unit_size, sub_uom) {
				@Override
				public int getId() {
					return (int)uom_result[0][0];
				}
			};
		}
		else {
			return null;
		}
	}
	
	public static abstract class UomRetrieved extends Uom implements UID{
		public UomRetrieved(String unitName, int unitSize, Uom subUom) {
			super(unitName, unitSize, subUom);
			// TODO Auto-generated constructor stub
		}
	}
}
