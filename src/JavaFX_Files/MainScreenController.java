package JavaFX_Files;

import JavaFX_Files.Model.Inventory;
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

    // Error Labels
    @FXML private Label selectPartErrorLabel;
    @FXML private Label selectProductErrorLabel;

    /* Functions for Part Buttons */

    public void addPartButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "View/AddPartGUI.fxml");

    }

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

    public int deletePartButtonClicked() {
        // grabs selected product
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
        }
        return 0;
    }

    // Search for part
    public boolean searchPart(int id) {
        for(Part part : Inventory.getAllParts())
        {
            if(part.getId() == id)
                return true;
        }
        return false;
    }


    // Select a part
    public Part selectPart(int id) {
        for(Part part : Inventory.getAllParts())
        {
            if(part.getId() == id)
                return part;
        }
        return null;
    }

    // Filter part list
    public ObservableList<Part> filterPart(String criteria) {
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



    /* Functions For Product Buttons */
    public void addProductButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "View/AddProductGUI.fxml");
    }
    public  int deleteProductButtonClicked() {
        // grabs selected product
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        //abort function if null
        if(selectedProduct == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You need to select a product!");
            alert.showAndWait();
            return 1;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            // add logic to check if there's an associated part

            Inventory.deleteProduct(selectedProduct);
        }
        return 0;
    }


    public int modifyProductButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/ModifyProductGUI.fxml"));
        loader.load();

        // Send the data selected from the table view to the Modify Part Menu.
        ModifyProductController MPrC = loader.getController();
        try
        {
            MPrC.sendProduct(productsTableView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You need to select a product!");
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


    // Search for products
    public boolean searchProduct(int id) {
        for(Product product: Inventory.getAllProducts())
        {
            if(product.getId() == id)
                return true;
        }
        return false;
    }

    // Select a product
    public Product selectProduct(int id) {
        for(Product product : Inventory.getAllProducts())
        {
            if(product.getId() == id)
                return product;
        }
        return null;
    }

    // Filter product list
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



    public void setExitButton(){
        System.exit(0);
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

        // to populate the parts table
        partsTableView.setItems(Inventory.getAllParts());
        //partsTableView.setItems(filterPart("Mot"));

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


        // to populate the product table
        productsTableView.setItems(Inventory.getAllProducts());
        //productsTableView.setItems(filterProduct("X"));



        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));




        /*
       Testing methods commented out

        partsTableView.getSelectionModel().select(selectPart(2));
        productsTableView.getSelectionModel().select(selectProduct(2));


        if(updatePart(1, new inHouse(1, "Bloator", 120.05, 20, 1, 3, 8689)))
            System.out.println("Update Done, Son");
        else
            System.out.println("Fail");


        if(deletePart(1))
            System.out.println("Deleted");
        else
            System.out.println("No Match");


        if(updateProduct(1, new Product(1, "BoatorCar", 9990.05, 2, 1, 3)))
            System.out.println("Update Done, Son");
        else
            System.out.println("Fail");


        if(deleteProduct(1))
            System.out.println("Deleted");
        else
            System.out.println("No Match");

         */

    }
}

