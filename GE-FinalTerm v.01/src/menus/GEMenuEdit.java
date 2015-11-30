package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GEConstants.EEditMenuItems;
import frames.GEDrawingPanel;
import shapes.GEGroup;

public class GEMenuEdit extends JMenu{

	private GEDrawingPanel drawingPanel;
	private EditMenuHandler editMenuHandler;

	public GEMenuEdit(String s) {
		super(s);
		editMenuHandler = new EditMenuHandler();
		for(EEditMenuItems btn : EEditMenuItems.values()){
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.addActionListener(editMenuHandler);
			menuItem.setActionCommand(btn.toString());
			this.add(menuItem);
		}
	}

	public void init(GEDrawingPanel dp){
		drawingPanel = dp;
	}
	private void group() {
		drawingPanel.group(new GEGroup());
	}
	private void ungroup() {
		drawingPanel.unGroup();
	}

	private class EditMenuHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch (EEditMenuItems.valueOf(e.getActionCommand())) {
			case Undo://함수 break;
			case Redo://함수 break;
			case 삭제://함수 break;
			case 잘라내기://함수 break;
			case 복사://함수 break;
			case 붙이기://함수 break;
			case Group : group(); break;
			case ungroup: ungroup(); break;

			}
		}

		
	}
	
}
