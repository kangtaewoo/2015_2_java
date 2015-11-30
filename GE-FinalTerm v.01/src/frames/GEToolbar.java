package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import constants.GEConstants;
import constants.GEConstants.EToolBarButtons;
import shapes.GEEllipse;
import shapes.GELine;
import shapes.GEPolygon;
import shapes.GERectangle;
import shapes.GESelect;


public class GEToolbar extends JToolBar {

	private ButtonGroup buttonGroup;
	private GEDrawingPanel drawingPanel;
	private GEToolBarHandler shapeToolBarhandler;
	
	public GEToolbar(String label) {
		super(label);
		shapeToolBarhandler = new GEToolBarHandler();
		buttonGroup = new ButtonGroup();
		JRadioButton rButton = null;
		
		for(EToolBarButtons btn : EToolBarButtons.values()){
			rButton = new JRadioButton();
			rButton.setIcon(new ImageIcon(GEConstants.IMG_URL
					+ btn.toString() + GEConstants.TOOLBAR_BTN));
			rButton.setSelectedIcon(new ImageIcon(GEConstants.IMG_URL
					+ btn.toString() + GEConstants.TOOLBAR_BTN_SLT));
			rButton.addActionListener(shapeToolBarhandler);//
			rButton.setActionCommand(btn.toString());//
			add(rButton);
			buttonGroup.add(rButton);
		}
	}
	
	public void init(GEDrawingPanel drawingPanel){
		this.drawingPanel = drawingPanel;
		this.clickDefaultButton();
	}
	
	private void clickDefaultButton(){
		JRadioButton rButton = (JRadioButton)this.getComponent(
				EToolBarButtons.Rectangle.ordinal());
		rButton.doClick();
	}
	
	private class GEToolBarHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton button = (JRadioButton)e.getSource();
			if(button.getActionCommand().equals(
					EToolBarButtons.Rectangle.name())){
					drawingPanel.setCurrentShape(new GERectangle());
			}else if(button.getActionCommand().equals(
					EToolBarButtons.Ellipse.name())){
					drawingPanel.setCurrentShape(new GEEllipse());
			}else if(button.getActionCommand().equals(
					EToolBarButtons.Line.name())){
					drawingPanel.setCurrentShape(new GELine());
			}else if(button.getActionCommand().equals(
					EToolBarButtons.polygon.name())){
					drawingPanel.setCurrentShape(new GEPolygon());
			}else if(button.getActionCommand().equals(
					EToolBarButtons.Select.name())){
					drawingPanel.setCurrentShape(new GESelect());
			}
		}
	}
}

