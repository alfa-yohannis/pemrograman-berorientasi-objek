package org.example;

import java.lang.reflect.Method;
import java.util.List;

import org.example.srp1b.Invoice;
import org.example.srp1b.InvoicePrinter;
import org.example.srp1b.InvoiceSaver;
import org.example.srp1b.Item;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class SRPTest1 {

    @Test
    void testInvoiceOnlyCalculatesTotal() {
        Method[] methods = Invoice.class.getDeclaredMethods();
        assertTrue(methods.length == 1, "Invoice should only have one method (calculateTotal)");
        assertEquals("calculateTotal", methods[0].getName(), "Invoice should only calculate totals");
    }

    @Test
    void testInvoicePrinterDoesNotDoBusinessLogic() {
        try {
            Method method = InvoicePrinter.class.getDeclaredMethod("print", Invoice.class);
            assertTrue(method.getReturnType() == void.class, "Printer should only print, not return business values");
        } catch (NoSuchMethodException e) {
            fail("InvoicePrinter should have a print method");
        }
    }

    @Test
    void testInvoiceSaverDoesNotCalculateTotals() {
        Method[] methods = InvoiceSaver.class.getDeclaredMethods();
        for (Method m : methods) {
            assertNotEquals("calculateTotal", m.getName(), "Saver should not calculate totals");
        }
    }

    @Test
    void testItemEncapsulatesDataOnly() {
        try {
            Method method = Item.class.getDeclaredMethod("getTotal");
            assertEquals(double.class, method.getReturnType(), "Item should expose total calculation");
        } catch (NoSuchMethodException e) {
            fail("Item should provide total calculation method");
        }
    }

    @Test
    void testEachClassCanBeTestedIndependently() {
        Invoice invoice = new Invoice(List.of(new Item(10.0, 1)));
        InvoicePrinter printer = new InvoicePrinter();
        InvoiceSaver saver = new InvoiceSaver();

        assertDoesNotThrow(() -> invoice.calculateTotal());
        assertDoesNotThrow(() -> printer.print(invoice));
        assertDoesNotThrow(() -> saver.saveToFile(invoice));
    }
}
