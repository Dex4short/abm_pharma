package misc.interfaces;

public interface TableConstants {
	
	public interface Products{
		public final String ProductFields[] = {"Item No.","Description","Lot No.","Date Added","Exp Date","Brand","Quantity","UOM","Cost","Unit Price","Discount","Unit Amount"};
		public final int item_no=0, description=1, lot_no=2, date_added=3, exp_date=4, brand=5, qty=6, uom=7, cost=8, unit_price=9, discount=10, unit_amount=11;
	}
	
	public interface Customers{
		public final String CustomerFields[] = {"Customer Name", "TIN No.", "Address", "Date", "Terms"};
		public final int customer_name=0, tin_no=1, address=2, date=3, terms=4;
	}
}