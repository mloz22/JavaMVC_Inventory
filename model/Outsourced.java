package model;

import model.Abstracts.Part;
//TODO: NOTHING
/**This class extends the Parts class for instantiation as Outsourced Parts*/
public class Outsourced extends Part {

    String companyName;

    /**This is the constructor used to instantiate Outsourced parts.
     * @param id denotes the Outsourced part's ID.
     * @param name denotes the Outsourced part's name.
     * @param price denotes the Outsourced part's price per item.
     * @param stock denotes the Outsourced part's current in stock quantity.
     * @param min denotes the Outsourced part's minimum allowable amount in stock.
     * @param max denotes the Outsourced part's maximum allowable amount in stock.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    };

    /**This method sets the machineId for the Outsourced part.
     * @param companyName denotes a Part's companyName.*/
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    };

    /**This method gets the companyName for the Outsourced part.*/
    public String getCompanyName(){
        return companyName;
    };

}
