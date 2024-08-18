package default_package.employee.counter;

import java.awt.Toolkit;

import database.mysql.MySQL_Inventory;
import database.mysql.MySQL_Packaging;
import database.mysql.MySQL_Pricing;
import default_package.ABM_Pharma;
import default_package.admin.inventory.TableInventory;
import gui.Button;
import misc.enums.UomType;
import misc.interfaces.UICustoms;
import misc.interfaces.UnitConverter;
import misc.objects.Order;
import misc.objects.Packaging;
import misc.objects.Pricing;
import misc.objects.Product;
import misc.objects.Quantity;

public abstract class TableCounterInventory extends TableInventory implements UICustoms, UnitConverter{
	private static final long serialVersionUID = -3325745314260689909L;

	public TableCounterInventory() {
		String modified_columns[] = {
				ProductFields[item_no],
				ProductFields[description],
				ProductFields[lot_no],
				ProductFields[date_added],
				ProductFields[exp_date],
				ProductFields[brand],
				ProductFields[qty],
				ProductFields[uom],
				ProductFields[cost],
				ProductFields[unit_price],
				ProductFields[discount],
				ProductFields[unit_amount],
				"..."
		};
		setColumns(modified_columns);
	}
	@Override
	public void addRowAt(Row row, int n) {
		row.addCell(new AddToCartButton());
		super.addRowAt(row, n);
	}
	public void returnOrderToInventory(Order returned_order) {
		int 
		pack_id = returned_order.getPackaging().getParentPackId();
		
		Packaging 
		pack = MySQL_Packaging.selectPackaging(pack_id),
		sub_packs[], packs[];
		
		Quantity new_qty = Quantity.addQuantities(
				pack.getQty(), 
				returned_order.getPackaging().getQty()
		);
		
		pack.setQty(new_qty);
		MySQL_Packaging.updatePackagingQuantity(pack);
		
		pack = MySQL_Packaging.selectPackaging(pack_id);
		if(pack.getParentPackId() == -1) {
			sub_packs = MySQL_Packaging.selectPackagingChildrenStoredArchived(pack_id);
		}
		else{
			sub_packs = MySQL_Packaging.selectPackagingChildrenStoredArchived(pack.getParentPackId());
			pack = MySQL_Packaging.selectPackaging(pack.getParentPackId());
		}
		
		packs = new Packaging[1 + sub_packs.length];
		packs[0] = pack;
		for(int i=0; i<sub_packs.length; i++) {
			packs[i + 1] = sub_packs[i];
		}

		packs = mergePackagings(packs);
		for(Packaging packaging: packs) {
			MySQL_Packaging.updatePackagingQuantity(packaging);
			MySQL_Packaging.updatePackagingProductCondition(packaging);
		}
		
		displayInventoryProducts();
	}
	
	public abstract void onAddToCart(Product product);
	
	private class AddToCartButton extends Button{
		private static final long serialVersionUID = 5389348761948869122L;

		public AddToCartButton() {
			super("+ Add to Cart");
			setArc(20);
		}
		@Override
		public void setBounds(int x, int y, int width, int height) {
			super.setBounds(x, y + 5, width, height - 10);
		}
		@Override
		public void onAction() {
			Product product = ((TableProductRow)getParent()).getProduct();
			
			int qty = product.getPackaging().getQty().getQuantity();
			if(qty == 0) {
				Toolkit.getDefaultToolkit().beep();
				ABM_Pharma.getWindow().getDisplayPanel().floatMessage("Item out of stock.");
			}
			else {
				ABM_Pharma.getWindow().getStacksPanel().pushPanel(new AddToCartSubPanelQty(product), shadow);
			}
		}
		
		private class AddToCartSubPanelQty extends SubPanelQty{
			private static final long serialVersionUID = 4404997689749490601L;
			
			public AddToCartSubPanelQty(Product product) {
				super(product);
			}
			@Override
			public void onProductQtyOk(Packaging newPackaging,Packaging bypackagings[]) {
				MySQL_Packaging.updateByPackagingQuantities(bypackagings);
				
				Product product = getProduct();
				product.setPackaging(newPackaging);
				
				UomType
				unitType1 = newPackaging.getUom().getUnitType(),
				unitType2;
				
				int 
				price_id = -1,
				parentPack_id = -1;
				
				for(Packaging pack: bypackagings) {
					unitType2 = pack.getUom().getUnitType();
					
					if(unitType1 == unitType2) {
						price_id = MySQL_Inventory.selectProductPriceIdByPackId(pack.getPackId());
						parentPack_id = pack.getPackId();
					}
				}
				
				Pricing newPricing = MySQL_Pricing.selectPricing(price_id);
				product.setPricing(newPricing);
				product.getPackaging().setParentPackId(parentPack_id);
				
				onAddToCart(product);
				
				ABM_Pharma.getWindow().getStacksPanel().popPanel();
				displayInventoryProducts();
			}
		}
	}
}
