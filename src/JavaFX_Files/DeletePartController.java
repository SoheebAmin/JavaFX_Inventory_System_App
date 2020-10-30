package JavaFX_Files;

import JavaFX_Files.Model.Inventory;
import JavaFX_Files.Model.inHouse;
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

public class DeletePartController implements Initializable{

    // Variables for all GUI text fields


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

