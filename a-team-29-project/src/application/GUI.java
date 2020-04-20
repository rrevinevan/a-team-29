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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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

    // Buttons for import and export of file
    Button in = new Button("Import");
    Button out = new Button("Export");

    // Graph Type
    ComboBox<String> reportType = new ComboBox<String>();
    reportType.getItems().add("Farm Report");
    reportType.getItems().add("Annual Report");
    reportType.getItems().add("Monthly Report");
    reportType.getItems().add("Date Range Report");

    Label test = new Label("not clicked");


    // Initializes the drop down menus
    ComboBox<String> one = new ComboBox<String>();
    one.getItems().add("Choose Year");

    ComboBox<String> two = new ComboBox<String>();
    two.getItems().add("Choose Month");

    ComboBox<String> three = new ComboBox<String>();
    three.getItems().add("Choose Day");

    // Shows different things based on report selected
    reportType.setOnAction(value -> {
      if (reportType.getValue() != null
          && ((String) reportType.getValue()).equals("Annual Report")) {
        // add all farmIDs
      } else if (reportType.getValue() != null
          && ((String) reportType.getValue()).equals("Annual Report")) {

      } else {
        test.setText("clicked");
      }
    });


    Label space = new Label(".");

    VBox vbox = new VBox(10, in, out, new Label("Select the report type:"), reportType,
        one, two, three);

    vbox.setAlignment(Pos.TOP_LEFT);

    Scene mainScene = new Scene(vbox, WINDOW_WIDTH, WINDOW_HEIGHT);


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
