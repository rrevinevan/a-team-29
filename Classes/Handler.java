package application;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Handler {
  ArrayList<Year> ds;

  public Handler() {
    ds = new ArrayList<Year>();
  }

  // takes a selected directory and imports each csv into a new month
  public void importFile(File folder) throws Exception {
    File[] listOfFiles = folder.listFiles();
    for (File file : listOfFiles) {
      // makes sure file is a real file and is a csv
      if (file.isFile() && file.getName().contains(".csv")) {
        int selYear = Integer.parseInt(file.getName().substring(0, file.getName().indexOf("-")));
        if (dsGetIndex(selYear) < 0) {
          ds.add(new Year(selYear));
        }
        int selMonth = Integer.parseInt(
            file.getName().substring(file.getName().indexOf("-") + 1, file.getName().indexOf(".")));

        // Creates ArrayList that will be added to dataset
        ArrayList<Data> toImport = new ArrayList<Data>();

        // Creates file scanner
        Scanner scanner = new Scanner(file);
        scanner.nextLine();
        while (scanner.hasNext()) {
          try {
            String[] line = scanner.nextLine().split(",");
            int day = Integer.parseInt(line[0].substring(line[0].lastIndexOf("-") + 1));
            String farmID = (String) line[1];
            int weight = Integer.parseInt(line[2]);
            toImport.add(new Data(day, farmID, weight));

            // IMPORTANT NOTE: months will be categorized as 1-12 and the getters and setters in the
            // Handler will deal with the shifting of the data to line up with the indexes of table
            ds.get(dsGetIndex(selYear)).setMonth(toImport, selMonth);
          } catch (Exception e) {
            scanner.close();
            throw new DataCorruptException();
          }
        }

        scanner.close();

        // This is just debug code to make sure that all data in the csv was imported
        ArrayList<Data> test = ds.get(dsGetIndex(selYear)).getMonth(selMonth);
        System.out.println("Month: " + selMonth);
        for (Data d : test)
          System.out.println(d.getDay() + " " + d.getFarmID() + " " + d.getWeight());
      }
    }
  }

  private int dsGetIndex(int year) {
    if (ds.size() < 1)
      return -1;
    for (int i = 0; i < ds.size(); i++)
      if (ds.get(i).getYear() == year)
        return i;
    return -1;
  }
}
