import List.ShoppingCart;
import Others.Customer;
import Others.Item;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

public class UI extends JPanel
{

    static private JFrame mainFrame;
    private Test test;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModel1;
    private DefaultTableModel tableModel2;
    private JTable table;
    private JTable table1;
    private JTable table2;
    private JComboBox comboCustomers1;
    private JComboBox comboCustomers2;
    private JLabel label1;
    private JLabel label2;

    private Customer currentCustomer;


    public UI()
    {
        super(new GridLayout(1, 1));
        test = new Test();
        currentCustomer = null;
        //Tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        JComponent panel1 = makePanel();
        panel1.setLayout(new FlowLayout());
        tabbedPane.addTab("Start Page", panel1);

        JComponent panel2 = makePanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
        tabbedPane.addTab("Store", panel2);

        JComponent panel3 = makePanel();
        tabbedPane.addTab("Shopping Cart", panel3);

        JComponent panel4 = makePanel();
        tabbedPane.addTab("WishList", panel4);

        JComponent panel5 = makePanel();
        tabbedPane.addTab("Notifications", panel5);

        add(tabbedPane);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        /* Tab 1 */
        //Buttons
        JButton button1_0 = new JButton("Load Store");
        button1_0.addActionListener(new ActionStore());
        panel1.add(button1_0);

        JButton button1_1 = new JButton("Load Customers");
        button1_1.addActionListener(new ActionCustomers());
        panel1.add(button1_1);

        /* Tab 2*/
        //TabbleView
        String[] collums = {"Department ID", "Product ID", "Name", "Price"};


        tableModel = new DefaultTableModel(collums, 0);
        table = new JTable(tableModel)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        panel2.add(scrollPane);


        //Buttons
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout());

        JButton button2_0 = new JButton("Add Item");
        JButton button2_1 = new JButton("Remove Item");
        JButton button2_2 = new JButton("Modify Item");

        button2_0.addActionListener(new Action3());
        button2_1.addActionListener(new Action4());
        button2_2.addActionListener(new Action3());

        buttonPane.add(button2_0);
        buttonPane.add(button2_1);
        buttonPane.add(button2_2);

        panel2.add(buttonPane);


        /* Tab 3*/

        String[] emptySelection = {" "};
        comboCustomers1 = new JComboBox(emptySelection);
        comboCustomers1.setMaximumSize(new Dimension(80, 20));
        comboCustomers1.addActionListener(new ActionCombos());

        label1 = new JLabel("  Budget: ");
        label2 = new JLabel("Used:    ");

        tableModel1 = new DefaultTableModel(collums, 0);
        table1 = new JTable(tableModel1);
        table1.setAutoCreateRowSorter(true);


        JScrollPane scrollPane1 = new JScrollPane(table1);
        table1.setFillsViewportHeight(true);
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.PAGE_AXIS));
        panel3.add(scrollPane1);

        JPanel buttonPane1 = new JPanel();
        buttonPane1.setLayout(new FlowLayout());

        JButton button3_0 = new JButton("Add to Cart");
        JButton button3_1 = new JButton("Remove from Cart");
        JButton button3_2 = new JButton("Finalize");

        button3_0.addActionListener(new ActionCart());
        button3_1.addActionListener(new ActionRemove());
        button3_2.addActionListener(new ActionFinalize());

        buttonPane1.add(button3_0);
        buttonPane1.add(button3_1);
        buttonPane1.add(button3_2);

        JPanel labelPane = new JPanel();
        labelPane.setLayout(new BorderLayout());

        labelPane.add(label1, BorderLayout.LINE_START);
        labelPane.add(label2, BorderLayout.LINE_END);


        panel3.add(comboCustomers1);
        panel3.add(buttonPane1);
        panel3.add(labelPane);

        /* Tab 4 */
        comboCustomers2 = new JComboBox(emptySelection);
        comboCustomers2.setMaximumSize(new Dimension(80, 20));
        comboCustomers2.addActionListener(new ActionCombos());

        tableModel2 = new DefaultTableModel(collums, 0);
        table2 = new JTable(tableModel2);
        table1.setAutoCreateRowSorter(true);

        JScrollPane scrollPane2 = new JScrollPane(table2);
        table1.setFillsViewportHeight(true);
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.PAGE_AXIS));
        panel4.add(scrollPane2);

        JPanel buttonPane2 = new JPanel();
        buttonPane2.setLayout(new FlowLayout());

        JButton button4_0 = new JButton("Add to List");
        JButton button4_1 = new JButton("Remove from List");

        button4_0.addActionListener(new ActionCart());
        button4_1.addActionListener(new ActionRemove());

        buttonPane2.add(button4_0);
        buttonPane2.add(button4_1);

        panel4.add(comboCustomers2);
        panel4.add(buttonPane2);

        /* Tab 5 */
        String[] collums5 = {"Type", "Product ID", "Department ID"};


        DefaultTableModel tableModel5 = new DefaultTableModel(collums5, 0);
        JTable table5 = new JTable(tableModel5)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        table5.setAutoCreateRowSorter(true);
        table5.setFillsViewportHeight(true);

        JScrollPane scrollPane5 = new JScrollPane(table5);
        panel5.add(scrollPane5);

        tabbedPane.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                if (currentCustomer == null)
                    return;

                if (tabbedPane.getSelectedIndex() == 4)
                {
                    tableModel5.setRowCount(0);
                    for (String notification : test.getNotifications(currentCustomer.getName()))
                    {
                        String[] rowSplit = notification.split(";");
                        tableModel5.addRow(rowSplit);
                    }
                } else if (tabbedPane.getSelectedIndex() == 2)
                {
                    try
                    {
                        if (test.getItem(currentCustomer.getName()))
                            System.out.println("Strategy followed");
                    } catch (Exception ex)
                    {
                        //there is no item in the wishlist
                    }
                }
            }
        });
    }

    private static void createAndShowGUI()
    {
        //Create and set up the window.
        mainFrame = new JFrame("Magazin Online");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(800, 600));

        //Add content to the window.
        mainFrame.add(new UI(), BorderLayout.CENTER);

        //Display the window.
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void main(String[] args)
    {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        createAndShowGUI();
    }

    protected JComponent makePanel()
    {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }

    protected void addItemToRow(DefaultTableModel tableModel, Item item)
    {
        Object[] row = {item.getDepID(),
                item.getID(),
                item.getName(),
                item.getPrice()
        };
        tableModel.addRow(row);
    }

    protected void updateLabels()
    {
        if (currentCustomer == null)
        {
            label1.setText("   Budget: ");
            label2.setText("Used:    ");
            return;
        }
        label1.setText("   Budget: " + currentCustomer.getShoppingCart().getBudget());
        label2.setText("Used: " + currentCustomer.getShoppingCart().getCartTotal() + "   ");
    }

    class ActionStore implements ActionListener //for loading store
    {
        public void actionPerformed(ActionEvent e)
        {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.getName().equals("store.txt"))
                {
                    System.out.println("Please load store.txt!");
                    return;
                }

                try
                {
                    test.loadStore(selectedFile);
                    tableModel.setRowCount(0);

                    for (Item item : test.getStore().getItems())
                        addItemToRow(tableModel, item);

                } catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    class ActionCustomers implements ActionListener //for loading customers
    {
        public void actionPerformed(ActionEvent e)
        {
            JButton button = (JButton) e.getSource();
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.getName().equals("customers.txt"))
                {
                    System.out.println("Please load customers.txt!");
                    return;
                }
                try
                {
                    test.loadCustomers(selectedFile);
                    for (Customer customer : test.getStore().getCustomers())
                    {
                        comboCustomers1.addItem(customer.getName());
                        comboCustomers2.addItem(customer.getName());
                    }


                } catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    class Action3 implements ActionListener //for add and modify item button
    {
        public void actionPerformed(ActionEvent e)
        {
            JTextField textField1 = new JTextField();
            JTextField textField2 = new JTextField();
            JTextField textField3 = new JTextField();
            JTextField textField4 = new JTextField();
            JPanel panel = new JPanel(new GridLayout(0, 1));

            panel.add(new JLabel("Dep. ID:"));
            panel.add(textField1);
            panel.add(new JLabel("Prod. ID:"));
            panel.add(textField2);
            panel.add(new JLabel("Name:"));
            panel.add(textField3);
            panel.add(new JLabel("Price:"));
            panel.add(textField4);

            if (e.getActionCommand().equals("Add Item"))
            {
                int result = JOptionPane.showConfirmDialog(null, panel, "Add New Item",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION)
                {
                    int depID = Integer.parseInt(textField1.getText());
                    int prodID = Integer.parseInt(textField2.getText());
                    String name = textField3.getText();
                    Double price = Double.parseDouble(textField4.getText());

                    if (test.addProduct(depID, prodID, price, name))
                        tableModel.addRow(new Object[]{depID, prodID, name, price});
                    else
                        JOptionPane.showMessageDialog(null,
                                "An item with this ID already exists!",
                                "Error adding item.",
                                JOptionPane.ERROR_MESSAGE);
                }

            } else if (e.getActionCommand().equals("Modify Item"))
            {
                if (!(table.getSelectedRow() > -1 && table.getSelectedRow() < table.getRowCount()))
                {
                    JOptionPane.showMessageDialog(null,
                            "Please select a item!",
                            "Error modifying item.",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                textField1.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                textField2.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                textField3.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                textField4.setText(table.getValueAt(table.getSelectedRow(), 3).toString());

                int result = JOptionPane.showConfirmDialog(null, panel,
                        "Modify Item",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION)
                {
                    int oldDepID = (int) table.getValueAt(table.getSelectedRow(), 0);
                    int oldProdID = (int) table.getValueAt(table.getSelectedRow(), 1);

                    int depID = Integer.parseInt(textField1.getText());
                    int prodID = Integer.parseInt(textField2.getText());
                    String name = textField3.getText();
                    Double price = Double.parseDouble(textField4.getText());

                    Item newItem = new Item(name, prodID, price);

                    test.getStore().getDepartment(oldDepID).modifyItem(oldProdID, newItem);

                    table.setValueAt(depID, table.getSelectedRow(), 0);
                    table.setValueAt(prodID, table.getSelectedRow(), 1);
                    table.setValueAt(name, table.getSelectedRow(), 2);
                    table.setValueAt(price, table.getSelectedRow(), 3);

                }
            }
        }
    }

    class Action4 implements ActionListener //for remove item button
    {
        public void actionPerformed(ActionEvent e)
        {
            //Row sorter only cares about the view, not the model, so we have to convert
            //the indices when fetching the value
            int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());

            int prodID = (int) tableModel.getValueAt(selectedRow, 1);
            tableModel.removeRow(selectedRow);

            test.delProduct(prodID);
            updateLabels();
        }
    }

    class ActionCombos implements ActionListener //for combo boxes
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JComboBox comboBox = (JComboBox) e.getSource();
            int index = comboBox.getSelectedIndex();
            comboCustomers1.setSelectedIndex(index);
            comboCustomers2.setSelectedIndex(index);

            if (index == 0)
            {
                tableModel1.setRowCount(0);
                tableModel2.setRowCount(0);
                currentCustomer = null;
                updateLabels();
                return;
            }

            String customerName = comboBox.getSelectedItem().toString();
            currentCustomer = test.getStore().getCustomer(customerName);

            updateLabels();

            tableModel1.setRowCount(0); //to refresh
            for (Item item : currentCustomer.getShoppingCart()) //load Shopping Cart
                addItemToRow(tableModel1, item);

            tableModel2.setRowCount(0);
            for (Item item : currentCustomer.getWishList()) //load Wish List
                addItemToRow(tableModel2, item);
        }
    }

    class ActionCart implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (currentCustomer == null)
            {
                JOptionPane.showMessageDialog(null, "No user selected!");
                return;
            }
            String inputID = JOptionPane.showInputDialog(null, "Please enter Item ID: ");
            if (!inputID.equals(null))
            {
                int itemID = Integer.parseInt(inputID);
                for (Item item : test.getStore().getItems())
                    if (item.getID() == itemID)
                    {
                        if (e.getActionCommand().equals("Add to Cart"))
                        {
                            if (test.addItem(itemID, "ShoppingCart", currentCustomer.getName()))
                            {
                                addItemToRow(tableModel1, item);
                            }
                        } else if (e.getActionCommand().equals("Add to List"))
                        {
                            if (test.addItem(itemID, "WishList", currentCustomer.getName()))
                                addItemToRow(tableModel2, item);
                        }
                    }
            }
            updateLabels();

        }


    }

    class ActionRemove implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("Remove from Cart"))
            {
                int selectedRow = table1.convertRowIndexToModel(table1.getSelectedRow());

                int prodID = (int) tableModel1.getValueAt(selectedRow, 1);
                tableModel1.removeRow(selectedRow);
                test.delItem(prodID, "ShoppingCart", currentCustomer.getName());
                updateLabels();
            } else if (e.getActionCommand().equals("Remove from List"))
            {
                int selectedRow = table2.convertRowIndexToModel(table2.getSelectedRow());

                int prodID = (int) tableModel2.getValueAt(selectedRow, 1);
                tableModel2.removeRow(selectedRow);
                test.delItem(prodID, "WishList", currentCustomer.getName());
            }
        }
    }

    class ActionFinalize implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Double budget = currentCustomer.getShoppingCart().getBudget();
            budget -= currentCustomer.getShoppingCart().getCartTotal();
            currentCustomer.setShoppingCart(new ShoppingCart(budget));
            tableModel1.setRowCount(0);
            updateLabels();
        }
    }
}