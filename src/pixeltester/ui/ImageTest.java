/**
 * Project: PixelTester
 * File: ImageTest.java
 * Date: May 2, 2016
 * Time: 10:19:09 PM
 */
package pixeltester.ui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import pixeltester.Main;

/**
 * @author Maksim Lisitski, A00969947
 *
 */

public class ImageTest extends JFrame {
	private static final long serialVersionUID = 2747967779638522239L;
	private static final String IMAGE_FILE = "/image.png";

	@SuppressWarnings("serial")
	public ImageTest() {

		setSize(1366, 768);
		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon image = new ImageIcon(getClass().getResource(IMAGE_FILE));
		JLabel label = new JLabel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (image != null) {
					g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
				}
			}
		};

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		setContentPane(panel);
		panel.add(label);

		// register shortcuts for fast mode
		panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "mode1");
		panel.getActionMap().put("mode1", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.setCurrentColors(1);
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
				MainFrame.showShortMessage("Extended Pixel Test mode 1", 700);
				Main.saveMode("mode1");
				dispose();
			}
		});

		// register shortcuts for extended mode
		panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "mode2");
		panel.getActionMap().put("mode2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.setCurrentColors(2);
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
				MainFrame.showShortMessage("Extended Pixel Test mode 2", 700);
				Main.saveMode("mode2");
				dispose();
			}
		});

		// register shortcuts for closing app
		panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
		panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK), "Escape");
		panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK), "Escape");
		panel.getActionMap().put("Escape", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Main.saveMode("mode3");
			}
		});

	}

}
