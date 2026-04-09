package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ISPTest1 {

    @Test
    void testLegacyOldPrinterIsForcedToImplementUnsupportedFeatures() {
        org.example.isp1a.OldPrinter printer = new org.example.isp1a.OldPrinter();
        org.example.isp1a.Document document = new org.example.isp1a.Document("Dokumen");

        assertDoesNotThrow(() -> printer.print(document));
        assertThrows(UnsupportedOperationException.class, () -> printer.scan(document));
        assertThrows(UnsupportedOperationException.class, () -> printer.fax(document));
    }

    @Test
    void testRefactoredOldPrinterOnlyDependsOnPrintableInterface() {
        org.example.isp1b.OldPrinter printer = new org.example.isp1b.OldPrinter();
        org.example.isp1b.Document document = new org.example.isp1b.Document("Dokumen");

        assertDoesNotThrow(() -> printer.print(document));
        assertThrows(NoSuchMethodException.class,
                () -> org.example.isp1b.OldPrinter.class.getDeclaredMethod("scan", org.example.isp1b.Document.class));
        assertThrows(NoSuchMethodException.class,
                () -> org.example.isp1b.OldPrinter.class.getDeclaredMethod("fax", org.example.isp1b.Document.class));
    }
}
