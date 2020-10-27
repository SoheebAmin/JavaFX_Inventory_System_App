package JavaFX_Files;

import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable{


    @FXML private Button exitButton;

    public void setExitButton(){
        System.exit(0);
    }

    /* The all important code that actually allows us to change to a different scene! */

    public void addPartButtonClicked(ActionEvent event) throws IOException {
        // gets the scene to load and sets it in a variable
        Parent AddPartParent = FXMLLoader.load(getClass().getResource(("AddPartGUI.fxml")));
        Scene AddPartScene = new Scene(AddPartParent);

        // sets new scene into the window
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();   // need this to get the stage info
        window.setScene(AddPartScene);
        window.show();

    }

    public void addProductButtonClicked(ActionEvent event) throws IOException {
        // gets the scene to load and sets it in a variable
        Parent AddProductParent = FXMLLoader.load(getClass().getResource(("AddProductGUI.fxml")));
        Scene AddProductScene = new Scene(AddProductParent);

        // sets new scene into the window
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();   // need this to get the stage info
        window.setScene(AddProductScene);
        window.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

