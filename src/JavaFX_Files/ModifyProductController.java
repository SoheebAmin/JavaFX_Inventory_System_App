package JavaFX_Files;

import JavaFX_Files.Model.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable{

    //Variables for the Parts TableView
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Double> partPricePerUnitCol;
    @FXML private TableColumn<Part, Integer> partStockCol;

    //a buffer that will hold parts being added or removed
    private ObservableList<Part> partsBuffer = currentProduct.getAllAssociatedParts();

    //Variables for the Associated Parts TableView
    @FXML private TableView<Part> aPartsTableView;
    @FXML private TableColumn<Part, Integer> aPartIdCol;
    @FXML private TableColumn<Part, String> aPartNameCol;
    @FXML private TableColumn<Part, Double> aPartPricePerUnitCol;
    @FXML private TableColumn<Part, Integer> aPartStockCol;

    // Variables for Radio Buttons and GUI text fields
    @FXML private TextField idText;
    @FXML private TextField nameText;
    @FXML private TextField inventoryText;
    @FXML private TextField priceText;
    @FXML private TextField minText;
    @FXML private TextField maxText;

    // get the object that is being worked on from the Main Screen Controller.
    private static Product currentProduct = null;
    public static void getCurrentProduct(Product product) {
        currentProduct = product;
    }

    public int addButtonClicked() {
        // grabs selected part
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        //abort function if null
        if(selectedPart == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You need to select a part!");
            alert.showAndWait();
            return 1;
        }
        // adds selected parts to the buffer
        partsBuffer.add(selectedPart);

        // shows the updated associated parts buffer
        aPartsTableView.setItems(partsBuffer);

        aPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        aPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        return 0;
    }

    public int removeButtonClicked() {
        // grabs selected part
        Part selectedPart = aPartsTableView.getSelectionModel().getSelectedItem();

        //abort function if null
        if(selectedPart == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You need to select a part to remove!");
            alert.showAndWait();
            return 1;
        }
        // removes selected parts to the buffer
        partsBuffer.remove(selectedPart);

        // shows the updated associated parts buffer
        aPartsTableView.setItems(partsBuffer);

        aPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        aPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        return 0;

    }

    public int saveButtonClicked(ActionEvent event) throws IOException {

        /* First, values are checked since they may have been edited to incorrect formats */

        // initial values given since required if variables set in if/try blocks.
        int id = 0;
        int inventory = 0;
        double price = 0;
        int min = 0;
        int max = 0;
        boolean errorDetected = false; // to let the function know to terminate once errors are displayed
        boolean mixOrMaxInvalid = false; // to ensure the right error shows for mix/max user error

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


        // check if any dialogue box was produced. If so, exit the function
        if (errorDetected) {
            return 1;
        }

        // Alert asking for confirmation if user wants to save.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            // add it to the Inventory observable list, so it saved and displayed in GUI.

            Inventory.updateProduct(id, new Product(id, name, price, inventory, min, max));

            // removes previous associated parts
            System.out.println("For product " +currentProduct.getName() + ", parts before removing: " + currentProduct.getAllAssociatedParts());
            currentProduct.removeAllAssociatedParts();


            // replaces them with the ones in the buffer
            currentProduct.setAllAssociatedParts(partsBuffer);
            System.out.println("For product " +currentProduct.getName() + ", parts after trying to add are: " + currentProduct.getAllAssociatedParts());

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

    // method to get data from the main screen.
    public void sendProduct(Product product) {
        idText.setText((String.valueOf((product.getId()))));
        nameText.setText(product.getName());
        inventoryText.setText(String.valueOf(product.getStock()));
        priceText.setText(String.valueOf(product.getPrice()));
        minText.setText(String.valueOf(product.getMin()));
        maxText.setText(String.valueOf(product.getMax()));
    }

    private void errorDialogueBox(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // display the associated parts
        aPartsTableView.setItems(currentProduct.getAllAssociatedParts());
        System.out.println("For product " +currentProduct.getName() + ", the current associated parts are: " + currentProduct.getAllAssociatedParts());

        aPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        aPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // grabs the parts tableviews
        partsTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}

