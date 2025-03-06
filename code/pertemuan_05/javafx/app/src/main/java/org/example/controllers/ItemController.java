package org.example.controllers;

import java.util.List;

import org.example.Main;
import org.example.helpers.IForm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

public class ItemController implements IForm {

	@FXML
	private TableView<Item> table;
	@FXML
	private TableColumn<Item, String> colCode;
	@FXML
	private TableColumn<Item, String> colName;
	@FXML
	private TableColumn<Item, Double> colPrice;
	@FXML
	private TableColumn<Item, Double> colQuantity;

	private ObservableList<Item> items = FXCollections.observableArrayList();

	public void initialize() {
		// Initialize cell value factories
		colCode.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
		colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		colPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
		colQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

		// Set table items
		table.setItems(items);
		table.setEditable(true);

		// Make columns editable
		colCode.setCellFactory(TextFieldTableCell.forTableColumn());
		colName.setCellFactory(TextFieldTableCell.forTableColumn());
		colPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		colQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		// Set edit commit actions for each editable column
		colCode.setOnEditCommit(event -> {
			Item item = event.getRowValue();
			item.setCode(event.getNewValue());
			handleTableEdit(item, "code");
		});

		colName.setOnEditCommit(event -> {
			Item item = event.getRowValue();
			item.setName(event.getNewValue());
			handleTableEdit(item, "name");
		});

		colPrice.setOnEditCommit(event -> {
			Item item = event.getRowValue();
			item.setPrice(event.getNewValue());
			handleTableEdit(item, "price");
		});

		colQuantity.setOnEditCommit(event -> {
			Item item = event.getRowValue();
			item.setQuantity(event.getNewValue());
			handleTableEdit(item, "quantity");
		});

		// Load items into the table
		loadItems();

		items.add(new Item("", "", 0.0, 0.0));
	}

	private void loadItems() {
    try (Session session = Main.getSessionFactory().openSession()) {
        List<org.example.models.Item> itemList = session.createQuery("FROM Item", org.example.models.Item.class).list();

        // Convert Hibernate Item entity to JavaFX Item
        items.setAll(itemList.stream()
            .map(item -> new Item(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
            .toList());
    } catch (Exception e) {
        e.printStackTrace();
    }
}


private void handleTableEdit(Item item, String column) {
	try (Session session = Main.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();

			// Fetch the existing Hibernate entity
			org.example.models.Item existingItem = session.get(org.example.models.Item.class, item.getCode());

			if (existingItem == null) {
					// Convert JavaFX Item to Hibernate Item and insert
					org.example.models.Item newItem = new org.example.models.Item();
					newItem.setCode(item.getCode());
					newItem.setName(item.getName());
					newItem.setPrice(item.getPrice());
					newItem.setQuantity(item.getQuantity());

					session.persist(newItem);
			} else {
					// Update only the modified field
					switch (column) {
							case "code" -> existingItem.setCode(item.getCode());
							case "name" -> existingItem.setName(item.getName());
							case "price" -> existingItem.setPrice(item.getPrice());
							case "quantity" -> existingItem.setQuantity(item.getQuantity());
					}
					session.merge(existingItem);
			}

			transaction.commit();

			// Add an empty row if needed
			if (items.isEmpty() || !"".equals(items.get(items.size() - 1).getCode().trim())) {
					items.add(new Item("", "", 0.0, 0.0));
			}
	} catch (Exception e) {
			e.printStackTrace();
	}
}


	@FXML
	private void closeWindow() {
		table.getScene().getWindow().hide();
	}

	public static class Item {
		private final SimpleStringProperty code;
		private final SimpleStringProperty name;
		private final SimpleDoubleProperty price;
		private final SimpleDoubleProperty quantity;

		public Item(String code, String name, double price, double quantity) {
			this.code = new SimpleStringProperty(code);
			this.name = new SimpleStringProperty(name);
			this.price = new SimpleDoubleProperty(price);
			this.quantity = new SimpleDoubleProperty(quantity);
		}

		public String getCode() {
			return code.get();
		}

		public SimpleStringProperty codeProperty() {
			return code;
		}

		public void setCode(String code) {
			this.code.set(code);
		}

		public String getName() {
			return name.get();
		}

		public SimpleStringProperty nameProperty() {
			return name;
		}

		public void setName(String name) {
			this.name.set(name);
		}

		public double getPrice() {
			return price.get();
		}

		public SimpleDoubleProperty priceProperty() {
			return price;
		}

		public void setPrice(double price) {
			this.price.set(price);
		}

		public double getQuantity() {
			return quantity.get();
		}

		public SimpleDoubleProperty quantityProperty() {
			return quantity;
		}

		public void setQuantity(double quantity) {
			this.quantity.set(quantity);
		}
	}

	@Override
	public String getDocumentCode() {
		return null;
	}
}
