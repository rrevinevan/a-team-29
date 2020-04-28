/**
 * Handler created by Evan Grubis & Andrew Meyers in a-team-29-project
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Scanner;

/**
 * This Class handles all of the importing, exporting, manipulations, and
 * calculations of dat
 */
public class Handler {
	ArrayList<Year> ds;

	/** Constructs a new instance of Handler */
	public Handler() {
		ds = new ArrayList<Year>();
	}

	/**
	 * Takes a selected directory and imports each CSV into a new month
	 * 
	 * @param folder - the folder of the CSV data file
	 * @throws Exception when there are issues with the importation of a data file
	 */
	public void importFile(File folder) throws Exception {
		File[] listOfFiles = folder.listFiles();

		// Iterates through all data files in folder
		for (File file : listOfFiles) {

			// makes sure file is a real file and is a CSV
			if (file.isFile() && file.getName().contains(".csv")) {
				int selYear = Integer.parseInt(file.getName().substring(0, file.getName().indexOf("-")));

				// Adds a new Year to ds if selYear is not already in ds
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

				// Iterates through the file
				while (scanner.hasNext()) {
					try {
						String[] line = scanner.nextLine().split(",");
						int day = Integer.parseInt(line[0].substring(line[0].lastIndexOf("-") + 1));
						String farmID = (String) line[1];
						int weight = Integer.parseInt(line[2]);
						toImport.add(new Data(day, farmID, weight));

						// IMPORTANT NOTE: months will be categorized as 1-12 and the getters and
						// setters in the
						// Handler will deal with the shifting of the data to line up with the indexes
						// of table
						ds.get(dsGetIndex(selYear)).setMonth(toImport, selMonth);
					} catch (Exception e) {
						scanner.close();
						throw new DataCorruptException();
					}
				}

				scanner.close();

				// This is just debug code to make sure that all data in the CSV was imported
				ArrayList<Data> test = ds.get(dsGetIndex(selYear)).getMonth(selMonth);
				System.out.println("Month: " + selMonth);
				for (Data d : test)
					System.out.println(d.getDay() + " " + d.getFarmID() + " " + d.getWeight());
			}
		}
	}

	/**
	 * Helper method that gets the index of ds that contains the passed year
	 * 
	 * @param year - the year to be searched for
	 * @return the index of ds that contains year
	 */
	private int dsGetIndex(int year) {
		if (ds.size() < 1)
			return -1;
		for (int i = 0; i < ds.size(); i++)
			if (ds.get(i).getYear() == year)
				return i;
		return -1;
	}

	/**
	 * @return an ArrayList<Year> representing all of the years in the ds
	 */
	public ArrayList<Year> getYears() {
		return ds;
	}

	/**
	 * This method is used to determine the number of days in a passed month
	 * 
	 * @param month - The month to find the number of days in
	 * @returns the number of the days in the passed month
	 */
	public int getNumDaysInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);

		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * This method is used to get all farmIDs in a given year
	 * 
	 * @param year - the year to get all of the farmIDs from
	 * @return an ArrayList<Integer> representing all of the farm IDs in the passed
	 *         year
	 */
	public ArrayList<Integer> getAllFarmIDs(int year) {
		ArrayList<Integer> allFarmIDs = new ArrayList<>();

		int yearIndex = dsGetIndex(year);

		// Iterates through all of the data from each month
		for (int i = 1; i < 13; i++) {
			ArrayList<Data> monthOfData = ds.get(yearIndex).getMonth(i);

			for (int ii = 0; ii < monthOfData.size(); ii++) {
				Integer currID = Integer.parseInt(monthOfData.get(ii).getFarmID().substring(5));

				// Adds the farmID of the current Data entry if it is not already in the List
				if (!allFarmIDs.contains(currID)) {
					allFarmIDs.add(currID);
				}
			}

		}

		Collections.sort(allFarmIDs);

		return allFarmIDs;
	}

	/**
	 * This method is used to get all the total weight of a farm in a given year and
	 * month
	 * 
	 * @param farmID - the farm to get the total weight of
	 * @param year   - the year to calculate data from
	 * @param month  - the month to calculate data from
	 * @return an int representing the total weight produced by a farm in the passed
	 *         month, year
	 */
	public int getTotalWeightOfFarmInMonth(String farmID, int month, int year) {
		int totalFarmWeight = 0;

		int yearIndex = dsGetIndex(year);

		ArrayList<Data> monthOfData = ds.get(yearIndex).getMonth(month);

		// Iterates through the data of monthOfData
		for (int ii = 0; ii < monthOfData.size(); ii++) {

			// Adds the weight if it belongs to the passed farm
			if (monthOfData.get(ii).farmID.equals(farmID)) {
				totalFarmWeight += monthOfData.get(ii).weight;
			}
		}

		return totalFarmWeight;
	}

	/**
	 * This method is used to get the total weight in a passed month
	 * 
	 * @param year  - the year to calculate data from
	 * @param month - the month to calculate data from
	 * @return an int representing the total weight produced by all farms in a given
	 *         month,year
	 */
	public int getTotalWeightOfAllFarmsInMonth(int month, int year) {
		int totalAllFarmWeight = 0;
		int yearIndex = dsGetIndex(year);

		ArrayList<Data> monthOfData = ds.get(yearIndex).getMonth(month);

		// Iterates through the data of monthOfData
		for (int ii = 0; ii < monthOfData.size(); ii++) {

			totalAllFarmWeight += monthOfData.get(ii).weight;
		}

		return totalAllFarmWeight;
	}

	/**
	 * This method is used to get the total weight of a farm in a passed year
	 * 
	 * @param farmID - the farm that data is to be calculated upon
	 * @param year   - the year to calculate data from
	 * @return an int representing the total weight produced by the passed farm in
	 *         the passed year
	 */
	public int getTotalWeightOfFarmInYear(String farmID, int year) {
		int totalFarmWeight = 0;
		int yearIndex = dsGetIndex(year);

		for (int i = 1; i < 13; i++) {
			ArrayList<Data> monthOfData = ds.get(yearIndex).getMonth(i);

			// Iterates through the data of monthOfData
			for (int ii = 0; ii < monthOfData.size(); ii++) {

				// Adds weight to the total if farmIDs match
				if (monthOfData.get(ii).farmID.equals(farmID)) {
					totalFarmWeight += monthOfData.get(ii).weight;
				}
			}
		}

		return totalFarmWeight;
	}

	/**
	 * This method is used to get the total weight of all farms in a passed year
	 * 
	 * @param year - the year to calculate data from
	 * @return an int representing the total weight produced by all farms in the
	 *         passed year
	 */
	public int getTotalWeightOfAllFarmsInYear(int year) {
		int totalAllFarmWeight = 0;
		int yearIndex = dsGetIndex(year);

		for (int i = 1; i < 13; i++) {
			ArrayList<Data> monthOfData = ds.get(yearIndex).getMonth(i);

			// Iterates through the data of monthOfData
			for (int ii = 0; ii < monthOfData.size(); ii++) {
				totalAllFarmWeight += monthOfData.get(ii).weight;
			}
		}

		return totalAllFarmWeight;
	}

}
