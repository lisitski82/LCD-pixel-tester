/**
 * Project: PixelTester
 * File: HelpDialog.java
 * Date: Apr 16, 2016
 * Time: 6:56:52 PM
 */
package pixeltester.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import pixeltester.Main;

/**
 * @author Maksim Lisitski, A00969947
 *
 */

public class HelpDialog extends JDialog {

	private static final long serialVersionUID = -8350783838956764765L;

	public HelpDialog(JFrame owner) {

		super(owner, "Help", true);
		setAlwaysOnTop(true);

		// setSize(450, 300); - used pack() for auto-size
		getContentPane().setLayout(new MigLayout("", "[191px][32px]", "[17px][14px][14px][14px][14px][14px][14px][14px][14px][23px][23px]"));

		JLabel lblShortKeys = new JLabel("Short keys:");
		lblShortKeys.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(lblShortKeys, "cell 0 0,alignx left,aligny top");

		JLabel lblFShow = new JLabel("F1 - Show this help");
		getContentPane().add(lblFShow, "cell 0 1,alignx left,aligny top");

		JLabel label1 = new JLabel("1 - Fast Pixel mode ");
		getContentPane().add(label1, "cell 0 2,alignx left,aligny top");

		JLabel label2 = new JLabel("2 - Extended Pixel mode ");
		getContentPane().add(label2, "cell 0 3,alignx left,aligny top");

		JLabel lblVisual = new JLabel("3 - Visual defects test");
		getContentPane().add(lblVisual, "cell 0 4,alignx left,aligny top");

		JLabel lblCtrlOrShift = new JLabel("Ctrl or Shift or Caps Lock - Auto Cycling mode");
		getContentPane().add(lblCtrlOrShift, "cell 0 5,alignx left,aligny top");

		JLabel lblSpaceChange = new JLabel("Space - Next color");
		getContentPane().add(lblSpaceChange, "cell 0 6,alignx left,aligny top");

		JLabel lblNewLabel_1 = new JLabel("\u2190 or \u2192 - Previous or Next Color");
		getContentPane().add(lblNewLabel_1, "cell 0 7,alignx left,aligny top");

		JLabel lblNewLabel = new JLabel("\u2191 or \u2193 - Increase or Decrease intervals for autoCycling mode");
		getContentPane().add(lblNewLabel, "cell 0 8 2 1,alignx left,aligny top");

		JCheckBox chckbxNewCheckBox = new JCheckBox("Do not show this message on start");
		if (!Main.getHelpStatus()) {
			chckbxNewCheckBox.setSelected(true);
		}
		getContentPane().add(chckbxNewCheckBox, "cell 0 9,alignx left,aligny top");

		JButton btnOk = new JButton("Save");
		btnOk.setHorizontalAlignment(SwingConstants.RIGHT);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chckbxNewCheckBox.isSelected()) {
					Main.saveHelpOnStart(false);
				} else {
					Main.saveHelpOnStart(true);
				}
				dispose();
			}
		});
		getContentPane().add(btnOk, "cell 0 10,alignx right,aligny top");

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setHorizontalAlignment(SwingConstants.LEFT);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnCancel, "cell 1 10,alignx right,aligny top");

		pack();
		setLocationRelativeTo(owner);

	}

}
