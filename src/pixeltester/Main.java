/**
 * Project: PixelTester
 * File: Main.java
 * Date: Apr 12, 2016
 * Time: 9:58:06 PM
 */
package pixeltester;

import java.awt.EventQueue;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import pixeltester.ui.HelpDialog;
import pixeltester.ui.ImageTest;
import pixeltester.ui.MainFrame;

/**
 * @author Maksim Lisitski, A00969947
 *
 */

public class Main {
	private static Preferences prefs;

	public static void main(String[] args) {
		try {
			prefs = Preferences.userNodeForPackage(Main.class);
		} catch (SecurityException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		generateUI();
	}

	private static void generateUI() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {

					MainFrame frame = null;

					if (getMode().equals("mode1") || getMode().equals("mode2")) {
						frame = new MainFrame();

						frame.setVisible(true);
					} else if (getMode().equals("mode3")) {
						ImageTest test = new ImageTest();
						test.setVisible(true);
					}

					if (prefs.getBoolean("showHelp", true)) {
						HelpDialog helpDialog = new HelpDialog(frame);
						helpDialog.setVisible(true);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void saveAutoCycling(boolean isCycling) {
		prefs.putBoolean("isCycling", isCycling);
	}

	public static boolean getAutoCycling() {
		return prefs.getBoolean("isCycling", true);
	}

	public static void saveHelpOnStart(boolean isShown) {
		prefs.putBoolean("showHelp", isShown);
	}

	public static boolean getHelpStatus() {
		return prefs.getBoolean("showHelp", true);
	}

	public static int getAutoInterval() {
		return prefs.getInt("timerInterval", 2000);
	}

	public static void saveAutoInterval(int timerInterval) {
		prefs.putInt("timerInterval", timerInterval);
	}

	public static void saveMode(String modeNumber) {
		prefs.put("mode", modeNumber);
		// try {
		// prefs.flush();
		// } catch (BackingStoreException e) {
		// e.printStackTrace();
		// }
	}

	public static String getMode() {
		return prefs.get("mode", "mode1");
	}

}
