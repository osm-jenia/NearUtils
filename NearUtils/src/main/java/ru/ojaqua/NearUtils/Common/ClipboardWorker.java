package ru.ojaqua.NearUtils.Common;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

import org.springframework.stereotype.Component;

@Component
public class ClipboardWorker implements IClipboardWorker {

	public void setText(String text) {

		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	public String getText() {
		try {

			return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);

		} catch (Exception e) {
			throw new UError("Ошибка при получении текстовых данных из буфера обмена", e);
		}
	}

}
