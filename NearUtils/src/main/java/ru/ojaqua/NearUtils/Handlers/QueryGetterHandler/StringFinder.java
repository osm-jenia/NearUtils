package ru.ojaqua.NearUtils.Handlers.QueryGetterHandler;

/**
 * Класс для поиска подстроки в строке с сохранением текущей позиции
 * 
 * @author Aqua
 *
 */
class StringFinder {

	// Неопределенная позиция
	static final int UNDEF_POS = -1;

	// Текущая позиция подстроки inStr в строке string
	private int curPosition;
	// Строка в которой ищем
	private final char[] string;
	// Строку, которую ищем
	private final char[] inStr;

	public StringFinder(String string, String inStr) {
		this.curPosition = -1;
		this.string = string.toCharArray();
		this.inStr = inStr.toCharArray();

	}

	/**
	 * Найти слдующее вхождение подстроки в исходную строку При вызове устанавливает
	 * позицию curPosition в положение, где начинается подстрока inStr
	 * 
	 * @return true - нашли, позиция перемещается, false - не нашли, позиция не
	 *         изменяет значение
	 */
	public boolean find() {

		for (int i = curPosition + 1; i < string.length; ++i) {

			boolean isFound = true;
			for (int j = 0; j < inStr.length; ++j) {

				if (i + j >= string.length || inStr[j] != string[i + j])
					isFound = false;
			}

			if (isFound) {
				curPosition = i;
				return true;
			}
		}

		return false;
	}

	/**
	 * Переместить позицию на последнее вхождение подстроки inStr
	 * 
	 * @return true - нашли усешно, false - такой подстроки нет
	 */
//	public boolean findLast() {
//		while (find())
//			;
//
//		return curPosition != -1;
//	}

	public int getCurPosition() {
		return curPosition;
	}

}
