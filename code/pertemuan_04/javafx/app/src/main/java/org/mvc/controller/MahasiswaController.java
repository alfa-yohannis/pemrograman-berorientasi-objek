package org.mvc.controller;

import org.mvc.model.Mahasiswa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MahasiswaController {

  private final ObservableList<Mahasiswa> data = FXCollections.observableArrayList();

  @FXML
  private TextField txtNama;

  @FXML
  private TextField txtUmur;

  @FXML
  private TableView<Mahasiswa> tableMahasiswa;

  @FXML
  private TableColumn<Mahasiswa, String> colNama;

  @FXML
  private TableColumn<Mahasiswa, Number> colUmur;

  @FXML
  private void initialize() {
    colNama.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
    colUmur.setCellValueFactory(cellData -> cellData.getValue().umurProperty());
    tableMahasiswa.setItems(data);

    data.add(new Mahasiswa("Budi", 20));
    data.add(new Mahasiswa("Siti", 21));
  }

  @FXML
  private void tambahMahasiswa() {
    
    String nama = txtNama.getText() == null ? "" : txtNama.getText().trim();
    if (nama.isEmpty()) {
      return;
    }

    String umurText = txtUmur.getText() == null ? "" : txtUmur.getText().trim();
    int umur = 0;
    if (!umurText.isEmpty()) {
      try {
        umur = Integer.parseInt(umurText);
      } catch (NumberFormatException ex) {
        return;
      }
    }

    data.add(new Mahasiswa(nama, umur));
    txtNama.clear();
    txtUmur.clear();
  }
}
