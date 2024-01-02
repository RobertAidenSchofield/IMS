package com.example.proj.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Inventory {
    /**All Parts in the inventory.*/
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**All Products in the inventory.*/
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add parts to inventory
     * @param newPart part to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Add products to inventory
     * @param newProduct product to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * searches inventory for part by name
     * toLowerCase for source and passed string to make case-insensitive
     * @param partName String part name to lookup
     * @return Observable list of the parts found by Name
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> partNameFound = FXCollections.observableArrayList();

        for (Part part : allParts){
            if (part.getName().toLowerCase().contains((partName.toLowerCase()))) {
                partNameFound.add(part);
            }
            return partNameFound;
        }
        return partNameFound;
    }

    /**
     * Searches through parts, returns if found by ID.
     * @param partID Int part ID to lookup
     * @return Observable list of parts found by ID
     */
    public static Part lookupPart(int partID) {
        Part partIdFound = null;

        for(Part part : allParts){
            if(part.getId() == partID){
                partIdFound = part;
            }
        }
        return partIdFound;
    }

    /**
     * Searches through products, returns if found by ID.
     * @param productID Int product ID to lookup
     * @return Observable list of products found by ID.
     */
    public static Product lookupProduct(int productID) {
        Product productIdFound = null;

        for(Product product : allProducts){
            if(product.getId() == productID){
                productIdFound= product;
            }
        }
        return productIdFound;
    }


    /**
     * searches inventory for product by name
     * toLowerCase for source and passed string to make case-insensitive
     * @param productName String product name to lookup
     * @return Observable list of products found by name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productFound = FXCollections.observableArrayList();

        for(Product product : allProducts){
            if(product.getName().toLowerCase().contains((productName.toLowerCase()))){
                productFound.add(product);
            }
        }
        return productFound;
    }

    /**
     * updates part and index
     * @param index index of part to update
     * @param selectedPart part to update values
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);

    }

    /**
     * updates product and index
     * @param index index of product to update
     * @param newProduct product to update values
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes a selected part
     * @param selectedPart the part to delete
     * @return true if part was deleted
     */
    public static boolean deletePart(Part selectedPart) {

        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Delete a selected product
     * @param selectedProduct the product to delete
     * @return true if product was deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {

        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }

    }

    /**
     *
     * @return Observable list of all parts
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * @return Observable list of all products
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }



}
