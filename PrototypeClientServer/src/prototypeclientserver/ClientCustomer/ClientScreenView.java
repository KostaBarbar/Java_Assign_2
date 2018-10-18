/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver.ClientCustomer;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import prototypeclientserver.MenuItem;
import prototypeclientserver.Order;
import prototypeclientserver.components.NutritionalInfoTable;

/**
 * 
 */
public class ClientScreenView extends javax.swing.JFrame {

    /**
     * Creates new form ClientScreenView
     */
    public ClientScreenView() {
        initComponents();
        
        this.setName("Customer");
        
        TableColumnModel t = outputTable.getColumnModel();
        t.getColumn(0).setPreferredWidth(100);
        t.getColumn(1).setPreferredWidth(500);
        t.getColumn(2).setPreferredWidth(100);
        
        
    }
    
    public void updateOutputTable(MenuItem food, MenuItem beverage, MenuItem combined)
    {
        //Update First Row - Food
        outputTableChoices.setValueAt(food.getItemName(), 0, 0);
        outputTableChoices.setValueAt(food.getEnergy(), 0, 1);
        outputTableChoices.setValueAt(food.getProtein(), 0, 2);
        outputTableChoices.setValueAt(food.getCarbs(), 0, 3);
        outputTableChoices.setValueAt(food.getFat(), 0, 4);
        outputTableChoices.setValueAt(food.getFibre(), 0, 5);
        outputTableChoices.setValueAt(food.getPrice(), 0, 6);
        //Update Second Row - Beverage
        outputTableChoices.setValueAt(beverage.getItemName(), 1, 0);
        outputTableChoices.setValueAt(beverage.getEnergy(), 1, 1);
        outputTableChoices.setValueAt(beverage.getProtein(), 1, 2);
        outputTableChoices.setValueAt(beverage.getCarbs(), 1, 3);
        outputTableChoices.setValueAt(beverage.getFat(), 1, 4);
        outputTableChoices.setValueAt(beverage.getFibre(), 1, 5);
        outputTableChoices.setValueAt(beverage.getPrice(), 1, 6);
        //Update Fourth Row - Total
        outputTableChoices.setValueAt("Total Nutritional Value", 3, 0);
        outputTableChoices.setValueAt(combined.getEnergy(), 3, 1);
        outputTableChoices.setValueAt(combined.getProtein(), 3, 2);
        outputTableChoices.setValueAt(combined.getCarbs(), 3, 3);
        outputTableChoices.setValueAt(combined.getFat(), 3, 4);
        outputTableChoices.setValueAt(combined.getFibre(), 3, 5);
        outputTableChoices.setValueAt(combined.getPrice(), 3, 6);
    }
    
    public void setOutputPanel(int index)
    {
        JPanel[] arr = {outputPanelEmpty, outputPanelOrders, outputPanelChoices};
        for (int i = 0; i < arr.length; i++)
        {
            if (i == index)
                arr[i].setVisible(true);
            else
                arr[i].setVisible(false);
        }
    }
    
    public void updateOrderTable(ArrayList<Order> ords)
    {
        //Code sourced from stackoverflow to clear table values
        //Source : https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
        //Removes all rows from the table
        DefaultTableModel dm = (DefaultTableModel)outputTable.getModel();
        int rc = dm.getRowCount();
        for (int i = rc - 1; i >= 0; i--)
            dm.removeRow(i);
        //End borrowed code
        //Create new rows for the orders to insert
        for (int i = 0; i < ords.size(); i++)
        {
            String n = ords.get(i).getCustomerName();
            String items = ords.get(i).getItems();
            int p = ords.get(i).getBeverage().getPrice() + ords.get(i).getFood().getPrice();
            String price = Integer.toString(p);
            dm.addRow(new String[]{n, items, price});
        }
    }
    /**
     * Adds an ActionListener for Enter Data button
     * @param l ActionListener to add
     */    
    public void addEnterDataListener(ActionListener l)
    {
        buttonEnterData.addActionListener(l);
    }
    
    /**
     * Adds an ActionListener for Display Choice button
     * @param l ActionListener to add
     */    
    public void addDisplayChoiceListener(ActionListener l)
    {
        buttonDisplayChoices.addActionListener(l);
    }
    /**
     * Adds a FocusListener for Display Order button
     * @param f FocusListener to add
     */    
    public void addDisplayOrderListener(FocusListener f)
    {
        buttonDisplayOrder.addFocusListener(f);
    }
    
    public void addDisplayOrderListener(ActionListener l)
    {
        buttonDisplayOrder.addActionListener(l);
    }
    
    public void addResetListener(ActionListener l)
    {
        buttonReset.addActionListener(l);
    }
    
    public void addContentToOutputPanel(JPanel content) {
        outputPanelEmpty.removeAll();
        
        Panel p2 = new Panel();
        p2.setLayout(new BorderLayout());
        p2.add(content);
        
        outputPanelEmpty.add(p2);
        
        outputPanelEmpty.updateUI();
        pack();
    }
    
    public void resetOutputPanel()
    {
        outputPanelEmpty.removeAll();
    }
    
    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }
    
    public MenuItems getMenuItems() {
        return menuItems;
    }
    
     /**
     * Displays a pop-up message to the JFrame
     * @param msg String message to display
     */
    public void displayPopup(String msg) {
        //src: https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
        JOptionPane.showMessageDialog(null, msg);
        //end src
    }
    
    /**
     * Updates the Output Order Table with new row
     * Clears the previous configuration of rows
     * Then adds the new specified row
     * @param ord Order to add
     */
    public void updateOutputOrderTable(Order order)
    {
        NutritionalInfoTable table = new NutritionalInfoTable(order.getFood(), order.getBeverage(), order.getFood());
        
        outputPanelEmpty.add(table);
        
        this.pack();
        /*
        //Code sourced from stackoverflow to clear table values
        //Source : https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
        //Remove all rows from the table
        DefaultTableModel dm = (DefaultTableModel)outputOrderTable.getModel();
        int rc = dm.getRowCount();
        for (int i = rc - 1; i >= 0; i--)
            dm.removeRow(i);
        //End borrowed code
        //Add specified row
        String n = ord.getCustomerName();
        String items = ord.getItems();
        dm.addRow(new String[]{n, items});
        */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonEnterData = new javax.swing.JButton();
        buttonDisplayChoices = new javax.swing.JButton();
        buttonDisplayOrder = new javax.swing.JButton();
        panelContent = new javax.swing.JPanel();
        outputPanelEmpty = new javax.swing.JPanel();
        outputPanelOrders = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        outputTable = new javax.swing.JTable();
        outputPanelChoices = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        outputTableChoices = new javax.swing.JTable();
        customerDetails = new prototypeclientserver.ClientCustomer.CustomerDetails();
        menuItems = new prototypeclientserver.ClientCustomer.MenuItems();
        buttonReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonEnterData.setText("Enter Data");

        buttonDisplayChoices.setText("Display Choices");

        buttonDisplayOrder.setText("Display Orders");

        panelContent.setLayout(new java.awt.CardLayout());

        outputPanelEmpty.setPreferredSize(new java.awt.Dimension(500, 300));
        outputPanelEmpty.setLayout(new java.awt.BorderLayout());
        panelContent.add(outputPanelEmpty, "card2");

        outputTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer", "Contents", "Price"
            }
        ));
        outputTable.setEnabled(false);
        outputTable.setFocusable(false);
        jScrollPane3.setViewportView(outputTable);

        javax.swing.GroupLayout outputPanelOrdersLayout = new javax.swing.GroupLayout(outputPanelOrders);
        outputPanelOrders.setLayout(outputPanelOrdersLayout);
        outputPanelOrdersLayout.setHorizontalGroup(
            outputPanelOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 613, Short.MAX_VALUE)
            .addGroup(outputPanelOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(outputPanelOrdersLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        outputPanelOrdersLayout.setVerticalGroup(
            outputPanelOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 141, Short.MAX_VALUE)
            .addGroup(outputPanelOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(outputPanelOrdersLayout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(20, Short.MAX_VALUE)))
        );

        panelContent.add(outputPanelOrders, "card3");

        outputTableChoices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Item Name", "Energy", "Protein", "Carbohydrate", "Total Fat", "Fibre", "Price"
            }
        ));
        outputTableChoices.setEnabled(false);
        outputTableChoices.setFocusable(false);
        jScrollPane2.setViewportView(outputTableChoices);

        javax.swing.GroupLayout outputPanelChoicesLayout = new javax.swing.GroupLayout(outputPanelChoices);
        outputPanelChoices.setLayout(outputPanelChoicesLayout);
        outputPanelChoicesLayout.setHorizontalGroup(
            outputPanelChoicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelChoicesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                .addContainerGap())
        );
        outputPanelChoicesLayout.setVerticalGroup(
            outputPanelChoicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelChoicesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        panelContent.add(outputPanelChoices, "card4");

        buttonReset.setText("Reset");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonEnterData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonDisplayChoices)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonDisplayOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonReset)
                        .addGap(17, 17, 17))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelContent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customerDetails, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(menuItems, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(6, 6, 6))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customerDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuItems, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelContent, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonEnterData)
                    .addComponent(buttonDisplayChoices)
                    .addComponent(buttonDisplayOrder)
                    .addComponent(buttonReset))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientScreenView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonDisplayChoices;
    private javax.swing.JButton buttonDisplayOrder;
    private javax.swing.JButton buttonEnterData;
    private javax.swing.JButton buttonReset;
    private prototypeclientserver.ClientCustomer.CustomerDetails customerDetails;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private prototypeclientserver.ClientCustomer.MenuItems menuItems;
    private javax.swing.JPanel outputPanelChoices;
    private javax.swing.JPanel outputPanelEmpty;
    private javax.swing.JPanel outputPanelOrders;
    private javax.swing.JTable outputTable;
    private javax.swing.JTable outputTableChoices;
    private javax.swing.JPanel panelContent;
    // End of variables declaration//GEN-END:variables
}
