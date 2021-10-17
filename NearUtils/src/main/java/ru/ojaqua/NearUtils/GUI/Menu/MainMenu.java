package ru.ojaqua.NearUtils.GUI.Menu;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.ojaqua.NearUtils.NearUtilsApp;
import ru.ojaqua.NearUtils.Handlers.Handler;

@Component
public class MainMenu extends Menu {

	@Autowired
	private List<Handler> handlers;

	public MainMenu(List<Handler> handlers) {
		super();
	}

	@PostConstruct
	private void init() {

		List<MenuItemParam> prmList = handlers.stream().map(hndl -> MenuItemParam.crExecuter(hndl.getName(), hndl)).collect(Collectors.toList());

		init(new MenuParam(prmList), NearUtilsApp.nameProgram);

	}

}
