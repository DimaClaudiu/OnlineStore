package Others;

import Department.Department;
import List.ShoppingCart;

import java.util.Vector;

public class Store
{
    private static Store storeInstance = null;
    private String name;
    private Vector<Department> departments;
    private Vector<Customer> customers;

    private Store()
    {
        departments = new Vector<>();
        customers = new Vector<>();
    }

    public static Store getInstance()
    {
        if (storeInstance == null)
            storeInstance = new Store();

        return storeInstance;
    }

    public void setName(String hame)
    {
        this.name = name;
    }

    public void enter(Customer customer)
    {
        customers.add(customer);
    }

    public void exit(Customer customer)
    {
        customers.remove(customer);
    }

    public ShoppingCart getShopingCart(Double budget)
    {
        return new ShoppingCart(budget);
    }

    public Vector<Customer> getCustomers()
    {
        return customers;
    }

    public Customer getCustomer(String customerName)
    {
        for (Customer customer : customers)
            if (customer.getName().equals(customerName))
                return customer;

        return null;
    }

    public Vector<Department> getDepartments()
    {
        return departments;
    }

    public void addDepartment(Department department)
    {
        departments.add(department);
    }

    public Department getDepartment(Integer ID)
    {
        for (Department d : departments)
            if (d.getID() == ID)
                return d;

        return null;
    }

    public Vector<Item> getItems()
    {
        Vector<Item> allItems = new Vector<>();
        for (Department department : departments)
            allItems.addAll(department.getItems());

        return allItems;
    }

}
