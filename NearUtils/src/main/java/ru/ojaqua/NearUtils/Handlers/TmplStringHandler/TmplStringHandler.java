package ru.ojaqua.NearUtils.Handlers.TmplStringHandler;

import org.springframework.stereotype.Component;

import ru.ojaqua.NearUtils.GUI.Menu.TmplStringMenu;
import ru.ojaqua.NearUtils.Handlers.Handler;

/**
 * Обработчик шаблонов строк
 * 
 * @author Aqua
 *
 */
@Component
public class TmplStringHandler implements Handler {

	private final TmplStringMenu menu;

	public TmplStringHandler(TmplStringMenu menu) {
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
