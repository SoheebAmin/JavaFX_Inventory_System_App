package JavaFX_Files;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScreenGUI.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        // creating the pre-placed objects from the parts tableview.
        inHouse part1 = new inHouse(1, "Motor", 12.05, 2, 1, 3, 8689);
        inHouse part2 = new inHouse(2, "Rotor", 21.10, 5, 1, 3, 0101);
        Inventory.addPart(part1);
        Inventory.addPart(part2);

        // creating the pre-placed objects from the products tableview.
        Product product1 = new Product(1, "MotorCar", 120.05, 2, 1, 3);
        Product product2 = new Product(2, "RotorCar", 201.20, 5, 1, 3);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        launch(args);
    }
}
