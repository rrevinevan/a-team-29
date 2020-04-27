package application;

import java.util.ArrayList;

public class Year {
  int year;
  ArrayList<Data>[] months;
  
  public Year(int year) {
    this.year = year;
    months = new ArrayList[12];
  }

  /**
   * @return the id
   */
  public int getYear() {
    return year;
  }

  public void addData(int month, int day, String farmID, int weight) {
    months[month].add(new Data(day, farmID, weight));
  }
  
  //sets a specific month to an arraylist, used for importation purposes
  public void setMonth(ArrayList<Data> data, int month) {
    months[month - 1] = data;
  }
  
  public ArrayList<Data> getMonth(int month){
    return months[month-1];
    }
  }
