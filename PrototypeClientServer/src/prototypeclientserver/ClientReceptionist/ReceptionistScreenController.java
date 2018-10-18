/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver.ClientReceptionist;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import prototypeclientserver.DataModel;
import prototypeclientserver.MenuItem;
import prototypeclientserver.Order;
import prototypeclientserver.components.NutritionalInfoTable;
import prototypeclientserver.onModelUpdate;

/**
 *
 * @author stephenfleming
 */
public class ReceptionistScreenController {
    private ReceptionistScreenView view;
    private ReceptionistScreenModel model;
    
    private DataModel dataModel;
    
    public ReceptionistScreenController() {
        view = new ReceptionistScreenView();
        model = new ReceptionistScreenModel();
        
        dataModel = new DataModel();
        dataModel.setUpdateInvertal(view, 5000, new onModelUpdate() {
            public void onUpdate() {
                    updateViewListWithOrders();
            }
        });
        
        updateViewListWithOrders();
        
        view.setVisible(true);
        
        //Bill Listener
        view.addBillListener((ActionEvent e) -> {
            Order selected = view.getServedList().getSelectedOrder();
            
            //Check if item has been billed yet
            if (selected == null || selected.isBilled())
                return;
            
            //If not billed, bill the selected order       
            model.billOrder(selected);
            
            //Disable bill button again
            view.setBillButtonEnabled(false);
            
            MenuItem combined = dataModel.combineMenuItems(selected.getFood(), selected.getBeverage());
            
            //Show bill
            //updateTableWithSelectedOrder(view.getOrderStatus().getSelectedServedOrder());
            NutritionalInfoTable table = new NutritionalInfoTable(selected.getFood(), selected.getBeverage(), combined);
            view.addContentToOutputPanel(table);
            
            Order o = view.getServedList().getSelectedOrder();
            
            if (o != null) {
                dataModel.billOrder(o);
                view.getServedList().removeSelectedItemFromList();
                
                //updateViewListWithOrders();
            }
            
            //Change output panel to show the bill
            //view.changeOutputPanel(1);
        });
        
        view.getServedList().addListSelectionEventListener((ListSelectionEvent e) -> {
            if (view.getServedList().getSelectedOrder() == null)
                return;
            
            if (view.getServedList().getSelectedOrder().isBilled())
                view.setBillButtonEnabled(false);
            else
                view.setBillButtonEnabled(true);
        });
    }
    
    private void updateViewListWithOrders() {
        int lastSelected = view.getServedList().getSelectedIndex();
        
        view.getServedList().clearList();
        
        ArrayList<Order> ords = dataModel.getOrders();
        ords.forEach(o -> {
            if (o.isServed() && !o.isBilled())
                view.getServedList().addOrderToList(o);
        });
        
        if (lastSelected != -1)
            view.getServedList().setSelectedIndex(lastSelected);
    }
}   
