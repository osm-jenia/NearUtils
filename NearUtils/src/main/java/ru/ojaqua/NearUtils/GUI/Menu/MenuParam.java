package ru.ojaqua.NearUtils.GUI.Menu;

import java.util.Collections;
import java.util.List;

import javax.swing.SwingConstants;

public class MenuParam {

	// Выравнивание строк в меню
	// Констатнта из SwingConstants
	final private int alignment;
	// Пункты меню
	final private List<MenuItemParam> menuItems;

	public MenuParam(List<MenuItemParam> menuItems) {
		super();
		this.alignment = SwingConstants.CENTER;
		this.menuItems = menuItems;
	}

	public MenuParam(int alignment, List<MenuItemParam> menuItems) {
		super();
		this.alignment = alignment;
		this.menuItems = menuItems;
	}

	public int getAlignment() {
		return alignment;
	}

	public List<MenuItemParam> getMenuItems() {
		return Collections.unmodifiableList(menuItems);
	}

}
