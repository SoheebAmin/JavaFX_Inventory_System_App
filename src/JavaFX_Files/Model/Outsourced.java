package JavaFX_Files.Model;

import JavaFX_Files.Part;

/** This is the Outsourced Class, which is a sub-class of the Parts Class. */
public class Outsourced extends Part {

    private String companyName;

    /** This is the Outsourced constructor. It sets the machineId, which also initiates the value of machineId */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
