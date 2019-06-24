package List;

import Department.BookDepartment;
import Department.MusicDepartment;
import Department.SoftwareDepartment;
import Department.VideoDepartment;
import Others.Item;
import Others.Store;


public class ShoppingCart extends ItemList implements Visitor
{
    private Double budget;
    private Double cartTotal;

    public ShoppingCart(Double budget)
    {
        this.budget = budget;
        this.cartTotal = 0.0;
    }

    public boolean add(Item item)
    {
        if (cartTotal + item.getPrice() > budget)
            return false;

        cartTotal += item.getPrice();
        return super.add(item);
    }

    public boolean remove(Item item)
    {
        if (super.remove(item))
        {
            cartTotal -= item.getPrice();
            Store.getInstance().getDepartment(item.getDepID()).updateCustomers();
            return true;
        }
        return false;
    }


    @Override
    public void visit(BookDepartment bookDepartment)
    {
        for(Item item:this)
            if(item.getDepID() == bookDepartment.getID())
                item.setPrice(item.getPrice() * 0.9);

        //for (Item item : bookDepartment.getItems())
          //  item.setPrice(item.getPrice() * 0.9);
    }

    @Override
    public void visit(MusicDepartment musicDepartment)
    {
        int budgetSurplus = 0;

        for(Item item:this)
            if(item.getDepID() == musicDepartment.getID())
                budgetSurplus += item.getPrice();

        //for (Item item : musicDepartment.getItems())
          //  if (this.containts(item))
            //    budgetSurplus += item.getPrice();

        budget += budgetSurplus * 0.1;
    }

    @Override
    public void visit(SoftwareDepartment softwareDepartment)
    {
        if (this.getItem(1).getPrice() > budget)
            for(Item item:this)
                if(item.getDepID() == softwareDepartment.getID())
                    item.setPrice(item.getPrice() * 0.8);
//            for (Item item : softwareDepartment.getItems())
//                if (this.containts(item))
//                    item.setPrice(item.getPrice() * 0.8);
    }

    @Override
    public void visit(VideoDepartment videoDepartment)
    {
        Double totalVideoPrice = new Double(0);

        for (Item item : this)
            if (item.getDepID() == videoDepartment.getID())
                totalVideoPrice += item.getPrice();

        Double maxPrice = new Double(0);

        for (Item item : videoDepartment.getItems())
            if (item.getPrice() > maxPrice)
                maxPrice = item.getPrice();

        if (totalVideoPrice > maxPrice)
        {
            for (Item item : this)
                if (item.getDepID() == videoDepartment.getID())
                    item.setPrice(item.getPrice() * 0.85);
        }

        int budgetSurplus = 0;
        for (Item item : videoDepartment.getItems())
            if (this.containts(item))
                budgetSurplus += item.getPrice();

        budget += budgetSurplus * 0.05;


    }

    public Double getBudget()
    {
        return budget;
    }

    public Double getCartTotal()
    {
        return cartTotal;
    }
}
