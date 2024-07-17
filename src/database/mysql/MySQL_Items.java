package database.mysql;

import misc.interfaces.UID;
import misc.objects.Date;
import misc.objects.Item;

public class MySQL_Items {
	public static String ItemsColumns[] = {"item_id", "item_no", "description", "lot_no", "date_added", "exp_date", "brand"};
	
	public static int insertItem(Item item) {
		int item_id = MySQL.nextUID("items");
		
		Object values[] = {
				item_id, 
				item.getItemNo(),
				item.getDescription(), 
				item.getLotNo(), 
				java.sql.Date.valueOf(item.getDateAdded().toString()), 
				java.sql.Date.valueOf(item.getExpDate().toString()), 
				item.getBrand()
		};
		MySQL.insert("items", ItemsColumns, values);
		
		return item_id;
	}
	public static Item selectItem(int item_id) {
		Object item_result[][] = MySQL.select(ItemsColumns, "items", "where item_id=" + item_id);
		
		String 
		item_no 	= (String)item_result[0][1], 
		description = (String)item_result[0][2], 
		lot_no 		= (String)item_result[0][3],
		brand		= (String)item_result[0][6];
		
		Date
		date_added	= new Date(((java.sql.Date)item_result[0][4]).toString()),
		exp_date	= new Date(((java.sql.Date)item_result[0][5]).toString());
		
		return new ItemRetrieved(item_no, description, lot_no, date_added, exp_date, brand) {
			@Override
			public int getId() {
				return (int)item_result[0][0];
			}
		};
	}
	
	public static abstract class ItemRetrieved extends Item implements UID{

		public ItemRetrieved(String item_no, String description, String lot_no, Date date_added, Date exp_date,String brand) {
			super(item_no, description, lot_no, date_added, exp_date, brand);
			// TODO Auto-generated constructor stub
		}
	}
}
