package misc.objects;

public class Product {
	private String item_no,description, lot_no, brand;
	private Date date_added, exp_date;
	private Quantity qty;
	private Uom uom;
	private Decimal cost, unit_price, unit_amount;
	private Percentage discount;
	
	public Product(String item_no, String description, String lot_no, Date date_added, Date exp_date, String brand, Quantity qty, Uom uom, Decimal cost, Decimal unit_price, Percentage discount, Decimal unit_amount) {
		setItemNo(item_no);
		setDescription(description);
		setLotNo(lot_no);
		setDateAdded(date_added);
		setExpDate(exp_date);
		setBrand(brand);
		setQty(qty);
		setCost(cost);
		setUnitPrice(unit_price);
		setDiscount(discount);
		setUnitAmount(unit_amount);
	}
	public String getItemNo() {
		return item_no;
	}
	public void setItemNo(String item_no) {
		this.item_no = item_no;
	}
	public Quantity getQty() {
		return qty;
	}
	public void setQty(Quantity qty) {
		this.qty = qty;
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
	public Uom getUom() {
		return uom;
	}
	public void setUom(Uom uom) {
		this.uom = uom;
	}
	public Decimal getCost() {
		return cost;
	}
	public void setCost(Decimal cost) {
		this.cost = cost;
	}
	public Decimal getUnitPrice() {
		return unit_price;
	}
	public void setUnitPrice(Decimal unit_price) {
		this.unit_price = unit_price;
	}
	public Decimal getUnitAmount() {
		return unit_amount;
	}
	public void setUnitAmount(Decimal unit_amount) {
		this.unit_amount = unit_amount;
	}
	public Percentage getDiscount() {
		return discount;
	}
	public void setDiscount(Percentage discount) {
		this.discount = discount;
	}
}
