package model.Abstracts;

/**This class creates the abstract Parts class*/
public abstract class Part  {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part(){
        this.id = 0;
        this.name = "default_part";
        this.price = 0.00;
        this.stock = 0;
        this. min = 0;
        this.max = 0;
    }

    public Part(int id, String name, double price, int stock, int min, int max){
       this.id = id;
       this.name = name;
       this.price = price;
       this.stock = stock;
       this. min = min;
       this.max = max;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(double price){
       this.price = price;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public void setMin(int min){
        this.max = min;
    }

    public void setMax(int max){
        this.max = max;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getStock(){
        return stock;
    }

    public int getMin(){
        return min;
    }

    public int getMax(){
        return max;
    }
}
