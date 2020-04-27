package application;

public class Data {
  int day, weight;
  String farmID;
  
  public Data(int day, String farmID, int weight) {
    this.day = day;
    this.farmID = farmID;
    this.weight = weight;
  }

  /**
   * @return the day
   */
  public int getDay() {
    return day;
  }

  /**
   * @param day the day to set
   */
  public void setDay(int day) {
    this.day = day;
  }

  /**
   * @return the year
   */
  public String getFarmID() {
    return farmID;
  }

  /**
   * @param year the year to set
   */
  public void setYear(String farmID) {
    this.farmID = farmID;
  }

  /**
   * @return the weight
   */
  public int getWeight() {
    return weight;
  }

  /**
   * @param weight the weight to set
   */
  public void setWeight(int weight) {
    this.weight = weight;
  }
  
  
}
