package com.example.proj.model;

public class InHouse extends Part {

    private int machineID;

    /**
     * Inhouse constructor
     * @param id the id to set
     * @param name the name to set
     * @param price the price to set
     * @param stock the stock to set
     * @param min the min to set
     * @param max the max to set
     * @param machineID the machineId to set
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Inhouse machineID setter
     * @param machineID the machineID to be set
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * Inhouse machineID getter
     * @return machien ID
     */
    public int getMachineID() {
        return machineID;
    }


}