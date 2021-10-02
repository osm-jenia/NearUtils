package ru.ojaqua.NearUtils.GUI;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;

/**
 * Создает строку, которую будм выводить в меню И рассчитывает необходимые
 * метрики
 * 
 * @author Aqua
 *
 */
class MenuItemCalculator {

	// Количество строк
	static final int MAX_LINE_COUNT = 2;
	// Количество символов по ширине
	static final int MAX_CHAR_COUNT_IN_LINE = 50;

	private final int width;
	private final int high;
	private final String outStr;

	MenuItemCalculator(String str, JButton button) {

		Stream<String> strStream = Arrays.stream(str.split("\n"));

		List<String> strList = strStream.map((p) -> p.length() > MAX_CHAR_COUNT_IN_LINE ? p.substring(0, MAX_CHAR_COUNT_IN_LINE - 3) + "..." : p)
				.limit(MAX_LINE_COUNT + 1).collect(Collectors.toList());

		if (strList.size() == MAX_LINE_COUNT + 1)
			strList.set(MAX_LINE_COUNT, "...");

		this.width = strList.stream().mapToInt((p) -> button.getFontMetrics(button.getFont()).stringWidth(p)).max().orElse(0);

		this.high = button.getFontMetrics(button.getFont()).getHeight() * strList.size();

		if (strList.size() > 1)
			this.outStr = "<html>" + String.join("<br/>", strList) + "</html>";
		else if (strList.size() == 1)
			this.outStr = strList.get(0);
		else
			this.outStr = "";
	}

	String getStringForMenu() {
		return outStr;
	}

	int getWidth() {
		return width;
	}

	int getHigh() {
		return high;
	}
}
