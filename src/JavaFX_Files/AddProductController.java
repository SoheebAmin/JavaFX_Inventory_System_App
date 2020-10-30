package JavaFX_Files;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable{

    // Variables for all GUI text fields
    @FXML
    private TextField idText;
    @FXML private TextField nameText;
    @FXML private TextField inventoryText;
    @FXML private TextField priceText;
    @FXML private TextField minText;
    @FXML private TextField maxText;


    public void saveButtonClicked(ActionEvent event) throws IOException {

        int id = Integer.parseInt(idText.getText());
        String name = nameText.getText();
        int inventory = Integer.parseInt(inventoryText.getText());
        double price = Double.parseDouble(priceText.getText());
        int min = Integer.parseInt(minText.getText());
        int max = Integer.parseInt(maxText.getText());

        // add it to the Inventory observable list, so it saved and displayed in GUI.
        Inventory.addProduct(new Product(id, name, price, inventory, min, max));

        changeScene(event, "MainScreenGUI.fxml");
    }

    public void cancelButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "MainScreenGUI.fxml");
    }

    public void changeScene(ActionEvent event, String sceneName) throws IOException {
        // gets the scene to load and sets it in a variable
        Parent MainScreenParent = FXMLLoader.load(getClass().getResource((sceneName)));
        Scene MainScreenScene = new Scene(MainScreenParent);

        // sets new scene into the window
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();   // need this to get the stage info
        window.setScene(MainScreenScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

