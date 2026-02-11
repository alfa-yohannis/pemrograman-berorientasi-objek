package shape;

public class Triangle extends Shape implements Drawable {
		private double base;
		private double height;
		
		public Triangle(String color, double base, double height) {
			super(color);
			this.base = base;
			this.height = height;
		}
		
		@Override
		public double getArea() {
			return 0.5 * base * height;
		}
		
		@Override
		public void draw() {
			// System.out.println("Drawing a triangle with base " + base + " and height " + height);
			System.out.println("  /\\  ");
			System.out.println(" /  \\ ");
			System.out.println("/----\\");
		}
	}
