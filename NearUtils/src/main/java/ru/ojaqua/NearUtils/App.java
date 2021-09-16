package ru.ojaqua.NearUtils;

import java.awt.SystemTray;
import java.util.List;

//import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.melloware.jintellitype.JIntellitype;

import ru.ojaqua.NearUtils.Common.ClipboardWorker;
import ru.ojaqua.NearUtils.GUI.UMenu;
import ru.ojaqua.NearUtils.GUI.UMenuItemParam;
import ru.ojaqua.NearUtils.GUI.USystemTray;
import ru.ojaqua.NearUtils.Handlers.QueryGetterHandler;
import ru.ojaqua.NearUtils.Handlers.SCRGetterHandler;

/**
 * Hello world!
 *
 */
public class App
{
	private static final int WINDOWS_A = 88;
	private static final String nameProgram = "Near utils";
	
	
    static void prepareProgram() {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// first check to see if an instance of this application is already
		// running, use the name of the window title of this JFrame for checking
		if (JIntellitype.checkInstanceAlreadyRunning(nameProgram)) {
			System.exit(1);
		}

		// next check to make sure JIntellitype DLL can be found and we are on
		// a Windows operating System
		if (!JIntellitype.isJIntellitypeSupported()) {
			System.exit(1);
		}
    	
		// Check the SystemTray support
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}
    	
    }
    
	
	public static void main( String[] args )
    {
	    prepareProgram();
	    
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	
            	
            	
            	List<UMenuItemParam> menuParm =
            			List.of( UMenuItemParam.crExecuter("Получить SCR", new SCRGetterHandler(ClipboardWorker::getText, ClipboardWorker::setText )),
            					 UMenuItemParam.crExecuter("Получить запрос из трассы", new QueryGetterHandler(ClipboardWorker::getText, ClipboardWorker::setText ))
            					);
//            	List.of( UMenuItemParam.crExecuter("Item1", ()->{JOptionPane.showMessageDialog(null, "clic Item1!");}),
//            			 UMenuItemParam.crExecuter("Item2", ()->{JOptionPane.showMessageDialog(null, "clic Item2!");}),
//            			 UMenuItemParam.crSubMenu("subMenu   >", List.of(UMenuItemParam.crExecuter("Item3", ()->{JOptionPane.showMessageDialog(null, "clic Item3!");}),
//            					                                     UMenuItemParam.crExecuter("Item4", ()->{JOptionPane.showMessageDialog(null, "clic Item4!");})
//            					                                    )
//            		                             )
//            			
//            			);
            	
            	UMenu menu = new UMenu(menuParm, nameProgram);
            	
            	var systemTray = new USystemTray(SystemTray.getSystemTray(), menu);
            	
            	//JIntellitype.getInstance().registerHotKey(WINDOWS_A, JIntellitype.MOD_WIN, 'A');
            	JIntellitype.getInstance().registerHotKey(WINDOWS_A, JIntellitype.MOD_ALT + JIntellitype.MOD_SHIFT, '3');
            	JIntellitype.getInstance().addHotKeyListener(aIdentifier->{if(aIdentifier == WINDOWS_A)
            		                                                         menu.show();
            		                                                        
            		                                                        });
            	
            }
        });
    }
    
    
}
