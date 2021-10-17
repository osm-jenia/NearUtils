package ru.ojaqua.NearUtils.Handlers.SCRGetterHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import ru.ojaqua.NearUtils.Common.ClipboardWorker;
import ru.ojaqua.NearUtils.Handlers.Handler;

@Component
public class SCRGetterHandler implements Handler {

	ClipboardWorker clipboard;

	public SCRGetterHandler(ClipboardWorker clipboard) {

		this.clipboard = clipboard;

	}

	@Override
	public String getName() {
		return "Получить номера запросов";
	}

	@Override
	public void run() {

		String inputString = clipboard.getText();

		if (inputString == null) {
			clipboard.setText("");
		} else {
			ArrayList<String> queries = new ArrayList<>();

			String pattern = "(\\D\\d{6}\\D)|(^\\d{6}\\D)|(\\D\\d{6}$)|(^\\d{6}$)";

			Pattern ptrn = Pattern.compile(pattern);
			Matcher matcher = ptrn.matcher(inputString);

			while (matcher.find()) {

				String res = matcher.group();
				if (res.length() == 8)
					queries.add(res.substring(1, 7));
				else if (res.matches("^\\d{6}\\D"))
					queries.add(res.substring(0, 6));
				else if (res.matches("^\\D\\d{6}"))
					queries.add(res.substring(1));
				else
					queries.add(res);
			}

			Collections.sort(queries);
			clipboard.setText(String.join(", ", queries));
		}

	}

}
