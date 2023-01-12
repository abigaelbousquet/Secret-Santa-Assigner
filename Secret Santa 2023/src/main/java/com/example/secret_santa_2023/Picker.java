package com.example.secret_santa_2023;

import java.util.ArrayList;

public class Picker {
  /** an ArrayList<Santa> of Santas to be given assignments */
  private ArrayList<Santa> santas;

  /** a Hat containing all names for the drawing */
  private Hat almightyHat;

  /** the number of Santas/names */
  private int numSantas;

  /**
   * creates an instance of a Picker with a nonempty list of Santas & 
   * nonempty hat
   * @param inputtedNames, a nonempty ArrayList<String> of names for the drawing
   */
  public Picker(ArrayList<String> inputtedNames) {
    almightyHat = new Hat(inputtedNames);

    // create the ArrayList of santas
    santas = new ArrayList<Santa>();
    numSantas = inputtedNames.size();
    for (int i = 0; i < numSantas; i++) {
      santas.add(new Santa(inputtedNames.get(i)));
    }
  }

  /**
   * gets the Hat from this Picker
   * @return the almightyHat, a Hat
   */
  public Hat getHat() {
    return almightyHat;
  }

  /**
   * gets the santas from this Picker
   * @return santas, an ArrayList<Santa>
   */
  public ArrayList<Santa> getSantas() {
    return santas;
  }

  /**
   * shuffles the ArrayList<Santa>:
   * results in value of santas being a new ArrayList<Santa> containing the 
   * same Santas as the original (unshuffled), but in a new order
   */
  public void shuffleSantas() {
    ArrayList<Santa> unshuffled = santas;
    ArrayList<Santa> shuffled = new ArrayList<Santa>();

    for (int i = 0; i < numSantas; i++) {
      int randomIndex = (int) (Math.random()*(unshuffled.size()-1));
      shuffled.add(unshuffled.remove(randomIndex));
    }
    
    santas = shuffled;
  }

  /**
   * resets all Santas in santas for this Picker
   */
  public void resetAllSantas() {
    for (int i = 0; i < numSantas; i++) {
      santas.get(i).resetAssignment();
    }
  }

  /**
   * makes all secret santa assignments:
   * results in each Santas in santas having (assigned == true) & 
   * a nonempty assignment
   */
  public void makeAllAssignments() {
    // first, shuffle santas to create a random drawing order
    shuffleSantas();

    /* make assignments until all successful assignments are made, where a
    successful assignment is defined by an assignment that is not one's 
    own name */
    boolean success = false;
    while (!success) {
      try {
        for (int i = 0; i < numSantas; i++) {
          Santa currentDrawer = santas.get(i);
          String drawerName = currentDrawer.getName();

          if (almightyHat.getCurrentSize() == 1) {
            String lastName = almightyHat.getNamesLeft().get(0);
            if (lastName.equals(drawerName)) {
              throw new InvalidDrawingException(drawerName + " must draw themselves");
            }
            else {
              currentDrawer.setAssignment(lastName);
              almightyHat.removeName(lastName);
              success = true;
            }
          }
          else {
            String drawnName = almightyHat.drawUntilSuccess(drawerName);
            currentDrawer.setAssignment(drawnName);
            almightyHat.removeName(drawnName);
          }
        }
      }
      catch(Exception e) {
        System.out.println("One minute...");
        System.out.println(e);
        System.out.println("We are ensuring a successful complete drawing...");
        System.out.println(); // for readability

        resetAllSantas();
        almightyHat.reset();
      }
    }
  }
}
