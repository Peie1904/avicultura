package org.peie.avicultura.viewer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class AppDateField extends JTextField {
	public AppDateField() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void datePopUp() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2
						&& e.getButton() == MouseEvent.BUTTON1) {

					popUp(e.getXOnScreen(), e.getYOnScreen());

				}

				if (e.getButton() == MouseEvent.BUTTON3) {

				}
			}
		});
	}

	private void popUp(int x, int y) {
		DatePopUp.dateCreate(x, y, this);
	}

}
