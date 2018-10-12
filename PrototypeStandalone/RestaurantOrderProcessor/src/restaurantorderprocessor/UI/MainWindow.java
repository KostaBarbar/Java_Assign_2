/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantorderprocessor.UI;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 * Main JFrame class
 * @author stephenfleming & kosta 
 */
public class MainWindow extends javax.swing.JFrame {
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        //Disable buttons
        buttonPrepare.setEnabled(false);
        buttonBill.setEnabled(false);
    }
    /**
     * Getter for Customer Details part of the form
     * @return CustomerDetails component
     */
    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }
    /**
     * Getter for Menu Items part of the form
     * @return MenuItems component
     */
    public MenuItems getMenuItems() {
        return menuItems;
    }
    /**
     * Getter for Order Status part of the form
     * @return OrderStatus component
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
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
    /**
     * Adds an ActionListener for Clear Display button
     * @param l ActionListener to add
     */    
    public void addClearDisplayListener(ActionListener l)
    {
        buttonClearDisplay.addActionListener(l);
    }
    /**
     * Adds an ActionListener for Quit button
     * @param l ActionListener to add
     */    
    public void addQuitListener(ActionListener l)
    {
        buttonQuit.addActionListener(l);
    }
    /**
     * Method to make Display Button focusable
     * Used to prevent Focus Listener bug
     * @param b boolean - set focusable to this
     */
    public void setDisplayOrderFocus(boolean b)
    {
        buttonDisplayOrder.setFocusable(b);
    }
    /**
     * Adds an ActionListener for Prepare button
     * @param l ActionListener to add
     */
    public void addPrepareListener(ActionListener l) {
        buttonPrepare.addActionListener(l);
    } 
    /**
     * Adds an ActionListener for Bill Button
     * @param l ActionListener to add
     */
    public void addBillListener(ActionListener l) {
        buttonBill.addActionListener(l);
    }
    /**
     * Change the output card layout to the appropriate panel
     * Panels Index:
     *      - 0 : Empty Output Panel
     *      - 1 : Output Table Panel
     *      - 2 : Output Order Panel
     * @param index integer to change panel index to
     */
    public void changeOutputPanel(int index)
    {
        //Create panel array
        JPanel[] arr = {outputEmptyPanel, outputTablePanel, outputOrderPanel};
        //Cycle through array and display the correct panel
        //Don't show the panels which aren't wanted
        for (int i = 0; i < arr.length; i++)
        {
            if (i == index)
                arr[i].setVisible(true);
            else
                arr[i].setVisible(false);
        }
    }
    /**
     * Changes the title of the Output Order panel
     * @param s string to change title to
     */
    public void changeOutputOrderTitle(String s)
    {
        outputOrderTitle.setText(s);
    }
    /**
     * Updates the Output Order Table with new rows
     * Clears the previous configuration of rows
     * Then adds the new array of rows
     * @param ords ArrayList to add
     */
    public void updateOutputOrderTable(ArrayList<Order> ords)
    {
        //Code sourced from stackoverflow to clear table values
        //Source : https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
        //Removes all rows from the table
        DefaultTableModel dm = (DefaultTableModel)outputOrderTable.getModel();
        int rc = dm.getRowCount();
        for (int i = rc - 1; i >= 0; i--)
            dm.removeRow(i);
        //End borrowed code
        //Create new rows for the orders to insert
        for (int i = 0; i < ords.size(); i++)
        {
            String n = ords.get(i).getCustomerName();
            String items = ords.get(i).getItems();
            dm.addRow(new String[]{n, items});
        }
    }
    /**
     * Updates the Output Order Table with new row
     * Clears the previous configuration of rows
     * Then adds the new specified row
     * @param ord Order to add
     */
    public void updateOutputOrderTable(Order ord)
    {
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
        
    }
    /**
     * Updates the Output Table panels table
     * Sets the food, beverage and combined rows
     * @param food MenuItem to add to food row
     * @param beverage MenuItem to add to beverage row
     * @param combined MenuItem to add to combined row
     */
    public void updateOutputTable(MenuItem food, MenuItem beverage, MenuItem combined)
    {
        //Update First Row - Food
        outputTable.setValueAt(food.getItemName(), 0, 0);
        outputTable.setValueAt(food.getEnergy(), 0, 1);
        outputTable.setValueAt(food.getProtein(), 0, 2);
        outputTable.setValueAt(food.getCarbs(), 0, 3);
        outputTable.setValueAt(food.getFat(), 0, 4);
        outputTable.setValueAt(food.getFibre(), 0, 5);
        outputTable.setValueAt(food.getPrice(), 0, 6);
        //Update Second Row - Beverage
        outputTable.setValueAt(beverage.getItemName(), 1, 0);
        outputTable.setValueAt(beverage.getEnergy(), 1, 1);
        outputTable.setValueAt(beverage.getProtein(), 1, 2);
        outputTable.setValueAt(beverage.getCarbs(), 1, 3);
        outputTable.setValueAt(beverage.getFat(), 1, 4);
        outputTable.setValueAt(beverage.getFibre(), 1, 5);
        outputTable.setValueAt(beverage.getPrice(), 1, 6);
        //Update Fourth Row - Total
        outputTable.setValueAt("Total Nutritional Value", 3, 0);
        outputTable.setValueAt(combined.getEnergy(), 3, 1);
        outputTable.setValueAt(combined.getProtein(), 3, 2);
        outputTable.setValueAt(combined.getCarbs(), 3, 3);
        outputTable.setValueAt(combined.getFat(), 3, 4);
        outputTable.setValueAt(combined.getFibre(), 3, 5);
        outputTable.setValueAt(combined.getPrice(), 3, 6);
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
     * Method to enable/disable Prepare button
     * @param value boolean to set Enabled state of button
     */
    public void setPrepareButtonEnabled(boolean value) {
        buttonPrepare.setEnabled(value);
    }
    /**
     * Method to enable/disable Bill button
     * @param value boolean to set Enabled state of button
     */
    public void setBillButtonEnabled(boolean value) {
        buttonBill.setEnabled(value);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customerDetails = new restaurantorderprocessor.UI.CustomerDetails();
        menuItems = new restaurantorderprocessor.UI.MenuItems();
        orderStatus = new restaurantorderprocessor.UI.OrderStatus();
        panelOutputContainer = new javax.swing.JPanel();
        outputEmptyPanel = new javax.swing.JPanel();
        outputTablePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        outputTable = new javax.swing.JTable();
        outputOrderPanel = new javax.swing.JPanel();
        outputOrderTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputOrderTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        buttonEnterData = new javax.swing.JButton();
        buttonDisplayChoices = new javax.swing.JButton();
        buttonDisplayOrder = new javax.swing.JButton();
        buttonPrepare = new javax.swing.JButton();
        buttonBill = new javax.swing.JButton();
        buttonClearDisplay = new javax.swing.JButton();
        buttonQuit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Restaurant Order");

        panelOutputContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelOutputContainer.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout outputEmptyPanelLayout = new javax.swing.GroupLayout(outputEmptyPanel);
        outputEmptyPanel.setLayout(outputEmptyPanelLayout);
        outputEmptyPanelLayout.setHorizontalGroup(
            outputEmptyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 928, Short.MAX_VALUE)
        );
        outputEmptyPanelLayout.setVerticalGroup(
            outputEmptyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );

        panelOutputContainer.add(outputEmptyPanel, "card2");

        outputTable.setModel(new javax.swing.table.DefaultTableModel(
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
        outputTable.setEnabled(false);
        outputTable.setFocusable(false);
        jScrollPane2.setViewportView(outputTable);

        javax.swing.GroupLayout outputTablePanelLayout = new javax.swing.GroupLayout(outputTablePanel);
        outputTablePanel.setLayout(outputTablePanelLayout);
        outputTablePanelLayout.setHorizontalGroup(
            outputTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
                .addContainerGap())
        );
        outputTablePanelLayout.setVerticalGroup(
            outputTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputTablePanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        panelOutputContainer.add(outputTablePanel, "card3");

        outputOrderTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outputOrderTitle.setText("outputOrderTitle");

        outputOrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Customer Name", "Ordered Items"
            }
        ));
        jScrollPane1.setViewportView(outputOrderTable);
        if (outputOrderTable.getColumnModel().getColumnCount() > 0) {
            outputOrderTable.getColumnModel().getColumn(0).setMinWidth(300);
            outputOrderTable.getColumnModel().getColumn(0).setMaxWidth(300);
        }

        javax.swing.GroupLayout outputOrderPanelLayout = new javax.swing.GroupLayout(outputOrderPanel);
        outputOrderPanel.setLayout(outputOrderPanelLayout);
        outputOrderPanelLayout.setHorizontalGroup(
            outputOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputOrderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(outputOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputOrderTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE))
                .addContainerGap())
        );
        outputOrderPanelLayout.setVerticalGroup(
            outputOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputOrderPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(outputOrderTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelOutputContainer.add(outputOrderPanel, "card4");

        buttonEnterData.setText("Enter Data");
        buttonEnterData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEnterDataActionPerformed(evt);
            }
        });

        buttonDisplayChoices.setText("Display Choices");

        buttonDisplayOrder.setText("Display Order");

        buttonPrepare.setText("Prepare");
        buttonPrepare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPrepareActionPerformed(evt);
            }
        });

        buttonBill.setText("Bill");

        buttonClearDisplay.setText("Clear Display");

        buttonQuit.setText("Quit");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(buttonEnterData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonDisplayChoices)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonDisplayOrder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonPrepare)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonBill)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonClearDisplay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonQuit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonEnterData)
                    .addComponent(buttonDisplayChoices)
                    .addComponent(buttonDisplayOrder)
                    .addComponent(buttonPrepare)
                    .addComponent(buttonBill)
                    .addComponent(buttonClearDisplay)
                    .addComponent(buttonQuit))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customerDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(orderStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOutputContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(customerDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOutputContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonEnterDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEnterDataActionPerformed
        
    }//GEN-LAST:event_buttonEnterDataActionPerformed

    private void buttonPrepareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPrepareActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonPrepareActionPerformed

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
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBill;
    private javax.swing.JButton buttonClearDisplay;
    private javax.swing.JButton buttonDisplayChoices;
    private javax.swing.JButton buttonDisplayOrder;
    private javax.swing.JButton buttonEnterData;
    private javax.swing.JButton buttonPrepare;
    private javax.swing.JButton buttonQuit;
    private restaurantorderprocessor.UI.CustomerDetails customerDetails;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private restaurantorderprocessor.UI.MenuItems menuItems;
    private restaurantorderprocessor.UI.OrderStatus orderStatus;
    private javax.swing.JPanel outputEmptyPanel;
    private javax.swing.JPanel outputOrderPanel;
    private javax.swing.JTable outputOrderTable;
    private javax.swing.JLabel outputOrderTitle;
    private javax.swing.JTable outputTable;
    private javax.swing.JPanel outputTablePanel;
    private javax.swing.JPanel panelOutputContainer;
    // End of variables declaration//GEN-END:variables
}
