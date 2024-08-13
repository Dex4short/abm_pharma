package database.mysql;

import misc.objects.Date;
import misc.objects.Item;

public class MySQL_Items {
	public static String ItemsColumns[] = {"item_id", "item_no", "description", "lot_no", "date_added", "exp_date", "brand"};
	
	public static void insertItem(Item item) {
		item.setItemId(MySQL.nextUID("item_id", "items"));
		Object values[] = {
				item.getItemId(),
				item.getItemNo(),
				item.getDescription(), 
				item.getLotNo(), 
				item.getDateAdded().toSQLDate(), 
				item.getExpDate().toSQLDate(), 
				item.getBrand()
		};
		MySQL.insert("items", ItemsColumns, values);
	}
	public static Item selectItem(int item_id) {
		Object item_result[][] = MySQL.select(ItemsColumns, "items", "where item_id=" + item_id);
		
		String 
		item_no 	= (String)item_result[0][1], 
		description = (String)item_result[0][2], 
		lot_no 		= (String)item_result[0][3],
		brand		= (String)item_result[0][6];
		
		Date
		date_added	= Date.parseDate(((java.sql.Date)item_result[0][4])),
		exp_date	= Date.parseDate(((java.sql.Date)item_result[0][5]));
		
		return new Item(item_id, item_no, description, lot_no, date_added, exp_date, brand);
	}
	public static void updateItem(Item item) {
		Object values[] = {
				item.getItemId(),
				item.getItemNo(),
				item.getDescription(), 
				item.getLotNo(), 
				item.getDateAdded().toSQLDate(), 
				item.getExpDate().toSQLDate(), 
				item.getBrand()
		};
		MySQL.update("items", ItemsColumns, values, "where item_id=" + item.getItemId());
	}
	public static void deleteItem(int item_id) {
		MySQL.delete("items", "where item_id=" + item_id);
	}
}
