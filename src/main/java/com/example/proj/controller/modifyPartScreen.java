package com.example.proj.controller;

import com.example.proj.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Modify part class, the Controller for the Modify part View
 * @author Robert Schofield
 */
public class modifyPartScreen implements Initializable {

    int index;
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
    private AnchorPane modPartScreen;

    @FXML
    private RadioButton inHouseRad;

    @FXML
    private RadioButton outsourcedRad;

    @FXML
    private Label machIdOrCompLab;

    @FXML
    private TextField modMachineId;

    @FXML
    private TextField modMaxPrice;

    @FXML
    private TextField modPartID;

    @FXML
    private TextField modPartInv;

    @FXML
    private TextField modPartName;

    @FXML
    private TextField modPrice;

    @FXML
    private TextField modPriceMin;

    @FXML
    private Button saveBnt;

    @FXML
    private Button cancelBtn;
    @FXML
    private ToggleGroup radioButton;

    /**
     * Sets label to Machine ID
     * @param event Activated when inHouse radio selected
     * @throws IOException
     */
    @FXML
    void inhouseSel(ActionEvent event) throws IOException{
        machIdOrCompLab.setText("Machine ID");
        modMachineId.setText("");

    }

    /**
     * Sets lable to Company name
     * @param event Activated when ouutsourced radio selected
     * @throws IOException
     */
    @FXML
    void outsourcedSel(ActionEvent event) throws IOException{
        machIdOrCompLab.setText("Company Name");
        modMachineId.setText("");

    }

    /**
     * Handles user cancellation and redirects to main screen
     * RUNTIME ERROR - included .getClassLoader as redirect would not execute
     * @param event Activated when Cancel button is clicked
     * @throws IOException
     */
    @FXML
    public void modifypartcancelhandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getClassLoader().getResource("com/example/proj/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Updates Part in inventory with field validation then redirects to mainScreen
     * Checks if the part is Outsourced or InHouse, allows user to change.
     * RUNTIME ERROR - included .getClassLoader as redirect would not execute
     * @param event Activated when the SAVE button is clicked
     * @throws IOException
     */
    @FXML
    public void modifypartsavehandler(ActionEvent event) throws IOException {
        try {
            int id = selectedPart.getId();
            String name = modPartName.getText();
            int stock = Integer.parseInt(modPartInv.getText());
            double price = Double.parseDouble(modPrice.getText());
            int min = Integer.parseInt(modPriceMin.getText());
            int max = Integer.parseInt(modMaxPrice.getText());
            String machineId = modMachineId.getText();


            if (max < min) {
                alertHandler(3);
                return;
            }
            else if (stock > max || stock < min) {
                alertHandler(4);
                return;
            }

            if (inHouseRad.isSelected()) {
                int t = Integer.parseInt(machineId);
                Part modPart = new InHouse(id, name, price, stock, min, max, t);
                Inventory.updatePart(index, modPart);
            }

            if (outsourcedRad.isSelected()) {
                Part modPart = new Outsourced(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(index, modPart);
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


    /**
     * Populates Input boxes
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = mainScreenController.PartModGetter();
        index = Inventory.getAllParts().indexOf(selectedPart);

        if (selectedPart instanceof InHouse) {
            inHouseRad.setSelected(true);
            outsourcedRad.setSelected(false);
            machIdOrCompLab.setText("Machine ID");
            modMachineId.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
        }

        if (selectedPart instanceof Outsourced){
            outsourcedRad.setSelected(true);
            inHouseRad.setSelected(false);
            machIdOrCompLab.setText("Company Name");
            modMachineId.setText(((Outsourced) selectedPart).getCompanyName());
        }

        modPartID.setText(String.valueOf(selectedPart.getId()));
        modPartName.setText(selectedPart.getName());
        modPartInv.setText(String.valueOf(selectedPart.getStock()));
        modPrice.setText(String.valueOf(selectedPart.getPrice()));
        modMaxPrice.setText(String.valueOf(selectedPart.getMax()));
        modPriceMin.setText(String.valueOf(selectedPart.getMin()));
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







