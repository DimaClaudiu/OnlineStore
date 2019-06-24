package List;

import Others.Customer;
import Others.Item;
import Others.Store;

import java.util.Comparator;
import java.util.Vector;

public class WishList extends ItemList
{
    public String strat;
    private Strategy strategy;
    private Customer owner;
    private Vector<Item> orderOfItems;

    public WishList(Customer owner)
    {
        super(new Comparator()
        {
            public int compare(Object n1, Object n2)
            {
                Item item1 = (Item) ((Node) n1).getValue();
                Item item2 = (Item) ((Node) n2).getValue();

                int value = item1.getName().compareTo(item2.getName());

                if (value == 0)
                    return item1.getPrice().compareTo(item2.getPrice());

                return value;
            }
        });
        this.owner = owner;
        strategy = null;
        orderOfItems = new Vector<>();
    }

    public Customer getOwner()
    {
        return owner;
    }

    public boolean add(Item item)
    {
        orderOfItems.add(item);
        return super.add(item);
    }

    public boolean remove(Item item)
    {
        if (super.remove(item))
        {
            Store.getInstance().getDepartment(item.getDepID()).updateObservers();
            orderOfItems.remove(item);
            return true;
        }
        return false;
    }

    public Item getLastAddedItem()
    {
        return orderOfItems.lastElement();
    }

    public void setStrategy(String strategy)
    {
        if (strategy.equals("A"))
            this.strategy = new StrategyA();
        else if (strategy.equals("B"))
            this.strategy = new StrategyB();
        else
            this.strategy = new StrategyC();
        strat = strategy;
    }

    public Item executeStrategy()
    {
        return strategy.execute(this);
    }


}
