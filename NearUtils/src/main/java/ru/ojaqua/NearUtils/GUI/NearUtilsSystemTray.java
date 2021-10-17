package ru.ojaqua.NearUtils.GUI;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

import ru.ojaqua.NearUtils.Common.NearUtilsError;
import ru.ojaqua.NearUtils.GUI.Menu.MainMenu;

@Component
public class NearUtilsSystemTray {

	final private MainMenu mainMenu;
	final private SystemTray tray;

	public NearUtilsSystemTray(SystemTray tray, MainMenu mainMenu) {
		this.mainMenu = mainMenu;
		this.tray = tray;
	}

	@PostConstruct
	void init() {
		TrayIcon trayIcon = new TrayIcon(createImage("/nearUtilsTray.png", "NearUtils"));

		trayIcon.setImageAutoSize(true);

		MenuItem aboutItem = new MenuItem("About");
		MenuItem exitItem = new MenuItem("Exit");

		final PopupMenu popup = new PopupMenu();
		popup.add(aboutItem);
		popup.add(exitItem);

		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Программа для вызова подручных действий\nАвтор: Осмаковский");
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				System.exit(0);
			}
		});

		trayIcon.setPopupMenu(popup);

		trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainMenu.show();
			}
		});

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			throw new NearUtilsError("Возникли проблемы при добавлении иконки в трей", e);
		}

	}

	protected Image createImage(String path, String description) {
		URL imageURL = NearUtilsSystemTray.class.getResource(path);

		if (imageURL == null) {
			throw new NearUtilsError("Не найден ресурс: " + path);
		}

		return new ImageIcon(imageURL, description).getImage();
	}

}
