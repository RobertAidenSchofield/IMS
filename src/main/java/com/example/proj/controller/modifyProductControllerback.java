package com.example.proj.controller;


import com.example.proj.model.Inventory;
import com.example.proj.model.Part;
import com.example.proj.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.proj.controller.mainScreenController.ProductModGetter;
import static com.example.proj.model.Inventory.getAllParts;
import static com.example.proj.model.Product.addAssociatedPart;
import static com.example.proj.model.Product.getAllAssociatedParts;


/**
 * The Modify product controller.
 * @author Robert Schofied
 */
public class modifyProductControllerback implements Initializable {
        /**
         * Sets Stage
         */
        Stage stage;
        /**
         * Sets Scene
         */
        Parent scene;
        private Product selectedProduct;

        private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

        @FXML
        private TableColumn<Part, Integer> modassociatedinventory;

        @FXML
        private TableColumn<Part, Integer> modassociatedpartid;

        @FXML
        private TableColumn<Part,String> modassociatedpartname;

        @FXML
        private TableColumn<Part, Double> modassociatedprice;

        @FXML
        private TableView<Part> modifyproductassociatedpartdata;

        @FXML
        private TextField modifyproductid;

        @FXML
        private TextField modifyproductinv;

        @FXML
        private TextField modifyproductmax;

        @FXML
        private TextField modifyproductmin;

        @FXML
        private TextField modifyproductname;

        @FXML
        private TableView<Part> modifyproductpartdata;

        @FXML
        private TextField modifyproductprice;

        @FXML
        private AnchorPane modifyproductscreen;

        @FXML
        private TextField modifyproductsearch;

        @FXML
        private TableColumn<Part, Integer> modpartid;

        @FXML
        private TableColumn<Part, Integer> modpartinventory;

        @FXML
        private TableColumn<Part,String> modpartname;

        @FXML
        private TableColumn<Part, Double> modpartprice;

        @FXML
        private Button searchmodifypartsbutton;

        /**
         * Adds Part to associated part pane
         *
         * @param event Activated when add button clicked
         */
        @FXML
        void modifyproductadd(ActionEvent event) {
                Part part = modifyproductpartdata.getSelectionModel().getSelectedItem();
                if(part==null) {
                        alertHandler(5);
                }
                else if (!associatedParts.contains(part)) {
                        addAssociatedPart(part);
                        modifyproductassociatedpartdata.setItems(getAllAssociatedParts());
                }

        }

        /**
         * Cancels modify action, verifies selection with user and redirects to main screen
         *
         * @param event activated when cancel button clicked
         * @throws IOException
         */
        @FXML
        void modifyproductcancelhandler(ActionEvent event)throws IOException {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Cancel changes and return to the main screen?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getClassLoader().getResource("com/example/proj/mainScreen.fxml"));
                        stage.setTitle("IMS");
                        stage.setScene(new Scene((Parent) scene));
                        stage.show();
                }
        }

        /**
         * Removes Associated Part from Product
         * @param event Acvitated when Remove button clicked
         * @throws IOException
         */
        @FXML
        void modifyproductremove(ActionEvent event) throws IOException {

                Part selectedPart = modifyproductassociatedpartdata.getSelectionModel().getSelectedItem();
                if (selectedPart == null){
                        alertHandler(5);
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Do you want to remove the selected part?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                        Product.deleteAssociatedPart(selectedPart);
                        modifyproductassociatedpartdata.setItems(getAllAssociatedParts());
                }
        }

        /**
         * Updates product in inventory with field validation then redirects to mainScreen
         * Checks if the product has an associated part.
         * RUNTIME ERROR - included .getClassLoader as redirect would not execute
         * @param event Activated when the SAVE button is clicked
         * @throws IOException
         */
        @FXML
        void modifyproductsavehandler(ActionEvent event) throws IOException {

                try {
                        int id = Integer.parseInt(modifyproductid.getText());
                        String name = modifyproductname.getText();
                        int stock = Integer.parseInt(modifyproductinv.getText());
                        double price = Double.parseDouble(modifyproductprice.getText());
                        int min = Integer.parseInt(modifyproductmin.getText());
                        int max = Integer.parseInt(modifyproductmax.getText());

                        if(max<min){
                                alertHandler(3);
                                return;
                        }
                        else if (stock > max || stock < min) {
                                alertHandler(4);
                                return;
                        }
                        else {
                                Product newProduct = new Product(id, name, price, stock, min, max);
                                newProduct.setName(modifyproductname.getText());
                                newProduct.setPrice(Double.parseDouble(modifyproductprice.getText()));
                                newProduct.setStock(Integer.parseInt(modifyproductinv.getText()));
                                newProduct.setMin(Integer.parseInt(modifyproductmin.getText()));
                                newProduct.setMax(Integer.parseInt(modifyproductmax.getText()));


                                if(newProduct != associatedParts) {
                                        Inventory.updateProduct(0, newProduct);
                                }

                                for(Part part : associatedParts) {
                                        if (part != associatedParts){
                                                addAssociatedPart(part);
                                        }
                                }
                                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                                scene = FXMLLoader.load(getClass().getClassLoader().getResource("com/example/proj/mainScreen.fxml"));
                                stage.setScene(new Scene(scene));
                                stage.show();


                        }
                }
                catch(Exception e){
                        alertHandler(1);
                }
        }

        /**
         * Searches products and returns results
         * @param event Activated when Search button clicked
         */
        @FXML
        void modifyproductsearchhandler(ActionEvent event) {

                String s = modifyproductsearch.getText();
                ObservableList<Part> foundParts = FXCollections.observableArrayList();
                ObservableList<Part> allParts = Inventory.getAllParts();

                for (Part part : allParts) {
                        if (String.valueOf(part.getId()).contains(s) ||
                                part.getName().toLowerCase().contains(s.toLowerCase())) {
                                foundParts.add(part);
                        }
                }
                if(foundParts.isEmpty()) {
                        alertHandler(2);

                }
                else if (modifyproductsearch.getText().trim().isEmpty()) {
                        modifyproductpartdata.setItems(getAllParts());
                }
                else {
                        modifyproductpartdata.setItems(foundParts);
                        modifyproductsearch.setText("");
                }

        }

        /**
         * Takes the product selected on the Main Screen and sends data to the fields on the Modify Product screen
         *
         */
        public void prodGetter() {

                /*Populate Product text boxes*/
                selectedProduct= ProductModGetter();

                modifyproductid.setText(String.valueOf(selectedProduct.getId()));
                modifyproductname.setText(selectedProduct.getName());
                modifyproductinv.setText(String.valueOf(selectedProduct.getStock()));
                modifyproductprice.setText(String.valueOf(selectedProduct.getPrice()));
                modifyproductmax.setText(String.valueOf(selectedProduct.getMax()));
                modifyproductmin.setText(String.valueOf(selectedProduct.getMin()));

        }

        /**
         * Populates tables
         * @param url
         * @param resourceBundle
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                prodGetter();

                modifyproductpartdata.setItems(Inventory.getAllParts());
                /* Populate tables */
                modpartid.setCellValueFactory(new PropertyValueFactory<>("id"));
                modpartname.setCellValueFactory(new PropertyValueFactory<>("name"));
                modpartinventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
                modpartprice.setCellValueFactory(new PropertyValueFactory<>("price"));

                modifyproductassociatedpartdata.setItems(selectedProduct.getAllAssociatedParts());
                modassociatedpartid.setCellValueFactory(new PropertyValueFactory<>("id"));
                modassociatedpartname.setCellValueFactory(new PropertyValueFactory<>("name"));
                modassociatedinventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
                modassociatedprice.setCellValueFactory(new PropertyValueFactory<>("price"));

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
                                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                                alert.showAndWait();
                                break;
                        case 4:
                                alert.setContentText("Inventory must be a number equal to or between Min and Max");
                                alert.showAndWait();
                                break;
                        case 5:
                                alert.setContentText("Product not selected");
                                alert.showAndWait();
                                break;
                        case 7:
                                alert.setContentText("Name cannot be empty.");
                                alert.showAndWait();
                                break;

                }
        }
}
