package org.example.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.example.helpers.IForm;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class MainController {

	@FXML
	private MenuItem menuItemPrint;
	@FXML
	private MenuItem menuItemClose;
	@FXML
	private MenuItem menuItemOpenItem;
	@FXML
	private MenuItem menuItemAbout;
	@FXML
	private MenuItem menuItemOpenSalesOrder;
	@FXML
	private BorderPane centerPane;

	private IForm currentForm;

	public void initialize() {
		System.out.println("Main Controller Initialized");
	}

	@FXML
	private void openItem() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Item.fxml"));
			Parent itemFormRoot = loader.load();
			centerPane.setCenter(itemFormRoot);
			currentForm = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
			showAlert("Error", "Could not open Item Form.");
		}
	}

	@FXML
	private void openSalesOrder() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/SalesOrder.fxml"));
			Parent salesOrderRoot = loader.load();
			centerPane.setCenter(salesOrderRoot);
			currentForm = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
			showAlert("Error", "Could not open Sales Order Form.");
		}
	}

	@FXML
	private void showAbout() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("Order Form Application");
		alert.setContentText("Developed with JavaFX and BIRT for report generation.");
		alert.showAndWait();
	}

	@FXML
	private void printReport() {
		new Thread(() -> {
			try {
				EngineConfig config = new EngineConfig();
				Platform.startup(config);
				IReportEngineFactory factory = (IReportEngineFactory) Platform
						.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);

				if (factory == null) {
					System.out.println(factory);
				}

				IReportEngine engine = factory.createReportEngine(config);

				IReportRunnable design = engine.openReportDesign(
						"/data2/projects/pemrograman-berorientasi-objek/code/pertemuan_05/javafx/app/build/resources/main/test.rptdesign");
				IRunAndRenderTask task = engine.createRunAndRenderTask(design);
				task.setParameterValue("order_code", currentForm.getDocumentCode());
				PDFRenderOption options = new PDFRenderOption();
				options.setOutputFileName("document.pdf");
				options.setOutputFormat("pdf");

				task.setRenderOption(options);
				task.run();
				task.close();
				engine.destroy();

				if (Desktop.isDesktopSupported()) {
					File myFile = new File("document.pdf");
					Desktop.getDesktop().open(myFile);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				Platform.shutdown();
			}
		}).start();

	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
