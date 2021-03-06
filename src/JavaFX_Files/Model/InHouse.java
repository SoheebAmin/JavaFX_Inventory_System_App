package JavaFX_Files.Model;

import JavaFX_Files.Part;

/** This is the InHouse Class, which is a sub-class of the Parts Class. */
public class InHouse extends Part {

    private int machineId;

    /** This is the inHouse constructor. It sets the machineId, which also initiates the value of machineId */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
