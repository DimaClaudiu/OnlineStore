package Department;

import List.Visitor;
import Others.Customer;
import Others.Item;
import Others.Notification;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

abstract public class Department implements Visitable
{
    private String name;
    private Vector<Item> items;
    private Set<Customer> customersBuying;
    private Set<Customer> customersWanting;
    private int ID;

    public Department(String name, int ID)
    {
        items = new Vector<>();
        customersBuying = new HashSet<>();
        customersWanting = new HashSet<>();
        this.name = name;
        this.ID = ID;
    }

    public void enter(Customer customer)
    {
        customersBuying.add(customer);
    }

    public void exit(Customer customer)
    {
        customersWanting.remove(customer);
    }

    public Set<Customer> getCustomers()
    {
        return customersBuying;
    }

    public void updateCustomers()
    {
        Vector<Customer> formerCustomers = new Vector<>();
        for (Customer customer : customersBuying)
        {
            boolean isCustomer = false;
            for (Item item : customer.getShoppingCart())
                if (item.getDepID() == ID)
                {
                    isCustomer = true;
                    break;
                }

            if (!isCustomer)
                formerCustomers.add(customer);
        }
        customersBuying.removeAll(formerCustomers);
    }

    public int getID()
    {
        return ID;
    }

    public void addItem(Item item)
    {
        items.add(item);
        Notification notificationAdd = new Notification(Notification.NotificationType.ADD,
                item.getID(), this.ID);
        notifyAllObservers(notificationAdd);

    }

    public Item getItem(int itemID)
    {
        for (Item item : items)
            if (item.getID() == itemID)
                return item;
        return null;
    }

    public void removeItem(int itemID)
    {
        for (Item item : items)
            if (item.getID() == itemID)
            {
                Notification notificationAdd = new Notification(Notification.NotificationType.REMOVE,
                        item.getID(), this.ID);
                items.remove(item);
                notifyAllObservers(notificationAdd);

                return;
            }
    }

    public void modifyItem(int itemID, Double newPrice)
    {
        for (Item item : items)
            if (item.getID() == itemID)
            {
                Notification notificationAdd = new Notification(Notification.NotificationType.MODIFY,
                        item.getID(), this.ID);
                item.setPrice(newPrice);
                notifyAllObservers(notificationAdd);

                return;
            }
    }

    public void modifyItem(int itemID, Item newItem)
    {
        for (Item item : items)
            if (item.getID() == itemID)
            {
                Notification notificationAdd = new Notification(Notification.NotificationType.MODIFY,
                        item.getID(), this.ID);
                item = newItem;
                notifyAllObservers(notificationAdd);

                return;
            }
    }

    public Vector<Item> getItems()
    {
        return items;
    }

    public void addObserver(Customer customer)
    {
        customersWanting.add(customer);
    }

    public void removeObserver(String customerName)
    {
        customersWanting.remove(customerName);
    }

    public Vector<String> getObservers()
    {
        Vector<String> observerNames = new Vector<>();

        for(Customer customer:customersWanting)
            observerNames.add(customer.getName());

        return observerNames;
    }

    public void updateObservers()
    {
        Vector<Customer> formerObservers = new Vector<>();
        for (Customer customer : customersWanting)
        {
            boolean isObserver = false;
            for (Item item : customer.getWishList())
                if (item.getDepID() == ID)
                {
                    isObserver = true;
                    break;
                }

            if (!isObserver)
                formerObservers.add(customer);
        }
        customersWanting.removeAll(formerObservers);
    }

    public void notifyAllObservers(Notification notification)
    {
        Set<Customer> allMyCustomers = new HashSet<>(); // merge all the customers

        //allMyCustomers.addAll(customersBuying);
        allMyCustomers.addAll(customersWanting);

        for (Customer customer : allMyCustomers)
            customer.update(notification);
    }

    public abstract void accept(Visitor visitor);

}