/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver;

import java.util.ArrayList;

/**
 * Order class to store order data
 * @author stephenfleming & kosta  
 */
public class Order {
    int orderId;
    String custName;
    int tableNumber;
    MenuItem[] items;
    boolean served;
    boolean billed;
    /**
     * Constructor for Order
     * @param cn customer name string
     * @param oId order id integer
     * @param table table number integer
     * @param menuItems ArrayList of MenuItems for the order
     */
    public Order(String cn, int oId, int table, ArrayList<MenuItem> menuItems) {
        orderId = oId;
        tableNumber = table;
        custName = cn;
        served = false;
        billed = false;
        items = new MenuItem[menuItems.size()];
        for (int i = 0; i < menuItems.size(); i++) {
         items[i] = menuItems.get(i);
        }
    }
    /**
     * Converts items array to a string containing all items names
     * @return string containing each item name
     */
    public String itemsToString() {
        String itemString = "";
        for (int i = 0; i < items.length; i++) {
            itemString += items[i].getItemName();
            
            if (i != items.length-1)
                itemString += ", ";
        }
        return itemString;
    }
    /**
     * Getter for the food MenuItem
     * @return food MenuItem
     */
    public MenuItem getFood() {
        return items[0];
    }
    /**
     * Getter for the beverage MenuItem
     * @return beverage MenuItem
     */
    public MenuItem getBeverage() {
        return items[1];
    }
    /**
     * Getter for the order id
     * @return orderID int
     */
    public int getID() {
        return orderId;
    }
    /**
     * Setter for served boolean
     * @param value boolean set value to this
     */
    public void setServed(boolean value) {
        served = value;
    }
    /**
     * Setter for billed boolean
     * @param value boolean set value to this
     */
    public void setBilled(boolean value) {
        billed = value;
    }
    /**
     * Getter for the billed state
     * @return billed boolean
     */
    public boolean isBilled() {
        return billed;
    }
    /**
     * Getter for the served state
     * @return served boolean
     */
    public boolean isServed() {
        return served;
    }
    /**
     * Getter for customer name
     * @return customer name string
     */
    public String getCustomerName()
    {
        return custName;
    }
    /**
     * Getter for the items in items array
     * Concatenates item names of all items in items array
     * @return concatenated string of item names
     */
    public String getItems()
    {
        String res = "";
        for (int i = 0; i < items.length; i++) {
            res += items[i].getItemName();
            if (i != items.length-1)
                res += ", ";
        }
        return res;
    }
    /**
     * Getter for table number
     * @return table number integer
     */
    public int getTable()
    {
        return tableNumber;
    }
    /**
     * Overridden toString method
     * Concatenates string using:
     *      - Order: orderID
     *      - Table: tableNumber
     *      - orderString: itemsToString()
     * @return concatenated string 
     */
    @Override
    public String toString() {
        String orderString = String.format("Order: %d | Table: %d | ", orderId, tableNumber);
        orderString += itemsToString();
        return orderString;
    }
}
