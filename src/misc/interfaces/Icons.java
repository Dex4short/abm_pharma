package misc.interfaces;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

public interface Icons {
	public static ImageIcon
	PrinterIcon = getIcon("res/printer.png"),
	SendIcon = getIcon("res/send.png"),
	PencilIcon = getIcon("res/pencil.png"),
	DeleteIcon = getIcon("res/delete.png"),
	AddIcon = getIcon("res/add.png"),
	CheckIcon = getIcon("res/check.png"),
	InventoryIcon = getIcon("res/inventory.png"),
	ReserveIcon = getIcon("res/reserve.png"),
	ReturnIcon = getIcon("res/return.png"),
	RefreshIcon = getIcon("res/refresh.png"),
	FunctionIcon = getIcon("res/function.png");

	public static ImageIcon getIcon(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path)); 
	}
}
