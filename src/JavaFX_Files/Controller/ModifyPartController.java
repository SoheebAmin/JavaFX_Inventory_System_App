package JavaFX_Files.Controller;

import JavaFX_Files.Model.Inventory;
import JavaFX_Files.Model.InHouse;
import JavaFX_Files.Model.Outsourced;
import JavaFX_Files.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This method is the class to create the Modify Part JavaFX_Files.Controller object. */
public class ModifyPartController implements Initializable{

    // Variables for Radio Buttons and GUI text fields
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outsourcedButton;
    @FXML private TextField idText;
    @FXML private TextField nameText;
    @FXML private TextField inventoryText;
    @FXML private TextField priceText;
    @FXML private TextField minText;
    @FXML private TextField maxText;
    @FXML private TextField machineIdOrCompanyLabelText;
    @FXML private Label finalLabel;

    /** Toggle the final label to say "Company Name". */
    public void changeToOutsourced() {
        finalLabel.setText("Company Name");
    }

    /** Toggle the final label to say "Machine ID". */
    public void changeToInHouse() {
        finalLabel.setText("Machine ID");
    }

    /** This method allows the user to save the changes to any fields and the sub-class type for the part.
     It also performs the error checking to ensure all values are valid.
     It recreates the product based on user specification and replaces the original */
    public int saveButtonClicked(ActionEvent event) throws IOException {

        /* First, values are checked since they may have been edited to incorrect formats */

        // initial values given since required if variables set in if/try blocks.
        int id = 0;
        int inventory = 0;
        double price = 0;
        int min = 0;
        int max = 0;
        int machineId = 0;
        String companyName = "";

        boolean errorDetected = false;
        boolean mixOrMaxInvalid = false;

        // Check if ID is an int (if we disable auto-generate for the IDs)
        try
        {
            id = Integer.parseInt(idText.getText());
        } catch (NumberFormatException e) {
            errorDialogueBox("ID Error: Please enter a whole number");
            errorDetected = true;
        }

        // Check if String is not empty
        String name = nameText.getText();
        if (name.equals("")) {
            errorDialogueBox("Name Error: Please enter a name");
            errorDetected = true;
        }

        // check if inventory is an int
        try {
            inventory = Integer.parseInt(inventoryText.getText());
        } catch (NumberFormatException e) {
            errorDialogueBox("Inventory Error: Please enter a whole number");
            errorDetected = true;
        }

        // check if double is a numerical value
        try {
            price = Double.parseDouble(priceText.getText());
        } catch (NumberFormatException e) {
            errorDialogueBox("Price Error: Please enter a number");
            errorDetected = true;
        }

        // check if min is an int
        try {
            min = Integer.parseInt(minText.getText());
        } catch (NumberFormatException e) {
            errorDialogueBox("Min Error: Please enter a whole number");
            errorDetected = true;
            mixOrMaxInvalid = true;
        }

        // check if max is an int
        try {
            max = Integer.parseInt(maxText.getText());
        } catch (NumberFormatException e) {
            errorDialogueBox("Max Error: Please enter a whole number");
            errorDetected = true;
            mixOrMaxInvalid = true;
        }

        if (min > max && !mixOrMaxInvalid) {
            errorDialogueBox("Min cannot be larger than Max");
            errorDetected = true;
        }

        // check if inventory is less than the min or more than the max
        if ((min > inventory) || (inventory > max))
        {
            errorDialogueBox("Please ensure the inventory is between the mix and max values!");
            errorDetected = true;
        }

        // If in-house is selected, check if machine ID is an int
        if(inHouseButton.isSelected()) {
            try {
                machineId = Integer.parseInt(machineIdOrCompanyLabelText.getText());
            } catch (NumberFormatException e) {
                errorDialogueBox("Machine ID Error: Please enter a whole number");
                errorDetected = true;
            }
        }
        else
        {
            // If outsourced is selected, check if anything was entered
            companyName = machineIdOrCompanyLabelText.getText();
            if (name.equals(""))
            {
                errorDialogueBox("Name Error: Please enter a company name");
                errorDetected = true;
            }
        }

        // check if any dialogue box was produced. If so, exit the function
        if (errorDetected) {
            return 1;
        }

        // Alert asking for confirmation if user wants to save.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            // check to see if this is for an in-house or eternal part
            if(inHouseButton.isSelected())
            {
                // update the part with the new info.
                Inventory.updatePart(id, new InHouse(id, name, price, inventory, min, max, machineId));

            }
            else
            {
                Inventory.updatePart(id, new Outsourced(id, name, price, inventory, min, max, companyName));

            }
            changeScene(event, "../View/MainScreenGUI.fxml");
        }
        return 0;
    }


    /** This method returns to the MainScreenController without making any changes to the Inventory class. */
    public void cancelButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "../View/MainScreenGUI.fxml");
    }


    /** This method wraps together the common code to change scenes. */
    public void changeScene(ActionEvent event, String sceneName) throws IOException {
        Parent MainScreenParent = FXMLLoader.load(getClass().getResource((sceneName)));
        Scene MainScreenScene = new Scene(MainScreenParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(MainScreenScene);
        window.show();
    }

    /** This method allows the Main Screen to send the data of the selected part to the created controller object. */
    public void sendPart(Part part) {
        idText.setText(String.valueOf(part.getId()));
        nameText.setText(part.getName());
        inventoryText.setText(String.valueOf(part.getStock()));
        priceText.setText(String.valueOf(part.getPrice()));
        minText.setText(String.valueOf(part.getMin()));
        maxText.setText(String.valueOf(part.getMax()));

        // select the type of object to send, and the initial conditions of the new screen.
        if(part instanceof InHouse)
        {
            inHouseButton.setSelected(true);
            machineIdOrCompanyLabelText.setText(String.valueOf(((InHouse) part).getMachineId()));
            finalLabel.setText("Machine ID");
        }

        if(part instanceof Outsourced)
        {
            outsourcedButton.setSelected(true);
            machineIdOrCompanyLabelText.setText(((Outsourced) part).getCompanyName());
            finalLabel.setText("Company Name");
        }

    }

    /** This method wraps together the common code to generate an error dialogue box */
    private void errorDialogueBox(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    /** Method to set initial conditions of the controller. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}

