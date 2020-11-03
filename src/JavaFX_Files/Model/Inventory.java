package JavaFX_Files.Model;

import JavaFX_Files.Part;
import JavaFX_Files.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    // Keeps track of the auto-generated Ids
    private static int partIdCount = 0;
    private static int productIdCount = 0;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // For searches
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();


    public static int getPartIdCount() {
        return partIdCount;
    }

    public static void setPartIdCount(int partIdCount) {
        Inventory.partIdCount = partIdCount;
    }

    public static int getProductIdCount() {
        return productIdCount;
    }

    public static void setProductIdCount(int productIdCount) {
        Inventory.productIdCount = productIdCount;
    }

    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }

    public static ObservableList<Product> getFilteredProducts() {
        return filteredProducts;
    }

    // Update part
    public static boolean updatePart(int id, Part updatedPart) {

        int indexCounter = 0; // counter to keep track of index

        for(Part part : Inventory.getAllParts())
        {
            if(part.getId() == id)
            {
                Inventory.getAllParts().set(indexCounter, updatedPart);
                return true;
            }
            indexCounter++;
        }
        return false;
    }

    // Update product
    public static boolean updateProduct(int id, Product updatedProduct) {

        int indexCounter = 0; // counter to keep track of index

        for(Product product : Inventory.getAllProducts())
        {
            if(product.getId() == id)
            {
                Inventory.getAllProducts().set(indexCounter, updatedProduct);
                return true;
            }
            indexCounter++;
        }
        return false;
    }

    // Get the product by its Id
    public static Product getProduct(int id) {

        int indexCounter = 0; // counter to keep track of index

        for(Product product : Inventory.getAllProducts())
        {
            if(product.getId() == id)
            {
                return product;
            }
            indexCounter++;
        }
        return null;
    }

    // Delete part
    public static void deletePart(Part partToDelete) {
        Inventory.getAllParts().remove(partToDelete);
    }

    // Delete product
    public static void deleteProduct(Product productToDelete) {
        Inventory.getAllProducts().remove(productToDelete);
    }



}
