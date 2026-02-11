package shape;

	public class Rectangle extends Shape implements Drawable {
		private double width;
		private double height;
		
		public Rectangle(String color, double width, double height) {
			super(color);
			this.width = width;
			this.height = height;
		}
		
		@Override
		public double getArea() {
			return width * height;
		}
		
		@Override
		public void draw() {
			// System.out.println("Drawing a rectangle with width " + width + " and height " + height);
			System.out.println("|-----|");
			System.out.println("|     | ");
			System.out.println("|-----|");
		}
	}
