package customs.tables;

import gui.Table;
import misc.interfaces.TableConstants;

public abstract class TableCustomers extends Table implements TableConstants.Customers{
	private static final long serialVersionUID = 3671439018669987502L;

	public TableCustomers() {
		super(CustomerFields);
	}
	
}
