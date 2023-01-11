package com.example.secret_santa_2023;

public class Santa {
  /** the name of this santa */
  private String name;

  /** the person who this santa is assigned to */
  private String assignment;

  /** the assigned status of this santa, true if they have 'drawn a name' 
      and false otherwise */
  private boolean assigned;

  /**
   * creates an instance of a Santa with the initial assigned status false
   * @param inputtedName, the name of the Santa
   */
  public Santa(String inputtedName) {
    name = inputtedName;
    assignment = null;
    assigned = false;
  }

  /**
   * gets the name of the Santa
   * @return the name of this Santa, a String
   */
  public String getName() {
    return name;
  }

  /**
   * gets the assignment of the Santa
   * @return the assignment of this Santa, a String representing the name 
   *         of who they are assigned to
   */
  public String getAssignment() {
    return assignment;
  }

  /**
   * gets the assigned status of the Santa
   * @return the assigned status of this Santa, a boolean
   */
  public boolean assignedP() {
    return assigned;
  }

  /**
   * sets the assignment of the santa
   * @param name, a String representing the name of the person who this 
   *        Santa is to be assigned to
   */
  public void setAssignment(String name) {
    assignment = name;
    assigned = true;
  }

  /**
   * resets the assignment of the Santa
   */
  public void resetAssignment() {
    assignment = null;
    assigned = false;
  }
}
