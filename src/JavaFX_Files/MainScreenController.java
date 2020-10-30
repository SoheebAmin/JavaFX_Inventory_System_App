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

    /* Functions for Part Buttons */

    public void addPartButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "View/AddPartGUI.fxml");
    }

    public  void deletePartButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "View/DeletePartGUI.fxml");
    }


    /* Functions For Product Buttons */

    public void addProductButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "View/AddProductGUI.fxml");
    }

    public  void deleteProductButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "View/DeleteProductGUI.fxml");
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

