/**
 * Project: PixelTester
 * File: MainFrame.java
 * Date: Apr 12, 2016
 * Time: 10:00:37 PM
 */
package pixeltester.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import pixeltester.Main;

/**
 * @author Maksim Lisitski, A00969947
 */

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -5735551319663625409L;
	private JPanel contentPane;
	private int i = 0;
	private static Color fastModeColors[] = { Color.BLACK, new Color(255, 0, 255) };
	private static Color extModeColors[] = { Color.BLACK, Color.WHITE, Color.GREEN, Color.BLUE, Color.RED };

	private Timer timer;
	private ActionListener autoCycleListener;

	private int timerInterval;
	private boolean isCycling;
	private static Color currentColors[];

	// main constructor
	@SuppressWarnings("serial")
	public MainFrame() {

		isCycling = Main.getAutoCycling();
		activateTimer();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);

		// if Mac os, enter full screen mode
		if (System.getProperty("os.name").contains("mac")) {
			getRootPane().putClientProperty("apple.awt.fullscreenable", Boolean.valueOf(true));
		}

		if (Main.getMode().equals("mode1")) {
			setCurrentColors(1);
			System.out.println("mode1");

		} else if (Main.getMode().equals("mode2")) {
			setCurrentColors(2);
			System.out.println("mode2");
		}

		contentPane = new JPanel();
		contentPane.setBackground(currentColors[i++ % currentColors.length]);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// register F1 to display Help
		contentPane.getInputMap().put(KeyStroke.getKeyStroke("F1"), "HelpDialog");
		contentPane.getActionMap().put("HelpDialog", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HelpDialog helpDialog = new HelpDialog(MainFrame.this);
				helpDialog.setVisible(true);
			}
		});

		// register shortcuts for closing app
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK), "Escape");
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK), "Escape");
		contentPane.getActionMap().put("Escape", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				savePreferences();
			}
		});

		// register shortcuts for autocycling mode
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, InputEvent.CTRL_DOWN_MASK, false), "autoMode");
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_CAPS_LOCK, 0), "autoMode");
		// contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ALT, InputEvent.ALT_DOWN_MASK, false), "autoMode");
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK, false), "autoMode");
		// contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "autoMode");
		contentPane.getActionMap().put("autoMode", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isCycling = !isCycling;
				String text;
				if (isCycling) {
					text = "ON";
				} else {
					text = "OFF";
				}
				showShortMessage("Auto mode is:" + text, 400);
			}
		});

		// register shortcuts for next color
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "nextColor");
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "nextColor");
		contentPane.getActionMap().put("nextColor", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.setBackground(currentColors[i++ % currentColors.length]);
			}
		});

		// register shortcuts for previous color
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "prevColor");
		contentPane.getActionMap().put("prevColor", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (i <= 0) {
					i = currentColors.length;
				}
				contentPane.setBackground(currentColors[i-- % currentColors.length]);
			}
		});

		// register shortcuts for speed up
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "speedUp");
		contentPane.getActionMap().put("speedUp", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				timerInterval = timerInterval + 500;
				timer = new Timer(timerInterval, autoCycleListener);
				timer.start();
				showShortMessage("New time interval, msec: " + timerInterval, 400);
			}
		});

		// register shortcuts for speed down
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "speedDown");
		contentPane.getActionMap().put("speedDown", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				timerInterval = timerInterval - 500;
				if (timerInterval <= 500) {
					timerInterval = 500;
				}
				timer = new Timer(timerInterval, autoCycleListener);
				timer.start();
				showShortMessage("New time interval, msec: " + timerInterval, 400);
			}
		});

		// register shortcuts for fast mode
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "mode1");
		contentPane.getActionMap().put("mode1", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				i = 0;
				contentPane.setBackground(fastModeColors[i++ % fastModeColors.length]);
				setCurrentColors(1);
				showShortMessage("Fast Pixel Test mode 1 ", 700);
				Main.saveMode("mode1");
			}
		});

		// register shortcuts for extended mode
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "mode2");
		contentPane.getActionMap().put("mode2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				i = 0;
				contentPane.setBackground(extModeColors[i++ % extModeColors.length]);
				setCurrentColors(2);
				showShortMessage("Extended Pixel Test mode 2", 700);
				Main.saveMode("mode2");
			}
		});

		// register shortcuts for image test
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "mode3");
		contentPane.getActionMap().put("mode3", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageTest test = new ImageTest();
				test.setVisible(true);
				showShortMessage("Picture defect test mode 3", 700);
				savePreferences();
				dispose();
			}
		});

	}

	static void showShortMessage(String textToDisplay, int delay) {
		Frame frame = new JFrame();
		frame.setUndecorated(true);
		JLabel label = new JLabel(textToDisplay);
		frame.add(label);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		Timer t = new Timer(delay, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		t.setRepeats(false);
		t.start();
	}

	private void activateTimer() {
		autoCycleListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (isCycling) {
					contentPane.setBackground(currentColors[i++ % currentColors.length]);
				}
			}
		};
		timerInterval = Main.getAutoInterval();
		timer = new Timer(timerInterval, autoCycleListener);
		timer.start();
	}

	public static void setCurrentColors(int a) {
		if (a == 1) {
			currentColors = fastModeColors.clone();
		}
		if (a == 2) {
			currentColors = extModeColors.clone();
		}
	}

	private void savePreferences() {
		Main.saveAutoInterval(timerInterval);
		Main.saveAutoCycling(isCycling);

	}
}