package ru.ojaqua.NearUtils.Common;

import javax.swing.JOptionPane;

public class ExceptionHandler {

	public static void process(Exception ex) {

		System.err.println(java.time.LocalDateTime.now());

		if (ex instanceof NearUtilsError) {
			System.err.println(((NearUtilsError) ex).getAddInfoForTrace());
		}

		ex.printStackTrace();

		JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
	}
}
