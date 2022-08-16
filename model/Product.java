package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Abstracts.Part;
//TODO: FIX DELETE_PRODUCT_METHOD
/**This class extends Parts class for Products*/
public class Product extends Part {


    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /**This is the default constructor for Product.*/
    public Product(){
        this.id = 0;
        this.name = "default_product";
        this.price = 0.00;
        this.stock = 0;
        this. min = 0;
        this.max = 1;
    }
    /**This is the overloaded constructor for Product which inputs id, name, price, in-stock, min, and max values.
     * @param id denotes the Product's ID.
     * @param name denotes the Product's name.
     * @param price denotes the Product's price per item.
     * @param stock denotes the Product's current in stock quantity.
     * @param min denotes the Product's minimum allowable amount in stock.
     * @param max denotes the Product's maximum allowable amount in stock.
     * */
    public Product(int id, String name, double price, int stock, int min, int max){

      this.id = id;
      this.name = name;
      this.price = price;
      this.stock = stock;
      this.min = min;
      this.max = max;

    }
    /**This method sets the Product id
     * @param id denotes a Product's ID.*/
    public void setId(int id){
        this.id = id;
    }

    /**This method sets the Product id
     * @param name denotes a Product's name.*/
    public void setName(String name){
        this.name = name;
    }

    /**This method sets the Product id
     * @param price denotes a Product's price.*/
    public void setPrice(double price){
       this.price = price;
    }

    /**This method sets the Product id
     * @param stock denotes a Product's stock.*/
    public void setStock(int stock){
       this.stock = stock;
    }

    /**This method sets the Product id
     * @param min denotes a Product's min.*/
    public void setMin(int min){
        this.min = min;
    }

    /**This method sets the Product id
     * @param max denotes a Product's max.*/
    public void setMax(int max){
        this.max = max;
    }

    /**This method gets the Product id*/
    public int getId(){
        return id;
    }

    /**This method gets the Product name*/
    public String getName(){ return name; }

    /**This method gets the Product price*/
    public double getPrice(){
        return price;
    }

    /**This method gets the Product stock*/
    public int getStock(){
        return stock;
    }

    /**This method gets the Product min*/
    public int getMin(){
        return min;
    }

    /**This method gets the Product max*/
    public int getMax(){
        return max;
    }

    /**This method adds the selected part to the associated parts of the product in inventory.*/
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }

    /**This method gets all the associated parts of the product in inventory.*/
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

    //FIXME: implement this method
    /**This method deletes an associated part from a product.*/
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return false;
    }





}
