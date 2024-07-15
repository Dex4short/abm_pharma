package database.mysql;

import misc.objects.Date;

public class MySQL_Items {
	public static String ItemsColumns[] = {"item_id", "item_no", "description", "lot_no", "date_added", "exp_date", "brand"};
	
	public static int insertItem(String item_no, String description, String lot_no, Date date_added, Date exp_date, String brand) {
		int item_id = MySQL.nextUID("items");
		
		Object values[] = {
				item_id, 
				item_no,
				description, 
				lot_no, 
				java.sql.Date.valueOf(date_added.toString()), 
				java.sql.Date.valueOf(exp_date.toString()), 
				brand
		};
		MySQL.insert("items", ItemsColumns, values);
		
		return item_id;
	}
}
