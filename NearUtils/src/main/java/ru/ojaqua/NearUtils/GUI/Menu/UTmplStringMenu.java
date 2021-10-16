package ru.ojaqua.NearUtils.GUI.Menu;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.swing.SwingConstants;

import org.springframework.stereotype.Component;

import ru.ojaqua.NearUtils.NearUtilsApp;
import ru.ojaqua.NearUtils.Common.IClipboardWorker;
import ru.ojaqua.NearUtils.Handlers.TmplStringHandler.TmplStringItemHandler;
import ru.ojaqua.NearUtils.Param.NearUtilsParam;

@Component
public class UTmplStringMenu extends UMenu {

	private final NearUtilsParam params;
	private final IClipboardWorker clipboard;

	public UTmplStringMenu(NearUtilsParam params, IClipboardWorker clipboard) {
		super();
		this.params = params;
		this.clipboard = clipboard;
	}

	@PostConstruct
	private void init() {

		List<UMenuItemParam> tmplStringPrmList = params.getStringHandlerPrm().getTmplStringList().stream()
				.map(str -> UMenuItemParam.crExecuter(str, new TmplStringItemHandler(clipboard, str))).collect(Collectors.toList());

		init(new UMenuParam(SwingConstants.LEFT, tmplStringPrmList), NearUtilsApp.nameProgram);

	}
}
