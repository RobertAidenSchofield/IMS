package com.example.proj.controller;


import com.example.proj.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Add part Controller
 * @author Robert Schofield
 */
public class addPartScreen implements Initializable {
    /**
     * Sets Scene
     */
    Parent scene;
    /**
     * Sets Stage
     */
    Stage stage;

    private Part selectedPart;
    @FXML
    private ToggleGroup addparttoggle;

    @FXML
    private TextField addMachineId;

    @FXML
    private TextField addMaxPrice;

    @FXML
    private TextField addPartID;

    @FXML
    private TextField addPartInv;

    @FXML
    private TextField addPartName;

    @FXML
    private AnchorPane addPartScreen;

    @FXML
    private TextField addPrice;

    @FXML
    private TextField addPriceMin;

    @FXML
    private Button cancelBtn;

    @FXML
    private RadioButton inHouseRad;

    @FXML
    private Label machIdOrCompLab;

    @FXML
    private RadioButton outsourcedRad;

    @FXML
    private Button saveBnt;
    @FXML
    ToggleGroup radBut = new ToggleGroup();
    private static AtomicInteger autoGen = new AtomicInteger(1);

    /**
     * Sets label to  Company name Or Machine ID depending on radio button selected
     * @param actionEvent Activated when radio button clicked
     */
    public void radButtonClicked(ActionEvent actionEvent) {
        if(inHouseRad.isSelected()){
            machIdOrCompLab.setText("Machine ID");
        }
        else if (outsourcedRad.isSelected()) {
            machIdOrCompLab.setText("Company Name");
        }
    }


    /**
     * Handles user cancellation and redirects to main screen
     * RUNTIME ERROR - included .getClassLoader as redirect would not execute
     * @param event Activated when Cancel button is clicked
     * @throws IOException
     *
     */
    @FXML
    void addPartcancelhandler(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("com/example/proj/mainScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * Saves part to inventory with field validation then redirects to mainScreen
     * RUNTIME ERROR - included .getClassLoader as redirect would not execute
     * @param event Activated when the ADD button is clicked
     * @throws IOException
      */
    @FXML
    void addPartsavehandler(ActionEvent event) throws IOException {

        try {
            String name = addPartName.getText();
            int stock = Integer.parseInt(addPartInv.getText());
            double price = Double.parseDouble(addPrice.getText());
            int min = Integer.parseInt(addPriceMin.getText());
            int max = Integer.parseInt(addMaxPrice.getText());
            String machineId = addMachineId.getText();


            if (max < min) {
                alertHandler(3);
                return;
            }

            if (stock > max || stock < min) {
                alertHandler(4);
                return;
            }


            if (radBut.getSelectedToggle().equals(inHouseRad)) {
                int id = autoGen.getAndIncrement();
                int t = Integer.parseInt(machineId);
                Part addParts = new InHouse(id, name, price, stock, min, max, t);
                Inventory.addPart(addParts);
                Inventory.deletePart(selectedPart);
            }

            if (radBut.getSelectedToggle().equals(outsourcedRad)) {
                int id = autoGen.getAndIncrement();
                Part addParts = new Outsourced(id, name, price, stock, min, max, machineId);
                Inventory.addPart(addParts);
                Inventory.deletePart(selectedPart);

            }
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getClassLoader().getResource("com/example/proj/mainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (Exception exception){
            alertHandler(1);

        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
