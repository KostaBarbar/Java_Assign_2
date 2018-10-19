/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver;

import java.util.ArrayList;

/**
 *
 * @author kosta
 */
public class Order {
    int orderId;
    String custName;
    int tableNumber;
    
    MenuItem[] items;
    
    boolean served;
    boolean billed;
    
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
    
    public String itemsToString() {
        String itemString = "";
        
        for (int i = 0; i < items.length; i++) {
            itemString += items[i].getItemName();
            
            if (i != items.length-1)
                itemString += ", ";
        }   
        
        return itemString;
    }
    
    public MenuItem getFood() {
        return items[0];
    }
    
    public MenuItem getBeverage() {
        return items[1];
    }
    
    public int getID() {
        return orderId;
    }
    
    
    public void setServed(boolean value) {
        served = value;
    }
    
    public void setBilled(boolean value) {
        billed = value;
    }
    
    public boolean isBilled() {
        return billed;
    }
    
    public boolean isServed() {
        return served;
    }
    
    public String getCustomerName()
    {
        return custName;
    }
    
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
    
    public int getTable()
    {
        return tableNumber;
    }
    
    @Override
    public String toString() {
        String orderString = String.format("Order: %d | Table: %d | ", orderId, tableNumber);
        
        orderString += itemsToString();
        
        return orderString;
    }
}
