package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import constants.GEConstants;
import constants.GEConstants.EAnchorTypes;
import constants.GEConstants.EState;
import shapes.GEGroup;
import shapes.GEPolygon;
import shapes.GESelect;
import shapes.GEShape;
import transformer.GEDrawer;
import transformer.GEGrouper;
import transformer.GEMover;
import transformer.GEResizer;
import transformer.GETransformer;
import utils.GECursorManager;

public class GEDrawingPanel extends JPanel {
	
	private GEShape currentShape, selectedShape;
	private ArrayList<GEShape> shapeList;
	private MouseDrawinghandler drawingHandler;
	private EState currentState;
	private GETransformer transformer;
	private Color fillColor, lineColor;
	private GECursorManager cursors;
	
	public GEDrawingPanel(){
		super();
		shapeList = new ArrayList<GEShape>();
		currentState = EState.Idle;
		drawingHandler = new MouseDrawinghandler();
		cursors = new GECursorManager();
		addMouseListener(drawingHandler);
		addMouseMotionListener(drawingHandler);
		this.setForeground(GEConstants.FOREGROUND_COLOR);
		this.setBackground(GEConstants.BACKGROUND_COLOR);
		initializeGraphicsAtributes();
	}
	

	public void setFillColor(Color fillColor) {
		if(selectedSetColor(false, fillColor) == true){
			return;
		}
		this.fillColor = fillColor;
	}

	public void setLineColor(Color lineColor) {
		if(selectedSetColor(true, lineColor) == true){
			return;
		}
		this.lineColor = lineColor;
	}
	
	public boolean selectedSetColor(boolean flag, Color color){
		if(selectedShape != null){
			if(flag == true){ // line
				selectedShape.setLineColor(color);
			}else{//fill
				selectedShape.setFillColor(color);
			}
			repaint();
			return true;
		}
		return false;
	}
	public void setCurrentShape(GEShape currentShape) {
		this.currentShape = currentShape;
	}
	public void setCurrentState(EState currentState) {
		this.currentState = currentState;
	}
	public void group(GEGroup group) {
		// TODO Auto-generated method stub
		boolean check = false;
		for (int i = shapeList.size(); i > 0; i--) {
			GEShape shape = shapeList.get(i - 1);
			if(shape.isSelected()){
				shape.setSelected(false);
				group.addShape(shape);
				shapeList.remove(shape);
				check = true;
			}
		}
		if(check){
			group.setSelected(true);
			shapeList.add(group);
		}
		repaint();
	}

	public void unGroup() {
		// TODO Auto-generated method stub
		ArrayList<GEShape> tempList = new ArrayList<GEShape>();
		for (int i = shapeList.size(); i > 0; i--) {
			GEShape shape = shapeList.get(i - 1);
			if(shape instanceof GEGroup && shape.isSelected()){
				for(GEShape childShape: ((GEGroup)shape).getChildList()){
					childShape.setSelected(true);
					tempList.add(childShape);
				}
				shapeList.remove(shape);
			}
		}
		shapeList.addAll(tempList);
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		for(GEShape shape : shapeList){
			shape.draw(g2D);
		}

	}
	private void initializeGraphicsAtributes() {
		// TODO Auto-generated method stub
		lineColor = GEConstants.COLOR_LINE_DEFAULT;
		fillColor = GEConstants.COLOR_FILL_DEFAULT;
	}
	private void initDraw(Point startP){
		currentShape = currentShape.clone();
		currentShape.setLineColor(lineColor);
		currentShape.setFillColor(fillColor);
	}
	private void continueDrawing(Point currentP){
		((GEDrawer)transformer).continueDrawing(currentP);
	}
		
	private GEShape onShape(Point p){
		for(int i = shapeList.size(); i > 0; i--){
			GEShape shape = shapeList.get(i-1);
			if(shape.onShape(p)){
				return shape;
			}
		}
		return null;
	}

	private void clearSelectedShapes(){
		for(GEShape shape : shapeList){
			shape.setSelected(false);
		}
	}

	private void finishDraw(){
		shapeList.add(currentShape);
	}
	

	private class MouseDrawinghandler extends MouseInputAdapter{

		@Override
		public void mouseDragged(MouseEvent e) {
			if(currentState != EState.Idle){
				transformer.transformer(
						(Graphics2D)getGraphics(), e.getPoint());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (currentState == EState.Idle) {
				if (currentShape instanceof GESelect){
					selectedShape = onShape(e.getPoint());
					if (selectedShape != null) {
					   clearSelectedShapes();
					   selectedShape.setSelected(true);
					   selectedShape.onAnchor(e.getPoint()); 
					   if (selectedShape.getSelectedAnchor() == EAnchorTypes.NONE){ 
							transformer = new GEMover(selectedShape);
							((GEMover) transformer).init(e.getPoint());
							setCurrentState(EState.Moving); 
						}else { 
							transformer = new GEResizer(selectedShape); 
							((GEResizer) transformer).init(e.getPoint()); 
							setCurrentState(EState.Resizer); 
						}
					}else {
						setCurrentState(EState.Selecting);
						clearSelectedShapes();
						initDraw(e.getPoint());
						transformer = new GEGrouper(currentShape);
						((GEGrouper) transformer).init(e.getPoint());
					}
				}else {
					clearSelectedShapes();
					initDraw(e.getPoint());
					transformer = new GEDrawer(currentShape);
					((GEDrawer) transformer).init(e.getPoint());
					if (currentShape instanceof GEPolygon) {
						setCurrentState(EState.NPointsDrawing);
					} else {
						setCurrentState(EState.TwoPointsDrawing);
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(currentState == EState.TwoPointsDrawing){
				finishDraw();
				currentState = EState.Idle;
			}else if(currentState == EState.NPointsDrawing){
				return;
			}else if(currentState == EState.Resizer){
				((GEResizer)transformer).finalize(e.getPoint());
			} else if(currentState == EState.Selecting){
				((GEGrouper)transformer).finalize(shapeList);

			}
			setCurrentState(EState.Idle);;
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1){
				if(currentState == EState.NPointsDrawing){
					if(e.getClickCount() == 1){
						continueDrawing(e.getPoint());
					}else if (e.getClickCount() == 2){
						finishDraw();
						currentState = EState.Idle;
						repaint();
					}
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(currentState == EState.NPointsDrawing){
				transformer.transformer(
						(Graphics2D)getGraphics(), e.getPoint());
			}else if(currentState == EState.Idle){
				GEShape shape = onShape(e.getPoint());
				if(shape == null){
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}else if(shape.isSelected() == true){
					EAnchorTypes anchorType = shape.onAnchor(e.getPoint());
					setCursor(cursors.get(anchorType.ordinal()));
				}
			}

		}
	}

}
