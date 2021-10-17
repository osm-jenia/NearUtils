package ru.ojaqua.NearUtils.Handlers.QueryGetterHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import ru.ojaqua.NearUtils.Common.ClipboardWorker;
import ru.ojaqua.NearUtils.Common.NearUtilsError;
import ru.ojaqua.NearUtils.Handlers.Handler;

@Component
public class QueryGetterHandler implements Handler {

	ClipboardWorker clipboard;

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
		COLON, // Двоеточие
		COLON_VAR // Параметр с двоеточием
	};

	/**
	 * виды подстоновок в запрос
	 * 
	 * @author Aqua
	 *
	 */
	enum VarTypeQuery {
		UNDEF, // неопределено
		COLON_WORD, // параметр с двоеточием
		QUESTION_MARK // вопросительный знак
	};

	static final String varPatternStr = "#\\d+, type RSD\\w+, value: \\S*\\s";

	static final String endQueryPatternStr = "^Result=\\s\\d+\\s{5}.*";
	static final String threeSpacePatternStr = "(\\s{3})|$";

	public QueryGetterHandler(ClipboardWorker clipboard) {
		this.clipboard = clipboard;
	}

	@Override
	public String getName() {
		return "Получить запрос из трассы";
	}

	@Override
	public void run() {
		String inputString = clipboard.getText();

		if (inputString == null) {
			clipboard.setText("");
			return;
		}

		// Разбиваем входящую строку на массив строк
		Pattern linesPattern = Pattern.compile("\\R");
		String[] lines = linesPattern.split(inputString);

		List<String> vars = new ArrayList<>();
		int startPositionInLine = StringFinder.UNDEF_POS;
		int endPositionInLine = StringFinder.UNDEF_POS;
		boolean isVarFinished = false;
		boolean isQueryFinished = false;
		StringBuilder query = new StringBuilder();

		for (int i = 0; i < lines.length && !isQueryFinished; i++) {
			var line = lines[i];

			//
			// Вычитываем значения переменных запроса
			//
			if (!isVarFinished && addVarIfExists(vars, line)) {

				if (startPositionInLine == StringFinder.UNDEF_POS)
					startPositionInLine = line.indexOf("#1, type RSD");

			}
			// Если уже была первая переменная, но в текущей линии больше переменных не
			// нашли
			// Это значит, что переменные закончились и теперь читаем запрос
			else if (startPositionInLine != StringFinder.UNDEF_POS && !isQueryFinished) {

				if (!isVarFinished)
					isVarFinished = true;

				if (!isQueryFinished && line.substring(startPositionInLine).matches(endQueryPatternStr))
					isQueryFinished = true;

				if (!isQueryFinished) {

					//
					// Если не определена позиция окончания строки запроса, то находим ee
					//
					if (endPositionInLine == StringFinder.UNDEF_POS) {
						// Если первая переменная оказалась в позиции 0, то считаем что в буфере
						// окончание строки также является окончанием запроса
						// Такой алгоритм нужен, если неверно отрабатывает более сложный - по трем
						// пробелаам, а такие варианты теоретически возможны, так как запрос может
						// состоять из двух строк при этом в первой строке будет масса мест с тремя
						// пробелами,тогда мы скорее всего неверно рассчитаем
						if (startPositionInLine == 0)
							endPositionInLine = line.length();
						else
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

		clipboard.setText(queryWithValues);
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

		int startLineSpacePosition = StringFinder.UNDEF_POS;
		int endPositionInLine = StringFinder.UNDEF_POS;

		StringFinder strFinder = new StringFinder(startQueryLine.substring(startPositionInLine), "   ");
		// Если количество на запрос строк больше 1, то длинну определяем по пробельным
		// символам на каждой строке
		if (lines.length > startQueryLineNumber + 1) {

			nextSpacePlace: while (endPositionInLine == StringFinder.UNDEF_POS) {

				if (strFinder.find())
					startLineSpacePosition = startPositionInLine + strFinder.getCurPosition();
				else
					startLineSpacePosition = StringFinder.UNDEF_POS;

				if (startLineSpacePosition > 0 && startLineSpacePosition < startQueryLine.length()) {
					for (int j = startQueryLineNumber; j < lines.length && endPositionInLine == StringFinder.UNDEF_POS; j++) {

						// Если дошли до Result, то приехали в конец
						if (lines[j].substring(startPositionInLine).matches(endQueryPatternStr)) {

							if (startQueryLineNumber == j) {
								throw new NearUtilsError("Неожиданный Result. Не нашел текст запроса");
//							}else if (startQueryLineNumber + 3 >= j) { // Это ситуация неопределенности, так как позиция правого края запроса у нас не
//																		// подтвердилась переносом строки
//								if (strFinder.findLast()) // В этой ситуации вычисляем самый правый тройной пробел
//									endPositionInLine = startPositionInLine + strFinder.getCurPosition();
//								else// Если его нет, то берем максимальную длинну
//									endPositionInLine = startQueryLine.length();
							} else
								endPositionInLine = startLineSpacePosition;
							break nextSpacePlace;

//						} else if (j == lines.length - 1 && startQueryLineNumber + 2 >= j) { // Если мы находимся на полседней строке и при этом
//																								// запрос вместился в две строки, то это ситуация
//																								// неопределенности, так как длина не подтвердилась
//																								// переносом строки
//
//							if (strFinder.findLast()) // В этой ситуации вычисляем самый правый тройной пробел
//								endPositionInLine = startPositionInLine + strFinder.getCurPosition();
//							else// Если его нет, то берем максимальную длинну
//								endPositionInLine = startQueryLine.length();
						} else if (j == lines.length - 1) { // Если мы находимся на последней строке
							endPositionInLine = startLineSpacePosition;
							break nextSpacePlace;
							// Если длинна текущей строки меньше, чем позиция трех пробелов, то считаем, что
							// все норм
							// Следующий должен быть Result по идее
							// Если длинна текущей строки равна, то норм
						} else if (j == startQueryLineNumber || lines[j].length() <= startLineSpacePosition) {

							continue;

						} else
							continue nextSpacePlace;
					}
				}
				// Не нашли пробелов в строке, значит запрос идет до конца строки
				else {
					endPositionInLine = startQueryLine.length();
				}
			}
		} else { // Одна строка осталась в массиве, значит ее длинну и берем
//			if (strFinder.findLast())
//				endPositionInLine = startPositionInLine + strFinder.getCurPosition();
//			else
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

		VarTypeQuery varTypeQuery = VarTypeQuery.UNDEF;
		State state = State.QUERY;
		StringBuilder queryWithVar = new StringBuilder();
		int i = 0;
		for (var c : query.toCharArray()) {
			switch (state) {
			case QUERY -> {
				if (c == '\'') {
					state = State.STRING;
					queryWithVar.append(c);
				} else if (c == '?' && (varTypeQuery.equals(VarTypeQuery.UNDEF) || varTypeQuery.equals(VarTypeQuery.QUESTION_MARK))) {
					if (vars.size() <= i)
						throw new NearUtilsError(
								"Количество переменых в массиве не соответствует количеству в запросе, в запросе мест для подстановок больше",
								"Количество элементов в массиве: " + vars.size() + "\n" + "Запрос без параметров: " + query + "\n"
										+ "Запрос с подстановкой: " + queryWithVar.toString() + "\n" + "Массив значаений: " + vars.toString() + "\n"
										+ "Тип строки подстоновки: " + varTypeQuery.toString());

					queryWithVar.append(getValueStr(vars.get(i)));
					++i;
					varTypeQuery = VarTypeQuery.QUESTION_MARK;
				} else if (c == ':' && (varTypeQuery.equals(VarTypeQuery.UNDEF) || varTypeQuery.equals(VarTypeQuery.COLON_WORD))) {
					state = State.COLON;
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
			case COLON -> {
				if (c == '=') {
					state = State.QUERY;
					queryWithVar.append(":=");
				} else if (Character.isLetterOrDigit(c) || c == '_') {
					state = State.COLON_VAR;
					if (vars.size() <= i)
						throw new NearUtilsError(
								"Количество переменых в массиве не соответствует количеству в запросе, в запросе мест для подстановок больше",
								"Количество элементов в массиве: " + vars.size() + "\n" + "Запрос без параметров: " + query + "\n"
										+ "Запрос с подстановкой: " + queryWithVar.toString() + "\n" + "Массив значаений: " + vars.toString() + "\n"
										+ "Тип строки подстоновки: " + varTypeQuery.toString());

					queryWithVar.append(getValueStr(vars.get(i)));
					++i;
					varTypeQuery = VarTypeQuery.COLON_WORD;
				}
			}
			case COLON_VAR -> {
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
			case "RSDDATE" -> "to_date('" + (value.substring(7).equals("0.0.0") ? "1.1.1" : value.substring(7)) + "', 'dd.mm.yyyy')";
			case "RSDTIME" -> "to_date('01.01.0001 " + value.substring(7) + "', 'DD.MM.YYYY HH24:MI:SS')";
			default -> value.substring(7);
			};
		}

		return resultValue;
	}

}
