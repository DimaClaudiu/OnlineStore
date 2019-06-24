package Others;

import List.ShoppingCart;
import List.WishList;
import java.util.Vector;



public class Customer
{
    private String name;
    private ShoppingCart shoppingCart;
    private WishList wishList;
    private Vector<Notification> notifications;

    public Customer(String name, Double budget)
    {
        this.name = name;
        shoppingCart = new ShoppingCart(budget);
        wishList = new WishList(this);
        notifications = new Vector<>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void update(Notification notification)
    {
        notifications.add(notification);
    }

    public ShoppingCart getShoppingCart()
    {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart)
    {
        this.shoppingCart = shoppingCart;
    }

    public WishList getWishList()
    {
        return wishList;
    }

    public void setWishList(WishList wishList)
    {
        this.wishList = wishList;
    }

    public Vector<String> getNotifications()
    {
        Vector<String> myNotifications = new Vector<>();

        for(Notification notification:notifications)
            myNotifications.add(notification.getNotification());

        return myNotifications;
    }

    public String toString()
    {
        return "Customer: " + name +
                " budget: " + shoppingCart.getBudget() +
                " used: " + shoppingCart.getCartTotal() +
                " strategy: " + wishList.strat;
    }
}
