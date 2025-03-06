package org.example.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.Main;
import org.example.helpers.IForm;
import org.example.models.Item;
import org.example.models.Order;
import org.example.models.OrderDetail;
import org.example.models.OrderDetailPK;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SalesOrderController implements IForm {

    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextArea txtNote;
    @FXML
    private TableView<OrderItem> table;
    @FXML
    private TableColumn<OrderItem, Integer> colLine;
    @FXML
    private TableColumn<OrderItem, String> colCode, colName;
    @FXML
    private TableColumn<OrderItem, Double> colPrice, colQuantity, colTotal;
    @FXML
    private Button btnAddItem, btnRemoveItem, btnConfirm;

    private ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();
    private boolean isAddMode = false;

    private static String currentCode;

    public void initialize() {
        colLine.setCellValueFactory(cellData -> cellData.getValue().lineProperty().asObject());
        colCode.setCellValueFactory(cellData -> cellData.getValue().itemCodeProperty());
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        // Set colQuantity as editable
        colQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        colQuantity
                .setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        colQuantity.setOnEditCommit(event -> {
            OrderItem orderItem = event.getRowValue();
            double newQuantity = event.getNewValue();
            orderItem.setQuantity(newQuantity); // Update quantity in OrderItem
            calculateTotal(); // Recalculate total after quantity change
        });

        colTotal.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        table.setItems(orderItems);
        table.setEditable(true); // Enable editing in the table

        displayLastOrder();
    }

    @FXML
    private void newOrder() {
        isAddMode = true;
        enableDisableElements();
        clearForm();
    }

    @FXML
    private void findOrder() {
        // Implement find order logic
    }

    @FXML
    private void displayFirstOrder() {
        try (Session session = Main.getSessionFactory().openSession()) {
            Query<Order> query = session.createQuery(
                    "FROM Order o WHERE o.code = (SELECT MIN(o2.code) FROM Order o2)", Order.class);
            Order firstOrder = query.uniqueResult();

            if (firstOrder != null) {
                displayOrder(firstOrder.getCode());
            } else {
                System.out.println("No orders found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void displayPrevOrder() {
        try (Session session = Main.getSessionFactory().openSession()) {
            Query<Order> query = session.createQuery(
                    "FROM Order o WHERE o.code = (SELECT MAX(o2.code) FROM Order o2 WHERE o2.code < :currentCode)",
                    Order.class);
            query.setParameter("currentCode", txtCode.getText());

            Order prevOrder = query.uniqueResult();

            if (prevOrder != null) {
                displayOrder(prevOrder.getCode());
            } else {
                System.out.println("No previous order found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void displayNextOrder() {
        try (Session session = Main.getSessionFactory().openSession()) {
            Query<Order> query = session.createQuery(
                    "FROM Order o WHERE o.code = (SELECT MIN(o2.code) FROM Order o2 WHERE o2.code > :currentCode)",
                    Order.class);
            query.setParameter("currentCode", txtCode.getText());

            Order nextOrder = query.uniqueResult();

            if (nextOrder != null) {
                displayOrder(nextOrder.getCode());
            } else {
                System.out.println("No next order found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void displayLastOrder() {
        try (Session session = Main.getSessionFactory().openSession()) {
            Query<Order> query = session.createQuery(
                    "FROM Order o WHERE o.code = (SELECT MAX(o2.code) FROM Order o2)",
                    Order.class);

            Order lastOrder = query.uniqueResult();

            if (lastOrder != null) {
                displayOrder(lastOrder.getCode());
            } else {
                System.out.println("No orders found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayOrder(String orderCode) {
        try (Session session = Main.getSessionFactory().openSession()) {
            Order order = session.get(Order.class, orderCode);

            if (order != null) {
                currentCode = order.getCode();
                txtCode.setText(currentCode);
                txtDate.setText(order.getDate().toString());
                txtNote.setText(order.getNote());
                loadOrderItems(currentCode);

                isAddMode = false;
                enableDisableElements();
            } else {
                System.out.println("Order not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadOrderItems(String code) {
        orderItems.clear();
        try (Session session = Main.getSessionFactory().openSession()) {
            Query<OrderDetail> query = session.createQuery(
                    "FROM OrderDetail od WHERE od.order.code = :orderCode",
                    OrderDetail.class);
            query.setParameter("orderCode", code);

            List<OrderDetail> orderDetails = query.getResultList();

            for (OrderDetail od : orderDetails) {
                orderItems.add(new OrderItem(
                        od.getId().getLine(),
                        od.getItemCode(),
                        od.getName(),
                        od.getPrice(),
                        od.getQuantity()));
            }

            calculateTotal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addItem() {
        if (isAddMode) {
            openSelectForm();
        }
    }

    private void openSelectForm() {
        try {
            // Load SelectForm.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SelectForm.fxml"));
            Parent root = loader.load();

            // Get the controller and set the callback
            SelectFormController controller = loader.getController();

            // Set query for SelectFormController to load items with quantity > 0
            String query = "SELECT code, name, price FROM item WHERE quantity > 0";
            controller.loadTableData(query); // Assuming loadItems(query) is a method in SelectFormController

            // Set the OnSelectListener to handle selected item data
            controller.setOnSelectListener(values -> {
                if (values != null && values.length > 0) {
                    String itemCode = (String) values[0];
                    String itemName = (String) values[1];
                    double itemPrice = ((Number) values[2]).doubleValue(); // Convert price to double
                    double quantity = 1.0; // Default quantity

                    int line = orderItems.size() + 1;
                    orderItems.add(new OrderItem(line, itemCode, itemName, itemPrice, quantity));
                    calculateTotal();
                }
            });

            // Show SelectForm in a new modal window
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Select Item");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeItem() {
        if (isAddMode) {
            OrderItem selectedItem = table.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                orderItems.remove(selectedItem);
                calculateTotal();
            }
        }
    }

    @FXML
    private void confirmOrder() {
        if (isAddMode) {
            try (Session session = Main.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();

                // Step 1: Generate a new order code
                String maxCode = session.createQuery("SELECT MAX(o.code) FROM Order o", String.class).uniqueResult();
                String newCode = (maxCode != null) ? String.valueOf(Integer.parseInt(maxCode) + 1) : "1";

                // Step 2: Create and save new Order entity
                Order newOrder = new Order();
                newOrder.setCode(newCode);
                newOrder.setNote(txtNote.getText());
                newOrder.setDate(LocalDateTime.now());
                session.persist(newOrder);

                // Step 3: Process Order Items in Batch
                List<OrderDetail> orderDetails = new ArrayList<>();
                List<String> itemCodes = new ArrayList<>();

                for (OrderItem item : orderItems) {
                    OrderDetail orderDetail = new OrderDetail(
                            new OrderDetailPK(newCode, item.getLine()),
                            newOrder,
                            item.getItemCode(),
                            item.getName(),
                            item.getPrice(),
                            item.getQuantity());
                    orderDetails.add(orderDetail);
                    itemCodes.add(item.getItemCode());
                }

                // Bulk insert OrderDetail
                for (OrderDetail orderDetail : orderDetails) {
                    session.persist(orderDetail);
                }

                // Step 4: Update Item Stock in Bulk
                List<Item> itemsToUpdate = session.createQuery(
                        "FROM Item i WHERE i.code IN :codes", Item.class)
                        .setParameter("codes", itemCodes)
                        .list();

                Map<String, Item> itemMap = itemsToUpdate.stream()
                        .collect(Collectors.toMap(Item::getCode, item -> item));

                for (OrderItem item : orderItems) {
                    Item itemEntity = itemMap.get(item.getItemCode());
                    if (itemEntity != null) {
                        itemEntity.setQuantity(itemEntity.getQuantity() - item.getQuantity());
                        session.merge(itemEntity);
                    }
                }

                // Step 5: Commit transaction
                transaction.commit();

                // Finalize Order Process
                isAddMode = false;
                enableDisableElements();
                displayLastOrder(); // Display last order to confirm save

                // Show success message
                new Alert(Alert.AlertType.INFORMATION, "Order saved successfully.").showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void calculateTotal() {
        double total = orderItems.stream().mapToDouble(OrderItem::getTotal).sum();
        txtTotal.setText(String.format("%.2f", total));
    }

    private void enableDisableElements() {
        btnAddItem.setDisable(!isAddMode);
        btnRemoveItem.setDisable(!isAddMode);
        btnConfirm.setDisable(!isAddMode);
        txtNote.setEditable(isAddMode);
    }

    private void clearForm() {
        txtCode.clear();
        txtDate.clear();
        txtNote.clear();
        txtTotal.clear();
        orderItems.clear();
    }

    public class OrderItem {
        private final SimpleIntegerProperty line;
        private final SimpleStringProperty itemCode;
        private final SimpleStringProperty name;
        private final SimpleDoubleProperty price;
        private final SimpleDoubleProperty quantity;
        private final SimpleDoubleProperty total;

        public OrderItem(int line, String itemCode, String name, double price, double quantity) {
            this.line = new SimpleIntegerProperty(line);
            this.itemCode = new SimpleStringProperty(itemCode);
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleDoubleProperty(price);
            this.quantity = new SimpleDoubleProperty(quantity);
            this.total = new SimpleDoubleProperty(price * quantity);
        }

        // Getters
        public int getLine() {
            return line.get();
        }

        public String getItemCode() {
            return itemCode.get();
        }

        public String getName() {
            return name.get();
        }

        public double getPrice() {
            return price.get();
        }

        public double getQuantity() {
            return quantity.get();
        }

        public double getTotal() {
            return total.get();
        }

        // Setters for updating properties
        public void setQuantity(double quantity) {
            this.quantity.set(quantity);
            updateTotal();
        }

        public void setPrice(double price) {
            this.price.set(price);
            updateTotal();
        }

        // Property getters for JavaFX binding
        public SimpleIntegerProperty lineProperty() {
            return line;
        }

        public SimpleStringProperty itemCodeProperty() {
            return itemCode;
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public SimpleDoubleProperty priceProperty() {
            return price;
        }

        public SimpleDoubleProperty quantityProperty() {
            return quantity;
        }

        public SimpleDoubleProperty totalProperty() {
            return total;
        }

        // Private method to update the total when price or quantity changes
        private void updateTotal() {
            total.set(price.get() * quantity.get());
        }

        public void setLine(int i) {
            line.set(i);

        }
    }

    @Override
    public String getDocumentCode() {
        return txtCode.getText();
    }
}
