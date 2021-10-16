package ru.ojaqua.NearUtils.Handlers;

/**
 * Общий интерфейс для всех обработчиков
 *
 */
public interface IHandler extends Runnable {

	/**
	 * Запустить на выполнение
	 */
	void run();

	/**
	 * Получить имя обработчика
	 * 
	 * @return имя обработчика
	 */
	String getName();

}
