package ru.ojaqua.NearUtils.GUI;

import java.util.List;

public class UMenuItemParam {
	UMenuItemType type;
	private String itemName;
	private List<UMenuItemParam> submenu;
	private Runnable executer;

	public static UMenuItemParam crSeparator() {
		UMenuItemParam item = new UMenuItemParam();

		item.type = UMenuItemType.Separator;

		return item;
	}

	public static UMenuItemParam crSubMenu(String itemName, List<UMenuItemParam> submenu) {
		UMenuItemParam item = new UMenuItemParam();

		item.type = UMenuItemType.SubMenu;
		item.itemName = itemName;
		item.submenu = submenu;

		return item;
	}

	public static UMenuItemParam crExecuter(String itemName, Runnable executer) {
		UMenuItemParam item = new UMenuItemParam();

		item.type = UMenuItemType.Executer;
		item.itemName = itemName;
		item.executer = executer;

		return item;
	}

	private UMenuItemParam() {
	}

	public UMenuItemType getType() {
		return type;
	}

	public String getItemName() {
		return itemName;
	}

	public List<UMenuItemParam> getSubmenu() {
		return submenu;
	}

	public Runnable getExecuter() {
		return executer;
	}

}
