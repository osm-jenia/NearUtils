package ru.ojaqua.NearUtils.Handlers;

public class StringFinder {
	private int curPosition;
	private char[] string;
	private char[] inStr;

	public StringFinder(String string, String inStr) {
		this.curPosition = -1;
		this.string = string.toCharArray();
		this.inStr = inStr.toCharArray();

	}

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

		curPosition = string.length;

		return false;
	}

	public int getCurPosition() {
		return curPosition;
	}

}
