package org.mvc.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;;

public class Mahasiswa {
	private StringProperty nama = new SimpleStringProperty();
	private IntegerProperty umur = new SimpleIntegerProperty();
	
	public Mahasiswa(String nama, int umur) {
		this.nama.set(nama);
		this.umur.set(umur);
	}
	
	public StringProperty namaProperty() { return nama; }
	public IntegerProperty umurProperty() { return umur; }
}