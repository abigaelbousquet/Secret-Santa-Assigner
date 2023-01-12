package com.example.secret_santa_2023;

import java.util.ArrayList;

public class Hat {
  /** the names of people who have not yet been assigned a secret santa */
  private ArrayList<String> namesLeft;

  /** the number of names 'in the hat' currently */
  private int currentSize;

  /** the original list of names in this Hat */
  private ArrayList<String> originalNames;

  /** the number of names 'in the hat' originally */
  private int originalSize;

  /**
   * creates an instance of a nonempty Hat
   * @param inputtedNames, a nonempty ArrayList<String> whose elements represent
   *        the names of people who have not been assigned a secret santa yet
   */
  public Hat(ArrayList<String> inputtedNames) {
    currentSize = inputtedNames.size();
    originalSize = inputtedNames.size();

    namesLeft = new ArrayList<String>();
    originalNames = new ArrayList<String>();

    for (int i = 0; i < originalSize; i++) {
      namesLeft.add(inputtedNames.get(i));
      originalNames.add(inputtedNames.get(i));
    }
  }

  /**
   * gets the current size of the hat
   * @return the currentSize of the hat, an int
   */
  public int getCurrentSize() {
    return currentSize;
  }

  /**
   * gets the original size of the hat
   * @return the originalSize of the hat, an int
   */
  public int getOriginalSize() {
    return originalSize;
  }

  /**
   * gets the current names in the hat
   * @return the names left in the hat, an ArrayList<String>
   */
  public ArrayList<String> getNamesLeft() {
    return namesLeft;
  }

  /**
   * gets the original names in the hat
   * @return all names originally in the hat, an ArrayList<String>
   */
  public ArrayList<String> getOriginalNames() {
    return originalNames;
  }

  /**
   * adds a name to the ArrayList<String> namesLeft and 
   * subsequently adds 1 to the currentSize of the hat
   * @param name, a String representing the name of the person whose
   *        name should be added to the hat
   */
  public void addName(String name) {
    namesLeft.add(name);
    currentSize ++;
  }

  /**
   * removes a name from the ArrayList<String> namesLeft of the hat, and 
   * subsequently subtracts 1 from the currentSize of the hat
   * @param name, a String representing the name of the person whose
   *        name should be removed from the hat
   */
  public void removeName(String name) {
    namesLeft.remove(name);
    currentSize --;
  }

  /**
   * resets the Hat to its original state with all original names 
   * 'back in the hat'
   */
  public void reset() {
    namesLeft.clear();
    currentSize = 0;
    for (int i = 0; i < originalSize; i++) {
      addName(originalNames.get(i));
    }
    System.out.println("Reset success.");
  }

  /**
   * 'draws' a random name from the Hat
   * @return a randomly chosen name from this Hat, a String
   */
  public String drawRandomName() {
    int randomIndex = (int) (Math.random()*getCurrentSize());
    return namesLeft.get(randomIndex);
  }

  /**
   * 'draws' random names until a successful draw is made; in other words,
   * 'draws' until the drawer doesn't draw themselves
   * @param drawerName, a String representing the person currently looking 
   *        for an assignment
   * @return a String, the name of the person who drawerName can be 
   *         successfully assigned to
   */
  public String drawUntilSuccess(String drawerName) {
    boolean success = false;
    String drawnName = null;
    while (!success) {
      drawnName = drawRandomName();
      if (!(drawnName.equals(drawerName))) {
        success = true;
      }
    }
    return drawnName;
  }
}
