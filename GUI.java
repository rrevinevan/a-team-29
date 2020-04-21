/**
 * GUI created by Evan Grubis & Andrew Meyers in a-team-29-project
 * 
 * Authors:    Andrew Meyers (ajmeyers4@wisc.edu)
 * 			   Evan Grubis (egrubis@wisc.edu)
 * 
 * Date:      04/20/20
 *
 * Course:	  CS 400
 * Semester:  Spring 2020
 * Lecture:   002
 *
 * IDE:       Eclipse IDE for Java Developers
 * OS:        Windows 10 Home
 */

package application;

import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

/**
 * This class is JavaFX Application that is used to make the Farming Logistics
 * 9000 GUI Application. This application is used to help users analyze and
 * manipulate data about farms milk production.
 * 
 * @author - Evan Grubis
 * @author - Andrew Meyers
 */
public class GUI extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "Farming Logistics 9000";

	/**
	 * This method is used to start the JavaFX Application
	 * 
	 * @param primaryStage - The primary stage to be used for this GUI
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		final Font ITALIC_FONT = Font.font("Serif", FontPosture.ITALIC, Font.getDefault().getSize());
		// save args example
		args = this.getParameters().getRaw();
		int rt = 0;

		// Creates a new border pane that is the main layout for this GUI
		BorderPane bp = new BorderPane();

		// Buttons for import and export of file
		Button in = new Button("Import");
		Button out = new Button("Export");

		// Creates an HBox for the input and output buttons
		HBox inOut = new HBox();
		inOut.getChildren().add(in);
		inOut.getChildren().add(new Label("     "));
		inOut.getChildren().add(out);

		// Sets up comboBox for report types that will determine graph type
		ComboBox<String> reportType = new ComboBox<String>();
		reportType.setPromptText("Choose Report Type");
		reportType.getItems().add("Farm Report");
		reportType.getItems().add("Annual Report");
		reportType.getItems().add("Monthly Report");
		reportType.getItems().add("Date Range Report");

		// Initializes the comboBoxes for each report type
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

		Label space = new Label(" ");

		// Initializes a comboBox for
		ComboBox<String> orderCombo = new ComboBox<String>();
		orderCombo.setPromptText("Choose sorting method");
		orderCombo.getItems().add("Farm ID");
		orderCombo.getItems().add("Weight ascending");
		orderCombo.getItems().add("Weight decending");

		// Creates a new vBox for the left side of the GUI
		VBox vbox = new VBox(10, new Label("Data File Controls"), inOut, new Label(" "), reportType, new Label(" "));

		// Creates the axes and title for the bar chart
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		Label chartTitle = new Label("");

		// Make bar chart
		BarChart<CategoryAxis, NumberAxis> chart = new BarChart(xAxis, yAxis);

		// Shows different comboBoxes depending on what report type is selected
		reportType.setOnAction(value -> {

			// Farm Report selected
			if (reportType.getValue() != null && ((String) reportType.getValue()).equals("Farm Report")) {

				// Removes previous vBox options
				vbox.getChildren().remove(yearCombo);
				vbox.getChildren().remove(monthCombo);
				vbox.getChildren().remove(dayCombo);
				vbox.getChildren().remove(farmCombo);
				vbox.getChildren().remove(yearCombo2);
				vbox.getChildren().remove(monthCombo2);
				vbox.getChildren().remove(dayCombo2);
				vbox.getChildren().remove(space);
				vbox.getChildren().remove(orderCombo);

				// Add all years to the year comboBox
				// Add all farms to the farm comboBox

				// Displays year and farm combo boxes
				vbox.getChildren().add(yearCombo);
				vbox.getChildren().add(farmCombo);

				// Clear options in year and farm comboBoxes
				yearCombo.getItems().clear();
				farmCombo.getItems().clear();

				// Sets axes names
				xAxis.setLabel("Month");
				yAxis.setLabel("Weight");
				chartTitle.setText("Report for farm [id] for [year]");

				// adds data to the graph

			}
			// Annual Report selected
			else if (reportType.getValue() != null && ((String) reportType.getValue()).equals("Annual Report")) {

				// Removes previous vBox options
				vbox.getChildren().remove(yearCombo);
				vbox.getChildren().remove(monthCombo);
				vbox.getChildren().remove(dayCombo);
				vbox.getChildren().remove(farmCombo);
				vbox.getChildren().remove(yearCombo2);
				vbox.getChildren().remove(monthCombo2);
				vbox.getChildren().remove(dayCombo2);
				vbox.getChildren().remove(space);
				vbox.getChildren().remove(orderCombo);

				// Add all years to the year comboBox

				// Displays year and order comboBoxes
				vbox.getChildren().add(yearCombo);
				vbox.getChildren().add(space);
				vbox.getChildren().add(orderCombo);

				// Clears out year comboBox
				yearCombo.getItems().clear();

				// Sets axes names
				xAxis.setLabel("Farm ID");
				yAxis.setLabel("Weight / Percent");
				chartTitle.setText("Report for all farms for [year]");

			}
			// Monthly Report Selected
			else if (reportType.getValue() != null && ((String) reportType.getValue()).equals("Monthly Report")) {

				// Removes previous vBox options
				vbox.getChildren().remove(yearCombo);
				vbox.getChildren().remove(monthCombo);
				vbox.getChildren().remove(dayCombo);
				vbox.getChildren().remove(farmCombo);
				vbox.getChildren().remove(yearCombo2);
				vbox.getChildren().remove(monthCombo2);
				vbox.getChildren().remove(dayCombo2);
				vbox.getChildren().remove(space);
				vbox.getChildren().remove(orderCombo);

				// Add all years and months to comboBoxes

				// Displays year, month, and order comboBoxes
				vbox.getChildren().add(yearCombo);
				vbox.getChildren().add(monthCombo);
				vbox.getChildren().add(space);
				vbox.getChildren().add(orderCombo);

				// Clears out year and month comboBoxes
				yearCombo.getItems().clear();
				monthCombo.getItems().clear();

				// Sets axes names
				xAxis.setLabel("Farm ID");
				yAxis.setLabel("Weight / Percent");
				chartTitle.setText("Report for [month], [year] for all farms");

			}
			// Date Range Report Selected
			else if (reportType.getValue() != null && ((String) reportType.getValue()).equals("Date Range Report")) {

				// Removes previous vBox options
				vbox.getChildren().remove(yearCombo);
				vbox.getChildren().remove(monthCombo);
				vbox.getChildren().remove(dayCombo);
				vbox.getChildren().remove(farmCombo);
				vbox.getChildren().remove(yearCombo2);
				vbox.getChildren().remove(monthCombo2);
				vbox.getChildren().remove(dayCombo2);
				vbox.getChildren().remove(space);
				vbox.getChildren().remove(orderCombo);

				// Add all years, months, & days to date combo boxes

				// Displays date and order comboBoxes
				vbox.getChildren().add(yearCombo);
				vbox.getChildren().add(monthCombo);
				vbox.getChildren().add(dayCombo);
				vbox.getChildren().add(yearCombo2);
				vbox.getChildren().add(monthCombo2);
				vbox.getChildren().add(dayCombo2);
				vbox.getChildren().add(space);
				vbox.getChildren().add(orderCombo);

				// Clears out date comboBoxes
				yearCombo.getItems().clear();
				monthCombo.getItems().clear();
				dayCombo.getItems().clear();
				yearCombo2.getItems().clear();
				monthCombo2.getItems().clear();
				dayCombo2.getItems().clear();

				// Sets axes names
				xAxis.setLabel("Farm ID");
				yAxis.setLabel("Weight / Percent");
				chartTitle.setText("Report for [month]/[day]/[year] to [month2]/[day2]/[year2] for all farms");
			}
		});

		// Sets left side of the border pane to the vBox
		bp.setLeft(vbox);

		// Creates vbox for the center
		VBox centerVbox = new VBox(chartTitle, chart);
		bp.setCenter(centerVbox);

		// Setting up bottom
		// Instantiates combo boxes for input to edit data that are stored in a HBox
		HBox editDataInput = new HBox();

		// Makes drop down boxes for editing data
		ComboBox<String> bottomFarmIDCombo = new ComboBox<String>();
		bottomFarmIDCombo.setPromptText("Farm ID");
		ComboBox<String> bottomYearCombo = new ComboBox<String>();
		bottomYearCombo.setPromptText("Year");
		ComboBox<String> bottomMonthCombo = new ComboBox<String>();
		bottomMonthCombo.setPromptText("Month");
		ComboBox<String> bottomDayCombo = new ComboBox<String>();
		bottomDayCombo.setPromptText("Day");
		ComboBox<String> bottomWeightCombo = new ComboBox<String>();
		bottomWeightCombo.setPromptText("Milk Weight");
		
		// Adds combo boxes to the editDataInput hbox
		editDataInput.getChildren().add(bottomFarmIDCombo);
		editDataInput.getChildren().add(new Label("     "));
		editDataInput.getChildren().add(bottomYearCombo);
		editDataInput.getChildren().add(new Label("     "));
		editDataInput.getChildren().add(bottomMonthCombo);
		editDataInput.getChildren().add(new Label("     "));
		editDataInput.getChildren().add(bottomDayCombo);
		editDataInput.getChildren().add(new Label("     "));
		editDataInput.getChildren().add(bottomWeightCombo);
		editDataInput.getChildren().add(new Label("          "));

		Button editOrAdd = new Button("Execute Edit or Addition");
		editDataInput.getChildren().add(editOrAdd);

		// Sets up TextField and button used for removing data
		TextField currentTextField = new TextField();
		currentTextField.setMaxWidth(50);
		Button deleteButton = new Button("Delete");

		// Makes another HBox to handle text input from the user used for removing data
		HBox currentEditHbox = new HBox(new Label("Current:   "), currentTextField, new Label("   "), deleteButton);

		// Create error label
		Label errorLabel = new Label("No Errors");
		errorLabel.setFont(ITALIC_FONT);

		// Set error label
		// errorLabel.setText("");
		if (errorLabel.getText().equals("No Errors"))
			errorLabel.setTextFill(Color.web("009900"));
		else
			errorLabel.setTextFill(Color.web("990000"));

		// Makes a new vBox to set everything up in the bottom of the borderPane
		VBox bottomVbox = new VBox();
		bottomVbox.getChildren().add(new Label("Edit Data"));
		bottomVbox.getChildren().add(new Label(" "));
		bottomVbox.getChildren().add(editDataInput);
		bottomVbox.getChildren().add(new Label(" "));
		bottomVbox.getChildren().add(currentEditHbox);
		bottomVbox.getChildren().add(new Label(" "));
		bottomVbox.getChildren().add(new Label("Errors:"));
		bottomVbox.getChildren().add(errorLabel);
		bottomVbox.getChildren().add(new Label(" "));

		// Organizes everything in the bottom
		editDataInput.setAlignment(Pos.CENTER);
		currentEditHbox.setAlignment(Pos.CENTER);
		bottomVbox.setAlignment(Pos.CENTER);
		bp.setBottom(bottomVbox);

		// Creates the mainScene of the GUI
		Scene mainScene = new Scene(bp, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * Main method is just used to launch the GUI
	 * 
	 * @param args - arguments not used
	 */
	public static void main(String[] args) {
		launch(args);
	}
}