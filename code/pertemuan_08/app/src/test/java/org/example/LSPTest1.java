package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.example.lsp1a.RectangleResizer;
import org.junit.jupiter.api.Test;

public class LSPTest1 {

    @Test
    void testRectangleResizesToExpectedArea() {
        RectangleResizer resizer = new RectangleResizer();

        assertEquals(50, resizer.resizeRectangle(new org.example.lsp1a.Rectangle()));
    }

    @Test
    void testSquareViolatesRectangleContract() {
        RectangleResizer resizer = new RectangleResizer();

        assertNotEquals(50, resizer.resizeRectangle(new org.example.lsp1a.Square()));
    }

    @Test
    void testRefactoredShapesRemainSubstitutableThroughShape() {
        org.example.lsp1b.Shape rectangle = new org.example.lsp1b.Rectangle(5, 10);
        org.example.lsp1b.Shape square = new org.example.lsp1b.Square(5);

        assertEquals(50, rectangle.getArea());
        assertEquals(25, square.getArea());
    }

    @Test
    void testRefactoredSquareNoLongerExtendsRectangle() {
        assertEquals(Object.class, org.example.lsp1b.Square.class.getSuperclass());
    }
}
