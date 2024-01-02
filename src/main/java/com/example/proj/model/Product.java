package com.example.proj.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Product {

    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * Product constructor
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Product id getter
     * @return int ID
     */
    public int getId() {
        return id;
    }

    /**
     * Product name getter
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Product max getter
     * @return int max
     */
    public int getMax() {

        return max;
    }

    /**
     * Product min getter
     * @return int min
     */
    public int getMin() {

        return min;
    }

    /**
     * Product price getter
     * @return double price
     */

    public double getPrice() {
        return price;
    }

    /**
     * Product stock getter
     * @return int Stock
     */
    public int getStock() {
        return stock;
    }


    /**
     *Product id setter
     * @param id int id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *Product name setter
     * @param name String name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *Product price setter
     * @param price double price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *Product stock setter
     * @param stock int stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Product min setter
     * @param min int min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *Product max setter
     * @param max int max
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**
     *Associated part adder
     * @param part associated part to be added
     */
    public static void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Associated part delete
     * @param selectedAssociatedPart Part to be associated
     * @return true is asscoiated part is deleted
     */
    public static boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**
     *  Returns associated parts in list
     * @return Observable list of associated parts
     */
    public static ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

    /**
     * @param associatedParts the associated parts to set
     */
    public  void setAssociatedParts(ObservableList<Part> associatedParts) { Product.associatedParts = associatedParts; }

}
