/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import java.io.File;

/**
 * Contains most\all the functionality that the customer model did.
 * One model for all views as they need the same behaviour.
 * @author stephenfleming & kosta
 */
public class DataModel {
    /**
     * Extended class to implement Threading into the client server model
     */
    private class DataModelUpdater extends Thread {
        private Object synchroObject;
        private int invertal;
        
        private onModelUpdate callback;
        
        private boolean active;
        
        public DataModelUpdater(Object syncObj, int time, onModelUpdate evt) {
            synchroObject = syncObj;
            invertal = time;
            callback = evt;
            
            this.start();
        }
        /**
         * Sets the interval for the thread to run it's tasks
         * @param time time in milliseconds between running the thread
         */
        public synchronized void setInvertal(int time) {
            invertal = time;
        }
        /**
         * Override for the run method
         * Sleeps for the allocated amount of time before executing update function
         */
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(invertal);
                    
                    synchronized(synchroObject){
                        callback.onUpdate();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }    
    }
    
    private final String PATH = fixedPath();
    
    private SQLConnector sql = new SQLConnector();
        
    private CSVReader csvReader;
    
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    
    private ArrayList<MenuItem> food = new ArrayList<>();
    private ArrayList<MenuItem> beverage = new ArrayList<>();
    
    private DataModelUpdater updater;
    
    /**
     * Constructor for the DataModel
     * Attempts to connect to the database or create it if it doesn't exit
     */
    public DataModel() {
        //Try connect to SQL
        if (sql.testConnection()) {
            //Check if the database exists
            if (!sql.testDatabaseExists())
            {
                try {
                    //Create and populate the database with tables and values from the CSV
                    sql.createAndPopulateDatabase(getFoodFromCSV(), getBeverageFromCSV());
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
            }
            else
            {
                try {
                    //If the database exists, connect to it
                    sql.connectToDatabase();
                } catch (SQLException e) {
                    System.out.println("DB Already Exists.");
                }
            }
        }
        //Update the models food and beverage lists
        updateMenuItemList();
        //Update the orders list from the sql database
        updateOrderList();
    }
    
    public void setUpdateInvertal(Object sync, int milliseconds, onModelUpdate evt) {
        if (updater != null) 
            updater.stop();

        updater = new DataModelUpdater(sync, milliseconds, evt);
    }
    /**
     * Getter for the SQLConnector
     * @return the SQLConnector
     */
    public SQLConnector getSQL() {
        return sql;
    }
    /**
     * Getter for the list of orders
     * Updates the list before returning
     * @return ArrayList<Order> of orders
     */
    public ArrayList<Order> getOrders() {
        updateOrderList();
            
        return orders;
    }
    /**
     * Getter for the menuItems
     * @return ArrayList<MenuItem> of menuitems
     */
    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }
    /**
     * Getter for food in model
     * @return ArrayList of all food items
     */
    public ArrayList<MenuItem> getFood() {
        return food;
    }
    /**
     * Getter for the food items from the CSV file
     * @return ArrayList<MenuItem> of food items
     */
    public ArrayList<MenuItem> getFoodFromCSV()
    {
        return csvReader.GetFood(PATH);
    }
    /**
     * Getter for the beverage items from the model
     * @return ArrayList<MenuItem> of beverages
     */
    public ArrayList<MenuItem> getBeverage() {
        return beverage;
    }
    /**
     * Getter for the beverages from the CSV file
     * @return ArrayList<MenuItem> of beverage items
     */
    public ArrayList<MenuItem> getBeverageFromCSV()
    {
        return csvReader.GetBeverage(PATH);
    }
    /**
     * Function to update the list of orders
     * Initially clears the list of orders before repopulating the list
     */
    public void updateOrderList ( ){
        orders.clear();
        
        try {
            //Get orders from the database
            ArrayList<OrderRecord> orderRecords = sql.getOrders();
            //Iterate over each order in orders, process the data and add an order to the list
            orderRecords.forEach(r -> {
                ArrayList<MenuItem> orderItems = new ArrayList<>();
                MenuItem food = getMenuItem(r.food);
                MenuItem beverage = getMenuItem(r.beverage);
                
                orderItems.add(food);
                orderItems.add(beverage);
                
                Order o = new Order(r.name, r.id, r.table, orderItems);
                
                if (r.served)
                    o.setServed(true);
                
                if (r.billed)
                    o.setBilled(true);
                
                orders.add(o);
            });
        } catch (SQLException e) {
            System.out.println("GET ORDER LIST: " + e.toString());
        }    
    }
    /**
     * Function to update the items in the comboboxes
     */
    public void updateMenuItemList() {
        try {
            //Get all the menuitems from the database
            ArrayList<MenuItem> itms = sql.getMenuItems();
            //Check whether they food or beverage and add to the appropriate list
            itms.forEach(i -> {
                if (i.getMenuDesc().trim().equals("food"))
                    food.add(i);
                else 
                    beverage.add(i);
            });
            
            menuItems = itms;
        } catch (SQLException e) {
            System.out.println("GET MENU ITEMS: " + e.toString());
        }
    }
    /**
     * Searches Food and Beverage ArrayLists for specified item
     * @param itemname item name string to search for
     * @return MenuItem which contains specified item name
     */
    public MenuItem getMenuItem(String itemname) {
        for (MenuItem b : menuItems) {
            if (b.getItemName().trim().equals(itemname)) {
                return b;
            }
        } 
        //If no MenuItem is found return null
        return null;
    }
    /**
     * Creates a new order and returns the order created
     * @param cn customer name string
     * @param table table number integer
     * @param selectedItems menu items selected array
     * @return newly created order
     */
    public Order createOrder(String cn, int table, String[] selectedItems) {
        ArrayList<MenuItem> items = new ArrayList<>();
        MenuItem tmp;
        
        int orderId = orders.size();
        if (orders.size() > 0)
            orderId = orders.get(orders.size()-1).orderId + 1;
        //Loop through each item
        for (String s : selectedItems) {
            //Grab MenuItem from string name
            tmp = getMenuItem(s);
            
            //Null check
            if (tmp != null)
                //Add to ArrayList
                items.add(tmp);
        }
        //Create new order from obtained inputs
        Order newOrder = new Order(cn, orderId, table, items);
        //Add to orders array
        orders.add(newOrder);
        //Return newly created order
        return newOrder;
    }
    /**
     * Function to filter orders for the Chef to view
     * Only shows orders that haven't been served yet
     */
    public void filterOrdersForChef()
    {
        int length = orders.size() - 1;
        for (int i = length; i >= 0; i--)
        {
            if (orders.get(i).isServed())
                    orders.remove(i);
        }
    }
    /**
     * Attempt to connect to the SQL database
     * @throws SQLException if cannot connect
     */
    public void prepareSQL() throws SQLException
    {
        sql.connectToDatabase();
    }
    /**
     * Updates an order in the model and the database
     * 'Serves' the order
     * @param o the order to serve
     */
    public void serveOrder(Order o) {
        //Update model
        o.setServed(true);
        //Update database
        sql.updateOrderBoolean(o.getID(), "served", true);
    }
    /**
     * Updates an order in the model and the database
     * 'Bills' the order
     * @param o the order to bill
     */
    public void billOrder(Order o) {
        //Update model
        o.setBilled(true);
        //Update database
        sql.updateOrderBoolean(o.getID(), "billed", true);
    }
    /**
     * Combines the values of two MenuItems
     * Used for the output table panel, in the total row
     * @param a first MenuItem
     * @param b second MenuItem
     * @return result of combining MenuItem contents
     */
    public MenuItem combineMenuItems(MenuItem a, MenuItem b)
    {
        return new MenuItem(
                "Combined", 
                a.getMealType(), 
                "Combined", 
                a.getPrice() + b.getPrice(),
                a.getEnergy() + b.getEnergy(),
                a.getProtein() + b.getProtein(),
                a.getCarbs() + b.getCarbs(),
                a.getFat() + b.getFat(),
                a.getFibre() + b.getFibre(),
                0
        );
    }
    /**
     * Converts ArrayLists into String arrays
     * @param arr ArrayList of MenuItems to convert 
     * @return string array of item names
     */
    public String[] getStringArray(ArrayList<MenuItem> arr)
    {
        //Create values array with required size
        String[] values = new String[arr.size()];
        //Loop through all MenuItems and add their names to the array
        for (int i = 0; i < arr.size(); i++)
        {
            values[i] = arr.get(i).getItemName();
        }
        //Return result
        return values;
    }
    /**
     * Resets the list of orders in the model
     */
    public void resetOrders()
    {
        orders = new ArrayList<>();
    }
    /**
     * Function to return the corrected path to the items CSV file.
     * @return file path string
     */
    public String fixedPath()
    {
        //The main folder name
        //By default the main folder name is the 
        String FOLDER_NAME = "Java_Assign_2";
        String result = "";
        String[] components = System.getProperty("user.dir").split(Pattern.quote(File.separator));
        int index;
        for (index = 0; index < components.length-1; index++) {
            if (components[index].equals(FOLDER_NAME))
                break;
            else
                result += components[index] + File.separator;
        }
        result += components[index] + File.separator + "PrototypeStandalone"+File.separator+"RestaurantOrderProcessor"+File.separator+"src/restaurantorderprocessor/items.csv";
        return result;
    }
    
}
