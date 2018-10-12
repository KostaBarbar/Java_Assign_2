/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver.ClientCustomer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import prototypeclientserver.MenuItem;
import prototypeclientserver.Order;

import prototypeclientserver.CSVReader;
import prototypeclientserver.SQLConnector;

/**
 *
 * @author stephenfleming
 */
public class ClientScreenModel {
    private final String PATH = System.getProperty("user.dir") + "/src/prototypeclientserver/items.csv";
    
    private CSVReader csvReader;
    
    private SQLConnector sql = new SQLConnector();
    
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<MenuItem> food = new ArrayList<>();
    private ArrayList<MenuItem> beverage = new ArrayList<>();
    
    public ClientScreenModel() throws SQLException {
        csvReader = new CSVReader();
    }
    
    public void prepareSQL() throws SQLException
    {
        
        sql.connectToDatabase();
        ArrayList<MenuItem> temp = sql.getMenuItems();
        food = csvReader.GetFood(temp);
        beverage = csvReader.GetBeverage(temp); 
    }
    
    
    public SQLConnector getSQL()
    {
        return sql;
    }
    
    /**
     * Getter for orders in model
     * @return ArrayList of all orders
     */
    public ArrayList<Order> getOrders() {
        return orders;
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
    
    public ArrayList<MenuItem> getBeverageFromCSV()
    {
        return csvReader.GetBeverage(PATH);
    }
    /**
     * Getter for beverages in model
     * @return ArrayList of all beverage items
     */
    public ArrayList<MenuItem> getBeverage() {
        return beverage;
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
     * Search for the food MenuItem associated with a specific item name
     * @param itemname item name string to search for
     * @return MenuItem which contains specified item name
     */
    public MenuItem getSpecificFood(String itemname)
    {
        for (MenuItem f : food) {
            if (f.getItemName().equals(itemname)) {
                return f;
            }
        }
        //If no MenuItem is found return null
        return null;
    }
    /**
     * Search for the beverage MenuItem associated with a specific item name
     * @param itemname item name string to search for
     * @return MenuItem which contains specified item name
     */
    public MenuItem getSpecificBeverage(String itemname)
    {
        for (MenuItem b : beverage) {
            if (b.getItemName().equals(itemname)) {
                return b;
            }
        }
        //If no MenuItem is found return null
        return null;
    }
    
    /**
     * Searches Food and Beverage ArrayLists for specified item
     * @param itemname item name string to search for
     * @return MenuItem which contains specified item name
     */
    public MenuItem getSpecificItem(String itemname) {
        //Try get MenuItem as food
        MenuItem tmp = getSpecificFood(itemname);
        //If passes, return MenuItem
        if (tmp != null)
            return tmp;
        else
            //Else check beverages and return MenuItem found
            return tmp = getSpecificBeverage(itemname);
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
        //Loop through each item
        for (String s : selectedItems) {
            //Grab MenuItem from string name
            tmp = getSpecificItem(s);
            //Null check
            if (tmp != null)
                //Add to ArrayList
                items.add(tmp);
        }
        //Create new order from obtained inputs
        Order newOrder = new Order(cn, orders.size(), table, items);
        //Add to orders array
        orders.add(newOrder);
        //Return newly created order
        return newOrder;
    }
    
    /**
     * Validates inputs against regex expression
     * @param input string input to check 
     * @param r regex to check against
     * @return boolean if input passes regex test
     */
    public boolean validateInput(String input, String r)
    {
        Pattern regex = Pattern.compile(r);
        if (!regex.matcher(input).matches()) {
               return false;
        }
        return true;
    }
}
