package ru.ojaqua.NearUtils.GUI.Menu;

public class UMenuItemParam {
	UMenuItemType type;
	private String itemName;
	private UMenuParam submenu;
	private Runnable executer;

	public static UMenuItemParam crSeparator() {
		UMenuItemParam item = new UMenuItemParam();

		item.type = UMenuItemType.Separator;

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

	public UMenuParam getSubmenu() {
		return submenu;
	}

	public Runnable getExecuter() {
		return executer;
	}

}
