/**
 * GUI created by Evan Grubis & Andrew Meyers in a-team-29-project
 * 
 * Authors: Andrew Meyers (ajmeyers4@wisc.edu) Evan Grubis (egrubis@wisc.edu)
 * 
 * Date: 04/20/20
 *
 * Course: CS 400 Semester: Spring 2020 Lecture: 002
 *
 * IDE: Eclipse IDE for Java Developers OS: Windows 10 Home
 */

package application;

import java.io.File;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * This class is JavaFX Application that is used to make the Farming Logistics 9000 GUI Application.
 * This application is used to help users analyze and manipulate data about farms milk production.
 * 
 * @author - Evan Grubis
 * @author - Andrew Meyers
 */
public class GUI extends Application {
  // store any command-line arguments that were entered.
  // NOTE: this.getParameters().getRaw() will get these also
  private List<String> args;

  // creates the handler class that is the mediator between the GUI and the data structure
  private Handler h;

  private static final int WINDOW_WIDTH = 1000;
  private static final int WINDOW_HEIGHT = 600;
  private static final String APP_TITLE = "Farming Logistics 9000";
  private static final Font ITALIC_FONT =
      Font.font("Serif", FontPosture.ITALIC, Font.getDefault().getSize());

  /**
   * This method is used to start the JavaFX Application
   * 
   * @param primaryStage - The primary stage to be used for this GUI
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    // initialize handler
    h = new Handler();

    // Create error label
    Label errorLabel = new Label("No Errors");
    errorLabel.setFont(ITALIC_FONT);


    // save args example
    args = this.getParameters().getRaw();
    int rt = 0;

    // Creates a new border pane that is the main layout for this GUI
    BorderPane bp = new BorderPane();

    // Buttons for import and export of file
    Button in = new Button("Import");
    Button out = new Button("Export");

    // Implements importing of data
    in.setOnAction(value -> {
      try {
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(primaryStage);
        h.importFile(selectedDirectory);
        errorLabel.setText("No Errors");
        errorLabel.setTextFill(Color.web("00FF00"));
      } catch (NullPointerException e) {
        // this happens when the DirectoryChooser is exited without selecting a file and can be
        // ignored
      } catch (Exception e) {
        errorLabel.setText("There was an error while importing a file");
        errorLabel.setTextFill(Color.web("990000"));
      }
    });

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

    // Initializes comboboxes for graph settings
    ComboBox<String> orderCombo = new ComboBox<String>();
    orderCombo.setPromptText("Choose sorting method");
    orderCombo.getItems().add("Farm ID");
    orderCombo.getItems().add("Weight ascending");
    orderCombo.getItems().add("Weight decending");

    ComboBox<String> yAxisCombo = new ComboBox<String>();
    yAxisCombo.setPromptText("Y Axis");
    yAxisCombo.getItems().add("Weight");
    yAxisCombo.getItems().add("Percent");

    // Creates a new vBox for the left side of the GUI
    VBox vbox = new VBox(10, new Label("Data File Controls"), inOut, new Label(" "), reportType,
        new Label(" "));
    vbox.setTranslateX(5);

    // Creates the axes and title for the bar chart
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel(" ");
    Label chartTitle = new Label("");

    // Make bar chart
    BarChart<CategoryAxis, NumberAxis> chart = new BarChart(xAxis, yAxis);
    chart.setAnimated(false);
    chart.setLegendVisible(false);

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
        vbox.getChildren().remove(yAxisCombo);

        // Add all years to the year comboBox
        // Add all farms to the farm comboBox

        // Displays year and farm combo boxes
        vbox.getChildren().add(yearCombo);
        vbox.getChildren().add(farmCombo);
        vbox.getChildren().add(space);
        vbox.getChildren().add(yAxisCombo);

        // Resets y axis
        yAxisCombo.setValue("Weight");
        yAxis.setLabel("Weight");

        // Clear options in year and farm comboBoxes
        yearCombo.getItems().clear();
        farmCombo.getItems().clear();

        // Sets axes names
        xAxis.setLabel("Month");
        chartTitle.setText("Report for farm [id] for [year]");

        // adds data to the graph
        chart.getData().clear();

        //this is what needs to be implemented for the graphs to show the data
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.getData().add(new XYChart.Data("1", 178));
        dataSeries1.getData().add(new XYChart.Data("2", 65));
        dataSeries1.getData().add(new XYChart.Data("3", 23));

        chart.getData().add(dataSeries1);

        // sets color of graph
        for (Node n : chart.lookupAll(".default-color0.chart-bar"))
          n.setStyle("-fx-bar-fill: blue;");
      }
      // Annual Report selected
      else if (reportType.getValue() != null
          && ((String) reportType.getValue()).equals("Annual Report")) {

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
        vbox.getChildren().remove(yAxisCombo);

        // Add all years to the year comboBox

        // Displays year and order comboBoxes
        vbox.getChildren().add(yearCombo);
        vbox.getChildren().add(space);
        vbox.getChildren().add(orderCombo);
        vbox.getChildren().add(yAxisCombo);

        // Resets y axis
        yAxisCombo.setValue("Weight");
        yAxis.setLabel("Weight");

        // Clears out year comboBox
        yearCombo.getItems().clear();

        // Sets axes names
        xAxis.setLabel("Farm ID");
        chartTitle.setText("Report for all farms for [year]");

        // adds data to the graph
        chart.getData().clear();

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.getData().add(new XYChart.Data("1", 27));
        dataSeries2.getData().add(new XYChart.Data("2", 60));
        dataSeries2.getData().add(new XYChart.Data("3", 44));

        chart.getData().add(dataSeries2);

        // sets color of graph
        for (Node n : chart.lookupAll(".default-color0.chart-bar"))
          n.setStyle("-fx-bar-fill: orange;");
      }
      // Monthly Report Selected
      else if (reportType.getValue() != null
          && ((String) reportType.getValue()).equals("Monthly Report")) {

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
        vbox.getChildren().remove(yAxisCombo);

        // Resets y axis
        yAxisCombo.setValue("Weight");
        yAxis.setLabel("Weight");

        // Add all years and months to comboBoxes

        // Displays year, month, and order comboBoxes
        vbox.getChildren().add(yearCombo);
        vbox.getChildren().add(monthCombo);
        vbox.getChildren().add(space);
        vbox.getChildren().add(orderCombo);
        vbox.getChildren().add(yAxisCombo);

        // Clears out year and month comboBoxes
        yearCombo.getItems().clear();
        monthCombo.getItems().clear();

        // Sets axes names
        xAxis.setLabel("Farm ID");
        chartTitle.setText("Report for [month], [year] for all farms");

        // adds data to the graph
        chart.getData().clear();

        XYChart.Series dataSeries3 = new XYChart.Series();
        dataSeries3.getData().add(new XYChart.Data("2", 43));
        dataSeries3.getData().add(new XYChart.Data("5", 200));
        dataSeries3.getData().add(new XYChart.Data("7", 57));
        dataSeries3.getData().add(new XYChart.Data("34", 20));
        dataSeries3.getData().add(new XYChart.Data("654", 33));

        chart.getData().add(dataSeries3);

        // sets color of graph
        for (Node n : chart.lookupAll(".default-color0.chart-bar"))
          n.setStyle("-fx-bar-fill: green;");
      }
      // Date Range Report Selected
      else if (reportType.getValue() != null
          && ((String) reportType.getValue()).equals("Date Range Report")) {

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
        vbox.getChildren().remove(yAxisCombo);


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
        vbox.getChildren().add(yAxisCombo);

        // Resets y axis
        yAxisCombo.setValue("Weight");
        yAxis.setLabel("Weight");

        // Clears out date comboBoxes
        yearCombo.getItems().clear();
        monthCombo.getItems().clear();
        dayCombo.getItems().clear();
        yearCombo2.getItems().clear();
        monthCombo2.getItems().clear();
        dayCombo2.getItems().clear();

        // Sets axes names
        xAxis.setLabel("Farm ID");
        chartTitle
            .setText("Report for [month]/[day]/[year] to [month2]/[day2]/[year2] for all farms");

        // adds data to the graph
        chart.getData().clear();

        XYChart.Series dataSeries4 = new XYChart.Series();
        dataSeries4.getData().add(new XYChart.Data("2", 43));
        dataSeries4.getData().add(new XYChart.Data("5", 200));
        dataSeries4.getData().add(new XYChart.Data("7", 57));
        dataSeries4.getData().add(new XYChart.Data("34", 20));
        dataSeries4.getData().add(new XYChart.Data("654", 33));
        dataSeries4.getData().add(new XYChart.Data("3423", 57));
        dataSeries4.getData().add(new XYChart.Data("234234", 88));
        dataSeries4.getData().add(new XYChart.Data("2342343242", 343));

        chart.getData().add(dataSeries4);

        // sets color of graph
        for (Node n : chart.lookupAll(".default-color0.chart-bar"))
          n.setStyle("-fx-bar-fill: red;");
      }
    });

    // Pick y axis label
    yAxisCombo.setOnAction(value -> {
      if (yAxisCombo.getValue() != null && ((String) yAxisCombo.getValue()).equals("Weight"))
        yAxis.setLabel("Weight");
      else
        yAxis.setLabel("Percentage");
    });


    // Sets left side of the border pane to the vBox
    bp.setLeft(vbox);


    // Add test data


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
    HBox currentEditHbox =
        new HBox(new Label("Current:   "), currentTextField, new Label("   "), deleteButton);



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

    // STARTS USING THE HANDLER


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
