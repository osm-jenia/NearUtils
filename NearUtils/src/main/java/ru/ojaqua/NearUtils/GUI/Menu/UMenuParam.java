package ru.ojaqua.NearUtils.GUI.Menu;

import java.util.Collections;
import java.util.List;

import javax.swing.SwingConstants;

public class UMenuParam {

	// Выравнивание строк в меню
	// Констатнта из SwingConstants
	final private int alignment;
	// Пункты меню
	final private List<UMenuItemParam> menuItems;

	public UMenuParam(List<UMenuItemParam> menuItems) {
		super();
		this.alignment = SwingConstants.CENTER;
		this.menuItems = menuItems;
	}

	public UMenuParam(int alignment, List<UMenuItemParam> menuItems) {
		super();
		this.alignment = alignment;
		this.menuItems = menuItems;
	}

	public int getAlignment() {
		return alignment;
	}

	public List<UMenuItemParam> getMenuItems() {
		return Collections.unmodifiableList(menuItems);
	}

}
