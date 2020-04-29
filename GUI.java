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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

  // creates the handler object that is the mediator between the GUI and the data
  // structure
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
  @SuppressWarnings("unchecked")
  @Override
  public void start(Stage primaryStage) throws Exception {
    // initializes handler
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

    // Button for updating graph
    Button updateButt = new Button("Update Graph");

    // Implements importing of data
    in.setOnAction(value -> {
      try {
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(primaryStage);
        String importErrors = h.importFile(selectedDirectory);
        if (!importErrors.equals("")) {
          errorLabel.setText(importErrors);
          errorLabel.setTextFill(Color.web("990000"));
        } else {
          errorLabel.setText("No Errors");
          errorLabel.setTextFill(Color.web("009900"));
        }
      } catch (NullPointerException e) {
        // Happens when the DirectoryChooser is exited without selecting a file and
        // can be ignored
      } catch (Exception e) {

        // Updates error label when there is an error importing
        errorLabel.setText("Something went wrong with file importation");
        errorLabel.setTextFill(Color.web("990000"));
      }
    });

    // Creates an HBox for the input and output buttons
    HBox inOut = new HBox();
    inOut.getChildren().add(in);
    inOut.getChildren().add(new Label("     "));
    inOut.getChildren().add(out);

    // Creates an HBox for statistics
    HBox stats = new HBox();
    stats.setAlignment(Pos.CENTER);
    stats.setSpacing(10);

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

    // Initializes comboBoxes for sorting settings
    ComboBox<String> orderCombo = new ComboBox<String>();
    orderCombo.setPromptText("Sorting Order");
    orderCombo.getItems().add("Farm ID");
    orderCombo.getItems().add("Weight Ascending");
    orderCombo.getItems().add("Weight Decending");

    // Initializes ComboBox for the y axis type
    ComboBox<String> yAxisCombo = new ComboBox<String>();
    yAxisCombo.setPromptText("Y-Axis Options");
    yAxisCombo.getItems().add("Weight");
    yAxisCombo.getItems().add("Percent");

    // Creates a new vBox for the left side of the GUI
    VBox vbox = new VBox(10, new Label("Data File Controls:"), inOut, new Label(" "),
        new Label("Graph Controls:"), updateButt, new Label(" "), reportType, new Label(" "));
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

    // Adds months to monthComboBoxes
    monthCombo.getItems().add("January");
    monthCombo.getItems().add("February");
    monthCombo.getItems().add("March");
    monthCombo.getItems().add("April");
    monthCombo.getItems().add("May");
    monthCombo.getItems().add("June");
    monthCombo.getItems().add("July");
    monthCombo.getItems().add("August");
    monthCombo.getItems().add("September");
    monthCombo.getItems().add("October");
    monthCombo.getItems().add("November");
    monthCombo.getItems().add("December");

    monthCombo2.getItems().add("January");
    monthCombo2.getItems().add("February");
    monthCombo2.getItems().add("March");
    monthCombo2.getItems().add("April");
    monthCombo2.getItems().add("May");
    monthCombo2.getItems().add("June");
    monthCombo2.getItems().add("July");
    monthCombo2.getItems().add("August");
    monthCombo2.getItems().add("September");
    monthCombo2.getItems().add("October");
    monthCombo2.getItems().add("November");
    monthCombo2.getItems().add("December");

    // Adds all days to day comboBoxes
    for (int i = 1; i < 32; i++) {
      dayCombo.getItems().add(Integer.toString(i));
      dayCombo2.getItems().add(Integer.toString(i));
    }

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

        // Clear options in year and farm comboBoxes
        if (yearCombo.getItems() != null)
          yearCombo.getItems().clear();
        farmCombo.getItems().clear();
        yearCombo.setPromptText("Year");
        farmCombo.setPromptText("Farm ID");
        yAxisCombo.setPromptText("y-Axis");

        // Add all years to the year comboBox
        ArrayList<Year> years = h.getYears();
        for (int i = 0; i < years.size(); i++) {
          yearCombo.getItems().add(Integer.toString((years.get(i).year)));
        }

        // Add all FarmIDs to farmID comboBox
        yearCombo.setOnAction(v -> {
          if (reportType.getValue() != null) {
            if (reportType.getValue().contentEquals("Farm Report")) {
              farmCombo.getItems().clear();

              try {
                ArrayList<String> farmIDList =
                    h.getAllFarmIDs(Integer.parseInt(yearCombo.getValue()));
                // Sets values in farmCombo
                for (int i = 0; i < farmIDList.size(); i++) {
                  farmCombo.getItems().add(farmIDList.get(i));
                }
              } catch (Exception e) {
                // ignore
              }


            }
          }
        });

        // Displays year, farm, and yAxis combo boxes
        vbox.getChildren().add(yearCombo);
        vbox.getChildren().add(farmCombo);
        vbox.getChildren().add(space);
        vbox.getChildren().add(yAxisCombo);

        // Sets axes names
        xAxis.setLabel("Month");
        chartTitle.setText("Report for farm [id] for [year]");

        // Adds statistics\
        // do this with the button update
        // /////////////////////////////////////////////////////////////////////////////////////

        // sets color of graph
        for (Node n : chart.lookupAll(".default-color0.chart-bar")) {
          n.setStyle("-fx-bar-fill: blue;");
        }
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

        // Clear comboBoxes that will be used
        yearCombo.getItems().clear();
        yearCombo.setPromptText("Year");
        orderCombo.setPromptText("Sorting Order");
        yAxisCombo.setPromptText("y-Axis");

        // Add all years to the year comboBox
        ArrayList<Year> years = h.getYears();
        for (int i = 0; i < years.size(); i++) {
          yearCombo.getItems().add(Integer.toString((years.get(i).year)));
        }

        // Displays year, order, and yAxis comboBoxes
        vbox.getChildren().add(yearCombo);
        vbox.getChildren().add(space);
        vbox.getChildren().add(orderCombo);
        vbox.getChildren().add(yAxisCombo);

        // Sets axes names
        xAxis.setLabel("Farm ID");
        chartTitle.setText("Report for all farms for [year]");

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

        // Clear necessary comboBoxes that will be used
        yearCombo.getItems().clear();
        yearCombo.setPromptText("Year");
        yAxisCombo.setPromptText("y-Axis");
        orderCombo.setPromptText("Sorting Order");

        // Add all years to the year comboBox
        ArrayList<Year> years = h.getYears();
        for (int i = 0; i < years.size(); i++) {
          yearCombo.getItems().add(Integer.toString((years.get(i).year)));
        }

        // Displays year, month, order, and yAxis comboBoxes
        vbox.getChildren().add(yearCombo);
        vbox.getChildren().add(monthCombo);
        vbox.getChildren().add(space);
        vbox.getChildren().add(orderCombo);
        vbox.getChildren().add(yAxisCombo);

        // Sets axes names
        xAxis.setLabel("Farm ID");
        chartTitle.setText("Report for [month], [year] for all farms");

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
        // Clears out necessary date comboBoxes
        yearCombo.getItems().clear();
        yearCombo2.getItems().clear();
        yearCombo.setPromptText("Year");
        yearCombo2.setPromptText("Year2");
        yAxisCombo.setPromptText("y-Axis");
        orderCombo.setPromptText("Sorting Order");

        // Add all years to years comboBoxes
        ArrayList<Year> years = h.getYears();
        for (int i = 0; i < years.size(); i++) {
          yearCombo.getItems().add(Integer.toString((years.get(i).year)));
          yearCombo2.getItems().add(Integer.toString((years.get(i).year)));
        }

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

        // Sets axes names
        xAxis.setLabel("Farm ID");
        chartTitle
            .setText("Report for [month]/[day]/[year] to [month2]/[day2]/[year2] for all farms");

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

    // Adds a button
    // Sets left side of the border pane to the vBox
    bp.setLeft(vbox);

    // Creates vbox for the center
    VBox centerVbox = new VBox(chartTitle, chart, stats);
    bp.setCenter(centerVbox);

    // ADDING DATA TO THE GRAPH
    updateButt.setOnAction(value -> {
      if (reportType.getValue() != null) {

        // Farm Report
        if (reportType.getValue().equals("Farm Report")) {
          if (yearCombo.getValue() != null && farmCombo.getValue() != null
              && yAxisCombo.getValue() != null) {

            chart.getData().clear();
            XYChart.Series dataSeries1 = new XYChart.Series();

            // y = weight
            if (yAxisCombo.getValue().equals("Weight")) {

              // Iterates through all the months
              for (int i = 1; i < 13; i++) {
                int totWeightOfFarmInMonth = h.getTotalWeightOfFarmInMonth(farmCombo.getValue(), i,
                    Integer.parseInt(yearCombo.getValue()));

                dataSeries1.getData()
                    .add(new XYChart.Data(Integer.toString(i), totWeightOfFarmInMonth));
              }
            }
            // y = percentage
            else {
              double allFarmsTotalWeight = 0;
              double farmWeight = 0;

              // Iterates through all of the months
              for (int i = 1; i < 13; i++) {

                allFarmsTotalWeight = (double) h.getTotalWeightOfAllFarmsInMonth(i,
                    Integer.parseInt(yearCombo.getValue()));

                farmWeight = (double) h.getTotalWeightOfFarmInMonth(farmCombo.getValue(), i,
                    Integer.parseInt(yearCombo.getValue()));

                double weightPercent = (farmWeight / allFarmsTotalWeight) * 100.0;
                weightPercent = Math.round(weightPercent * 100.0) / 100.0;

                dataSeries1.getData().add(new XYChart.Data(Integer.toString(i), weightPercent));
              }
            }

            // Updates the chart
            chart.getData().add(dataSeries1);

            // Sets axes names
            chartTitle
                .setText("Report on " + farmCombo.getValue() + " for " + yearCombo.getValue());

            // Updates stats
            stats.getChildren().clear();
            stats.getChildren().add(new Label("Min:"));
            stats.getChildren().add(new Label("" + h.getMinWeightInYear(farmCombo.getValue(),
                Integer.parseInt(yearCombo.getValue()))));
            stats.getChildren().add(new Label("Max:"));
            stats.getChildren().add(new Label("" + h.getMaxWeightInYear(farmCombo.getValue(),
                Integer.parseInt(yearCombo.getValue()))));
            stats.getChildren().add(new Label("Average:"));
            stats.getChildren()
                .add(new Label("" + (h.getTotalWeightOfFarmInYear(farmCombo.getValue(),
                    Integer.parseInt(yearCombo.getValue())) / 12)));



            errorLabel.setTextFill(Color.web("009900"));
            errorLabel.setText("No Errors");
          }
          // Updates error message when fields not properly filled in
          else {
            errorLabel.setTextFill(Color.web("990000"));
            errorLabel.setText("Ensure that all graph options are selected correctly");
          }
        }

        // Annual Report
        if (reportType.getValue().equals("Annual Report")) {
          chart.getData().clear();

          if (yearCombo.getValue() != null && orderCombo.getValue() != null
              && yAxisCombo.getValue() != null) {

            ArrayList<String> allFarms = h.getAllFarmIDs(Integer.parseInt(yearCombo.getValue()));

            XYChart.Series dataSeries2 = new XYChart.Series();

            // x-axis is Farm ID

            // y axis is weight
            if (yAxisCombo.getValue().equals("Weight")) {

              // Iterates through all of the farms and adds their annual weight to the
              // dataSeries
              for (int i = 0; i < allFarms.size(); i++) {
                dataSeries2.getData()
                    .add(new XYChart.Data(allFarms.get(i), h.getTotalWeightOfFarmInYear(
                        (allFarms.get(i)), Integer.parseInt(yearCombo.getValue()))));
              }
            }
            // y axis is percentage
            else {
              double totAllFarms =
                  (double) h.getTotalWeightOfAllFarmsInYear(Integer.parseInt(yearCombo.getValue()));

              // Iterates through all of the farms to get their annualWeight
              for (int i = 0; i < allFarms.size(); i++) {
                double singleFarmTot = (double) h.getTotalWeightOfFarmInYear((allFarms.get(i)),
                    Integer.parseInt(yearCombo.getValue()));

                double weightPercent = (singleFarmTot / totAllFarms) * 100.0;
                weightPercent = Math.round(weightPercent * 100.0) / 100.0;

                dataSeries2.getData().add(new XYChart.Data(allFarms.get(i), weightPercent));
              }
            }

            // x-axis is Farm ascending --- cant figure out how to sort the data once it is in
            // dataSeries2
            if (orderCombo.getValue().equals("Weight Ascending")) {
              XYChart.Series ds3 = dataSeries2;
              Collections.sort(dataSeries2.getData(), new Comparator<XYChart.Data>() {

                public int compare(Data o1, Data o2) {
                  Number xValue1 = (Number) o1.getYValue();
                  Number xValue2 = (Number) o2.getYValue();
                  return new BigDecimal(xValue1.toString())
                      .compareTo(new BigDecimal(xValue2.toString()));
                }
              });
            //  chart.getData().add(dataSeries2);
            }


            // x-axis is Farm ID descending --- same as above, just opposite
            if (orderCombo.getValue().equals("Weight Decending")) {
              XYChart.Series ds3 = dataSeries2;
              Collections.sort(dataSeries2.getData(), new Comparator<XYChart.Data>() {

                public int compare(Data o1, Data o2) {
                  Number xValue1 = (Number) o1.getYValue();
                  Number xValue2 = (Number) o2.getYValue();
                  return new BigDecimal(xValue2.toString())
                      .compareTo(new BigDecimal(xValue1.toString()));
                }
              });
             // chart.getData().add(dataSeries2);
            }

            
            if (orderCombo.getValue().equals("Farm ID")) {
             // chart.getData().add(dataSeries2);
            }

            chart.getData().add(dataSeries2);
            System.out.println("is this running?");

            stats.getChildren().clear();
            stats.getChildren().add(new Label("Min:"));
            stats.getChildren().add(
                new Label("" + h.getMinWeightInYearForAll(Integer.parseInt(yearCombo.getValue()))));
            stats.getChildren().add(new Label("Max:"));
            stats.getChildren().add(
                new Label("" + h.getMaxWeightInYearForAll(Integer.parseInt(yearCombo.getValue()))));
            stats.getChildren().add(new Label("Average:"));
            stats.getChildren().add(new Label(""
                + (h.getTotalWeightOfAllFarmsInYear(Integer.parseInt(yearCombo.getValue())) / 12)));

            // Sets axes names
            chartTitle.setText("Report for all farms in " + yearCombo.getValue());
          }
          // Updates error message when fields not properly filled in
          else {
            errorLabel.setTextFill(Color.web("990000"));
            errorLabel.setText("Ensure that all graph options are selected correctly");
          }
        }

        // Monthly Report
        if (reportType.getValue().equals("Monthly Report")) {
          if (yearCombo.getValue() != null && farmCombo.getValue() != null
              && yAxisCombo.getValue() != null) {
            chart.getData().clear();
          }
          // Updates error message when fields not properly filled in
          else {
            errorLabel.setTextFill(Color.web("990000"));
            errorLabel.setText("Ensure that all graph options are selected correctly");
          }
        }

        // Date Range Report
        if (reportType.getValue().equals("Date Range Report")) {
          if (yearCombo.getValue() != null && monthCombo.getValue() != null
              && dayCombo.getValue() != null && yearCombo2.getValue() != null
              && monthCombo2.getValue() != null && dayCombo2.getValue() != null
              && orderCombo.getValue() != null && yAxisCombo.getValue() != null) {

            chart.getData().clear();
          }
          // Updates error message when fields not properly filled in
          else {
            errorLabel.setTextFill(Color.web("990000"));
            errorLabel.setText("Ensure that all graph options are selected correctly");
          }
        }
      } else {
        errorLabel.setTextFill(Color.web("990000"));
        errorLabel.setText("Ensure that a report type is selected");
      }

    });


    // SETTING UP BOTTOM for editing data
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
    if (errorLabel.getText().equals("No Errors"))
      errorLabel.setTextFill(Color.web("009900"));
    else
      errorLabel.setTextFill(Color.web("990000"));
    ScrollPane test4 = new ScrollPane();
    test4.setContent(errorLabel);


    // Makes a new vBox to set everything up in the bottom of the borderPane
    VBox bottomVbox = new VBox();
    bottomVbox.getChildren().add(new Label("Edit Data"));
    bottomVbox.getChildren().add(new Label(" "));
    bottomVbox.getChildren().add(editDataInput);
    bottomVbox.getChildren().add(new Label(" "));
    bottomVbox.getChildren().add(currentEditHbox);
    bottomVbox.getChildren().add(new Label(" "));
    bottomVbox.getChildren().add(test4);



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

    // for future printer implementation use
    // PrinterJob job = PrinterJob.createPrinterJob();
    // if(job != null){
    // job.showPrintDialog(primaryStage);
    // job.printPage(chart);
    // job.endJob();
    // }
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
