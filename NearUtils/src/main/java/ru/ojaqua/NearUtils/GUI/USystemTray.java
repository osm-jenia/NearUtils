package ru.ojaqua.NearUtils.GUI;

import java.awt.Image;
import java.net.URL;
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class USystemTray {
	
	final private TrayIcon trayIcon;
//	final private SystemTray tray;
//	final private UMenu mainMenu;

	public USystemTray(SystemTray tray, UMenu mainMenu) {
		trayIcon = new TrayIcon(createImage("/nearUtilsTray.png", "NearUtils"));
		
		trayIcon.setImageAutoSize(true);
		
//		this.tray = tray;
//		this.mainMenu = mainMenu; 
		
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
			System.out.println("TrayIcon could not be added.");
			return;
		}
	}
	
	protected Image createImage(String path, String description) {
		URL imageURL = USystemTray.class.getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			return (new ImageIcon(imageURL, description)).getImage();
		}
	}

//	public TrayIcon getTrayIcon() {
//		return trayIcon;
//	}
	
	
}
