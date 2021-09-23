package ru.ojaqua.NearUtils;

import java.awt.SystemTray;
import java.util.List;
import java.util.stream.Collectors;

//import javax.swing.JOptionPane; 
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.melloware.jintellitype.JIntellitype;

import ru.ojaqua.NearUtils.Common.ClipboardWorker;
import ru.ojaqua.NearUtils.Common.ExceptionHundler;
import ru.ojaqua.NearUtils.Common.UError;
import ru.ojaqua.NearUtils.GUI.UMenu;
import ru.ojaqua.NearUtils.GUI.UMenuItemParam;
import ru.ojaqua.NearUtils.GUI.USystemTray;
import ru.ojaqua.NearUtils.Handlers.QueryGetterHandler.QueryGetterHandler;
import ru.ojaqua.NearUtils.Handlers.SCRGetterHandler.SCRGetterHandler;
import ru.ojaqua.NearUtils.Handlers.TemplStringHandler.TemplStringHandler;
import ru.ojaqua.NearUtils.Handlers.TemplStringHandler.TemplStringPrmReader;

/**
 * Hello world!
 *
 */
public class NearUtilsApp {
	private static final int SHIFT_ALT_3 = 88;
	private static final String nameProgram = "Near utils";

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
			ExceptionHundler.exceptionHundler(e);
			return;
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				try {

					String templStringParmPath = TemplStringHandler.class.getResource("TemplStringParm.xml").getFile().substring(1);

					TemplStringPrmReader tmplReader = new TemplStringPrmReader(templStringParmPath);

					List<UMenuItemParam> tmplStringPrmList = tmplReader.getPrm().getTemplStringList().stream()
							.map(item -> UMenuItemParam.crExecuter(item.getName(), new TemplStringHandler(item.getTempl(), ClipboardWorker::setText)))
							.collect(Collectors.toList());

					List<UMenuItemParam> menuParm = List.of(
							UMenuItemParam.crExecuter("Получить номера запросов",
									new SCRGetterHandler(ClipboardWorker::getText, ClipboardWorker::setText)),
							UMenuItemParam.crExecuter("Получить запрос из трассы",
									new QueryGetterHandler(ClipboardWorker::getText, ClipboardWorker::setText)),
							UMenuItemParam.crSubMenu("Шаблоны строк", tmplStringPrmList)

					);

					UMenu menu = new UMenu(menuParm, nameProgram);

					@SuppressWarnings("unused")
					USystemTray systemTray = new USystemTray(SystemTray.getSystemTray(), menu);

					JIntellitype.getInstance().registerHotKey(SHIFT_ALT_3, JIntellitype.MOD_ALT + JIntellitype.MOD_SHIFT, '3');
					JIntellitype.getInstance().addHotKeyListener(aIdentifier -> {
						if (aIdentifier == SHIFT_ALT_3)
							menu.show();

					});
				} catch (Exception e) {
					ExceptionHundler.exceptionHundler(e);
				}

			}
		});
	}

}
