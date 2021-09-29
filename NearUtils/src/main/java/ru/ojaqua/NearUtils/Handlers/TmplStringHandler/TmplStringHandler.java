package ru.ojaqua.NearUtils.Handlers.TmplStringHandler;

import java.util.function.Consumer;

/**
 * Обработчик шаблонов строк По сути ничего со строкой не делает: просто
 * перекладывает из поставщика в потребитель
 * 
 * @author Aqua
 *
 */
public class TmplStringHandler implements Runnable {

	private Consumer<String> consumer;
	private String outString;

	public TmplStringHandler(String outString, Consumer<String> consumer) {
		super();
		this.outString = outString;
		this.consumer = consumer;
	}

	@Override
	public void run() {

		consumer.accept(outString);

	}

}
