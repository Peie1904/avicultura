package org.peie.avicultura.helper;


import javax.swing.JLabel;

public class NavBarItem {
	private JLabel navlabel;
	private AviInternalFrame iFrame;

	public NavBarItem(JLabel navlabel, AviInternalFrame iFrame) {
		super();
		this.navlabel = navlabel;
		this.iFrame = iFrame;
	}

	public JLabel getNavlabel() {
		return navlabel;
	}

	public AviInternalFrame getiFrame() {
		return iFrame;
	}

}
