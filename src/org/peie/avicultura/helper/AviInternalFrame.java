package org.peie.avicultura.helper;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.peie.avicultura.viewer.ZuchtPaareFrame;

public class AviInternalFrame extends JInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2585102774686646569L;
	private String name;
	private JPanel taskBar;
	private JButton button;
	
	public AviInternalFrame(JPanel taskBar,String name){
		super(name);
		this.name = name;
		this.taskBar = taskBar;
		
		
		newTask();
		
		
	}
	
	private void newTask(){
		
		button = new JButton(name);
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (isVisible()){
					setVisible(false);
					button.setForeground(Color.lightGray);
					
				}else{
					setVisible(true);
					button.setForeground(Color.black);
				}
				

			}
		});
		
		button.setForeground(Color.black);
		button.setVisible(true);
		
		taskBar.add(button);
		
	}
	

}
