package JavaFX_Files;

import JavaFX_Files.Model.Inventory;
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

    @FXML private Button exitButton;

    public void setExitButton(){
        System.exit(0);
    }

    /* The all important code that actually allows us to change to a different scene! */

    public void addPartButtonClicked(ActionEvent event) throws IOException {
        // gets the scene to load and sets it in a variable
        Parent AddPartParent = FXMLLoader.load(getClass().getResource(("View/AddPartGUI.fxml")));
        Scene AddPartScene = new Scene(AddPartParent);

        // sets new scene into the window
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();   // need this to get the stage info
        window.setScene(AddPartScene);
        window.show();

    }

    public void addProductButtonClicked(ActionEvent event) throws IOException {
        // gets the scene to load and sets it in a variable
        Parent AddProductParent = FXMLLoader.load(getClass().getResource(("View/AddProductGUI.fxml")));
        Scene AddProductScene = new Scene(AddProductParent);

        // sets new scene into the window
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();   // need this to get the stage info
        window.setScene(AddProductScene);
        window.show();

    }

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

