package com.example.proj.controller;

import com.example.proj.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.proj.model.Inventory.getAllParts;
import static com.example.proj.model.Inventory.getAllProducts;

/**
 * Main screen class, the Controller for Main screen View
 * @author Robert Schofield
 */
public class mainScreenController implements Initializable {
    /**
     * Sets Scene
     */
    Parent scene;
    /**
     * Sets Stage
     */
    Stage stage;
    /**
     * The part object selected in the table view
     */
    private static Part selectedPart;

    /**
     * The product object selected in the table view
     */
    private static Product selectedProduct;

    @FXML
    private Button mainpartsearch;

    @FXML
    private Button mainproductsearch;

    @FXML
    private Label messagebar;

    @FXML
    private TableColumn<Part, Integer> partid;

    @FXML
    private TableColumn<Part, String> partname;

    @FXML
    private AnchorPane parts;

    @FXML
    private TextField partsearch;

    @FXML
    private TableColumn<Part, Integer> partsinventorylevel;

    @FXML
    private TableColumn<Part, Double> partsprice;

    @FXML
    private TableView<Part> partstable;

    @FXML
    private TableColumn<Product, Integer> productid;

    @FXML
    private TableColumn<Product, Integer> productinventorylevel;

    @FXML
    private TableColumn<Product, String> productname;

    @FXML
    private TableColumn<Product, Double> productprice;

    @FXML
    private TextField productsearch;

    @FXML
    private TableView<Product> productstable;


    /**
     * Redirects from Main screen to Add part Screen
     * @param event Activated when ADD button clicked
     * @throws IOException
     */
    @FXML
    public void mainaddparthandler(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/proj/addPartScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * Redirects from Main screen to Add product Screen
     * RUNTIME ERROR - included .getClassLoader as redirect would not execute
     * @param event Activated when ADD button clicked
     * @throws IOException
     */
    @FXML
    void mainaddproducthandler(ActionEvent event) throws IOException  {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getClassLoader().getResource("com/example/proj/addProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes selected part from part table with user verification
     * @param event Activated when Delete button clicked
     */
    @FXML
    void maindeleteparthandler(ActionEvent event) {
        Part selectedPart = (Part) partstable.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            alertHandler(7);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }

    }

    /**
     * Deletes selected part from part table with user verification
     * @param event Activated when Delete button clicked
     */
    @FXML
    void maindeleteproducthandler(ActionEvent event) {
        Product selectedProduct = (Product) productstable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null){
            alertHandler(6);

            return;
        }

        ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
        if(!associatedParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("This Product has an Associated Part. Cannot Delete!");
            alert.showAndWait();
            return;

        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want to delete the selected product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(selectedProduct);
        }
    }

     /**
     * Exits the program with user verification
     * @param event Activated when Exit button clicked
     */
    @FXML
    void mainexithandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want to exit ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }



    }

    /**
     * Redirect from Mainscreen to modify part screen
     * RUNTIME ERROR - included .getClassLoader as redirect would not execute
     * @param event Activated when Modify button clicked
     * @throws IOException
     */
    @FXML
    void mainmodifyparthandler(ActionEvent event)throws IOException {
        selectedPart =  partstable.getSelectionModel().getSelectedItem();
        if(selectedPart == null){
            alertHandler(7);
        }
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getClassLoader().getResource("com/example/proj/modifyPartScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Redirects from Mainscreen to modify product screen
     * RUNTIME ERROR - included .getClassLoader as redirect would not execute
     * @param event Activated when Modify button clicked
     * @throws IOException
     */
    @FXML
    void mainmodifyproducthandler(ActionEvent event) throws IOException {

         selectedProduct = productstable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null){
            alertHandler(6);

        }
        else {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getClassLoader().getResource("com/example/proj/modifyProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        }
    }

    /**
     * Searches parts with text input from user
     * RUNTIME ERROR - added try and catch as looking up part by id via if-else statement produced an error
     * @param event Activated when Search button clicked
     */
    @FXML
    void mainpartsearchbutton(ActionEvent event) {

        String s = partsearch.getText();

        try{
            int id=Integer.parseInt(s);

            ObservableList<Part> foundPartsID = FXCollections.observableArrayList();
            foundPartsID.add(Inventory.lookupPart(id));
            if(foundPartsID.get(0)!= null) {
            partstable.setItems(foundPartsID);
            partsearch.setText("");
            }
            else{
            alertHandler(3);
                partsearch.setText("");
            }
            return;
        }
        catch (Exception e){}

        if(Inventory.lookupPart(s).isEmpty()){
            alertHandler(3);
            partsearch.setText("");

        }
        else if (partsearch.getText().trim().isEmpty()) {
            partstable.setItems(getAllParts());
        }

        else {
            partstable.setItems(Inventory.lookupPart(s));
            partsearch.setText("");
        }

    }

    /**
     * Searches products with text input from user
     * RUNTIME ERROR - added try and catch as looking up part by id via if-else statement produced an error
     * @param event Activated when Search button clicked
     */
    @FXML
    void mainproductsearchbutton(ActionEvent event) {

        String s = productsearch.getText();
        try{

            int id=Integer.parseInt(s);

            ObservableList<Product> foundProductID = FXCollections.observableArrayList();
            foundProductID.add(Inventory.lookupProduct(id));
            if(foundProductID.get(0)!= null) {
                productstable.setItems(foundProductID);
                productsearch.setText("");
            }
            else{
                alertHandler(2);
                productsearch.setText("");
            }
            return;
        }
        catch (Exception e){}

        if(Inventory.lookupProduct(s).isEmpty()){
            alertHandler(2);
            productsearch.setText("");
        }
        else if (productsearch.getText().trim().isEmpty()) {
            productstable.setItems(getAllProducts());
        }
        else {
            productstable.setItems(Inventory.lookupProduct(s));
            productsearch.setText("");
        }

    }

    /**
     * Used to pass selected part to another view
     * @return Selected part from main screen part table
     */
    public static Part PartModGetter(){
        return selectedPart;

    }
    /**
     * Used to pass selected product to another view
     * @return Selected product from main screen products table
     */
    public static Product ProductModGetter(){
        return selectedProduct;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Parts
        partstable.setItems(getAllParts());
        partid.setCellValueFactory(new PropertyValueFactory<>("id"));
        partname.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsinventorylevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsprice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //products
        productstable.setItems(getAllProducts());
        productid.setCellValueFactory((new PropertyValueFactory<>("id")));
        productname.setCellValueFactory(new PropertyValueFactory<>("name"));
        productinventorylevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productprice.setCellValueFactory(new PropertyValueFactory<>("price"));
       /* InHouse tire = new InHouse(1,"Tire", 50.55, 2, 1, 10,11);
        Inventory.addPart(tire);

        Outsourced headlight = new Outsourced(11,"Headlight", 22.22, 6, 1,8,"Light CO");
        Inventory.addPart(headlight);*/

    }

    /**
     * Alert Handler for various actions
     * @param alertType int value for desired alerts
     */
    private void alertHandler(int alertType){
        Alert alert= new Alert(Alert.AlertType.ERROR);
        switch (alertType) {
            case 1:
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setContentText("Product not found");
                alert.showAndWait();
                break;
            case 3:
                alert.setContentText("Part not found");
                alert.showAndWait();
                break;

            case 6:
                alert.setContentText("Product not selected");
                alert.showAndWait();
                break;
            case 7:
                alert.setContentText("Part not selected");
                alert.showAndWait();
                break;
            case 8:
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
            case 9:
                alert.setContentText("Are you sure you want to delete?");
                alert.showAndWait();
                break;


        }
    }
}
