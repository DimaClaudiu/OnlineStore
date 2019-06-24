package Others;

import java.text.DecimalFormat;

public class Item
{
    private String name;
    private int ID;
    private Double price;
    private int depID;


    public Item(String name, int ID, Double price)
    {
        this.name = name;
        this.ID = ID;
        this.price = price;
    }

    public Item(String name, int ID, Double price, int depID)
    {
        this(name, ID, price);
        this.depID = depID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getID()
    {
        return ID;
    }

    public int getDepID()
    {
        return depID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public String toString()
    {
        return name + ";" + ID + ";" + new DecimalFormat("#0.00").format(price);
    }

}
