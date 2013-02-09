package org.peie.avicultura.helper;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

public class NavBarItem {
	private JLabel navlabel;
	private JInternalFrame iFrame;

	public NavBarItem(JLabel navlabel, JInternalFrame iFrame) {
		super();
		this.navlabel = navlabel;
		this.iFrame = iFrame;
	}

	public JLabel getNavlabel() {
		return navlabel;
	}

	public JInternalFrame getiFrame() {
		return iFrame;
	}

}
