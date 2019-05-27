package graphics.shapes;

import java.awt.Point;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.ws.handler.MessageContext.Scope;


public class SCollection extends Shape {

	public List shapes;
	public Point loc;
	
	public SCollection(SCollection _SCollection){
		this.shapes=  new ArrayList<>(_SCollection.shapes);
		this.loc = new Point(_SCollection.loc);
	}
	
	public SCollection()
	{
		this.shapes = new ArrayList();
		this.loc = new Point(0,0);
	}
	
	public Point getLoc()
	{
		Shape s = (Shape) this.shapes.get(0);
		Point pnt = s.getLoc();
		for(Iterator it = this.shapes.iterator(); it.hasNext();)
		{
			s = (Shape)it.next();
			Point pos = s.getLoc();
			if (pos.x < pnt.x)
			{
				pnt.x=pos.x;
			}
			if (pos.y < pnt.y)
			{
				pnt.y=pos.y;
			}
		}
		return pnt;
	}
	
	public void setLoc(Point pnt)
	{
		this.translate(pnt.x-loc.x,pnt.y-loc.y);
		this.loc = pnt;
		
	}
	
	//Pour bouger les formes
	public void translate(int dx,int dy)
	{
		for(Iterator it = this.iterator(); it.hasNext();)
			((Shape)it.next()).translate(dx,dy);
		this.loc.x += dx;
		this.loc.y += dy;
	}
	
	public Rectangle getBounds()
	{
		Rectangle rect = ((Shape) (this.iterator()).next()).getBounds();
		for(Iterator it = this.iterator(); it.hasNext();)
			rect = rect.union(((Shape)it.next()).getBounds());
		return rect;
	}
	
	public void add(Shape shape)
	{
		this.shapes.add(shape);
	}
	public void remove(Shape shape){
		this.shapes.remove(shape);
	}
	
	public void accept(ShapeVisitor sp)
	{
		sp.visitCollection(this);
	}
	
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return this.shapes.iterator();
	}
	
	public String toString()
	{
		return this.shapes.toString();
	}
	
	public void setBounds(int dx, int dy)
	{
		for(Iterator it = this.iterator(); it.hasNext();)
			((Shape)it.next()).setBounds(dx,dy);
	}
}
