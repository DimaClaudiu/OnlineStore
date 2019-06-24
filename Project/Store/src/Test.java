import Department.*;
import Others.Customer;
import Others.Item;
import Others.Store;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Vector;

public class Test
{
    private static Store store;

    public Test()
    {
        store = Store.getInstance();
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        Test t = new Test();

        int testNr = 8;
        String testsPath = "./Resources/Teste/test0";

        File fileStore = new File(testsPath + testNr + "/store.txt");
        File fileCustomers = new File(testsPath + testNr + "/customers.txt");
        File fileEvents = new File(testsPath + testNr + "/events.txt");

        t.loadStore(fileStore);
        t.loadCustomers(fileCustomers);
        t.treatEvents(fileEvents);



    }

    // Reading, loading and executing files
    public Store getStore()
    {
        return store;
    }

    public void loadCustomers(File file) throws FileNotFoundException
    {
        Scanner sc = new Scanner(file);
        store.getCustomers().clear();
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++)
        {
            String[] toSplit = sc.nextLine().split(";");
            Customer customer = new Customer(toSplit[0], Double.parseDouble(toSplit[1]));
            customer.getWishList().setStrategy(toSplit[2]);

            store.enter(customer);
        }

    }

    public void loadStore(File file) throws FileNotFoundException
    {
        //File file = new File("./zDOWNLOADS/Teste/test00/store.txt");
        Scanner sc = new Scanner(file);

        for (Department department : store.getDepartments())
            department.getItems().clear();

        store.setName(sc.nextLine()); //getting store

        Department department = null;
        while (sc.hasNextLine())
        {
            String[] toSplit = sc.nextLine().split(";"); //getting department
            int depID = Integer.parseInt(toSplit[1]);
            switch (toSplit[0])
            {
                case "BookDepartment":
                    department = new BookDepartment("BD", depID);
                    break;

                case "MusicDepartment":
                    department = new MusicDepartment("MD", depID);
                    break;

                case "SoftwareDepartment":
                    department = new SoftwareDepartment("SD", depID);
                    break;

                case "VideoDepartment":
                    department = new VideoDepartment("VD", depID);
                    break;

            }

            int nrOfItems = Integer.parseInt(sc.nextLine()); //getting items

            for (int i = 0; i < nrOfItems; i++)
            {
                toSplit = sc.nextLine().split(";");
                Item item = new Item(toSplit[0], Integer.parseInt(toSplit[1]), Double.parseDouble(toSplit[2]), depID);
                department.addItem(item);
            }

            store.addDepartment(department);

        }
    }

    public void treatEvents(File file) throws FileNotFoundException
    {
        Scanner sc = new Scanner(file);

        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++)
        {
            //System.out.println(i+2);
            String[] toSplit = sc.nextLine().split(";");
            switch (toSplit[0])
            {
                case "addItem":
                    addItem(Integer.parseInt(toSplit[1]), toSplit[2], toSplit[3]);
                    break;
                case "delItem":
                    delItem(Integer.parseInt(toSplit[1]), toSplit[2], toSplit[3]);
                    break;
                case "addProduct":
                    addProduct(Integer.parseInt(toSplit[1]),
                            Integer.parseInt(toSplit[2]),
                            Double.parseDouble(toSplit[3]),
                            toSplit[4]);
                    break;
                case "modifyProduct":
                    modifyProduct(Integer.parseInt(toSplit[1]),
                            Integer.parseInt(toSplit[2]),
                            Double.parseDouble(toSplit[3]));
                    break;
                case "delProduct":
                    delProduct(Integer.parseInt(toSplit[1]));
                    break;
                case "getItem":
                    getItem(toSplit[1]);
                    break;
                case "getItems":
                    getItems(toSplit[1], toSplit[2]);
                    break;
                case "getTotal":
                    getTotal(toSplit[1], toSplit[2]);
                    break;
                case "accept":
                    accept(Integer.parseInt(toSplit[1]), toSplit[2]);
                    break;
                case "getObservers":
                    getObservers(Integer.parseInt(toSplit[1]));
                    break;
                case "getNotifications":
                    getNotifications(toSplit[1]);
                    break;
            }
        }
    }

    // For treating events
    public boolean addItem(int itemID, String where, String customerName)
    {
        for (Department department : store.getDepartments())
        {
            for (Item item : department.getItems())
            {
                if (item.getID() == itemID)
                {
                    for (Customer customer : store.getCustomers())
                    {
                        if (customer.getName().equals(customerName))
                        {
                            if (where.equals("ShoppingCart"))
                            {
                                if (customer.getShoppingCart().add(item))
                                {
                                    department.enter(customer);
                                    return true;
                                }
                            } else if (where.equals("WishList"))
                            {
                                customer.getWishList().add(item);
                                department.addObserver(customer);
                                return true;
                            } else
                                System.out.println("usage: ShoppingCart/ WishList");
                        }
                    }
                }
            }
        }
        return false;
    }

    public void delItem(int itemID, String from, String customerName)
    {
        for (Department department : store.getDepartments())
        {
            for (Item item : department.getItems())
            {
                if (item.getID() == itemID)
                {
                    for (Customer customer : store.getCustomers())
                    {
                        if (customer.getName().equals(customerName))
                        {
                            if (from.equals("ShoppingCart"))
                                customer.getShoppingCart().remove(item);
                            else if (from.equals("WishList"))
                                customer.getWishList().remove(item);
                            else
                                System.out.println("usage: ShoppingCart/ WishList");

                            return;
                        }
                    }
                }
            }
        }
    }

    public boolean addProduct(int depID, int itemID, Double price, String name)
    {
        for (Item item : store.getDepartment(depID).getItems())
            if (item.getID() == itemID)
                return false;

        store.getDepartment(depID).addItem(new Item(name, itemID, price));
        return true;
    }

    public void modifyProduct(int depID, int itemID, Double price)
    {
        store.getDepartment(depID).modifyItem(itemID, price);
        //store.getDepartment(depID).getItem(itemID).setPrice(price);
    }

    public void delProduct(int itemID)
    {
        for (Department department : store.getDepartments()) //remove from store departements
        {
            for (Item item : department.getItems())
            {
                if (item.getID() == itemID)
                {
                    department.removeItem(item.getID());
                    break;
                }
            }
        }

        for (Customer customer : store.getCustomers()) //remove from customers cart or wishlist
        {
            for (Item item : customer.getShoppingCart())
                if (item.getID() == itemID)
                {
                    customer.getShoppingCart().remove(item);
                    //break;
                }
            for (Item item : customer.getWishList())
                if (item.getID() == itemID)
                {
                    customer.getWishList().remove(item);
                    //break;
                }

        }
    }

    public boolean getItem(String customerName)
    {
        for (Customer customer : store.getCustomers())
            if (customer.getName().equals(customerName))
            {
                Item myItem = customer.getWishList().executeStrategy();
                if(customer.getShoppingCart().add(myItem))
                {
                    System.out.println(myItem);
                    return true;
                }
                return false;
            }
            return false;
    }

    public void getItems(String from, String customerName)
    {
        for (Customer customer : store.getCustomers())
            if (customer.getName().equals(customerName))
            {
                boolean comma = false;
                if (from.equals("ShoppingCart"))
                {
                    System.out.print("[");

                    for (Item item : customer.getShoppingCart())
                    {
                        if (comma)
                            System.out.print(", ");
                        comma = true;
                        System.out.print(item);
                    }

                    System.out.println("]");
                } else if (from.equals("WishList"))
                {
                    System.out.print("[");

                    for (Item item : customer.getWishList())
                    {
                        if (comma)
                            System.out.print(", ");
                        comma = true;
                        System.out.print(item);
                    }

                    System.out.println("]");
                } else
                    System.out.println("usage: ShoppingCart/ WishList");

            }
    }

    public void getTotal(String from, String customerName)
    {
        Double total = new Double(0);

        for (Customer customer : store.getCustomers())
            if (customer.getName().equals(customerName))
            {
                if (from.equals("ShoppingCart"))
                    for (Item item : customer.getShoppingCart())
                        total += item.getPrice();
                else if (from.equals("WishList"))
                    for (Item item : customer.getWishList())
                        total += item.getPrice();
                else
                    System.out.println("usage: ShoppingCart/ WishList");

            }

        System.out.println(new DecimalFormat("#0.00").format(total));
    }

    public void accept(int depID, String customerName)
    {
        Department department = store.getDepartment(depID);
        for (Customer customer : department.getCustomers())
            if (customer.getName().equals(customerName))
            {
                department.accept(customer.getShoppingCart());
                return;
            }

    }

    public void getObservers(int depID)
    {
        System.out.println(store.getDepartment(depID).getObservers());
    }

    public Vector<String> getNotifications(String customerName)
    {
        for (Customer customer : store.getCustomers())
            if (customer.getName().equals(customerName))
            {
                System.out.println(customer.getNotifications());
                return  customer.getNotifications();
            }
            return null;
    }

    public void showCustomers()
    {
        for (Customer customer : store.getCustomers())
            System.out.println(customer);

    }
}
