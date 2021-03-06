/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver.ServerChef;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import prototypeclientserver.DataModel;
import prototypeclientserver.Order;
import prototypeclientserver.onModelUpdate;

/**
 * This is the controller for the Chef Screen. 
 * This screen receives orders from the customer and, once served, passes them to the receptionist for billing.
 * The Chef screen also acts as the server.
 * @author stephenfleming & kosta
 */
public class ChefScreenController {
    ChefScreenView view;
    //ChefScreenModel model;
    
    DataModel dataModel;
    
    public ChefScreenController() {
        view = new ChefScreenView();
        //model = new ChefScreenModel();
        
        dataModel = new DataModel();
        dataModel.filterOrdersForChef();
        
        dataModel.setUpdateInvertal(view, 5000, new onModelUpdate() {
            public void onUpdate() {
                updateViewListWithOrders();
            }
        });
        
        view.setVisible(true);
        
        //Prepare Listener
        view.addPrepareListener((ActionEvent e) -> {
            //Serve the current selected order
            serveSelectedOrder();
        });
        
        updateViewListWithOrders();
    }
    
    /**
     * Method to serve selected order
     * Prompts users with a confirmation dialog to confirm their decision
     */
    public void serveSelectedOrder() {
        //Code to open confirmation panel sourced from Stack Overflow thread - top answer
        //Source: https://stackoverflow.com/questions/8689122/joptionpane-yes-no-options-confirm-dialog-box-issue
        int dialogConfirm = JOptionPane.YES_OPTION;
        //Confirm users want to add item to served list
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to proceed in preparing the order selected?", "Confirmation Dialog", dialogConfirm);
        if (dialogResult == dialogConfirm)
        //End borrowed code
        {
            Order o = view.getWaitingList().getSelectedOrder();
            
            if (o != null) {
                dataModel.serveOrder(o);
                view.getWaitingList().removeSelectedItemFromList();
            }
        }
        
        updateViewListWithOrders();
    }
    /**
     * Function to update the views list of orders
     * Clears the current list on screen and updates it with the current orders from the model
     */
    private void updateViewListWithOrders() {
        int lastSel = view.getWaitingList().getSelectedIndex();
        
        view.getWaitingList().clearList();
        
        ArrayList<Order> ords = dataModel.getOrders();
        ords.forEach(o -> {
            if (!o.isServed() && !o.isBilled())
                view.getWaitingList().addOrderToList(o);
        });
        
        if (lastSel != -1)
            view.getWaitingList().setSelectedIndex(lastSel);
    }
}
