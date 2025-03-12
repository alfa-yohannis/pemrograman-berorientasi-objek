package org.example;

import org.example.controllers.MainController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start; 

import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class MainControllerTest {

  private MainController mainController;
  private BorderPane rootPane;
  private MenuItem menuItemOpenItem;
  private MenuItem menuItemOpenSalesOrder;
  private MenuItem menuItemAbout;

  @Start
  private void start(Stage stage) {
    mainController = new MainController();
    rootPane = new BorderPane();
    menuItemOpenItem = new MenuItem("Open Item");
    menuItemOpenSalesOrder = new MenuItem("Open Sales Order");
    menuItemAbout = new MenuItem("About");

    mainController.setCenterPane(rootPane);
    mainController.setMenuItemOpenItem(menuItemOpenItem);
    mainController.setMenuItemOpenSalesOrder(menuItemOpenSalesOrder);
    mainController.setMenuItemAbout(menuItemAbout);

    stage.setScene(new Scene(rootPane, 800, 600));
    stage.show();
  }

  @BeforeEach
  void setup() {
    mainController.initialize();
  }

  @Test
  void testOpenItem(FxRobot robot) {
    robot.interact(() -> mainController.openItem());
    assert mainController.getCenterPane().getCenter() != null : "Item form was not loaded";
  }

  @Test
  void testOpenSalesOrder(FxRobot robot) {
    robot.interact(() -> mainController.openSalesOrder());
    assert mainController.getCenterPane().getCenter() != null : "Sales Order form was not loaded";
  }
}
