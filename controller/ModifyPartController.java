package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Abstracts.Part;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class controls the logic of how ModifyPart.fxml functions.*/
public class ModifyPartController implements Initializable {

    @FXML
    private Label toggleSource;
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outSourcedRadio;


    @FXML
    private TextField partIdText;
    @FXML
    private TextField partNameText;
    @FXML
    private TextField partPriceText;
    @FXML
    private TextField partStockText;
    @FXML
    private TextField partMinText;
    @FXML
    private TextField partMaxText;

    @FXML
    private boolean sourceInHouse;

    @FXML
    private ToggleGroup partSource;
    @FXML
    private TextField partSourceText;
    @FXML
    private Label fieldException;
    @FXML


    @Override
    /**This method initialized the ModifyPartController.
     *
     * @param url imports url
     * @param ResourceBundle imports resourceBundle
     * @return No return because it's a void method.
     * */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Modify Part Controller");

    }

    /**This method compiles the list of blank fields
     * @param textInput is a string array of all the fields used to determine which ones (if any) are blank.
     * @param toggleSource is used to determine whether the Machine Id / Company name field is blank.
     *
     * @return String which will either be blank if there are no empty fields, or which will contain a message listing the blank fields.
     * */
    public String isBlank(String[] textInput, String toggleSource){

        int status = 0;
        String[] exceptionMessage = new String[textInput.length];
        String errorFields = "The following fields are blank: ";

        for(int i = 0; i <textInput.length; i++ ) {
            if (textInput[i].equals("") && i == 0) {

                status += 1;

            }else if(textInput[i].equals("")){
                status += (Math.pow(2,i));


            }
        }
        if(status == 0){
            exceptionMessage[0] = "";
            return "";
        }
        if(status%2 == 1){
            exceptionMessage[0] = "Name";
            status = status -1;
        }
        if(status - 32 >=0){
            exceptionMessage[5] = toggleSource;
            status = status - 32;
        }
        if(status - 16 >=0){
            exceptionMessage[4] = "Max";
            status = status - 16;
        }
        if(status - 8 >=0){
            exceptionMessage[3] = "Min";
            status = status - 8;
        }
        if(status - 4 >=0){
            exceptionMessage[2] = "Inv";
            status = status - 4;
        }
        if(status - 2 >=0){
            exceptionMessage[1] = "Price";
            status = status-2;
        }
        for(int i = 0; i <exceptionMessage.length; i++){
            System.out.println("inside testing exceptionMessage: " + exceptionMessage[i]);
            if (exceptionMessage[i] != null){
                errorFields += exceptionMessage[i] + ", ";
            }
        }
        errorFields = errorFields.substring(0,errorFields.length()-2);
        //System.out.println("TESTESTESTTESzzzzzzzzzz" + errorFields);
        return errorFields + ". Please fill out all fields. ";
    }

    /**This method is used by the MainScreenController to send over the proper part to modify.
     * @param part is the part which the MainScreenController sends to be modified.
     *
     * @return No return statement since this is a void function.
     * */
    public void sendPart(Part part){

        partIdText.setText(String.valueOf(part.getId()));
        partNameText.setText(String.valueOf(part.getName()));
        partPriceText.setText(String.valueOf(part.getPrice()));
        partStockText.setText(String.valueOf(part.getStock()));
        partMinText.setText(String.valueOf(part.getMin()));
        partMaxText.setText(String.valueOf(part.getMax()));

        if(part instanceof Outsourced){
            onOutSourced();
            partSourceText.setText(((Outsourced) part).getCompanyName());

        }else if (part instanceof InHouse){
            onInHouse();
            partSourceText.setText(String.valueOf(((InHouse) part).getMachineId()));
            inHouseRadio.setSelected(true);
        }

    }

    /**This method modifies the part already in inventory.
     * @param actionEvent is used to redirect back to the MainScreen.
     *
     * @return There is no return because this is a void method.*/
    public void onSaveModifyPartButtonAction(ActionEvent actionEvent) throws IOException {
        fieldException.setWrapText(true);

        String[] inputs = {partNameText.getText(),partPriceText.getText(),
                partStockText.getText(),partMinText.getText(),
                partMaxText.getText(),partSourceText.getText()};
        try{
            String name = partNameText.getText();
            double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partStockText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int id = Integer.parseInt(partIdText.getText());

            if(name == "") {
                //System.out.println("Name is blank. Please fill out all required fields.");
                fieldException.setText(isBlank(inputs, toggleSource.getText()));
            }else if(toggleSource.getText() =="Company Name" && partSourceText.getText() == ""){
                //System.out.println("Company name is blank. Please fill out all required fields.");
                fieldException.setText(isBlank(inputs, toggleSource.getText()));
            }else if(min < 0){
                //System.out.println("Min does not have a legal value.");
                fieldException.setText("Please ensure that the \"Min\" and \"Max\" amounts are integers greater than or equal to zero and that the Min is less than or equal to the Max value.");
            }else if(max < 0){
                //System.out.println("Max does not have a legal value.");
                fieldException.setText("Please ensure that the \"Min\" and \"Max\" amounts are integers greater than or equal to zero and that the Min is less than or equal to the Max value.");
            }else if (max < min) {
                //System.out.println("Min does not have a legal value.");
                fieldException.setText("Please ensure that the \"Min\" and \"Max\" amounts are integers greater than or equal to zero and that the Min is less than or equal to the Max value.");
            }else if(stock > max || stock < min){
                //System.out.println("Inv does not have a legal value.");
                fieldException.setText("Please ensure that the Inv amount is between the \"Min\" and \"Max\" values.");
            }else{
                /*
                System.out.println("    ***          INSIDE onSaveModifyPartButtonAction          ***" );
                System.out.println("   id as it is:             =  " + id);
                System.out.println("   partIdText.getText()     =  " + partIdText.getText());
                System.out.println("   Inventory.lookupPart(id) =  "  );
                */
                for (Part part : Inventory.getAllParts()){
                    //System.out.println("   Inventory.getAllParts().indexOf(part) =  " + Inventory.getAllParts().indexOf(part));
                    if(part.getId() == Integer.parseInt(partIdText.getText())){
                        if(inHouseRadio.isSelected()) {

                            //System.out.println("inHouseRadio is selected");
                            int machineId = Integer.parseInt(partSourceText.getText());
                            Inventory.updatePart(Inventory.getAllParts().indexOf(part),new InHouse(id,name, price, stock, min, max, machineId));
                            loadMainScreen(actionEvent);
                        } else if (outSourcedRadio.isSelected()){

                            String companyName = partSourceText.getText();
                            //System.out.println("inHouseRadio is not selected");
                            //Inventory.updatePart(id-1,new Outsourced(id,name, price, stock, min, max, companyName));
                            Inventory.updatePart(Inventory.getAllParts().indexOf(part),new Outsourced(id,name, price, stock, min, max, companyName));
                            loadMainScreen(actionEvent);
                        }
                    }
                }

               // loadMainScreen(actionEvent);
            }
        }catch(NumberFormatException e){
            //System.out.println("Catch1: Number Format Exception. Please fill out all required fields.");
            fieldException.setText("NumberFormatException. Please properly fill out all required fields. Inv, Price, Min, Max and Machine ID should all be numbers. NOTE: Only Price can have decimals.");

            if(isBlank(inputs,toggleSource.getText()) != ""){
                fieldException.setText(isBlank(inputs, toggleSource.getText()));
            }
        }catch(NullPointerException e){
            System.out.println("Catch2: Null Pointer Exception. Please fill out all required fields.");
            fieldException.setText("NullPointerException. Please properly fill out all required fields.");

            if(isBlank(inputs,toggleSource.getText()) != ""){
                fieldException.setText(isBlank(inputs,toggleSource.getText()));
            }
        }

    }

    /**This method returns to the Main Screen.
     * The message is a greeting.
     * This will be deleted.
     * @param actionEvent imports the action of a button click which re-directs back to the MainScreen.fxml.
     * @return No return because it's a void method.
     * */
    public void loadMainScreen(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890, 600);
        stage.setTitle("Inventory Management Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**This cancels modifying in a new Part.
     * @param actionEvent imports the action of a clicking Cancel which re-directs back to the MainScreen.fxml.
     * @return No return because it's a void method.
     * */
    public void onCancelModifyPartButtonAction(ActionEvent actionEvent) throws IOException {
        loadMainScreen(actionEvent);
    }

    /**This method addressed boolean logic for InHouse parts and displays the appropriate text.
     * */
    public void onInHouse() {
        toggleSource.setText("Machine ID");
        //Radio button functionality
        inHouseRadio.setSelected(true);
        outSourcedRadio.setSelected(false);
    }

    /**This method addressed boolean logic for Outsourced parts and displays the appropriate text.
     * */
    public void onOutSourced() {
        toggleSource.setText("Company Name");
        //Radio button functionality
        inHouseRadio.setSelected(false);
        outSourcedRadio.setSelected(true);
    }

}

