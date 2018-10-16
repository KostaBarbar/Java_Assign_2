/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Contains most\all the functionality that the customer model did.
 * One model for all views as they need the same behaviour.
 * @author stephenfleming
 */
public class DataModel {
    private final String PATH = System.getProperty("user.dir") + "/src/prototypeclientserver/items.csv";
    
    private SQLConnector sql = new SQLConnector();
        
    private CSVReader csvReader;
    
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    
    private ArrayList<MenuItem> food = new ArrayList<>();
    private ArrayList<MenuItem> beverage = new ArrayList<>();
    
    public DataModel() {
        if (sql.testConnection()) {
            if (!sql.testDatabaseExists())
            {
                try {
                    sql.createAndPopulateDatabase(getFoodFromCSV(), getBeverageFromCSV());
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
            }
            else
            {
                try {
                    sql.connectToDatabase();
                } catch (SQLException e) {
                    System.out.println("DB Already Exists.");
                }
            }
        }
        
        updateMenuItemList();
        updateOrderList();
    }
    
    public SQLConnector getSQL() {
        return sql;
    }
   
    
    public ArrayList<Order> getOrders() {
        return orders;
    }
    
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
    
    public ArrayList<MenuItem> getFoodFromCSV()
    {
        return csvReader.GetFood(PATH);
    }
    
    public ArrayList<MenuItem> getBeverage() {
        return beverage;
    }
    
    public ArrayList<MenuItem> getBeverageFromCSV()
    {
        return csvReader.GetBeverage(PATH);
    }
    
    public void updateOrderList ( ){
        try {
            ArrayList<OrderRecord> orderRecords = sql.getOrders();
            
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
    
    public void updateMenuItemList() {
        try {
            ArrayList<MenuItem> itms = sql.getMenuItems();
            
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
    
    public void prepareSQL() throws SQLException
    {
        
        sql.connectToDatabase();
        ArrayList<MenuItem> temp = sql.getMenuItems();
    }
    
    public void serveOrder(Order o) {
        o.setServed(true);
        
        sql.updateOrderBoolean(o.getID(), "served", true);
    }
    
    public void billOrder(Order o) {
        o.setBilled(true);
        
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
   
}
