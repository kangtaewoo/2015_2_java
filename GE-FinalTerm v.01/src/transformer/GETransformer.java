package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import shapes.GEShape;
import java.awt.BasicStroke;
import constants.GEConstants;

public abstract class GETransformer {
	protected GEShape shape;
	protected BasicStroke basicStroke;
	protected Point previousP;
	
	public GETransformer(GEShape shape){
		this.shape = shape;
		float dashes[] = {GEConstants.DEFAULT_DASH_OFFSET};
		basicStroke = new BasicStroke(
			GEConstants.DEFAULT_DASHEDLINE_WIDTH,
			BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND, 10, dashes, 0);
	}
	
	
	abstract public void init(Point p);	
	abstract public void transformer (Graphics2D g2D, Point p);
}
