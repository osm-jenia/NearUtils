package ru.ojaqua.NearUtils.GUI.Menu;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.swing.SwingConstants;

import org.springframework.stereotype.Component;

import ru.ojaqua.NearUtils.NearUtilsApp;
import ru.ojaqua.NearUtils.Common.ClipboardWorker;
import ru.ojaqua.NearUtils.Handlers.TmplStringHandler.TmplStringItemHandler;
import ru.ojaqua.NearUtils.Param.NearUtilsParam;

@Component
public class TmplStringMenu extends Menu {

	private final NearUtilsParam params;
	private final ClipboardWorker clipboard;

	public TmplStringMenu(NearUtilsParam params, ClipboardWorker clipboard) {
		super();
		this.params = params;
		this.clipboard = clipboard;
	}

	@PostConstruct
	private void init() {

		List<MenuItemParam> tmplStringPrmList = params.getStringHandlerPrm().getTmplStringList().stream()
				.map(str -> MenuItemParam.crExecuter(str, new TmplStringItemHandler(clipboard, str))).collect(Collectors.toList());

		init(new MenuParam(SwingConstants.LEFT, tmplStringPrmList), NearUtilsApp.nameProgram);

	}
}
