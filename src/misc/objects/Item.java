package misc.objects;

public class Item {
	private int item_id;
	private String item_no,description, lot_no, brand;
	private Date date_added, exp_date;

	public Item(int inv_id, String item_no, String description, String lot_no, Date date_added, Date exp_date, String brand) {
		setItemId(inv_id);
		setItemNo(item_no);
		setItemNo(item_no);
		setDescription(description);
		setLotNo(lot_no);
		setDateAdded(date_added);
		setExpDate(exp_date);
		setBrand(brand);
	}
	public int getItemId() {
		return item_id;
	}
	public void setItemId(int item_id) {
		this.item_id = item_id;
	}
	public String getItemNo() {
		return item_no;
	}
	public void setItemNo(String item_no) {
		this.item_no = item_no;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLotNo() {
		return lot_no;
	}
	public void setLotNo(String lot_no) {
		this.lot_no = lot_no;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Date getDateAdded() {
		return date_added;
	}
	public void setDateAdded(Date date_added) {
		this.date_added = date_added;
	}
	public Date getExpDate() {
		return exp_date;
	}
	public void setExpDate(Date exp_date) {
		this.exp_date = exp_date;
	}
}
