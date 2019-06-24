package List;

import Others.Item;

public interface Strategy
{
    Item execute(WishList wishList);
}

class StrategyA implements Strategy
{
    public Item execute(WishList wishList)
    {
        double minPrice = Double.MAX_VALUE;
        Item myItem = null;

        for (Item item : wishList)
        {
            if (item.getPrice() < minPrice)
            {
                minPrice = item.getPrice();
                myItem = item;
            }
        }

        wishList.remove(myItem);
        return myItem;
    }
}

class StrategyB implements Strategy
{
    public Item execute(WishList wishList)
    {
        Item myItem = wishList.getItem(1);

        wishList.remove(myItem);
        return myItem;
    }
}

class StrategyC implements Strategy
{
    public Item execute(WishList wishList)
    {
        Item myItem = wishList.getLastAddedItem();

        wishList.remove(myItem);
        return myItem;
    }
}
