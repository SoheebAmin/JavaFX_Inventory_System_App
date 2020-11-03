package JavaFX_Files;

import JavaFX_Files.Model.Inventory;
import JavaFX_Files.Model.InHouse;
import JavaFX_Files.Model.Outsourced;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** The Main Class that allows the fist scene to load via JavaFX. */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/MainScreenGUI.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    /** The main method to set the pre-populate some parts and products and then set the program into motion. */
    public static void main(String[] args) {
        // creating the pre-placed objects from the parts tableview.
        Inventory.addPart(new InHouse(1, "Generic Motor", 899.99, 2, 1, 3, 8689));
        Inventory.addPart(new InHouse(2, "Generic Piston", 50.25, 5, 1, 3, 6101));
        Inventory.addPart(new Outsourced(3, "Snow Tires Set", 99.99, 6, 1, 4, "IceWheels Inc"));
        Inventory.setPartIdCount(3); // sets a base ID to assume, for adding new parts.

        // creating the pre-placed objects from the products tableview.
        Inventory.addProduct(new Product(1, "Nissan Versa", 8999.99, 1, 1, 3));
        Inventory.addProduct(new Product(2, "Kawasaki Bike ", 3199.99, 2, 1, 3));
        Inventory.setProductIdCount(2); // sets a base ID to assume, for adding new products

        launch(args);

    }
}
