package JavaFX_Files;

import JavaFX_Files.Model.Inventory;
import JavaFX_Files.Model.InHouse;
import JavaFX_Files.Model.Outsourced;
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

public class AddPartController implements Initializable{

    // Variables for Radio Buttons and GUI text fields
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outsourcedButton;
    @FXML private TextField idText;
    @FXML private TextField nameText;
    @FXML private TextField inventoryText;
    @FXML private TextField priceText;
    @FXML private TextField minText;
    @FXML private TextField maxText;
    @FXML private TextField machineIdText;
    @FXML private Label finalLabel;

    public void changeToOutsourced() {
        finalLabel.setText("Company Name");
    }

    public void changeToInHouse() {
        finalLabel.setText("Machine ID");
    }

    public int saveButtonClicked(ActionEvent event) throws IOException {
        // initial values given since required if variables set in try blocks.
        int inventory = 0;
        double price = 0;
        int min = 0;
        int max = 0;
        int machineId = 0;
        String companyName = "";

        boolean errorDetected = false;
        boolean mixOrMaxInvalid = false;

        // ID not included since auto-generated.

        // Check if name is not empty
        String name = nameText.getText();
        if (name.equals("")) {
            errorDialogueBox("Name Error: Please enter a part name");
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
            errorDialogueBox("Min cannot be lager than Max");
            errorDetected = true;
        }

        // If in-house is selected, check if machine ID is an int
        if(inHouseButton.isSelected() == true) {
            try {
                machineId = Integer.parseInt(machineIdText.getText());
            } catch (NumberFormatException e) {
                errorDialogueBox("Machine ID Error: Please enter a whole number");
                errorDetected = true;
            }
        }
        else
        {
            // If outsourced is selected, check if anything was entered
            companyName = nameText.getText();
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
            if(inHouseButton.isSelected() == true)
            {
                // add it to the Inventory observable list, so it saved and displayed in GUI.
                int idToSet = Inventory.getPartIdCount() + 1;
                Inventory.addPart(new InHouse(idToSet, name, price, inventory, min, max, machineId));
                Inventory.setPartIdCount(idToSet); //sets the ID count as the new ID

            }
            else
            {
                int idToSet = Inventory.getPartIdCount() + 1;
                Inventory.addPart(new Outsourced(idToSet, name, price, inventory, min, max, companyName));
                Inventory.setPartIdCount(idToSet); //sets the ID count as the new ID
            }
            changeScene(event, "View/MainScreenGUI.fxml");
        }
        return 0;
    }


    // The method to cancel and return to the previous scene
    public void cancelButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "View/MainScreenGUI.fxml");
    }

    public void changeScene(ActionEvent event, String sceneName) throws IOException {
        Parent MainScreenParent = FXMLLoader.load(getClass().getResource((sceneName)));
        Scene MainScreenScene = new Scene(MainScreenParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(MainScreenScene);
        window.show();
    }

    private void errorDialogueBox(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // just to set a default
        inHouseButton.setSelected(true);


    }
}

