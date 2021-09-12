package ru.ojaqua.NearUtils.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

public class UErrorMessageDialog {
	private JFrame frame;

	public UErrorMessageDialog( Exception ex ) {
		frame = new JFrame("Ошибка");
		frame.getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG);
		frame.setLayout(new FlowLayout());
		
		final String html = "<html><body style='width: %1spx'>%1s";
		JLabel message = new JLabel(String.format(html, 200, ex.getMessage()));
		frame.add(message);
		
		JButton buttonOk = new JButton("Ok");
		frame.add(buttonOk);
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});

		
		JButton buttonStack = new JButton("Стек");
		frame.add(buttonStack);
		
		int sizeWidth = 400;
		int sizeHeight = 300;
		frame.setSize(sizeWidth, sizeHeight);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = (screenSize.width - sizeWidth) / 2;
		int locationY = (screenSize.height - sizeHeight) / 2;
		frame.setBounds(locationX, locationY, sizeWidth, sizeHeight);
		
	}
	
	public void show() {
		frame.setVisible(true);
	} 

}
