package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Abstracts.Part;

/**This class stores all the instantiated Parts (InHouse & Outsourced) as well as the Products*/
public class Inventory extends Part {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**This method adds a new part to the inventory.
     * @param newPart is the new part to be added to inventory.*/
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**This method adds a new product to the inventory.
     * @param newProduct is the new product to be added to inventory.*/
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    //FIXME: Can I find a way to use this?
    /**This method looks up a part by name from the inventory.
     * @param partName is the new part to be found.
     * @return Returns an ObservableList<Part> of the filtered part.*/
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
        if(!filteredPartsList.isEmpty()){
            filteredPartsList.clear(); //prevents duplicate results
        }
        if(Inventory.getAllParts().isEmpty()){
            return filteredPartsList;
        }
        for(Part part: Inventory.getAllParts()){
            if( part.getName().contains(partName) ){
                filteredPartsList.add(part);
            }
        }
        return filteredPartsList;
    }

    //FIXME: Can I find a way to use this?
    /**This method looks up a product  by name from the inventory.
     * @param productName is the product to be found.
     * @return Returns an ObservableList<Product> of the filtered product.*/
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        System.out.println("onSearchPart() - 0");
        boolean isInt = false;
        try{
            Integer.parseInt(productName);
            System.out.println("%%%Search is an int: " + productName);
            isInt = true;

        }catch(NumberFormatException e){
            System.out.println("$$$Search is not an int: " + productName);
            isInt = false;
        }
        System.out.println("searchText is: "+ productName);
        filteredProducts.clear();
        for(Product product: Inventory.getAllProducts()){
            System.out.println("  onSearchProduct() - 1a searchText is: "+ productName);
            if(productName.isBlank()){
                System.out.println("  onSearchProduct() - 1b searchText is: " + productName);
                filteredProducts.clear();
            }else if(isInt){
                System.out.println("    onSearchProduct() - 2a searchText is: " + productName);
                if(product.getId() == Integer.parseInt(productName) || product.getName().toLowerCase().contains(productName)){
                    System.out.println("    onSearchProduct() - 2b searchText is: " + productName);
                    filteredProducts.add(product);

                }
            }else if(product.getName().toLowerCase().contains(productName)){
                System.out.println("      onSearchProduct() - 3a searchText is: " + productName);
                filteredProducts.add(product);


            }else if(filteredProducts.isEmpty()){
                System.out.println("      onSearchProduct() - 4a searchText is: " + productName);
                System.out.println("     test      TESTING product: " + product.getName().toLowerCase().contains(productName));

            }
        }

        return filteredProducts;
    }

    /**This method looks up a part to the inventory.
     * @param selectedPart is the new part to be deleted.
     * @return Returns a boolean value of true and removes the selected part from allParts.*/
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**This method looks up a product to the inventory.
     * @param selectedProduct is the product to be found.
     * @return Returns a boolean value of true and removes the selected product from allProducts.*/
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /**This method gets all parts in the inventory.
     * @return Returns a value of  ObservableList<Part> and gets the all parts in the inventory.*/
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**This method gets all products in the inventory.
     * @return Returns a value of  ObservableList<Product> and gets the all products in the inventory.*/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**This method updates the selected part in the inventory.
     * @param index is the index of the chosen part to update within the list of all the parts within the inventory.
     * @param selectedPart is the selected part to be updated.
     * @return This method returns nothing since it is void.*/
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index,selectedPart);
        for (Product product : allProducts){
            for(Part part : product.getAllAssociatedParts()){
                if(part.getId() == index+1){

                    System.out.println("***********");
                    System.out.println("the product is: "+ product.getName()+" and associate part and id are: " + part.getName() +
                            " id- " + part.getId()+" and the \'index\' is: " + index + " with part: " + Inventory.lookupPart(index).getName());
                    System.out.println("***********");

                    part.setName(Inventory.lookupPart(index).getName());
                    part.setPrice(Inventory.lookupPart(index).getPrice());
                    part.setStock(Inventory.lookupPart(index).getStock());
                    part.setMin(Inventory.lookupPart(index).getMin());
                    part.setMax(Inventory.lookupPart(index).getMax());

                }
            }
        }
    }

    /**This method updates the selected product in the inventory.
     * @param index is the index of the chosen product to update within the list of all the products within the inventory.
     * @param newProduct is the selected product to be updated.
     * @return This method returns nothing since it is void.*/
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**This method looks up a part by id from the inventory.
     * @param partId is the id of the part being searched for.
     * @return Returns a Part of the filtered partId.*/
    public static Part lookupPart(int partId) { return allParts.get(partId); }

    /**This method looks up a product by id from the inventory.
     * @param productId is the id of the product being searched for.
     * @return Returns a Product of the filtered productId.*/
    public static Product lookupProduct(int productId){
        System.out.println("Inside Inventory.java: ProductID: " + productId + " --- ProductName: " + ((Product)allProducts.get(productId)).getName());
        return allProducts.get(productId); }
}
