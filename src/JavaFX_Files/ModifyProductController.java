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

/** This method is the class to create the Modify Product Controller object. */
public class ModifyProductController implements Initializable{

    //Variables for the Parts TableView
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Double> partPricePerUnitCol;
    @FXML private TableColumn<Part, Integer> partStockCol;

    //a buffer that will hold parts being added or removed. Starts by grabbing current associated parts.
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

    // For the search search box
    @FXML private TextField partSearch;

    // get the object that is being worked on from the Main Screen Controller.
    private static Product currentProduct = null;

    /**  This method allows us to store which object we are working.
     It is identified via the user selection in the MainScreenController */
    public static void getCurrentProduct(Product product) {
        currentProduct = product;
    }

    /** This method allows us to search by ID or part name in the search bar */
    public void partSearchKeystroke() {
        ObservableList<Part> searchPartsBuffer = FXCollections.observableArrayList();
        String currentlyTyped = partSearch.getText();
        if(currentlyTyped.matches("^\\d+$")) //use regex to confirm if input is an int
        {
            int id = Integer.parseInt(currentlyTyped);
            Part partToAdd = MainScreenController.getPart(id);
            if(partToAdd != null)
                searchPartsBuffer.add(partToAdd);
            else
                searchPartsBuffer = Inventory.getAllParts();
        }
        else
        {
            searchPartsBuffer = MainScreenController.filterPart(currentlyTyped);
        }
        partsTableView.setItems(searchPartsBuffer);

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /** This method allows us to add an associated part to our buffer observable list.
     It also then updates the GUI to reflect the users selection. */
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

    /** This method allows the user to remove an associated part from the buffer.
     It also updates the GUI to display accordingly. */
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
        // Alert asking for confirmation if user wants to delete.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {// removes selected parts to the buffer
            partsBuffer.remove(selectedPart);

            // shows the updated associated parts buffer
            aPartsTableView.setItems(partsBuffer);

            aPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            aPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            aPartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            aPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        }
        return 0;
    }

    /** This method allows the user to save the changes to any fields and associated objects for the product.
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
            // saves all the updated fields into a new product
            Product recreatedProduct = new Product(id, name, price, inventory, min, max);

            // the new product replaces the old one.
            Inventory.updateProduct(id, recreatedProduct);

            // the associated parts set into the buffer (or the ones there by default) are saved
            recreatedProduct.setAllAssociatedParts(partsBuffer);

            changeScene(event, "View/MainScreenGUI.fxml");
        }
        return 0;
    }


    /** This method returns to the MainScreenController without making any changes to the Inventory class. */
    public void cancelButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "View/MainScreenGUI.fxml");
    }


    /** This method wraps the common code to change scenes */
    public void changeScene(ActionEvent event, String sceneName) throws IOException {
        Parent MainScreenParent = FXMLLoader.load(getClass().getResource((sceneName)));
        Scene MainScreenScene = new Scene(MainScreenParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(MainScreenScene);
        window.show();
    }

    /** This method allows the Main Screen to send the data of the selected product to the created controller object. */
    public void sendProduct(Product product) {
        idText.setText((String.valueOf((product.getId()))));
        nameText.setText(product.getName());
        inventoryText.setText(String.valueOf(product.getStock()));
        priceText.setText(String.valueOf(product.getPrice()));
        minText.setText(String.valueOf(product.getMin()));
        maxText.setText(String.valueOf(product.getMax()));
    }

    /** This method wraps together the common code to generate an error dialogue box. */
    private void errorDialogueBox(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    /** Method to set initial conditions of the controller. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // display the associated parts
        aPartsTableView.setItems(currentProduct.getAllAssociatedParts());

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

