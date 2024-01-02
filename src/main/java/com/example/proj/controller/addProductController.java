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
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.proj.model.Inventory.getAllParts;

/**
 * Add product controller.
 * @author Robert Schofield
 */
public class addProductController implements Initializable {

        /**
         * Sets Stage.
         */
        Stage stage;
        /**
         * Sets Scene.
         */
        Parent scene;

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
        private TableView<Part> addproductassociatedpartdata;

        @FXML
        private TextField addproductid;

        @FXML
        private TextField addproductinv;

        @FXML
        private TextField addproductmax;

        @FXML
        private TextField addproductmin;

        @FXML
        private TextField addproductname;

        @FXML
        private TableView<Part> addproductpartdata;

        @FXML
        private TextField addproductprice;

        @FXML
        private AnchorPane addproductscreen;

        @FXML
        private TextField addproductsearch;

        @FXML
        private TableColumn<Part, Integer> modpartid;

        @FXML
        private TableColumn<Part, Integer> modpartinventory;

        @FXML
        private TableColumn<Part,String> modpartname;

        @FXML
        private TableColumn<Part, Double> modpartprice;

        @FXML
        private Button searchaddpartsbutton;

        private static AtomicInteger autoGen = new AtomicInteger(1);// produces unique ID

        /**
         * associates part/product
         *
         * @param event Activated when ADD button clicked
         */
        @FXML
        void addproductadd(ActionEvent event) {
                Part selectedPart = addproductpartdata.getSelectionModel().getSelectedItem();

                if (selectedPart == null) {
                       alertHandler(5);
                }
                else if (!associatedParts.contains(selectedPart)){
                        associatedParts.add(selectedPart);
                        addproductassociatedpartdata.setItems(associatedParts);
                }
        }

        /**
         * Cancels modify product action and returns to mainscreen
         *
         * @param event Activated when Cancel button clicked
         * @throws IOException the io exception
         */
        @FXML
        void addproductcancelhandler(ActionEvent event)throws IOException {

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
         * Removes associated part/product
         *
         * @param event Activated when Remove Associated Part button clicked
         * @throws IOException the io exception
         */
        @FXML
        void addproductremove(ActionEvent event) throws IOException {

                Part selectedPart = addproductassociatedpartdata.getSelectionModel().getSelectedItem();
                if (selectedPart == null){
                        alertHandler(5);
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Do you want to remove the selected part?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                        associatedParts.remove(selectedPart);
                        Product.deleteAssociatedPart(selectedPart);
                        addproductassociatedpartdata.setItems(associatedParts);
                }

        }


        /**
         * Saves Product with input validation
         * RUNTIME ERROR - included .getClassLoader as redirect would not execute
         * @param event Activated when SAVE button clicked
         * @throws IOException the io exception
         */

        @FXML
        void addproductsavehandler(ActionEvent event) throws IOException {
                int id = autoGen.getAndIncrement();

                try {
                        String name = addproductname.getText();
                        int stock = Integer.parseInt(addproductinv.getText());
                        double price = Double.parseDouble(addproductprice.getText());
                        int min = Integer.parseInt(addproductmin.getText());
                        int max = Integer.parseInt(addproductmax.getText());

                        if (max < min) {
                                alertHandler(3);
                                return;
                        } else if (stock > max || stock < min) {
                                alertHandler(4);
                                return;
                        }
                                Product newProduct = new Product(id, name, price, stock, min, max);
                                newProduct.setAssociatedParts(associatedParts);

                                Inventory.addProduct(newProduct);
                                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                                scene = FXMLLoader.load(getClass().getClassLoader().getResource("com/example/proj/mainScreen.fxml"));
                                stage.setScene(new Scene(scene));
                                stage.show();

                }
                catch (Exception e) {
                    alertHandler(1);
                }
        }


        /**
         * Searches for parts
         *
         * @param event Activated when Search button clicked
         */
        @FXML
        void searchaddparts(ActionEvent event) {
                String s = addproductsearch.getText();

                try{
                        int id=Integer.parseInt(s);

                        ObservableList<Part> foundPartsID = FXCollections.observableArrayList();
                        foundPartsID.add(Inventory.lookupPart(id));
                        if(foundPartsID.get(0)!= null) {
                                addproductpartdata.setItems(foundPartsID);
                                addproductsearch.setText("");
                        }
                        else{
                                alertHandler(2);
                                addproductsearch.setText("");
                        }
                        return;
                }
                catch (Exception e){}

                if(Inventory.lookupPart(s).isEmpty()){
                        alertHandler(2);
                        addproductsearch.setText("");
                }
                else if (addproductsearch.getText().trim().isEmpty()) {
                        addproductpartdata.setItems(getAllParts());
                }
                else {
                        addproductpartdata.setItems(Inventory.lookupPart(s));
                        addproductsearch.setText("");
                }
        }

        /**
         * Populate tables
         * @param url
         * @param resourceBundle
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

                addproductpartdata.setItems(Inventory.getAllParts());
                addproductassociatedpartdata.setItems(associatedParts);


                modpartid.setCellValueFactory(new PropertyValueFactory<>("id"));
                modpartname.setCellValueFactory(new PropertyValueFactory<>("name"));
                modpartinventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
                modpartprice.setCellValueFactory(new PropertyValueFactory<>("price"));

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
                                alert.setContentText("Part not found");
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
                                alert.setContentText("Part not selected");
                                alert.showAndWait();
                                break;
                        case 6:
                                alert.setContentText("Part not added");
                                alert.showAndWait();
                                break;

                        case 7:
                                alert.setContentText("Name cannot be empty.");
                                alert.showAndWait();
                                break;

                }


        }
}
