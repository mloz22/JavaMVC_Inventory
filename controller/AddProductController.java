package controller;

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
import javafx.stage.Stage;
import model.Abstracts.Part;
import model.Inventory;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.util.ResourceBundle;

/** This class controls the logic of how AddProduct.fxml functions.*/
public class AddProductController implements Initializable {
    @FXML
    private Stage stage;

    @FXML
    private TextField productNameText;
    @FXML
    private TextField productPriceText;
    @FXML
    private TextField productStockText;
    @FXML
    private TextField productMinText;
    @FXML
    private TextField productMaxText;

    @FXML
    private TextField partSearchText;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partStock;
    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> associatedPartId;
    @FXML
    private TableColumn<Part, String> associatedPartName;
    @FXML
    private TableColumn<Part, Integer> associatedPartStock;
    @FXML
    private TableColumn<Part, Double> associatedPartPrice;
    @FXML
    private Label fieldException;


    @FXML
    private ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    @Override
    /**This method initialized the AddProductController.
     *
     * @param url imports url
     * @param ResourceBundle imports resourceBundle
     * @return No return because it's a void method.
     * */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        //FIXME: lookupProduct() needs to import whichever product was selected.
        //associatedPartsTableView.setItems(Inventory.lookupProduct(0).getAllAssociatedParts());
        setTableColumns();
        System.out.println("Add Product Controller has been initialized");

        //partsTableView.getSelectionModel();
    }

    /**This method initializes the TableView to set the values.
     *
     * @return No return because it's a void method.
     * */
    public void setTableColumns(){
        //Parts TableView
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Associated Parts TableView
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**This method adds the product to inventory.
     * @param actionEvent is used to redirect back to the MainScreen.
     *
     * @return There is no return because this is a void method.*/
    public void onSaveAddProductButtonAction(ActionEvent actionEvent) throws IOException {
        //Testing
        Product newProduct = null;
        fieldException.setWrapText(true);

        String[] inputs = {productNameText.getText(),productPriceText.getText(),
                productStockText.getText(),productMinText.getText(),
                productMaxText.getText()};
        try {
            int id = calcId();

            String name = productNameText.getText();
            double price = Double.parseDouble(productPriceText.getText());
            int stock = Integer.parseInt(productStockText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());

            if(name ==""){
                System.out.println("Name is blank. Please fill out all required fields.");
                fieldException.setText(isBlank(inputs));
            }else if(min < 0){
                System.out.println("Min does not have a legal value.");
                fieldException.setText("Please ensure that the \"Min\" and \"Max\" amounts are integers greater than or equal to zero and that the Min is less than or equal to the Max value.");
            }else if(max < 0){
                System.out.println("Max does not have a legal value.");
                fieldException.setText("Please ensure that the \"Min\" and \"Max\" amounts are integers greater than or equal to zero and that the Min is less than or equal to the Max value.");
            }else if (max < min) {
                System.out.println("Min does not have a legal value.");
                fieldException.setText("Please ensure that the \"Min\" and \"Max\" amounts are integers greater than or equal to zero and that the Min is less than or equal to the Max value.");
            }else if (stock > max || stock < min) {
                System.out.println("Stock does not have a legal value.");
                fieldException.setText("Please ensure that the Inv amount is between the \"Min\" and \"Max\" values.");
            } else {
                newProduct = new Product(id, name, price, stock, min, max);
                for (Part part : associatedPartsTableView.getItems()){
                    newProduct.addAssociatedPart(part);
                }
                Inventory.addProduct(newProduct);

                loadMainScreen(actionEvent);
            }
        }catch(NumberFormatException e){
            //System.out.println("Catch1: Number Format Exception. Please fill out all required fields.");
            fieldException.setText("NumberFormatException. Please properly fill out all required fields. Inv, Price, Min, and Max should all be numbers. NOTE: Only price can have decimals.");

            if(isBlank(inputs) != ""){
                fieldException.setText(isBlank(inputs));
            }
        }catch(NullPointerException e){
            //System.out.println("Catch2: Null Pointer Exception. Please fill out all required fields.");
            fieldException.setText("NullPointerException. Please properly fill out all required fields.");

            if(isBlank(inputs) != ""){
                fieldException.setText(isBlank(inputs));
            }
        }




    }

    /**This method compiles the list of blank fields
     * @param textInput is a string array of all the fields used to determine which ones (if any) are blank.
     *
     * @return String which will either be blank if there are no empty fields, or which will contain a message listing the blank fields.
     * */
    public String isBlank(String[] textInput){
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
        //System.out.println("TEST- " + errorFields);
        return errorFields + ". Please fill out all fields. ";
    }

    /**This method returns to the Main Screen.
     * @param actionEvent imports the action of a button click which re-directs back to the MainScreen.fxml.
     * @return No return because it's a void method.
     * */
    public void loadMainScreen(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890, 600);
        stage.setTitle("Inventory Management Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**This method tests for NumberFormatException when searching the Parts tableview.
     * @param searchId is the user input which will follow programming logic to find relevant Inventory items.
     *
     * @return Returns true only if the searchId is an integer
     * */
    public boolean isInt(String searchId){
        try{
            Integer.parseInt(searchId);
            System.out.println("Search is an int: " + searchId);
            return true;
        }catch(NumberFormatException e){
            System.out.println("Search is not an int: " + searchId);
            return false;
        }
    }

    /**This method utilizes the searchBox to find matching parts.
     * This method incorporates the Inventory.lookupPart method.
     * @return Returns to false if no matching items are found in the parts list.
     * */
    public boolean onSearchPart(){
        fieldException.setText("");
        fieldException.setText("");
        System.out.println("\n\n\n  *******  onSearchPart() - 0  ************");
        String searchText = partSearchText.getText().toLowerCase();
        System.out.println("searchText is: "+ searchText);
        filteredParts.clear();
        for(Part part: Inventory.getAllParts()){
            System.out.println("  onSearchPart() - 1a searchText is: "+ searchText);
            if(searchText.isBlank()){
                System.out.println("  onSearchPart() - 1b searchText is: " + searchText);
                filteredParts.clear();
                partsTableView.setItems(Inventory.getAllParts());
                fieldException.setText("");
            }else if(isInt(searchText)){
                System.out.println("    onSearchPart() - 2a searchText is: " + searchText + " ----part.getID = " + part.getId());
                if(part.getId() == Integer.parseInt(searchText) || part.getName().toLowerCase().contains(searchText)){
                    System.out.println("    onSearchPart() - 2b searchText is: " + searchText);
                    filteredParts.add(part);
                    partsTableView.setItems(filteredParts);
                    fieldException.setText("");
                }else if (filteredParts.isEmpty()){
                    System.out.println("    onSearchPart() - 2c searchText is: " + searchText + " ----part.getID = " + part.getId());
                    fieldException.setText("No matching part ids found.");
                    partsTableView.setItems(Inventory.getAllParts());
                }
            }else {
                if (part.getName().toLowerCase().contains(searchText)) {
                    System.out.println("      onSearchPart() - 3a searchText is: " + searchText + "  result of !isInt: " + !isInt(searchText));
                    filteredParts.add(part);
                    partsTableView.setItems(filteredParts);
                    fieldException.setText("");
                } else if (filteredParts.isEmpty()) {
                    System.out.println("      onSearchPart() - 4a searchText is: " + searchText + "  part.getName() = " + part.getName());
                    fieldException.setText("No matching parts found.");
                    partsTableView.setItems(Inventory.getAllParts());
                }
            }
        }

        return false;
    }
    /*public boolean OLDonSearchPart(){
        fieldException.setText("");
        //System.out.println("onSearchPart() - 0");
        String searchText = partSearchText.getText().toLowerCase();
        //System.out.println("searchText is: "+ searchText);
        filteredParts.clear();
        for(Part part: Inventory.getAllParts()){
            //System.out.println("  onSearchPart() - 1a searchText is: "+ searchText);
            if(searchText.isBlank()){
                //System.out.println("  onSearchPart() - 1b searchText is: " + searchText);
                filteredParts.clear();
                partsTableView.setItems(Inventory.getAllParts());
            }else if(isInt(searchText)){
                //System.out.println("    onSearchPart() - 2a searchText is: " + searchText);
                if(part.getId() == Integer.parseInt(searchText) || part.getName().toLowerCase().contains(searchText)){
                    System.out.println("    onSearchPart() - 2b searchText is: " + searchText);
                    filteredParts.add(part);
                    partsTableView.setItems(filteredParts);
                }
            }else if(part.getName().toLowerCase().contains(searchText)){
                //System.out.println("      onSearchPart() - 3a searchText is: " + searchText);
                filteredParts.add(part);
                partsTableView.setItems(filteredParts);
            }else if(!part.getName().toLowerCase().contains(searchText)) {
                //System.out.println("        onSearchPart() - 4a searchText is: " + searchText);
                partsTableView.setItems(Inventory.getAllParts());
                fieldException.setText("No matching parts found.");
            }
        }
        return false;
    }*/

    /**This method calculates the id for the product being added
     * @return This method returns the new ID for the new product.
     * */
    public int calcId(){
        int maxVal = 0;

        for (Product product : Inventory.getAllProducts()){
            if (product.getId() >= maxVal){
                maxVal = product.getId() + 1;

            }

            /*
            System.out.println(product.getName() + "'s id is: " + product.getId() +
                    " and max's value is: " + maxVal +", and previous id was: " + prevId);
            */
        }

        return maxVal;
    }

    /**This cancels adding in a new Product.
     * @param actionEvent imports the action of a clicking Cancel which re-directs back to the MainScreen.fxml.
     * @return No return because it's a void method.
     * */
    public void onCancelAddProductButtonAction(ActionEvent actionEvent) throws IOException {
        loadMainScreen(actionEvent);

    }

    /**This method removes any associated parts from the product.
     * @return There is no return statement since this is a void function.
     * */
    public void onRemoveAssociatedPartButtonAction() {
        fieldException.setWrapText(true);
        Alert alertError= new Alert(Alert.AlertType.ERROR);
        if (associatedPartsTableView.getSelectionModel().getSelectedItem() == null) {
            System.out.println("There is no selected part!");
            alertError.setTitle("Error");
            alertError.setContentText("No part was selected to be removed.");

            alertError.showAndWait();
            fieldException.setText("Please select a part if you wish to remove one.");
        }else {
            Part removePart = associatedPartsTableView.getSelectionModel().getSelectedItem();
            associatedPartsTableView.getItems().remove(removePart);
            fieldException.setText(removePart.getName() + " was removed from the product's associated parts.");
        }
    }


    /**This method adds any selected part to the product.
     * @return There is no return statement since this is a void function.
     * */
    public void onAddAssociatedPartButtonAction() {
        fieldException.setWrapText(true);
        Alert alertError= new Alert(Alert.AlertType.ERROR);
        if (partsTableView.getSelectionModel().getSelectedItem() == null) {
            System.out.println("There is no selected part!");
            alertError.setTitle("Error");
            alertError.setContentText("No part was selected to be added.");

            alertError.showAndWait();
            fieldException.setText("Please select a part if you wish to add one.");
        }else {
            Part addPart = partsTableView.getSelectionModel().getSelectedItem();
            associatedPartsTableView.getItems().add(addPart);
            fieldException.setText(addPart.getName() + " was added to the product's associated parts.");
        }

    }

}
