package ru.ojaqua.NearUtils.Handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.ojaqua.NearUtils.Common.UError;

public class SCRGetter implements Runnable {

	Supplier<String> supplier;
	Consumer<String> consumer;

	public SCRGetter(Supplier<String> supplier, Consumer<String> consumer) {
		this.supplier = supplier;
		this.consumer = consumer;
	}

	@Override
	public void run() {
		
		throw new UError("Тестовая ошибка Тестовая ошибка Тестовая ошибка Тестовая ошибка Тестовая ошибка Тестовая ошибка Тестовая ошибка Тестовая ошибка Тестовая ошибка Тестовая ошибка");

//		String inputString = supplier.get();
//
//		if (inputString == null) {
//			consumer.accept("");
//		} else {
//			ArrayList<String> queries = new ArrayList<>();
//
//			String pattern = "(\\D\\d{6}\\D)|(^\\d{6}\\D)|(\\D\\d{6}$)|(^\\d{6}$)";
//
//			Pattern ptrn = Pattern.compile(pattern);
//			Matcher matcher = ptrn.matcher(inputString);
//
//			while (matcher.find()) {
//
//				String res = matcher.group();
//				if (res.length() == 8)
//					queries.add(res.substring(1, 7));
//				else if (res.matches("^\\d{6}\\D"))
//					queries.add(res.substring(0, 6));
//				else if (res.matches("^\\D\\d{6}"))
//					queries.add(res.substring(1));
//				else
//					queries.add(res);
//			}
//			
//			Collections.sort(queries);
//			consumer.accept(String.join(", ", queries));
//		}

	}

}
