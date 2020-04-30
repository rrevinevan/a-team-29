/**
 * Year created by Evan Grubis & Andrew Meyers in a-team-29-project
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

import java.util.ArrayList;
import java.util.Collections;

/** This class represents a year of milk production data */
public class Year {
  int year;
  ArrayList<FarmData>[] months;

  /**
   * Constructs a new instance of Year
   * 
   * @param year - the year to be created
   */
  public Year(int year) {
    this.year = year;
    months = new ArrayList[12];
  }

  /**
   * @return the year
   */
  public int getYear() {
    return year;
  }

  /**
   * Adds a new instance of Data to a passed month in this Year
   * 
   * @param month  - the month the Data is to be added to
   * @param day    - the day of the Data to be added
   * @param farmID - the farmID of the Data to be added
   * @param weight - the weight of the Data to be added
   */
  public void addData(int month, int day, String farmID, int weight) {
    months[month - 1].add(new FarmData(day, farmID, weight));
  }

  /**
   * Sets a specific month to a passed ArrayList of Data used for implementation purposes
   * 
   * @param data  - the data to be added
   * @param month - the month the data is to be added to
   */
  public void setMonth(ArrayList<FarmData> data, int month) {
    months[month - 1] = data;
  }

  /**
   * @param month - the month to be retrieved
   * @return an ArrayList representing the passed month
   */
  public ArrayList<FarmData> getMonth(int month) {
    return months[month - 1];
  }
}