package ru.ojaqua.NearUtils;

import java.awt.SystemTray;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.melloware.jintellitype.JIntellitype;

import ru.ojaqua.NearUtils.Common.ClipboardWorker;
import ru.ojaqua.NearUtils.Common.ExceptionHundler;
import ru.ojaqua.NearUtils.Common.UError;
import ru.ojaqua.NearUtils.GUI.USystemTray;
import ru.ojaqua.NearUtils.GUI.Menu.UMenu;
import ru.ojaqua.NearUtils.GUI.Menu.UMenuItemParam;
import ru.ojaqua.NearUtils.GUI.Menu.UMenuParam;
import ru.ojaqua.NearUtils.Handlers.QueryGetterHandler.QueryGetterHandler;
import ru.ojaqua.NearUtils.Handlers.SCRGetterHandler.SCRGetterHandler;
import ru.ojaqua.NearUtils.Handlers.TmplStringHandler.TmplStringHandler;
import ru.ojaqua.NearUtils.Param.NearUtilsParamReader;

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

					URI uri = NearUtilsApp.class.getResource("/NearUtilsParam.xml").toURI();
					Path templStringParmPath = Paths.get(uri).toAbsolutePath();

					NearUtilsParamReader tmplReader = new NearUtilsParamReader(templStringParmPath);

					List<UMenuItemParam> tmplStringPrmList = tmplReader.getPrm().getStringHandlerPrm().getTmplStringList().stream()
							.map(str -> UMenuItemParam.crExecuter(str, new TmplStringHandler(str, ClipboardWorker::setText)))
							.collect(Collectors.toList());

					List<UMenuItemParam> menuItems = List.of(
							UMenuItemParam.crExecuter("Получить номера запросов",
									new SCRGetterHandler(ClipboardWorker::getText, ClipboardWorker::setText)),
							UMenuItemParam.crExecuter("Получить запрос из трассы",
									new QueryGetterHandler(ClipboardWorker::getText, ClipboardWorker::setText)),
							UMenuItemParam.crSubMenu("Шаблоны строк", new UMenuParam(SwingConstants.LEFT, tmplStringPrmList))

					);

					UMenu menu = new UMenu(new UMenuParam(menuItems), nameProgram);

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
