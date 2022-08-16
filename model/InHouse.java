package model;


import model.Abstracts.Part;
//TODO: NOTHING
/**This class extends the Parts class for instantiation as InHouse Parts*/
public class InHouse extends Part {

    private int machineId;

    /**This is the constructor used to instantiate InHouse parts.
     * @param id denotes the InHouse part's ID.
     * @param name denotes the InHouse part's name.
     * @param price denotes the InHouse part's price per item.
     * @param stock denotes the InHouse part's current in stock quantity.
     * @param min denotes the InHouse part's minimum allowable amount in stock.
     * @param max denotes the InHouse part's maximum allowable amount in stock.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    /**This method sets the machineId for the InHouse part.
     * @param machineId denotes a Part's machineId.*/
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**This method gets the machineId for the InHouse part.
     * */
    public int getMachineId(){
        return machineId;
    }

}
