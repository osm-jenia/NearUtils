package ru.ojaqua.NearUtils.GUI.Menu;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.ojaqua.NearUtils.NearUtilsApp;
import ru.ojaqua.NearUtils.Handlers.IHandler;

@Component
public class UMainMenu extends UMenu {

	@Autowired
	private List<IHandler> handlers;

	public UMainMenu(List<IHandler> handlers) {
		super();
	}

	@PostConstruct
	private void init() {

		List<UMenuItemParam> prmList = handlers.stream().map(hndl -> UMenuItemParam.crExecuter(hndl.getName(), hndl)).collect(Collectors.toList());

		init(new UMenuParam(prmList), NearUtilsApp.nameProgram);

	}

}
