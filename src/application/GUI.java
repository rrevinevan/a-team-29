package application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 750;
	private static final String APP_TITLE = "Farming Logistics 9000";

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();
		int rt = 0;

		// Creates a new border pane that is the main layout for this GUI
		BorderPane bp = new BorderPane();
		
		// Buttons for import and export of file
		Button in = new Button("Import");
		Button out = new Button("Export");
		
		HBox inOut = new HBox();
		inOut.getChildren().add(in);
		inOut.getChildren().add(new Label("     "));
		inOut.getChildren().add(out);

		// Graph Type
		ComboBox<String> reportType = new ComboBox<String>();
		reportType.setPromptText("Choose Report Type");
		reportType.getItems().add("Farm Report");
		reportType.getItems().add("Annual Report");
		reportType.getItems().add("Monthly Report");
		reportType.getItems().add("Date Range Report");


		// Initializes the drop down menus
		ComboBox<String> yearCombo = new ComboBox<String>();
		yearCombo.setPromptText("Choose Year");

		ComboBox<String> monthCombo = new ComboBox<String>();
		monthCombo.setPromptText("Choose Month");

		ComboBox<String> dayCombo = new ComboBox<String>();
		dayCombo.setPromptText("Choose Day");

		ComboBox<String> farmCombo = new ComboBox<String>();
		farmCombo.setPromptText("Choose Farm");

		ComboBox<String> yearCombo2 = new ComboBox<String>();
		yearCombo2.setPromptText("Choose end Year");

		ComboBox<String> monthCombo2 = new ComboBox<String>();
		monthCombo2.setPromptText("Choose end Month");

		ComboBox<String> dayCombo2 = new ComboBox<String>();
		dayCombo2.setPromptText("Choose end Day");

		VBox vbox = new VBox(10, new Label("Data File Controls"), inOut, new Label(" "), reportType, new Label(" "));

		// Shows different things based on report selected
		reportType.setOnAction(value -> {
			if (reportType.getValue() != null && ((String) reportType.getValue()).equals("Farm Report")) {

				// Removes previous vBox options
				vbox.getChildren().remove(yearCombo);
				vbox.getChildren().remove(monthCombo);
				vbox.getChildren().remove(dayCombo);
				vbox.getChildren().remove(farmCombo);
				vbox.getChildren().remove(yearCombo2);
				vbox.getChildren().remove(monthCombo2);
				vbox.getChildren().remove(dayCombo2);

				// Add all years to the year combo box
				// Add all farms to the farm combo box

				// Displays year and farm combo boxes
				vbox.getChildren().add(yearCombo);
				vbox.getChildren().add(farmCombo);
				
				// Clear options in year and farm combo boxes
				yearCombo.getItems().clear();
				farmCombo.getItems().clear();

			} else if (reportType.getValue() != null && ((String) reportType.getValue()).equals("Annual Report")) {

				// Removes previous vBox options
				vbox.getChildren().remove(yearCombo);
				vbox.getChildren().remove(monthCombo);
				vbox.getChildren().remove(dayCombo);
				vbox.getChildren().remove(farmCombo);
				vbox.getChildren().remove(yearCombo2);
				vbox.getChildren().remove(monthCombo2);
				vbox.getChildren().remove(dayCombo2);

				// Add all years to the year combo box

				// Displays year combo box
				vbox.getChildren().add(yearCombo);
				
				// Clears out year combo box
				yearCombo.getItems().clear();

			} else if (reportType.getValue() != null && ((String) reportType.getValue()).equals("Monthly Report")) {

				// Removes previous vBox options
				vbox.getChildren().remove(yearCombo);
				vbox.getChildren().remove(monthCombo);
				vbox.getChildren().remove(dayCombo);
				vbox.getChildren().remove(farmCombo);
				vbox.getChildren().remove(yearCombo2);
				vbox.getChildren().remove(monthCombo2);
				vbox.getChildren().remove(dayCombo2);

				// Add all years and months to combo boxes

				// Displays year and month combo boxes
				vbox.getChildren().add(yearCombo);
				vbox.getChildren().add(monthCombo);
				
				// Clears out year and month combo boxes
				yearCombo.getItems().clear();
				monthCombo.getItems().clear();

			} else if (reportType.getValue() != null && ((String) reportType.getValue()).equals("Date Range Report")) {

				// Removes previous vBox options
				vbox.getChildren().remove(yearCombo);
				vbox.getChildren().remove(monthCombo);
				vbox.getChildren().remove(dayCombo);
				vbox.getChildren().remove(farmCombo);
				vbox.getChildren().remove(yearCombo2);
				vbox.getChildren().remove(monthCombo2);
				vbox.getChildren().remove(dayCombo2);

				// Add all years, months, & days to date combo boxes

				// Displays date combo boxes
				vbox.getChildren().add(yearCombo);
				vbox.getChildren().add(monthCombo);
				vbox.getChildren().add(dayCombo);
				vbox.getChildren().add(yearCombo2);
				vbox.getChildren().add(monthCombo2);
				vbox.getChildren().add(dayCombo2);
				
				// Clears out date combo boxes
				yearCombo.getItems().clear();
				monthCombo.getItems().clear();
				dayCombo.getItems().clear();
				yearCombo2.getItems().clear();
				monthCombo2.getItems().clear();
				dayCombo2.getItems().clear();

			} else {
		        // Throw error for incorrect input here
			}
		});
		
		bp.setLeft(vbox);

		Scene mainScene = new Scene(bp, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
