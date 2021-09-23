package ru.ojaqua.NearUtils.Handlers.TemplStringHandler;

import java.util.function.Consumer;

/**
 * Обработчик шаблонов строк По сути ничего со строкой не делает: просто
 * перекладывает из поставщика в потребитель
 * 
 * @author Aqua
 *
 */
public class TemplStringHandler implements Runnable {

	private Consumer<String> consumer;
	private String outString;

	public TemplStringHandler(String outString, Consumer<String> consumer) {
		super();
		this.outString = outString;
		this.consumer = consumer;
	}

	@Override
	public void run() {

		consumer.accept(outString);

	}

}
