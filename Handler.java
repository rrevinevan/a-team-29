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
	public String importFile(File folder) throws Exception {
		File[] listOfFiles = folder.listFiles();
		String errors = "";

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
				ArrayList<FarmData> toImport = new ArrayList<FarmData>();

				// Creates file scanner
				Scanner scanner = new Scanner(file);
				scanner.nextLine();
				int count = 2;
				String internalErr = "";
				// Iterates through the file
				while (scanner.hasNext()) {
					try {
						String[] line = scanner.nextLine().split(",");
						int day = Integer.parseInt(line[0].substring(line[0].lastIndexOf("-") + 1));
						String farmID = (String) line[1];
						if (farmID.equals("-"))
							throw new DataCorruptException();
						int weight = Integer.parseInt(line[2]);
						toImport.add(new FarmData(day, farmID, weight));

						// IMPORTANT NOTE: months will be categorized as 1-12 and the getters and
						// setters in the
						// Handler will deal with the shifting of the data to line up with the indexes
						// of table
						ds.get(dsGetIndex(selYear)).setMonth(toImport, selMonth);
						count++;
					} catch (Exception e) {
						internalErr += count + ", ";
						count++;
					}

				}
				if (!internalErr.equals("")) {
					internalErr = "Month " + selMonth + ": " + internalErr;
					errors += internalErr;
				}

				scanner.close();

				// This is just debug code to make sure that all data in the CSV was imported
				ArrayList<FarmData> test = ds.get(dsGetIndex(selYear)).getMonth(selMonth);
				System.out.println("Month: " + selMonth);
				for (FarmData d : test)
					System.out.println(d.getDay() + " " + d.getFarmID() + " " + d.getWeight());

			}
		}
		if (!errors.equals("")) {
			errors = "There was an error on lines: " + errors;
			errors = errors.substring(0, errors.lastIndexOf(","));
		}

		return errors;
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
	public ArrayList<String> getAllFarmIDsInYear(int year) {
		ArrayList<String> allFarmIDs = new ArrayList<>();

		int yearIndex = dsGetIndex(year);

		// Iterates through all of the data from each month
		for (int i = 1; i <= 12; i++) {
			ArrayList<FarmData> monthOfData = ds.get(yearIndex).getMonth(i);

			for (int ii = 0; ii < monthOfData.size(); ii++) {
				String currID = monthOfData.get(ii).getFarmID();

				// Adds the farmID of the current Data entry if it is not already in the List
				if (!allFarmIDs.contains(currID)) {
					allFarmIDs.add(currID);
				}
			}

		}

		ArrayList<Integer> tempList = new ArrayList<Integer>();

		// Converts items in allFarmIDs to an Integer and puts in tempList
		for (int i = 0; i < allFarmIDs.size(); i++) {
			tempList.add(Integer.parseInt(allFarmIDs.get(i).substring(5)));
		}

		// Clears all FarmIDs and sorts tempList
		allFarmIDs.clear();
		Collections.sort(tempList);

		// Reconverts everything to Strings and returns
		for (int i = 0; i < tempList.size(); i++) {
			allFarmIDs.add("Farm " + Integer.toString(tempList.get(i)));
		}

		return allFarmIDs;
	}

	/**
	 * This method is used to get all farmIDs in a given year, month
	 * 
	 * @param year  - the year to get all of the farmIDs from
	 * @param month - the month to get all of the farmIDs from
	 * @return an ArrayList<Integer> representing all of the farm IDs in the passed
	 *         year, month
	 */
	public ArrayList<String> getAllFarmIDsInMonth(int year, int month) {
		int yearIndex = dsGetIndex(year);
		ArrayList<FarmData> monthsData = ds.get(yearIndex).getMonth(month);

		ArrayList<String> discoveredIDList = new ArrayList<String>();

		// Iterates through all of the data from the passed month
		for (int i = 0; i < monthsData.size(); i++) {

			// Adds the FarmID if its not already in the discovered list
			if (!discoveredIDList.contains(monthsData.get(i).farmID)) {
				discoveredIDList.add(monthsData.get(i).farmID);
			}
		}

		ArrayList<Integer> tempList = new ArrayList<Integer>();

		// Converts everything in disoveredIDList to an Integer and adds to tempList
		for (int i = 0; i < discoveredIDList.size(); i++) {
			tempList.add(Integer.parseInt(discoveredIDList.get(i).substring(5)));
		}

		// Sorts tempList and clears discoveredIDList
		Collections.sort(tempList);
		discoveredIDList.clear();

		// Iterates through tempList and re adds sorted items to discoveredIDList
		for (int i = 0; i < tempList.size(); i++) {
			discoveredIDList.add("Farm " + Integer.toString(tempList.get(i)));
		}

		return discoveredIDList;
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

		ArrayList<FarmData> monthOfData = ds.get(yearIndex).getMonth(month);

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
	 * @param month - the month to calculate data from
	 * @param year  - the year to calculate data from
	 * @return an int representing the total weight produced by all farms in a given
	 *         month,year
	 */
	public int getTotalWeightOfAllFarmsInMonth(int month, int year) {
		int totalAllFarmWeight = 0;
		int yearIndex = dsGetIndex(year);

		ArrayList<FarmData> monthOfData = ds.get(yearIndex).getMonth(month);

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
			ArrayList<FarmData> monthOfData = ds.get(yearIndex).getMonth(i);

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
			ArrayList<FarmData> monthOfData = ds.get(yearIndex).getMonth(i);

			// Iterates through the data of monthOfData
			for (int ii = 0; ii < monthOfData.size(); ii++) {
				totalAllFarmWeight += monthOfData.get(ii).weight;
			}
		}

		return totalAllFarmWeight;
	}

	/**
	 * Returns the minimum sum month of the year
	 * 
	 * @param farmID is the farm we are looking at
	 * @param year   is the year
	 * @return an int with the minimum of the year
	 */
	public int getMinWeightInYear(String farmID, int year) {
		int min = Integer.MAX_VALUE;
		int yearIndex = dsGetIndex(year);

		for (int i = 1; i <= 12; i++) {
			ArrayList<FarmData> monthOfData = ds.get(yearIndex).getMonth(i);
			int sum = 0;
			for (FarmData d : monthOfData)
				if (d.getFarmID().equals(farmID))
					sum += d.getWeight();

			if (sum < min) {
				min = sum;
			}
		}
		return min;
	}

	/**
	 * Returns the maximum sum month of the year
	 * 
	 * @param farmID is the farm we are looking at
	 * @param year   is the year
	 * @return an int with the max of the year
	 */
	public int getMaxWeightInYear(String farmID, int year) {
		int max = 0;
		int yearIndex = dsGetIndex(year);

		for (int i = 1; i <= 12; i++) {
			ArrayList<FarmData> monthOfData = ds.get(yearIndex).getMonth(i);
			int sum = 0;
			for (FarmData d : monthOfData)
				if (d.getFarmID().equals(farmID))
					sum += d.getWeight();

			if (sum > max) {
				max = sum;
			}
		}
		return max;
	}

	/**
	 * Gets the min weight of all farms in the passed year
	 * 
	 * @param year - year to make calculation from
	 * @return the min weight of all farms in the passed year
	 */
	public int getMinWeightInYearForAll(int year) {
		int min = Integer.MAX_VALUE;
		ArrayList<String> allFarms = getAllFarmIDsInYear(year);
		for (String farm : allFarms) {
			int temp = getTotalWeightOfFarmInYear(farm, year);
			if (temp < min)
				min = temp;
		}
		return min;
	}

	/**
	 * Gets the max weight of all farms in the passed year
	 * 
	 * @param year - year to make calculation from
	 * @return the max weight of all farms in the passed year
	 */
	public int getMaxWeightInYearForAll(int year) {
		int max = 0;
		ArrayList<String> allFarms = getAllFarmIDsInYear(year);
		for (String farm : allFarms) {
			int temp = getTotalWeightOfFarmInYear(farm, year);
			if (temp > max)
				max = temp;
		}
		return max;
	}

	/**
	 * Returns the data of a passed month in the passed year
	 * 
	 * @param year  - year to retrieve data from
	 * @param month = month to retrieve data from
	 * @return an ArrayList of FarmData representing all data in a month
	 */
	public ArrayList<FarmData> getFarmDataFromMonthInYear(int year, int month) {
		int yearIndex = dsGetIndex(year);

		return ds.get(yearIndex).getMonth(month);
	}

	/**
	 * Gets the min weight in the passed month, year
	 * 
	 * @param year  - year to retrieve data from
	 * @param month = month to retrieve data from
	 * @return an int representing the min weight in the month
	 */
	public int getMinWeightInMonthForAll(int year, int month) {
		ArrayList<String> allFarmsIDs = getAllFarmIDsInMonth(year, month);
		int small = getTotalWeightOfFarmInMonth(allFarmsIDs.get(0), month, year);

		// Iterates through all the farms in the month
		for (int i = 0; i < allFarmsIDs.size(); i++) {
			int tempSmall = getTotalWeightOfFarmInMonth(allFarmsIDs.get(i), month, year);

			// Updates small if a less weight is found
			if (tempSmall < small) {
				small = tempSmall;
			}
		}

		return small;
	}

	/**
	 * Gets the max weight found in the passed month, year
	 * 
	 * @param year  - year to retrieve data from
	 * @param month = month to retrieve data from
	 * @return an int representing the max weight in the month
	 */
	public int getMaxWeightInMonthForAll(int year, int month) {
		int big = 0;
		ArrayList<String> allFarmsIDs = getAllFarmIDsInMonth(year, month);

		// Iterates through all the farms in the month
		for (int i = 0; i < allFarmsIDs.size(); i++) {
			int tempBig = getTotalWeightOfFarmInMonth(allFarmsIDs.get(i), month, year);

			// Updates big if a greater weight is found
			if (big < tempBig) {
				big = tempBig;
			}
		}

		return big;
	}

	/**
	 * This method is used to obtain a list of all farmIDs involved in a date range
	 * period
	 * 
	 * @param d  - start day
	 * @param m  - start month
	 * @param y  - start year
	 * @param d2 - end day
	 * @param m2 - end month
	 * @param y2 - end year
	 * @return An ArrayList<String> representing all farms in the date range
	 */
	public ArrayList<String> getAllFarmsInPeriod(int d, int m, int y, int d2, int m2, int y2) { ///////////////////////////////////////////////////////////////////
		return null;
	}

	/**
	 * This method is used to get the weight of a specific farm in a passed time
	 * period
	 * 
	 * @param farmID - the farm to find the weight of
	 * @param d      - start day
	 * @param m      - start month
	 * @param y      - start year
	 * @param d2     - end day
	 * @param m2     - end month
	 * @param y2     - end year
	 * @return An Integer representing the total weight of the passed farm in the
	 *         time period
	 */
	public int getWeightInTimePeriod(String farmID, int d, int m, int y, int d2, int m2, int y2) { /////////////////////////////////////////////////////////////
		return 0;
	}

	/**
	 * This method is used to get the total weight of all farms in a passed period
	 * 
	 * @param d  - start day
	 * @param m  - start month
	 * @param y  - start year
	 * @param d2 - end day
	 * @param m2 - end month
	 * @param y2 - end year
	 * @return an Integer representing the total weight of all farms in the time
	 *         period
	 */
	public int getTotWeightInPeriod(int d, int m, int y, int d2, int m2, int y2) { /////////////////////////////////////////////////////////////////////////
		return 0;
	}

	/**
	 * This method is used to determine if a specified farm is in the data structure
	 * 
	 * @param farmID - the farm to be searched for
	 * @return true if the farm is in the data structure, false if otherwise
	 */
	public boolean containsFarm(String farmID) {
		// Check if the passed farm is in the data structure
		for (int i = 0; i < ds.size(); i++) {
			ArrayList<String> allFarmsInYear = getAllFarmIDsInYear(ds.get(i).year);

			if (allFarmsInYear.contains(farmID)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * This method is used to determine if a specified farm is in a passed year
	 * 
	 * @param farmID - the farm to be searched for
	 * @param year   - the year being searched in
	 * @return true if the farm is in the passed year, false if otherwise
	 */
	public boolean containsFarmInYear(String farmID, int year) {
		ArrayList<String> allFarmsInYear = getAllFarmIDsInYear(year);

		if (allFarmsInYear.contains(farmID)) {
			return true;
		}

		return false;

	}

	/**
	 * This method is used to remove a farm from the ds
	 * 
	 * @param farmID - The farm to be removed
	 * @return true if the farm is removed successfull, false if otherwise
	 */
	public boolean removeFarm(String farmID) {
		if (containsFarm(farmID) == false) {
			return false;
		}

		// Remove the farm after it has been found
		// Loop through all years in the data structure
		for (int i = 0; i < ds.size(); i++) {

			// Loop through every month in the year
			for (int ii = 1; ii < 13; ii++) {
				ArrayList<FarmData> monthsData = ds.get(i).getMonth(ii);

				// Loop through all of the data in each month
				for (int iii = 0; iii < monthsData.size(); iii++) {

					// Remove the data if it has matching farmID
					if (monthsData.get(iii).farmID.equals(farmID)) {
						monthsData.remove(iii);
						iii--;
					}
				}
			}
		}

		return true;
	}
	
	/**
	 * This method is used to add a piece of FarmData to the data structure
	 * 
	 * @param farmID - The farm to be added
	 * @param weight - The weight to be added
	 * @param y - the year it is to be added at
	 * @param m - the month it is to be added at
	 * @param d - the day it is to be added at
	 */
	public void addFarmData(String farmID, int weight, int y, int m, int d) {
		
		// Checks if adding a new year is required
		if (dsGetIndex(y) == -1) {
			ds.add(new Year(y));
			
			// Add new data
			ds.get(ds.size()-1).getMonth(m).add(new FarmData(d, farmID, weight));
		}
		// Modifying existing year
		else {
			
			// Add new data
			ds.get(dsGetIndex(y)).getMonth(m).add(new FarmData(d, farmID, weight));
		}
	}
}