package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import constants.GEConstants.EAnchorTypes;
import shapes.GEShape;

public class GEResizer extends GETransformer{ //도형의 이동을 담당

	private Point2D resizeAnchor;

	public GEResizer(GEShape shape) {
		super(shape);
		previousP = new Point();
	}

	@Override
	public void init(Point p) {
		previousP = p;
		resizeAnchor = getResizeAnchor();
		shape.moveReverse(resizeAnchor);
	}

	@Override
	public void transformer(Graphics2D g2d, Point p) {
		g2d.setXORMode(g2d.getBackground());
		g2d.setStroke(basicStroke);
		Point2D resizeFactor = computeResizeFactor(previousP, p);
		AffineTransform tempAffine = g2d.getTransform();
		g2d.translate(resizeAnchor.getX(), resizeAnchor.getY());
		shape.draw(g2d);
		shape.resizeCoordinate(resizeFactor);
		shape.draw(g2d);
		g2d.setTransform(tempAffine);
		previousP = p;		
	}

	private Point getResizeAnchor(){
		Point resizeAnchor = new Point();
		if (shape.getSelectedAnchor() == EAnchorTypes.NW) {
			resizeAnchor.setLocation(shape.getAnchorList().getAnchors().get(
					EAnchorTypes.SE.ordinal()).getX(),
					shape.getAnchorList().getAnchors().get(
							EAnchorTypes.SE.ordinal()).getY());
		} else if (shape.getSelectedAnchor() == EAnchorTypes.NN) {
			resizeAnchor.setLocation(0, shape.getAnchorList().getAnchors().get(
					EAnchorTypes.SS.ordinal()).getY());
		} else if (shape.getSelectedAnchor() == EAnchorTypes.NE) {
			resizeAnchor.setLocation(shape.getAnchorList().getAnchors().get(
					EAnchorTypes.SW.ordinal()).getX(),
					shape.getAnchorList().getAnchors().get(
							EAnchorTypes.SW.ordinal()).getY());
		} else if (shape.getSelectedAnchor() == EAnchorTypes.WW) {
			resizeAnchor.setLocation(shape.getAnchorList().getAnchors().get(
					EAnchorTypes.EE.ordinal()).getX(),0);
		} else if (shape.getSelectedAnchor() == EAnchorTypes.EE) {
			resizeAnchor.setLocation(shape.getAnchorList().getAnchors().get(
					EAnchorTypes.WW.ordinal()).getX(), 0);
		} else if (shape.getSelectedAnchor() == EAnchorTypes.SW) {
			resizeAnchor.setLocation(shape.getAnchorList().getAnchors().get(
					EAnchorTypes.NE.ordinal()).getX(),
					shape.getAnchorList().getAnchors().get(
							EAnchorTypes.NE.ordinal()).getY());
		} else if (shape.getSelectedAnchor() == EAnchorTypes.SS) {
			resizeAnchor.setLocation(0, shape.getAnchorList().getAnchors().get(
					EAnchorTypes.NN.ordinal()).getY());
		} else if (shape.getSelectedAnchor() == EAnchorTypes.SE) {
			resizeAnchor.setLocation(shape.getAnchorList().getAnchors().get(
					EAnchorTypes.NW.ordinal()).getX(),
					shape.getAnchorList().getAnchors().get(
							EAnchorTypes.NW.ordinal()).getY());
		}
		return resizeAnchor;
	}

	private Double computeResizeFactor(Point previousP, Point currentP){
		double deltaW = 0;
		double deltaH = 0;
		if (shape.getSelectedAnchor() == EAnchorTypes.NW) {
			deltaW =- (currentP.x - previousP.x);
			deltaH =- (currentP.y - previousP.y);
		} else if (shape.getSelectedAnchor() == EAnchorTypes.NN) {
			deltaW = 0;
			deltaH =- (currentP.y - previousP.y);
		} else if (shape.getSelectedAnchor() == EAnchorTypes.NE) {
			deltaW = currentP.x - previousP.x;
			deltaH =- (currentP.y - previousP.y);
		} else if (shape.getSelectedAnchor() == EAnchorTypes.WW) {
			deltaW =- (currentP.x - previousP.x);
			deltaH = 0;
		} else if (shape.getSelectedAnchor() == EAnchorTypes.EE) {
			deltaW = currentP.x - previousP.x;
			deltaH = 0;
		} else if (shape.getSelectedAnchor() == EAnchorTypes.SW) {
			deltaW =- (currentP.x - previousP.x);
			deltaH = currentP.y - previousP.y;
		} else if (shape.getSelectedAnchor() == EAnchorTypes.SS) {
			deltaW = 0;
			deltaH = currentP.y - previousP.y;
		} else if (shape.getSelectedAnchor() == EAnchorTypes.SE) {
			deltaW = currentP.x - previousP.x;
			deltaH = currentP.y - previousP.y;
		}
		double currentW = shape.getBounds().getWidth();
		double currentH = shape.getBounds().getHeight();
		double xFactor = 1.0;
		if (currentW > 0.0)
			xFactor = (1.0 + deltaW / currentW);
		double yFactor = 1.0;
		if (currentH > 0.0)
			yFactor = (1.0 + deltaH / currentH);
		return new Point.Double(xFactor, yFactor);
	}

	public void finalize(Point point){
		shape.move(resizeAnchor);
	}

}
