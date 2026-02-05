package shape;

public class ShapeDemo {
		public static void main(String[] args) {
			Shape[] shapes = {
				new Rectangle("blue", 4, 5),
				new Triangle("green", 3, 6)
			};
			
			for (Shape shape : shapes) {
				System.out.println("Color: " + shape.getColor());
				System.out.println("Area: " + shape.getArea());
				
				if (shape instanceof Drawable) {
					((Drawable) shape).draw();
				}
				
				System.out.println();
			}
		}
	}
