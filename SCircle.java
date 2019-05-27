package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SCircle extends Shape {
	
	int radius;
	Point loc;
	
	public SCircle(SCircle _SCircle){
		
		this.loc=new Point(_SCircle.loc);
		this.radius=_SCircle.radius;
	}
	public SCircle(Point loc,int radius)
	{
		this.loc=loc;
		this.radius=radius;
	}
	
	public int getRadius()
	{
		return this.radius;
	}
	
	public void setRadius(int radius)
	{
		this.radius=radius;
	}
	
	public Point getLoc()
	{
		return this.loc;
	}
	
	public void setLoc(Point pnt)
	{
		this.loc=pnt;
	}
	
	public void translate(int dx,int dy)
	{
		this.loc.x = this.loc.x + dx;
		this.loc.y = this.loc.y + dy;
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(loc.x, loc.y, 2*this.radius,2*this.radius);
	}
	
	public void setBounds(int dx, int dy)
	{
		this.radius=this.radius+(dx+dy)/2;
	}
	
	public void accept(ShapeVisitor sp)
	{
		sp.visitCircle(this);
	}

}
