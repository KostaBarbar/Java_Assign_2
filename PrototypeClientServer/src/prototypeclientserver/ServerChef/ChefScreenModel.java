/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver.ServerChef;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import prototypeclientserver.Order;

/**
 * The model for the Chef Screen.
 * This class holds lists of waiting and served orders, and manages the server.
 */
public class ChefScreenModel {
    ArrayList<Order> waitingOrders;
    
    public ChefScreenModel() {
        waitingOrders = new ArrayList<Order>();
    }
    
    public void serveOrder(Order o) {
        o.setServed(true);
    }
}
