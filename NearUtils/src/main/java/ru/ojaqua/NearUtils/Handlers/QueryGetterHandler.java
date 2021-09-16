package ru.ojaqua.NearUtils.Handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.ojaqua.NearUtils.Common.UError;

public class QueryGetterHandler implements Runnable {

	/**
	 * состояния конечного автомата, который используется при подстановке значений в
	 * запрос
	 * 
	 * @author Aqua
	 *
	 */
	enum State {
		QUERY, // Запрос
		STRING, // Строка запроса
		VAR // Переменная
	};

	Supplier<String> supplier;
	Consumer<String> consumer;
	static final int UNDEF_POS = -1;

	static final String varPatternStr = "#\\d+, type RSD(SHORT|CHAR|LONG|LPSTR|DATE|TIME|PT_NUMERIC), value: \\S*\\s";
	static final String endQueryPatternStr = "^Result=\\s\\d+\\s{5}.*";
	static final String threeSpacePatternStr = "(\\s{3})|$";

	public QueryGetterHandler(Supplier<String> supplier, Consumer<String> consumer) {
		this.supplier = supplier;
		this.consumer = consumer;
	}

	@Override
	public void run() {
		String inputString = supplier.get();

		if (inputString == null) {
			consumer.accept("");
			return;
		}

		// Разбиваем входящую строку на массив строк
		Pattern linesPattern = Pattern.compile("\\R");
		String[] lines = linesPattern.split(inputString);

		List<String> vars = new ArrayList<>();
		int startPositionInLine = UNDEF_POS;
		int endPositionInLine = UNDEF_POS;
		boolean isVarFinished = false;
		boolean isQueryFinished = false;
		StringBuilder query = new StringBuilder();

		for (int i = 0; i < lines.length && !isQueryFinished; i++) {
			var line = lines[i];

			//
			// Вычитываем значения переменных запроса
			//
			if (!isVarFinished && addVarIfExists(vars, line)) {

				if (startPositionInLine == UNDEF_POS)
					startPositionInLine = line.indexOf("#1, type RSD");

			}
			// Если уже была первая переменная, но в текущей линии больше переменных не
			// нашли
			// Это значит, что переменные закончились и теперь читаем запрос
			else if (startPositionInLine != UNDEF_POS && !isQueryFinished) {

				if (!isVarFinished)
					isVarFinished = true;

				if (!isQueryFinished && line.substring(startPositionInLine).matches(endQueryPatternStr))
					isQueryFinished = true;

				if (!isQueryFinished) {

					//
					// Если не определена позиция окончания строки запроса, то находим ee
					//
					if (endPositionInLine == UNDEF_POS) {
						endPositionInLine = getEndPositionInLine(lines, i, startPositionInLine);
					}

					if (!isQueryFinished) {
						if (line.length() > endPositionInLine)
							query.append(line.substring(startPositionInLine, endPositionInLine));
						else
							query.append(line.substring(startPositionInLine));
					}
				}

			}
		}

		//
		// Подставляем значения в запрос
		//
		String queryWithValues = getQueryWithValues(query.toString(), vars);

		consumer.accept(queryWithValues);
	}

	/**
	 * Добавить переменную запроса из ткущей линии
	 * 
	 * @param vars    список переменных
	 * @param curLine текущая строка
	 * @return true если добавили, false нет переменной в текущем запросе
	 */
	private boolean addVarIfExists(List<String> vars, String curLine) {
		Pattern varPattern = Pattern.compile(varPatternStr);
		Matcher varMatcher = varPattern.matcher(curLine);

		if (varMatcher.find()) {

			String varLine = varMatcher.group();
			vars.add(varLine);
			return true;
		}

		return false;
	}

	/**
	 * Получить позицию окончания в строке запроса Вычисляем до Result, либо до
	 * конца массива Общая идея такая: считаем, что позиция вычислена, если справа
	 * идут три пробела до конца, либо встретился конец строки
	 * 
	 * @param lines                массив строк, которые пришли на вход
	 * @param startPositionInLine  начальная позиция запроса в строке трассы
	 * @param startQueryLineNumber текущая строка с которой начинается запрос
	 * @return
	 */
	protected int getEndPositionInLine(String[] lines, int startQueryLineNumber, int startPositionInLine) {

		String startQueryLine = lines[startQueryLineNumber];

		int startLineSpacePosition = UNDEF_POS;
		int endPositionInLine = UNDEF_POS;

		// Если количество на запрос строк больше 1, то длинну определяем по пробельным
		// символам на каждой строке
		if (lines.length > startQueryLineNumber + 1) {

			StringFinder strFinder = new StringFinder(startQueryLine.substring(startPositionInLine), "   ");
			//Pattern startLineSpacePattern = Pattern.compile(threeSpacePatternStr);
			//Matcher startLineSpaceMatcher  = startLineSpacePattern.matcher(startQueryLine.substring(startPositionInLine));

			nextSpacePlace: while ( endPositionInLine == UNDEF_POS ) {

				if(strFinder.find())
				  startLineSpacePosition = startPositionInLine + strFinder.getCurPosition();
				else
				  startLineSpacePosition = UNDEF_POS; 

				if (startLineSpacePosition > 0 && startLineSpacePosition < startQueryLine.length()) {
					for (int j = startQueryLineNumber; j < lines.length && endPositionInLine == UNDEF_POS; j++) {

						
						// Если дошли до Result, то приехали в конец
						if (lines[j].substring(startPositionInLine).matches(endQueryPatternStr)) {

							if (startQueryLineNumber == j)
								throw new UError("Неожиданный Result. Не нашел текст запроса");

							endPositionInLine = startLineSpacePosition;
							break nextSpacePlace;
						// Если мы находимся на последней строке	
						} else if( j == lines.length - 1) {
							endPositionInLine = startLineSpacePosition;
							break nextSpacePlace;
						// Если длинна текущей строки меньше, чем позиция трех пробелов, то считаем, что все норм
					    // Следующий должен быть Result по идее
						} else if( startLineSpacePosition > lines[j].length() ) {
							
							continue;
						
						// Если на той же позиции нашли три пробела или окончание строки
						// Все норм
						} else if( lines[j].substring(startLineSpacePosition).matches("(^\\s{3}.+$)|$") ) {
							
							continue;
						}
						else
							continue nextSpacePlace;
					}
				}
				// Не нашли пробелов в строке, значит запрос идет до конца строки
				else {
					endPositionInLine = startQueryLine.length();
				}
			}
		} else { // Одна строка осталась в массиве, значит ее длинну и берем
			endPositionInLine = startQueryLine.length();
		}

		return endPositionInLine;
	}

	/**
	 * Получить запрос с подставленными значениями
	 * 
	 * @param query текст параметризованного запроса
	 * @param vars  параметры в формате "из трассы"
	 * @return
	 */
	protected String getQueryWithValues(String query, List<String> vars) {

		State state = State.QUERY;
		StringBuilder queryWithVar = new StringBuilder();
		int i = 0;
		for (var c : query.toCharArray()) {
			switch (state) {
			case QUERY -> {
				if (c == '\'') {
					state = State.STRING;
					queryWithVar.append(c);
				} else if (c == '?') {
					if (vars.size() <= i)
						throw new UError("Количество переменых в массиве " + vars.size() + ", а в запросе уже пытаемся подставить элемент" + i , 
								          "Запрос без параметров: " + query  + "\n" + 
								          "Запрос с подстановкой: " + queryWithVar.toString() + "\n"+
								          "Массив значаений: " + vars.toString()
								        );

					queryWithVar.append(getValueStr(vars.get(i)));
					++i;
				} else if (c == ':') {
					state = State.VAR;

					if (vars.size() <= i)
						throw new UError("Количество переменых в массиве " + vars.size() + ", а в запросе уже пытаемся подставить элемент" + i , 
						          "Запрос без параметров: " + query  + "\n" + 
						          "Запрос с подстановкой: " + queryWithVar.toString() + "\n"+
						          "Массив значаений: " + vars.toString()
						        );

					queryWithVar.append(getValueStr(vars.get(i)));
					++i;
				} else
					queryWithVar.append(c);

			}
			case STRING -> {
				if (c == '\'') {
					state = State.QUERY;
					queryWithVar.append(c);
				} else {
					queryWithVar.append(c);
				}

			}
			case VAR -> {
				if (!Character.isLetterOrDigit(c) && c != '_') {
					state = State.QUERY;
					queryWithVar.append(c);
				}
			}

			}

		}

		return queryWithVar.toString();
	}

	/**
	 * Получить строку параметра, как она пойдет в запрос
	 * 
	 * @param varFromTrace строка из трассы
	 * @return строка для подстановки в запрос
	 */
	protected String getValueStr(String varFromTrace) {

		Pattern typePattern = Pattern.compile("RSD\\w+");
		Pattern valuePattern = Pattern.compile("value: \\S+");

		Matcher typeMatcher = typePattern.matcher(varFromTrace);
		Matcher valueMatcher = valuePattern.matcher(varFromTrace);

		String type = null;
		String value = "";

		if (typeMatcher.find())
			type = typeMatcher.group();

		if (valueMatcher.find())
			value = valueMatcher.group();

		String resultValue = "'????'";

		if (type != null && value != null) {
			resultValue = switch (type) {
			case "RSDSHORT", "RSDLONG", "RSDPT_NUMERIC" -> value.substring(7);
			case "RSDLPSTR" -> "".equals(value) ? "chr(1)" : "'" + value.substring(7) + "'";
			case "RSDCHAR" -> "chr(" + value.substring(7) + ")";
			case "RSDDATE" -> "to_date('" + (value.substring(7).equals("0.0.0") ? "1.1.1" : value.substring(7))
					+ "', 'dd.mm.yyyy')";
			case "RSDTIME" -> "to_date('01.01.0001 " + value.substring(7) + "', 'DD.MM.YYYY HH24:MI:SS')";
			default -> value.substring(7);
			};
		}

		return resultValue;
	}

}
