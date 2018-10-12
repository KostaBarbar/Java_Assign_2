/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver.ClientReceptionist;

import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionEvent;
import prototypeclientserver.Order;
import prototypeclientserver.components.NutritionalInfoTable;

/**
 *
 * @author stephenfleming
 */
public class ReceptionistScreenController {
    private ReceptionistScreenView view;
    private ReceptionistScreenModel model;
    
    public ReceptionistScreenController() {
        view = new ReceptionistScreenView();
        model = new ReceptionistScreenModel();
        
        view.setVisible(true);
        
        
        //Bill Listener
        view.addBillListener((ActionEvent e) -> {
            Order selected = view.getServedList().getSelectedOrder();
            
            //Check if item has been billed yet
            if (selected.isBilled())
                return;
            
            //If not billed, bill the selected order       
            model.billOrder(selected);
            
            //Disable bill button again
            view.setBillButtonEnabled(false);
            
            //Show bill
            //updateTableWithSelectedOrder(view.getOrderStatus().getSelectedServedOrder());
            NutritionalInfoTable table = new NutritionalInfoTable(selected.getFood(), selected.getBeverage(), null);
            view.addContentToOutputPanel(table);
            
            //Change output panel to show the bill
            //view.changeOutputPanel(1);
        });
        
        view.getServedList().addListSelectionEventListener((ListSelectionEvent e) -> {
            if (view.getServedList().getSelectedOrder().isBilled())
                view.setBillButtonEnabled(false);
            else
                view.setBillButtonEnabled(true);
        });
    }
}   
