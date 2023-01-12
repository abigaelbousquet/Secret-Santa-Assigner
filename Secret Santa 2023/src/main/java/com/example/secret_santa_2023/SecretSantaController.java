package com.example.secret_santa_2023;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tab;

public class SecretSantaController {
    /** the names inputted by the user for the Secret Santa drawing */
    private ArrayList<String> inputtedNames = new ArrayList<String>();

    /** the Picker that will make and manage Secret Santa assignments */
    private Picker theBrain;

    /** the Santas created by the inputted names and with assignments determined by the Picker */
    private ArrayList<Santa> santasWithAssignments = new ArrayList<Santa>();

    /** the index of santasWithAssignments of the next Santa whose assignment has yet to be revealed */
    private int currentSantaIndex;

    /** the current Santa in santasWithAssignments */
    private Santa currentSanta;

    /** the TextField where a user inputs each new Santa's name */
    @FXML
    private TextField textFieldNewSanta;

    /** the TextArea which displays the list of all names inputted at current time */
    @FXML
    private TextArea textAreaCurrentSantas;

    /** the label which displays the current Santa whose assignment is to be revealed */
    @FXML
    private Label labelSantaName;

    /** the label which displays the current Santa's assignment */
    @FXML
    private Label labelAssignment;

    /** the tab containing the setup portion of the GUI */
    @FXML
    private Tab tabSetup;

    /** the tab containing the secret santa assignments portion of the GUI */
    @FXML
    private Tab tabAssignments;

    /** the label that displays errors following an attempted click to "Make Assignments" where
     * the preconditions to do so are not met */
    @FXML
    private Label labelErrorSetup;

    /** the label that displays errors following incorrect ordered use of buttons on Assignments tab */
    @FXML
    private Label labelErrorAssignments;

    /**
     * clears the error label inputted
     * @param l, the Label to be cleared
     */
    private void clearError(Label l) {
        l.setText("");
    }

    /**
     * sets the error label inputted to display inputted error
     * @param l, the Label to display the error
     * @param msg, a String explaining the error that has occurred
     */
    private void error(Label l, String msg) {
        l.setText("Error: " + msg);
    }

    /**
     * gets the names of all inputted names
     * @return a String, the list of all inputted names from most recent edition to least, with each name on a new line
     */
    private String getNamesList() {
        String names = "";
        for (String name : inputtedNames) {
            names += name + "\n";
        }
        return names;
    }

    /**
     * updates GUI labels and ArrayList<String> of inputted names to reflect the addition of a new name
     * via textFieldNewSanta
     * @precondition textFieldNewSanta is nonempty
     */
    @FXML
    protected void onAddSantaButtonClick() {
        clearError(labelErrorSetup);
        String newSantaName = textFieldNewSanta.getText();
        if (!(newSantaName.equals(""))) {
            inputtedNames.add(0, newSantaName);
            textFieldNewSanta.clear();
            textAreaCurrentSantas.setText(getNamesList());
        }
    }

    /**
     * updates GUI labels and ArrayList<String> of inputted names to reflect the removal of the most
     * recently inputted name
     * @precondition inputtedNames is nonempty
     */
    @FXML
    protected void onRemoveSantaButtonClick() {
        clearError(labelErrorSetup);
        if (inputtedNames.size() > 0) {
            inputtedNames.remove(0);
            textFieldNewSanta.clear();
            textAreaCurrentSantas.setText(getNamesList());
        }
    }

    /**
     * uses inputtedNames at time of calling to create a Picker with all inputted names, create a Santa corresponding
     * to each name, make successful assignments for each Santa, disable the Setup tab and enable the Assignments tab
     * @precondition inputtedNames has a size of at least 2
     */
    @FXML
    protected void onMakeAssignmentsButtonClick() {
        clearError(labelErrorSetup);
        if (inputtedNames.size() >= 2) {
            theBrain = new Picker(inputtedNames);
            theBrain.makeAllAssignments();
            tabAssignments.setDisable(false);
            tabSetup.setDisable(true);
        }
        else {
            error(labelErrorSetup, "You must have at least 2 people to do a gift exchange!");
        }
    }

    /**
     * updates variables relevant to current Santa to reflect the next Santa in santasWithAssignments
     */
    private void nextSanta() {
        currentSantaIndex ++;
        currentSanta = santasWithAssignments.get(currentSantaIndex);
    }

    /**
     * displays the current Santa name in labelSantaName
     */
    protected void displayCurrentSantaName() {
        labelSantaName.setText(currentSanta.getName());
    }

    /**
     * updates the contents of the Assignments tab upon the initial tab change from the Setup to Assignments tab
     */
    @FXML
    protected void onAssignmentsTabClick() {
        currentSantaIndex = 0;
        santasWithAssignments = theBrain.getSantas();
        currentSanta = santasWithAssignments.get(currentSantaIndex);
        displayCurrentSantaName();
    }

    /**
     * reveals the assignment of the current Santa
     * @precondition not all assignments have been already revealed (labelSantaName is not cleared)
     */
    @FXML
    protected void onAssignmentPleaseButtonClick() {
        if (!(labelSantaName.getText().equals(""))) {
            clearError(labelErrorAssignments);
            labelAssignment.setText(currentSanta.getAssignment());
        }
    }

    /**
     * resets the Assignments tab to set up for the assignment reveal for the next Santa in santasWithAssignments
     * @precondition the assignment for the current santa at time of calling must have been displayed
     * @precondition the currentSanta must not be the last Santa in santasWithAssignments
     */
    @FXML
    protected void onNextSantaButtonClick() {
        clearError(labelErrorAssignments);
        if (!(labelAssignment.getText().equals(""))) {
            labelAssignment.setText("");
            if (currentSantaIndex < (santasWithAssignments.size()-1)) {
                nextSanta();
                displayCurrentSantaName();
            }
            else if (currentSantaIndex == (santasWithAssignments.size()-1)) {
                labelSantaName.setText("");
            }
        }
        else if (labelSantaName.getText().equals("")) {
            error(labelErrorAssignments, "No more assignments to reveal.");
        }
        else {
            error(labelErrorAssignments, "Don't skip anyone!");
        }
    }
}