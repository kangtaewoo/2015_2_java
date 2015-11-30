package frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import constants.GEConstants;
import menus.GEMenuBar;

public class GEMainFrame extends JFrame {
	
	//ΩÃ±€≈Ê ∆–≈œ
	private static GEMainFrame uniqueMainFrame = 
			new GEMainFrame(GEConstants.TITLE_MAINFRAME);
	private GEDrawingPanel drawingPanel;
	private GEMenuBar menuBar;
	private GEToolbar toolBar;
	
	private GEMainFrame(String title){
		super(title);
		
		drawingPanel = new GEDrawingPanel();
		add(drawingPanel);
		
		menuBar = new GEMenuBar();
		setJMenuBar(menuBar); 
		
		toolBar = new GEToolbar(GEConstants.TITLE_SHAPETOOLBAR);
		add(BorderLayout.NORTH, toolBar);
	}
	
	//ΩÃ±€≈Ê ∆–≈œ
	public static GEMainFrame getInstance(){
		return uniqueMainFrame;
	}
	
	public void init(){
		toolBar.init(drawingPanel);
		menuBar.init(drawingPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(GEConstants.WIDTH_MAINFRAME, 
						GEConstants.HEIGHT_MAINFRAME);
		this.setVisible(true);
	}
}
