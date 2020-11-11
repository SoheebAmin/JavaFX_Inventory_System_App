package JavaFX_Files.Controller;

import JavaFX_Files.Model.Inventory;
import JavaFX_Files.ModifyPartController;
import JavaFX_Files.Part;
import JavaFX_Files.Product;
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

/** This is the class for the main controller that loads at the start of the program. */
public class MainScreenController implements Initializable{

    // Variables for the Parts Table tableview and columns.
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Double> partPricePerUnitCol;
    @FXML private TableColumn<Part, Integer> partStockCol;

    // Variables for the Products Table tableview and columns.
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> productIdCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Double> productPricePerUnitCol;
    @FXML private TableColumn<Product, Integer> productStockCol;

    //Search bar variables
    @FXML private TextField partSearch;
    @FXML private TextField productSearch;

    /** This method allows us to search by ID or part name in the search bar */
    public void partSearchKeystroke() {
        ObservableList<Part> partsBuffer = FXCollections.observableArrayList();
        String currentlyTyped = partSearch.getText();
        if(currentlyTyped.matches("^\\d+$")) //use regex to confirm if input is an int
        {
            int id = Integer.parseInt(currentlyTyped);
            Part partToAdd = getPart(id);
            if(partToAdd != null)
                partsBuffer.add(partToAdd);
            else
                partsBuffer = Inventory.getAllParts();
        }
        else
        {
            partsBuffer = filterPart(currentlyTyped);
        }
            partsTableView.setItems(partsBuffer);

            partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /** This method gets the part object using its id. */
    public static Part getPart(int id) {
        for(Part part : Inventory.getAllParts())
        {
            if(part.getId() == id)
                return part;
        }
        return null;
    }

    /** This method allows us to filter through the name of parts based on a user-provided string. *
     @param criteria is updated for every key typed by the user
     */
    public static ObservableList<Part> filterPart(String criteria) {
        // first, clear the filter if it needs to be
        if(!(Inventory.getFilteredParts().isEmpty()))
        {
            Inventory.getFilteredParts().clear();
        }
        // if not, create it based on the criteria.
        for(Part part : Inventory.getAllParts())
        {
            if(part.getName().contains(criteria))
            {
                Inventory.getFilteredParts().add(part);
            }
        }
        // checks to see if any results were found. If not, return original list.
        if((Inventory.getFilteredParts().isEmpty()))
        {
            return Inventory.getAllParts();
        }
        return Inventory.getFilteredParts();
    }

    /** This function loads the scene for the Add Part JavaFX_Files.Controller*/
    public void addPartButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "View/AddPartGUI.fxml");

    }

    /** This method grabs the data of the part selected, sends it to a new JavaFX_Files.Controller object, which is then loaded in a new scene. */
    public int modifyPartButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/ModifyPartGUI.fxml"));
        loader.load();

        // Send the data selected from the table view to the Modify Part Menu.
        ModifyPartController MPC = loader.getController();
        try
        {
            Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            MPC.sendPart(selectedPart);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You need to select a part!");
            alert.showAndWait();
            return 1;
        }

        // Creates new scene.
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        window.setScene(new Scene(scene));
        window.show();
        return 0;
    }

    /** This function loads the scene for the Add Part JavaFX_Files.Controller. */
    public int deletePartButtonClicked() {
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            Inventory.deletePart(selectedPart);
            partsTableView.setItems(Inventory.getAllParts()); // to reset the list in case a search happened before it.
        }
        return 0;
    }

    /* Functions For Product Buttons */

    /** This method allows us to search by ID or product name in the search bar */
    public void productSearchKeystroke() {
        ObservableList<Product> productBuffer = FXCollections.observableArrayList();
        String currentlyTyped = productSearch.getText();
        if(currentlyTyped.matches("^\\d+$")) //use regex to confirm if input is an int
        {
            int id = Integer.parseInt(currentlyTyped);
            Product productToAdd = getProduct(id);
            if(productToAdd != null)
                productBuffer.add(productToAdd);
            else
                productBuffer = Inventory.getAllProducts();
        }
        else
        {
            productBuffer = filterProduct(currentlyTyped);
        }
        productsTableView.setItems(productBuffer);

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /** This method allows us to get a product by using its generated Id. */
    public Product getProduct(int id) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == id)
                return product;
        }
        return null;
    }

    /** This method loads the Add Product JavaFX_Files.Controller in a new scene. */
    public void addProductButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "View/AddProductGUI.fxml");
    }

    /** Method to delete the selected product object from the Inventory class, while first checking if there are associated parts. */
    public  int deleteProductButtonClicked() {
        // grabs selected product
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        //abort function if null
        if(selectedProduct == null)
        {
            errorDialogueBox("You need to select a product!");
            return 1;
        }
        if(!selectedProduct.getAllAssociatedParts().isEmpty())
        {
            errorDialogueBox("There are associated parts that must be removed first!");
            return 1;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            Inventory.deleteProduct(selectedProduct);
            productsTableView.setItems(Inventory.getAllProducts()); // to reset the list in case a search happened before it.
        }
        return 0;
    }

    /** This method grabs the data of the product selected, sends it to a new JavaFX_Files.Controller object, which is then loaded in a new scene. */
    public int modifyProductButtonClicked(ActionEvent event) {
        try
        {
            Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
            ModifyProductController.getCurrentProduct(selectedProduct);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("View/ModifyProductGUI.fxml"));
            loader.load();

            // Send the data selected from the table view to the Modify Part Menu.
            ModifyProductController MPrC = loader.getController();
            ModifyProductController.getCurrentProduct(selectedProduct);
            MPrC.sendProduct(selectedProduct);

            // Creates new scene.
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            window.setScene(new Scene(scene));
            window.show();
        }
        catch (Exception e)
        {
            errorDialogueBox("You need to select a product!");
            return 1;
        }
        return 0;
    }

    /** This method allows us to filter through the name of products based on a user-provided string. *
     @param criteria is updated for every key typed by the user
     */
    public ObservableList<Product> filterProduct(String criteria) {
        // first, clear the filter if it needs to be
        if(!(Inventory.getFilteredProducts().isEmpty()))
        {
            Inventory.getFilteredProducts().clear();
        }
        for(Product product : Inventory.getAllProducts())
        {
            if(product.getName().contains(criteria))
            {
                Inventory.getFilteredProducts().add(product);
            }
        }
        // checks to see if any results were found. If not, return original list.
        if((Inventory.getFilteredProducts().isEmpty()))
        {
            return Inventory.getAllProducts();
        }
        return Inventory.getFilteredProducts();
    }

    /** This method wraps together the common code to generate an error dialogue box */
    private void errorDialogueBox(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    /** This method wraps the common code to change scenes. */
    public void changeScene(ActionEvent event, String sceneName) throws IOException {
        Parent MainScreenParent = FXMLLoader.load(getClass().getResource((sceneName)));
        Scene MainScreenScene = new Scene(MainScreenParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(MainScreenScene);
        window.show();
    }

    /** This method exits the program via the Exit button */
    public void setExitButton(){
        System.exit(0);
    }

    /** Method to set initial conditions of the controller. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // to populate the parts table
        partsTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


        // to populate the product table
        productsTableView.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}

