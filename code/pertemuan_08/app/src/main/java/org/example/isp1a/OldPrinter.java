package org.example.isp1a;

public class OldPrinter implements Machine {
    @Override
    public void print(Document document) {
    }

    @Override
    public void scan(Document document) {
        throw new UnsupportedOperationException("Scan not supported");
    }

    @Override
    public void fax(Document document) {
        throw new UnsupportedOperationException("Fax not supported");
    }
}
