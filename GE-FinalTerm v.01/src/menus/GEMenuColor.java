package menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GEConstants;
import constants.GEConstants.EColorMenuItems;
import frames.GEDrawingPanel;

public class GEMenuColor extends JMenu{
	
	private GEDrawingPanel drawingPanel;
	private ColorMenuHandler colorMenuHandler;

	public GEMenuColor(String s) {
		super(s);
		colorMenuHandler = new ColorMenuHandler();
		
		for(EColorMenuItems btn : EColorMenuItems.values()){
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.addActionListener(colorMenuHandler);
			menuItem.setActionCommand(btn.toString());
			add(menuItem);
		}
	}
	
	public void init(GEDrawingPanel drawingPanel){
		this.drawingPanel = drawingPanel;	
	}
	
	private void setLineColor(){
		Color lineColor = JColorChooser.showDialog(
				null, GEConstants.LINECOLOR_TITLE, null);
		if(lineColor != null){
			drawingPanel.setLineColor(lineColor);
		}
	}
	private void setFillColor(){
		Color fillColor = JColorChooser.showDialog(
				null, GEConstants.FILLCOLOR_TITLE, null);
		if(fillColor != null){
			drawingPanel.setFillColor(fillColor);
		}
	}
	private void clearLineColor(){
		drawingPanel.setLineColor(GEConstants.COLOR_LINE_DEFAULT);
	}
	private void clearFillColor(){
		drawingPanel.setFillColor(GEConstants.COLOR_FILL_DEFAULT);
	}
	
	private class ColorMenuHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			switch(EColorMenuItems.valueOf(e.getActionCommand())){
			case SetLineColor: setLineColor(); break;
			case SetFillColor: setFillColor(); break;
			case ClearLineColor: clearLineColor(); break;
			case ClearFillColor: clearFillColor(); break;
			}
		}
	}
}