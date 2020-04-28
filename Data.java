/**
 * Data created by Evan Grubis & Andrew Meyers in a-team-29-project
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

/**
 * This class is used to represent the data from a line of an imported CSV data
 * file. Each Data object has a day, weight, and farm ID.
 */
public class Data {
	int day, weight;
	String farmID;

	/**
	 * Constructs a new instance of Data
	 * 
	 * @param day - the day of the Data entry
	 * @param farmID - the farmID of the farm being represented in the Data entry
	 * @param weight - the weight of milk produced by the farm in the Data entry
	 */
	public Data(int day, String farmID, int weight) {
		this.day = day;
		this.farmID = farmID;
		this.weight = weight;
	}

	/**
	 * @return the day of this Data entry
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day - the new day for this Data entry
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @return the farmID of the farm being represented in this Data entry
	 */
	public String getFarmID() {
		return farmID;
	}

	/**
	 * @param farmID - the new farmID for this Data entry
	 */
	public void setFarmID(String farmID) {
		this.farmID = farmID;
	}

	/**
	 * @return the weight of the milk produced in this Data entry
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight - the new weight for this Data entry
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
}