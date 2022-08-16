package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import model.Abstracts.Part;
import model.Inventory;
import model.Product;
import java.io.IOException;
import java.util.Optional;

/* TODO:
      [x] 1. Both Tables
      [x]       A. Activate search box
      [x]            i. Part Table
      [x]               a. Search by ID
      [x]               b. Search by partial String match
      [x]           ii. Product Table
      [x]               a. Search by ID
      [x]               b. Search by partial String match
      [ ]          iii. Apply the search functionality to Inventory instead of the Controller.
      [x]       B. Adjust the price column to show TWO decimal spaces. (Update: not needed)
      [x]       C. Fix DELETE button
      [b]            i. To delete the item when viewing all parts/products
      [x]           ii. Open a prompt window.
      [x]          iii. Clear the search results after deletion.
      [ ]          iii. Apply the Inventory.deleteAssociatedPart() method within Add/Modify Product
      [x]       D. Fix the error that makes an exception occur when deleting a part/product and then trying to modify any other part/product.
      [x] 2. Product Table
      [x]       A. Fix Delete button to check for products with associated parts.
      [x]       B. Add exceptions handling features
      [x]            i. For nothing selected when clicking Add inside of AddProductController or ModifyProductController.
      [x]           ii. For Products with associated Parts.
      [x]       C. Fix modify product logic so that it doesn't break after a product has been deleted.
      [x] 3. Activate EXIT button functionality.
      [x] 4. loadFXML()
      [x]       A. See if I can use variables to import to reduce the amount of *.fxml files.
      [x] 5. Exception Handling
      [x] 6. Parts Table
      [x]       A. Populate Parts items without instantiation.
      [x]       B. InHouse/Outhouse Classes
 *
 * */

/** This class creates the main screen and displays all Parts and Products in Inventory,
 * which can then be manipulated by add, modify, or delete.*/
public class MainScreenController{

    @FXML
    private Stage stage;
    @FXML
    private Parent scene;

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
    private TextField productSearchText;
    @FXML
    private TableView productsTableView;

    @FXML
    private TableColumn productId;
    @FXML
    private TableColumn productName;
    @FXML
    private TableColumn productStock;
    @FXML
    private TableColumn productPrice;
    @FXML
    private Label partException;
    @FXML
    private Label productException;

    @FXML
    private ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**This method initializes the MainScreenController.
     *
     * @return No return because it's a void method.
     * */
    public void initialize() {
        System.out.println("Main Screen Controller has been initialized");
        partException.setWrapText(true);
        productException.setWrapText(true);

        partsTableView.setItems(Inventory.getAllParts());

        productsTableView.setItems(Inventory.getAllProducts());

        setTableColumns();

        initializeTests();


    }

    /**This method was used for testing program logic before implementation.
     *
     * @return Return is set to false
     * */
    public boolean initializeTests(){
        //video 15
        //partsTableView.getSelectionModel().select(selectPart(3)); done


        //older stuff
        //Video 15

        //functioning: but unneeded to automatically select a part on load
        //partsTableView.getSelectionModel().select(selectPart(7));

        //functioning: but unneeded to automatically select a product on load
        //productsTableView.getSelectionModel().select(selectProduct(4));

        //Search
        //if(onSearchPart(4)){System.out.println("Match!");}
        /*if(updatePart(3, new InHouse(3,"oogy",13,123,23,23,123))){
            System.out.println("Update Part succeeded!");
        }else{
            System.out.println("Update Part failed!");
        }



        testDeletePart(5);
        */
        //productsTableView.getSelectionModel().select(selectProduct(1));
        /*//Testing
        if(searchPart(1)){
            System.out.println("Part found!");
        }else{
            System.out.println("Part not found!");
        }

        if(searchProduct(1)){
            System.out.println("Product found!");
        }else{
            System.out.println("Product not found!");
        }
        if(update(3,new Product(4,"Clothes Drier",1099.97,2,0,10))){
            System.out.println("Update successful");
        }else{
            System.out.println("Update failed");
        }
        if(delete(3)){
            System.out.println("Delete successful");
        }else{
            System.out.println("Delete unsuccessful for id: " + 3 + allProducts.get(3).getName());
        }*/

        return false;
    }

    /**This method utilizes the searchBox to find matching parts.
     * This method incorporates the Inventory.lookupPart method.
     * @return Returns to false if no matching items are found in the parts list.
     * */
    public boolean onSearchPart(){
        partException.setText("");
        productException.setText("");
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
                partException.setText("");
            }else if(isInt(searchText)){
                System.out.println("    onSearchPart() - 2a searchText is: " + searchText + " ----part.getID = " + part.getId());
                if(part.getId() == Integer.parseInt(searchText) || part.getName().toLowerCase().contains(searchText)){
                    System.out.println("    onSearchPart() - 2b searchText is: " + searchText);
                    filteredParts.add(part);
                    partsTableView.setItems(filteredParts);
                    partException.setText("");
                }else if (filteredParts.isEmpty()){
                    System.out.println("    onSearchPart() - 2c searchText is: " + searchText + " ----part.getID = " + part.getId());
                    partException.setText("No matching part ids found.");
                    partsTableView.setItems(Inventory.getAllParts());
                }
            }else {
                if (part.getName().toLowerCase().contains(searchText)) {
                    System.out.println("      onSearchPart() - 3a searchText is: " + searchText + "  result of !isInt: " + !isInt(searchText));
                    filteredParts.add(part);
                    partsTableView.setItems(filteredParts);
                    partException.setText("");
                } else if (filteredParts.isEmpty()) {
                    System.out.println("      onSearchPart() - 4a searchText is: " + searchText + "  part.getName() = " + part.getName());
                    partException.setText("No matching parts found.");
                    partsTableView.setItems(Inventory.getAllParts());
                }
            }
        }

        return false;
    }

    /**This method utilizes the searchBox to find matching products.
     * This method incorporates the Inventory.lookupProducts method.
     * @return Returns to false if no matching items are found in the products list.
     * */
    public boolean onSearchProduct(){
        partException.setText("");
        productException.setText("");
        System.out.println("\n\n\n  *******  onSearchProduct() - 0  ************");
        String searchText = productSearchText.getText().toLowerCase();
        System.out.println("searchText is: "+ searchText);
        filteredProducts.clear();
        for(Product product: Inventory.getAllProducts()){
            System.out.println("  onSearchProduct() - 1a searchText is: "+ searchText);
            if(searchText.isBlank()){
                System.out.println("  onSearchProduct() - 1b searchText is: " + searchText);
                filteredProducts.clear();
                productsTableView.setItems(Inventory.getAllProducts());
                productException.setText("");
            }else if(isInt(searchText)){
                System.out.println("    onSearchProduct() - 2a searchText is: " + searchText + " ----product.getID = " + product.getId());
                if(product.getId() == Integer.parseInt(searchText) || product.getName().toLowerCase().contains(searchText)){
                    System.out.println("    onSearchProduct() - 2b searchText is: " + searchText);
                    filteredProducts.add(product);
                    productsTableView.setItems(filteredProducts);
                    productException.setText("");
                }else if (filteredProducts.isEmpty()){
                    System.out.println("    onSearchProduct() - 2c searchText is: " + searchText + " ----product.getID = " + product.getId());
                    productException.setText("No matching products ids found.");
                    productsTableView.setItems(Inventory.getAllProducts());
                }
            }else {
                if (product.getName().toLowerCase().contains(searchText)) {
                    System.out.println("      onSearchProduct() - 3a searchText is: " + searchText);
                    filteredProducts.add(product);
                    productsTableView.setItems(filteredProducts);
                    productException.setText("");
                } else if (filteredProducts.isEmpty()) {
                    System.out.println("      onSearchProduct() - 4a searchText is: " + searchText);
                    productException.setText("No matching products found.");
                    productsTableView.setItems(Inventory.getAllProducts());
                }
            }
        }

        return false;
    }

    /**This method routes to the appropriate fxml page.
     * @param fxmlKey is a string array containing either Add/Modify and either Part/Product, depending on which button was clicked.
     * @param actionEvent
     *
     * @return There is no return statement since this is a void method.
     * */
    public void loadFXML(String[] fxmlKey, ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/" + fxmlKey[0] + fxmlKey[1] + ".fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();

        if (fxmlKey[1] == "Product"){
            Scene scene = new Scene(root, 700, 450);
            stage.setScene(scene);
        } else if (fxmlKey[0] == "Modify"){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/" + fxmlKey[0] + fxmlKey[1] + ".fxml"));

            loader.load();

            ModifyPartController modPartController = loader.getController();
            modPartController.sendPart(partsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();

            stage.setScene(new Scene(scene,510,400));
            stage.showAndWait();

        }else{
            Scene scene = new Scene(root, 510, 400);
            stage.setScene(scene);
        }

        stage.setTitle(fxmlKey[0] + " " + fxmlKey[1]);
        stage.show();
    }


    /**This method tests whether a Part has been selected to be modified.
     *
     *
     * This is one of the methods I encountered a runtime error, which I addressed by adding a catch for NullPointerException.
     * Without this try-catch, when I had no parts selected to be modified, I would get the following exceptions:<br>
     * InvocationTargetException, RuntimeException, and NullPointerException. I also realized that I needed to select the proper exception area (partException or productException.)
     *
     *
     * */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {

        String[] fxmlKey = {"Modify","Part"};
        partException.setText("");
        productException.setText("");
        Alert alertError= new Alert(Alert.AlertType.ERROR);
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/" + fxmlKey[0] + fxmlKey[1] + ".fxml"));

            loader.load();

            ModifyPartController modPartController = loader.getController();


            //Old code which caused a RunTimeException
            //modPartController.sendPart(Inventory.lookupPart((partsTableView.getSelectionModel().getSelectedItem()).getId()-1));


            //New code to address a RunTimeException
            //System.out.println(" Part ID by index of selected item: " + Inventory.lookupPart(partsTableView.getSelectionModel().getSelectedIndex()).getId());
            for(Part part : Inventory.getAllParts()){

                if (Inventory.lookupPart(partsTableView.getSelectionModel().getSelectedIndex()).getId() == part.getId()){


                    modPartController.sendPart(Inventory.lookupPart(partsTableView.getSelectionModel().getSelectedIndex()));
                }
            }
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene,510,400));
            stage.show();
            stage.setTitle(fxmlKey[0] + " " + fxmlKey[1]);
        }catch(IndexOutOfBoundsException e){
            alertError.setTitle("Error");
            alertError.setContentText("No part was selected to be modified.");

            alertError.showAndWait();
            partException.setText("Please select a part if you wish to modify one.");
            System.out.println("No part selected to modify.");
        }

    }

    /**This method tests whether a Part has been selected to be modified.
     *
     *@param actionEvent
     *
     * @return No return since this is a void method.
     * */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {

        String[] fxmlKey = {"Modify", "Product"};
        partException.setText("");
        productException.setText("");
        Alert alertError= new Alert(Alert.AlertType.ERROR);
        int inc = 0;
        try{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/" + fxmlKey[0] + fxmlKey[1] + ".fxml"));
            loader.load();

            ModifyProductController modProductController = loader.getController();

            System.out.println("-----Product ID by index of selected item: " + Inventory.lookupProduct(productsTableView.getSelectionModel().getSelectedIndex()).getId());

            for(Product product : Inventory.getAllProducts()){
                System.out.println("-----Inside for loop: Product ID by index of selected item: " + Inventory.lookupProduct(productsTableView.getSelectionModel().getSelectedIndex()).getId());
                System.out.println("Iterated product name:           " + product.getName());
                System.out.println("Name of product to be removed:   " + ((Product)productsTableView.getSelectionModel().getSelectedItem()).getName());
                if (Inventory.lookupProduct(productsTableView.getSelectionModel().getSelectedIndex()).getId() == product.getId()){
                    System.out.println("-----IF STATEMENT:: Product ID by INDEX of selected item: " +
                            Inventory.lookupProduct(productsTableView.getSelectionModel().getSelectedIndex()).getId());
                    System.out.println("-----IF STATEMENT:: Product ID by GETID of selected item: " + product.getId());
                    System.out.println("Results of parameters for modProductController.sendProduct(): " + productsTableView.getSelectionModel().getSelectedIndex());
                    System.out.println("Last statement before modProductController.sendProduct(): " + Inventory.lookupProduct(productsTableView.getSelectionModel().getSelectedIndex()));

                    modProductController.sendProduct(Inventory.lookupProduct(productsTableView.getSelectionModel().getSelectedIndex()));

                }
            }
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene, 700, 450));
            stage.show();
            stage.setTitle(fxmlKey[0] + " " + fxmlKey[1]);
        }catch(IndexOutOfBoundsException e){

            //System.out.println("-----IndexOutOfBoundsException:: Product ID by index of selected item: " + Inventory.lookupProduct(productsTableView.getSelectionModel().getSelectedIndex()).getId());

            alertError.setTitle("Error");
            alertError.setContentText("No product was selected to be modified.");

            alertError.showAndWait();
            productException.setText("Please select a product if you wish to modify one.");
            System.out.println("No product selected to modify.");
        }

    }

    /**This method initializes the deletion logic for Parts.
     *
     * @return No return because it's a void method.
     * */
    public void onDeletePart() {
        //System.out.println("delete_parts_button_response clicked");

        partException.setText("");
        productException.setText("");

        Alert alertConfirmation= new Alert(Alert.AlertType.CONFIRMATION);
        Alert alertError= new Alert(Alert.AlertType.ERROR);

        try{
           alertConfirmation.setTitle("WARNING");
            alertConfirmation.setContentText("Do you wish to delete " + partsTableView.getSelectionModel().getSelectedItem().getName() + "?");

            Optional<ButtonType> result = alertConfirmation.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
                partSearchText.setText("");
                partsTableView.setItems(Inventory.getAllParts());
            }
        }catch(NullPointerException e){
            alertError.setTitle("Error");
            alertError.setContentText("Nothing to delete. Please select a part if you wish to delete one.");

            alertError.showAndWait();
            partException.setText("Please select a part if you wish to delete one.");
            //System.out.println("No part selected to delete.");
        }
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

        //Products TableView
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**This method routes to the AddPartController.
     * @param actionEvent is initiated when clicking the Add button in the parts section of the main screen.
     * @return No return because it's a void method.
     * */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        String[] fxmlKey = {"Add","Part"};
        loadFXML(fxmlKey, actionEvent);
    }

    /**This method routes to the AddProductController.
     * @param actionEvent is initiated when clicking the Add button in the products section of the main screen.
     * @return No return because it's a void method.
     * */
    public void onAddProduct(ActionEvent actionEvent) throws IOException  {
        String[] fxmlKey = {"Add","Product"};
        loadFXML(fxmlKey, actionEvent);
    }

    /**This method tests for NumberFormatException when searching either the Parts or Products tableviews.
     * @param searchId is the user input which will follow programming logic to find relevant Inventory items.
     *
     * @return Returns true only if the searchId is an integer
     * */
    public boolean isInt(String searchId){
        try{
            Integer.parseInt(searchId);
            //System.out.println("---Inside isInt() > searchId is an Int: " + searchId);
            return true;
        }catch(NumberFormatException e){
            //System.out.println("---Inside isInt() > searchId is is not an Int: " + searchId);
            return false;
        }

    }

    /**This method initializes the deletion logic for Products.
     *
     * @return No return because it's a void method.
     * */
    public void onDeleteProduct() {

        System.out.println("delete_product_button clicked");

        partException.setText("");
        productException.setText("");

        Alert alertError= new Alert(Alert.AlertType.ERROR);
        Alert alertConfirmation= new Alert(Alert.AlertType.CONFIRMATION);
        try {

            alertConfirmation.setTitle("CONFIRMATION");
            alertConfirmation.setContentText("Do you wish to delete " + ((Product)productsTableView.getSelectionModel().getSelectedItem()).getName() + "?");

            Optional<ButtonType> result = alertConfirmation.showAndWait();

            if(!(((Product)productsTableView.getSelectionModel().getSelectedItem()).getAllAssociatedParts().isEmpty()) &&
                    result.isPresent() && result.get() == ButtonType.OK){
                alertError.setTitle("Error");
                alertError.setContentText(((Product)productsTableView.getSelectionModel().getSelectedItem()).getName() + " has associated parts. Please remove them first before deletion.");
                alertError.showAndWait();
                productException.setText(((Product)productsTableView.getSelectionModel().getSelectedItem()).getName() + " has associated parts. Please modify the product by removing all associated parts in order to delete this product.");
            }else if(result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct((Product) productsTableView.getSelectionModel().getSelectedItem());
                productSearchText.setText("");
                productsTableView.setItems(Inventory.getAllProducts());
            }
        }catch(NullPointerException e){
            alertError.setTitle("Error");
            alertError.setContentText("Nothing to delete. Please select a product if you wish to delete one.");

            alertError.showAndWait();
            productException.setText("Please select a product if you wish to delete one. Note: It is first necessary to remove all associated parts from the product before it can be deleted.");
            System.out.println("No part selected to delete.");
        }
    }

    /**This method exits the program.
     *
     * @return No return because it's a void method.
     * */
    public void onExit() {
        System.exit(0);
    }

}
