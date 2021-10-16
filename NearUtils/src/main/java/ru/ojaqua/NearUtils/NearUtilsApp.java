package ru.ojaqua.NearUtils;

import java.awt.SystemTray;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.melloware.jintellitype.JIntellitype;

import ru.ojaqua.NearUtils.Common.ExceptionHandler;
import ru.ojaqua.NearUtils.Common.UError;
import ru.ojaqua.NearUtils.GUI.Menu.UMainMenu;

/**
 * Hello world!
 *
 */
@ComponentScan("ru.ojaqua.NearUtils")
public class NearUtilsApp {
	private static final int SHIFT_ALT_3 = 88;
	public static final String nameProgram = "Near utils";

	static void prepareProgram() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			throw new UError("Ошибка при инициализации приложения", e);
		}

		// first check to see if an instance of this application is already
		// running, use the name of the window title of this JFrame for checking
		if (JIntellitype.checkInstanceAlreadyRunning(nameProgram)) {
			System.exit(1);
		}

		// next check to make sure JIntellitype DLL can be found and we are on
		// a Windows operating System
		if (!JIntellitype.isJIntellitypeSupported()) {
			throw new UError("Пробемы при инициализации глобальной клавиши");
		}

		// Check the SystemTray support
		if (!SystemTray.isSupported()) {
			throw new UError("Системный трей не поддерживается");
		}

	}

	public static void main(String[] args) {
		try {
			prepareProgram();
		} catch (Exception e) {
			ExceptionHandler.process(e);
			return;
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			private ApplicationContext ctx;

			public void run() {

				try {
					ctx = new AnnotationConfigApplicationContext(NearUtilsApp.class);
					UMainMenu mainMenu = ctx.getBean(UMainMenu.class);

					JIntellitype.getInstance().registerHotKey(SHIFT_ALT_3, JIntellitype.MOD_ALT + JIntellitype.MOD_SHIFT, '3');
					JIntellitype.getInstance().addHotKeyListener(aIdentifier -> {
						if (aIdentifier == SHIFT_ALT_3)
							mainMenu.show();

					});
				} catch (Exception e) {
					ExceptionHandler.process(e);
				}

			}
		});
	}

}
