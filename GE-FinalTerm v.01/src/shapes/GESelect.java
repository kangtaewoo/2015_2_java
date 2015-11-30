package shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class GESelect extends GEShape {

	public GESelect() {
		super(new Rectangle());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initDraw(Point startP) {
		// TODO Auto-generated method stub
		this.startP = startP;
	}

	@Override
	public void setCoordinate(Point currentP) {
		// TODO Auto-generated method stub
		Rectangle tempRectangle = (Rectangle)myShape;
		tempRectangle.setFrameFromDiagonal(startP.x,startP.y,currentP.x,currentP.y);
	}

	@Override
	public GEShape clone() {
		// TODO Auto-generated method stub
		return new GESelect();
	}

}
