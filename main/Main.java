package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Abstracts.Part;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;





/** <h3>This class creates an app that lets a user create, read, update, or delete an inventory's products and parts.</h3>
 *<br><strong>RunTimeException</strong><br>
 * I encountered many exceptions, but the most 'exceptional' was an IndexOutofBoundsException, which can be followed along with by looking at the System.out.println() statements within MainScreenController.onModifyProduct().
 *
 * <br><strong>The Issue</strong><br>
 * The issue was once a product was deleted, my initial logic was using the productId instead of the product's index to look up the product. Once I corrected this, I could then:
 * <ol>
 *     <li>Delete any product which was not the last index in the inventory's list of products.</li>
 *     <li>Then, after step 1, I could modify the last index of all the inventory's products.</li>
 * </ol>
 * <strong>Solution</strong><br>
 * I kept the problem code in ModifyProductController on Lines 110 and 112. The issue was that I was always getting a value of -1. Once I simplified my code and just directly called the product that I had already sent from MainScreenController to ModifyProductController, the issue was resolved.
 *
 * <br><br><strong>Errors which were addressed</strong><br> My biggest issue was that after looking at the UML diagram
 * I realized that it wasn't asking for an Interface from InHouse or Outsourced. Once I realized
 * that those were the classes I could use to instantiate the Parts class, everything else started falling
 * into place.
 * <br><br><strong>Future Enhancements</strong><br>
 * <ul>
 *     <li>When there is an exception, then the field(s) in question should highlight in red.</li>
 *     <li>The price should have 2 decimal spaces</li>
 *     <li>Images of products</li>
 *     <li>A historical record of what parts have been added and removed from inventory.</li>
 * </ul><br><br>
 */
public class Main extends Application{


    /** This method loads the MainScreen with JavaFX.
     * @param stage loads the application window.
     * @return <h1>There is no return because this is a void method.</h1>
     * */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 890, 600));
        stage.show();


    }

    /** This is the main method.
     * This is the entry for the first method that
     * gets called when the java program is ran.
     * */
    public static void main(String[] args) {
        //loadTestData() may be commented out.
        loadTestData();


        launch(args);
    }

    /** This method creates pre-loaded information which will be visible on the stage.
     * @return There is no return since this is a void function.
     * */
    public static void loadTestData(){

        int id = 1;

        //Parts table values

        Part part1 = new InHouse(  id, "3/4\" Screw (steel) 12z",     0.05, 160, 150, 500,  4);
        Part part2 = new InHouse(++id, "3/4\" Nail (steel) 12q",     0.15, 150, 100, 500,  5);
        Part part3 = new InHouse(++id, "2\" Bolt (rubber)",       1.50, 150, 100, 300,  7);
        Part part4 = new InHouse(++id, "3/4\" L-bracket (steel)", 0.85,  50,  25, 100, 19);
        Part part5 = new InHouse(++id, "3/4\" Washer steel)",    0.75, 300, 300, 600, 22);

        Part part6 = new Outsourced(++id, "Condenser Fan & Motor (C)",  120.05,  160, 150,  500, "Acme");
        Part part7 = new Outsourced(++id, "Sandpaper (3 foot)",   1.40, 1000,  50, 2000, "Acme");
        Part part8 = new Outsourced(++id, "Water Filter (A-size)",   3.65,  160,  20,  500, "Deway");
        Part part9 = new Outsourced(++id, "100\" Graphite spokes (Kenmore!)", 20.50,  400, 350,  800, "DoGraffe");
        Part part10 = new Outsourced(++id, "Graphite A! handlebars (Kenmore)", 10.60,  40, 35,  800, "YaddaYadda");
        id = 1;

        //Products table values
        Product product1 = new Product(  id, "3/4 Bicycle",                499.99, 10,  5, 20);
        Product product2 = new Product(++id, "Chain & saw 4.0",               149.99, 60, 50, 61);
        Product product3 = new Product(++id, "15-foot Belt Sander",            299.97, 12,  1, 24);
        Product product4 = new Product(++id, "Washing Machine 56q",        899.95,  3,  3,  6);
        Product product5 = new Product(++id, "Dish Washer D-26z",            849.95,  3,  3,  6);
        Product product6 = new Product(++id, "Jigsaw! 99",                 199.95,  3,  3,  6);
        Product product7 = new Product(++id, "Refrigerator z675",          1899.95,  3,  3,  6);



        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        Inventory.addPart(part7);
        Inventory.addPart(part8);
        Inventory.addPart(part9);
        Inventory.addPart(part10);

        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part9);
        product1.addAssociatedPart(part10);
        product2.addAssociatedPart(part1);
        product2.addAssociatedPart(part3);
        product3.addAssociatedPart(part7);
        product3.addAssociatedPart(part6);
        product3.addAssociatedPart(part5);
        product3.addAssociatedPart(part4);
        product4.addAssociatedPart(part7);
        product5.addAssociatedPart(part3);
        product5.addAssociatedPart(part4);
        product6.addAssociatedPart(part2);
        product6.addAssociatedPart(part2);
        product6.addAssociatedPart(part9);
        product7.addAssociatedPart(part6);
        product7.addAssociatedPart(part5);
        product7.addAssociatedPart(part4);
        product7.addAssociatedPart(part7);
        product7.addAssociatedPart(part3);
        product7.addAssociatedPart(part4);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);
        Inventory.addProduct(product5);
        Inventory.addProduct(product6);
        Inventory.addProduct(product7);


    }


}

