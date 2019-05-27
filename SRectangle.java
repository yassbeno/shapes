package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SRectangle extends Shape {

	
	private Rectangle rect;
	
	
	public SRectangle(Point loc,int width,int height)
	{
		this.rect= new Rectangle(loc.x, loc.y,width, height);
	}
	public SRectangle(SRectangle _rect){
		this.rect = new Rectangle(_rect.getRect());
	}

	public Rectangle getRect()
	{
		return this.rect;
	}
	
	
	public Point getLoc()
	{
		return this.rect.getLocation();
	}
	
	public void setLoc(Point loc)
	{
		this.rect.setLocation(loc);
	}
	
	public void translate(int dx,int dy)
	{
		this.rect.translate(dx, dy);
	}
	
	public Rectangle getBounds()
	{
		return this.rect;
	}
	
	public void setBounds(int dx, int dy)
	{
		this.rect.width = this.rect.width + dx;
		this.rect.height = this.rect.height + dy;
		
	}
	
	public void accept(ShapeVisitor sp)
	{
		sp.visitRectangle(this);
	}
}
