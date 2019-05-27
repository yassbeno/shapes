package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.STriangle;
import graphics.shapes.Shape;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.ui.*;

public class ShapeController extends Controller

{

	private int coordX;
	private int coordY;
	private boolean shiftdown;
	private Shape shape;
	private String action;
	private Shape cp_shape;
	private Shape Last_Selected_Copied_Shape;
	private boolean justCoppied = false;

	public ShapeController(Object newModel) {
		super(newModel);
		this.action = "";

	}

	@Override
	public void mousePressed(MouseEvent e) {
		/*
		 * System.out.println(e); Shape s = this.getTarget(e.getPoint());
		 * System.out.println(s); coordX = e.getX(); coordY = e.getY();
		 */
		coordX = e.getX();
		coordY = e.getY();
		Point pnt = e.getPoint();
		this.shape = this.getTarget(pnt);
		Shape lt = this.getLTBox(pnt);
		Shape rb = this.getRBBox(pnt);
		if (lt != null) {
			this.shape = lt;
			this.action = "lt";
		} else if (rb != null) {
			this.shape = rb;
			this.action = "rb";
		} else {
			this.action = "";
			if (shiftDown() == false) {
				this.unselectAll();
			}
			if (this.shape != null) {
				SelectionAttributes at = (SelectionAttributes) this.shape.getAttributes("select");
				at.toggleSelection();
			}

		}

		this.getView().repaint();
		// System.out.println(s);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point pnt = new Point(-10, -10);
		this.shape = this.getTarget(pnt);
		this.action = "";
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		coordX = e.getX();
		coordY = e.getY();
		Shape s = this.getTarget(e.getPoint());
		if (s == null) {
			this.unselectAll();
		} else {
			((SelectionAttributes) s.getAttributes("select")).toggleSelection();
		}

		System.out.println("j'agrippe :");
		System.out.println(s);
		this.action = "";
		/*
		 * if (!shiftDown()) { for(Iterator it = ((SCollection)
		 * model).iterator(); it.hasNext();) { ((SelectionAttributes) ((Shape)
		 * it.next()).getAttributes("select")).selected = false; } } else {
		 * ((SelectionAttributes) target.getAttributes("select")).selected =
		 * true; }
		 * 
		 * if (target != null) { for(Iterator it = ((SCollection)
		 * model).iterator(); it.hasNext();) { ((SelectionAttributes) ((Shape)
		 * it.next()).getAttributes("select")).toggleSelection(); } }
		 */
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// System.out.println(e.getPoint());
		// Quant la souris entre dans la fenêtre d'affichage
		super.getView().requestFocus();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Quant la souris sort de la fenêtre
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		Point pnt = evt.getPoint();

		if (this.action == "lt" && ((SelectionAttributes) this.shape.getAttributes("select")).isSelected()) {
			System.out.println("Je change la taille");
			this.shape.setBounds(coordX - pnt.x, coordY - pnt.y);
			this.shape.translate(pnt.x - coordX, pnt.y - coordY);
		}

		else if (this.action == "rb" && ((SelectionAttributes) this.shape.getAttributes("select")).isSelected()) {
			System.out.println("Je change la taille");
			this.shape.setBounds(pnt.x - coordX, pnt.y - coordY);
		} else {
			if (this.shape != null) {
				this.shape.translate(pnt.x - coordX, pnt.y - coordY);
			}
		}

		// view.ShapesView(model);
		this.getView().repaint();
		coordX = evt.getX();
		coordY = evt.getY();

	}

	public boolean shiftDown()

	{
		return shiftdown;
	}

	private void translateSelected(int dx, int dy) {
		SCollection model = (SCollection) this.getModel();

		for (Iterator i = model.iterator(); i.hasNext();) {
			Shape shape = (Shape) i.next();
			if (((SelectionAttributes) (shape).getAttributes("select")).isSelected()) {
				shape.translate(dx - coordX, dy - coordY);
				System.out.println("Je déplace :");
				System.out.println(shape);

			}
		}
	}

	private void changeSize(Point loc) {
		Shape lt = getLTBox(loc);
		Shape rb = getRBBox(loc);

		if (lt != null && ((SelectionAttributes) lt.getAttributes("select")).isSelected()) {
			System.out.println("Je change la taille");
			lt.setBounds(coordX - loc.x, coordY - loc.y);
			lt.translate(loc.x - coordX, loc.y - coordY);
		}

		if (rb != null && ((SelectionAttributes) rb.getAttributes("select")).isSelected()) {
			System.out.println("Je change la taille");
			rb.setBounds(loc.x - coordX, loc.y - coordY);
		}
	}

	private Shape getTarget(Point pnt) {
		SCollection model = (SCollection) this.getModel();
		for (Iterator it = model.iterator(); it.hasNext();) {
			Shape shape = (Shape) it.next();
			if (shape.getBounds().contains(pnt)) {
				return shape;
			}
		}
		return null;
	}

	private Shape getLTBox(Point pnt) {
		SCollection model = (SCollection) this.getModel();
		Rectangle box;
		Point loc;
		for (Iterator<Shape> it = model.iterator(); it.hasNext();) {
			Shape shape = (Shape) it.next();
			shape.getBounds();
			loc = shape.getLoc();
			box = new Rectangle(loc.x - 6, loc.y - 6, 6, 6);
			if (box.contains(pnt)) {
				System.out.println(shape);
				return shape;
			}
		}
		return null;
	}

	private Shape getRBBox(Point pnt) {
		SCollection model = (SCollection) this.getModel();
		Rectangle box;
		Rectangle bounds;
		Point loc;
		for (Iterator<Shape> it = model.iterator(); it.hasNext();) {
			Shape shape = (Shape) it.next();
			bounds = shape.getBounds();
			loc = shape.getLoc();
			box = new Rectangle(loc.x + bounds.width, loc.y + bounds.height, 6, 6);
			System.out.println("Bijour");
			if (box.contains(pnt)) {
				System.out.println("J'ai trouvé");
				return shape;
			}
		}
		return null;
	}

	private boolean copyShape() {
		SCollection model = (SCollection) this.getModel();

		for (Iterator<Shape> i = model.iterator(); i.hasNext();) {
			Shape shape = (Shape) i.next();
			if (((SelectionAttributes) (shape).getAttributes("select")).isSelected()) {

				Last_Selected_Copied_Shape = shape;
				((SelectionAttributes) (shape).getAttributes("select")).toggleSelection();
				cp_shape = addCopiedShape(shape);
				return true;
			}
		}
		return false;
	}

	private void pasteShape(KeyEvent evt) {
		SCollection model = (SCollection) this.getModel();

		if (cp_shape != null) {
			if (justCoppied == false) {
				Shape new_shape = addCopiedShape(Last_Selected_Copied_Shape);
				new_shape.translate(20, 20);
				model.add(new_shape);
				System.out.println("was here");
			} else {
				cp_shape.translate(20, 20);
				model.add(cp_shape);
			}
			justCoppied = false;

		}
		
		this.getView().repaint();

	}

	private void unselectAll() {
		Shape s = null;
		SCollection model = (SCollection) this.getModel();
		for (Iterator i = model.iterator(); i.hasNext();) {
			s = (Shape) i.next();
			((SelectionAttributes) s.getAttributes("select")).unselect();
		}
	}

	@Override
	public void keyTyped(KeyEvent evt) {

	}

	@Override
	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode();
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		int key = evt.getKeyCode();

		if ((evt.getKeyCode() == KeyEvent.VK_C) && ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			copyShape();
			System.out.println("coppied Shape");
		}

		if ((evt.getKeyCode() == KeyEvent.VK_V) && ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			pasteShape(evt);
			System.out.println("pasted Shape");
		}
		if ((evt.getKeyCode() == KeyEvent.VK_A) && ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			SelectAll();
			System.out.println("selected all Shapes");
		}
		if ((evt.getKeyCode() == KeyEvent.VK_R) && ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			removeAll();
			System.out.println("selected all Shapes");
		}
		if((evt.getKeyCode()==KeyEvent.VK_BACK_SPACE)){
			deleteselected();
		}

	}

	private Shape addCopiedShape(Shape shape) {
		Shape new_shape;
		if (shape instanceof SRectangle) {
			new_shape = createNewRect(shape);
			justCoppied = true;
			return (SRectangle) new_shape;
		} else if (shape instanceof SCircle) {
			new_shape = createNewCircle(shape);
			justCoppied = true;
			return (SCircle) new_shape;
		} else if (shape instanceof STriangle) {
			new_shape = createNewTriangle(shape);
			justCoppied = true;
			return (STriangle) new_shape;
		} else if (shape instanceof SText) {
			new_shape = createNewText(shape);
			justCoppied = true;
			return (SText) new_shape;
		} else if (shape instanceof SCollection) {
			new_shape = createNewCollection((SCollection)shape);
			justCoppied = true;
			return (SCollection) new_shape;
		}
		System.out.println("no way");
		return null;

	}

	private Shape createNewRect(Shape _shape) {
		Shape new_shape;
		new_shape = new SRectangle((SRectangle) _shape);
		new_shape.addAttributes(new ColorAttributes((ColorAttributes) _shape.getAttributes("color")));
		new_shape.addAttributes(new SelectionAttributes());
		return new_shape;

	}

	private Shape createNewCircle(Shape _shape) {
		Shape new_shape;
		new_shape = new SCircle((SCircle) _shape);
		new_shape.addAttributes(new ColorAttributes((ColorAttributes) _shape.getAttributes("color")));
		new_shape.addAttributes(new SelectionAttributes());
		return new_shape;

	}

	private Shape createNewTriangle(Shape _shape) {
		Shape new_shape;
		new_shape = new STriangle((STriangle)_shape);
		new_shape.addAttributes(new ColorAttributes((ColorAttributes) _shape.getAttributes("color")));
		new_shape.addAttributes(new SelectionAttributes());
		return new_shape;

	}

	private Shape createNewText(Shape _shape) {
		Shape new_shape;
		new_shape = new SText((SText) _shape);
		new_shape.addAttributes(new ColorAttributes((ColorAttributes) _shape.getAttributes("color")));
		new_shape.addAttributes(new SelectionAttributes());
		new_shape.addAttributes(new FontAttributes());
		return new_shape;

	}

	private Shape createNewCollection(SCollection _shape) {
		SCollection new_Collection = new SCollection();
		new_Collection.addAttributes(new SelectionAttributes());
		Shape s;
		for (Iterator<Shape> i = _shape.iterator(); i.hasNext();) {
			s = (Shape) i.next();
			if (s instanceof SRectangle) {
				new_Collection.add(createNewRect(s));
			} else if (s instanceof SCircle) {
				new_Collection.add(createNewCircle(s));
			} else if (s instanceof STriangle) {
				new_Collection.add(createNewTriangle(s));
			} else if (s instanceof SText) {
				new_Collection.add(createNewText(s));
			} else if (s instanceof SCollection) {
				new_Collection.add(createNewCollection((SCollection) s));
			}
		}
		return new_Collection;
	}
	private void SelectAll(){
		SCollection model = (SCollection) this.getModel();
		Shape s;
		for (Iterator<Shape> i = model.iterator(); i.hasNext();) {
			s = (Shape) i.next();
			((SelectionAttributes) s.getAttributes("select")).toggleSelection();
			this.getView().repaint();
		}
		
	}
	private void removeAll(){
		SCollection model = (SCollection) this.getModel();
		model.shapes=new ArrayList<>();
		this.getView().repaint();
		}
	private void deleteselected(){
		SCollection model = (SCollection) this.getModel();
		for (Iterator<Shape> i = model.iterator(); i.hasNext();) {
			Shape shape = (Shape) i.next();
			if (((SelectionAttributes) (shape).getAttributes("select")).isSelected()) {
				model.shapes.remove(shape);
		}
			this.getView().repaint();

	
	}
}
}
