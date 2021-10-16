package ru.ojaqua.NearUtils.Handlers.TmplStringHandler;

import org.springframework.stereotype.Component;

import ru.ojaqua.NearUtils.GUI.Menu.UTmplStringMenu;
import ru.ojaqua.NearUtils.Handlers.IHandler;

/**
 * Обработчик шаблонов строк
 * 
 * @author Aqua
 *
 */
@Component
public class TmplStringHandler implements IHandler {

	private final UTmplStringMenu menu;

	public TmplStringHandler(UTmplStringMenu menu) {
		super();
		this.menu = menu;
	}

	@Override
	public String getName() {
		return "Шаблоны строк";
	}

	@Override
	public void run() {

		menu.show();

	}

}
