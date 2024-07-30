package default_package.admin.transactions;

import customs.tables.TableCustomers;

public abstract class TableCustomerTransactions extends TableCustomers{
	private static final long serialVersionUID = -716645426049000233L;

	public TableCustomerTransactions() {
		String modified_columns[] = {
				CustomerFields[customer_name],
				CustomerFields[tin_no],
				CustomerFields[address],
				CustomerFields[date],
				CustomerFields[terms],
				"Cost Amount",
				"Profit"
		};
		setColumns(modified_columns);
	}
	
}
