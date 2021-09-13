package ru.ojaqua.NearUtils.GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

import ru.ojaqua.NearUtils.Common.UError;

public class UMenu {
	private List<JButton> buttons = new ArrayList<>();
	private int currentFocus = 0;
	private JFrame frame;

	private void exceptionHundler( Exception ex ) {
		
		System.err.println(java.time.LocalDateTime.now());
		
		if( ex instanceof UError ) {
			System.err.println(((UError)ex).getAddInfoForTrace());
		}
		
		ex.printStackTrace();
		
		JOptionPane.showMessageDialog( null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE );
	}
	
	public UMenu(List<UMenuItemParam> menuItems, String nameProgram) {
		frame = new JFrame(nameProgram);
		frame.setLayout(new GridLayout(0, 1));
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		frame.setUndecorated(true);

		for (UMenuItemParam item : menuItems) {

			JButton button = new JButton(item.getItemName());
			button.setBorderPainted(false);
			button.setFont(new Font("DialogInput", Font.BOLD, 15));
			frame.add(button);
			buttons.add(button);

			button.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					frame.setVisible(false);
					if (item.getType() == UMenuItemType.Executer) {

						try {
							
							item.getExecuter().run();
							
						} catch (Exception ex) {
							exceptionHundler( ex );
						}

						frame.setVisible(false);
					} else if (item.getType() == UMenuItemType.SubMenu) {
						UMenu menu = new UMenu(item.getSubmenu(), nameProgram);
						menu.show();
					}
				}

			});

			button.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent ke) {
					int key = ke.getKeyCode();
					if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_UP) {
						currentFocus--;
						if (currentFocus < 0)
							currentFocus = buttons.size() - 1;

						buttons.get(currentFocus).requestFocus();
					}
					if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_DOWN) {
						currentFocus++;
						if (currentFocus >= buttons.size())
							currentFocus = 0;

						buttons.get(currentFocus).requestFocus();
					}
					if (key == KeyEvent.VK_PAGE_UP) {
						currentFocus = 0;
						buttons.get(currentFocus).requestFocus();
					}
					if (key == KeyEvent.VK_PAGE_DOWN) {
						currentFocus = buttons.size() - 1;
						buttons.get(currentFocus).requestFocus();
					}
					if (key == KeyEvent.VK_ENTER) {
						frame.setVisible(false);
						if (item.getType() == UMenuItemType.Executer) {

							try {
								
								item.getExecuter().run();
								
							} catch (Exception ex) {
								exceptionHundler( ex );
							}

							frame.setVisible(false);
						} else if (item.getType() == UMenuItemType.SubMenu) {
							UMenu menu = new UMenu(item.getSubmenu(), nameProgram);
							menu.show();
						}

					}
					if (key == KeyEvent.VK_ESCAPE) {
						frame.setVisible(false);
					}
				}
			});
		}

		int sizeWidth = 400;
		int sizeHeight = menuItems.size() * 50;
		frame.setSize(sizeWidth, sizeHeight);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = (screenSize.width - sizeWidth) / 2;
		int locationY = (screenSize.height - sizeHeight) / 2;
		frame.setBounds(locationX, locationY, sizeWidth, sizeHeight);

		buttons.get(0).requestFocusInWindow();
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}
}
