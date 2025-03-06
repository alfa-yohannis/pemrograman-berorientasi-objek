package org.example.controllers;

import java.util.List;

import org.example.Main;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SelectFormController {

    @FXML
    private TableView<Object[]> table;
    @FXML
    private TextField textField;

    private ObservableList<Object[]> tableData = FXCollections.observableArrayList();
    private OnSelectListener onSelectListener;
    private String baseQuery;

    public void initialize() {
        // Initialization logic can be left empty or used for other setup if needed
    }

    public void loadTableData(String query) {
        baseQuery = query; // Store base query
        executeQuery(baseQuery);
    }

    private void executeQuery(String query) {
        try (Session session = Main.getSessionFactory().openSession()) {
            NativeQuery<Object[]> nativeQuery = session.createNativeQuery(query, Object[].class);
            List<Object[]> resultList = nativeQuery.list();

            // Clear table columns and existing data
            table.getColumns().clear();
            tableData.clear();

            if (!resultList.isEmpty()) {
                Object[] firstRow = resultList.get(0);
                int columnCount = firstRow.length;

                // Dynamically create columns
                for (int i = 0; i < columnCount; i++) {
                    final int colIndex = i;
                    TableColumn<Object[], String> column = new TableColumn<>("Column " + (i + 1));
                    column.setCellValueFactory(cellData ->
                            new SimpleStringProperty(
                                    cellData.getValue()[colIndex] != null ? cellData.getValue()[colIndex].toString() : ""
                            ));
                    table.getColumns().add(column);
                }

                tableData.setAll(resultList);
            }

            table.setItems(tableData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onFind() {
        String searchText = textField.getText().trim();
        if (searchText.isEmpty()) {
            executeQuery(baseQuery);
            return;
        }

        String searchQuery = baseQuery + " WHERE ";
        searchQuery += " CONCAT_WS(' ', * ) LIKE :searchText"; // Assumes search on all columns

        try (Session session = Main.getSessionFactory().openSession()) {
            NativeQuery<Object[]> query = session.createNativeQuery(searchQuery);
            query.setParameter("searchText", "%" + searchText + "%");

            List<Object[]> resultList = query.list();
            tableData.setAll(resultList);
            table.setItems(tableData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onSelect() {
        Object[] selectedRow = table.getSelectionModel().getSelectedItem();
        if (selectedRow != null && onSelectListener != null) {
            onSelectListener.select(selectedRow);
        }
        onCancel();
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void select(Object[] values);
    }
}
