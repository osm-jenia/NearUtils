package ru.ojaqua.NearUtils.GUI.Menu;

public class MenuItemParam {
	MenuItemType type;
	private String itemName;
	private MenuParam submenu;
	private Runnable executer;

	public static MenuItemParam crSeparator() {
		MenuItemParam item = new MenuItemParam();

		item.type = MenuItemType.Separator;

		return item;
	}

	public static MenuItemParam crExecuter(String itemName, Runnable executer) {
		MenuItemParam item = new MenuItemParam();

		item.type = MenuItemType.Executer;
		item.itemName = itemName;
		item.executer = executer;

		return item;
	}

	private MenuItemParam() {
	}

	public MenuItemType getType() {
		return type;
	}

	public String getItemName() {
		return itemName;
	}

	public MenuParam getSubmenu() {
		return submenu;
	}

	public Runnable getExecuter() {
		return executer;
	}

}
