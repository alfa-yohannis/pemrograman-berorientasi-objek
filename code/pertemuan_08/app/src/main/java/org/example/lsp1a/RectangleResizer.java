package org.example.lsp1a;

public class RectangleResizer {
    public int resizeRectangle(Rectangle rectangle) {
        rectangle.setWidth(5);
        rectangle.setHeight(10);
        return rectangle.getArea();
    }
}
