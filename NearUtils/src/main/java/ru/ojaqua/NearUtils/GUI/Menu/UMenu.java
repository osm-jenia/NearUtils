package ru.ojaqua.NearUtils.GUI.Menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import ru.ojaqua.NearUtils.Common.ExceptionHandler;

public class UMenu {
	private List<JButton> buttons = new ArrayList<>();
	private int currentFocus = 0;
	private JFrame frame;

	protected UMenu() {

	}

	public void init(UMenuParam menuParam, String nameProgram) {
		List<UMenuItemParam> menuItems = menuParam.getMenuItems();
		frame = new JFrame(nameProgram);
		frame.setLayout(new GridLayout(0, 1));
		// frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		frame.setUndecorated(true);

		int curWidth = 0;
		int curHeight = 0;

		for (UMenuItemParam item : menuItems) {

			JButton button = new JButton();
			button.setBorderPainted(false);

			MenuItemCalculator itemCalculator = new MenuItemCalculator(item.getItemName(), button);

			button.setText(itemCalculator.getStringForMenu());

			curWidth = Integer.max(itemCalculator.getWidth(), curWidth);
			curHeight = Integer.max(itemCalculator.getHigh(), curHeight);

			frame.add(button);
			buttons.add(button);
			button.setHorizontalAlignment(menuParam.getAlignment());

			// button.setFocusPainted(false);

			button.addFocusListener(new FocusListener() {

				Color oldFColor = button.getForeground();
				Color oldBColor = button.getBackground();

				public void focusGained(FocusEvent e) {
					button.setBackground(Color.GRAY);
					button.setForeground(Color.WHITE);
				}

				public void focusLost(FocusEvent e) {
					button.setBackground(oldBColor);
					button.setForeground(oldFColor);
				}
			});

			button.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					frame.setVisible(false);
					if (item.getType() == UMenuItemType.Executer) {

						try {

							item.getExecuter().run();

						} catch (Exception ex) {
							ExceptionHandler.process(ex);
						}

						frame.setVisible(false);
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
								ExceptionHandler.process(ex);
							}

							frame.setVisible(false);
						}

					}
					if (key == KeyEvent.VK_ESCAPE) {
						frame.setVisible(false);
					}
				}
			});
		}

		int sizeWidth = curWidth + 20 * 2;// 400;
		int sizeHeight = menuItems.size() * (curHeight + 15 * 2);
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
