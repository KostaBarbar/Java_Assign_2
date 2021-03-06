/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantorderprocessor.UI;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;

/**
 * Form Component for Order Status
 * @author stephenfleming & kosta
 */
public class OrderStatus extends javax.swing.JPanel {
    
    //data models for the lists
    private DefaultListModel<Order> waitingListModel;
    private DefaultListModel<Order> servedListModel;

    /**
     * Creates new form OrderStatus
     */
    public OrderStatus() {
        initComponents();
        //Clears lists of all entries
        clearLists();
        //Init models
        waitingListModel = (DefaultListModel)listWaiting.getModel();
        servedListModel = (DefaultListModel)listServed.getModel();
    }
    /**
     * Takes the index of an item, moves it to the served list and removes it from the waiting list
     * @param index integer index of item to move
     */
    public void moveItemToServedList(int index) {    
        servedListModel.add(servedListModel.size(), waitingListModel.get(index));
        waitingListModel.remove(listWaiting.getSelectedIndex());
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
            waitingListModel.get(listWaiting.getSelectedIndex()).setServed(true);
            moveItemToServedList(listWaiting.getSelectedIndex());
        }

    }
    
    /**
     * Sets billed boolean value of currently selected index of served JTable to true
     */
    public boolean billSelectedOrder() {
        //Code to open confirmation panel sourced from Stack Overflow thread - top answer
        //Source: https://stackoverflow.com/questions/8689122/joptionpane-yes-no-options-confirm-dialog-box-issue
        int dialogConfirm = JOptionPane.YES_OPTION;
        //Confirm users want to add item to served list
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to bill the selected order?", "Confirmation Dialog", dialogConfirm);
        if (dialogResult == dialogConfirm)
        //End borrowed code
        {
            servedListModel.get(listServed.getSelectedIndex()).setBilled(true);
            return true;
        }
        
        return false;
    }
    
    /**
     * Delete the currently selected order in the bill list
     */
    public void removeSelectedBillOrder() {
        servedListModel.remove(listServed.getSelectedIndex());
    }
    /**
     * Adds an order to the waiting JTable
     * @param order order to insert
     */
    public void addOrderToWaitingList(Order order) {
        waitingListModel.add(waitingListModel.size(), order);
    }
    /**
     * Adds ListSelectionListener to the waiting list
     * @param evt ListSelectionListener to add
     */
    public void addListSelectionEventListenerToWaiting(ListSelectionListener evt) {
        listWaiting.addListSelectionListener(evt);    
    }
    /**
     * Adds ListSelectionListener to the served list
     * @param evt ListSelectionListener to add
     */
    public void addListSelectionEventListenerToServed(ListSelectionListener evt) {
        listServed.addListSelectionListener(evt);    
    }
    /**
     * Getter for number of orders in waiting list
     * @return number of orders in waiting list integer
     */
    public int getNumberOfWaitingItems() {
        return waitingListModel.size();
    }
    /**
     * Boolean check to see if an item is selected 
     * @return true if an item is selected, else false
     */
    public boolean isWaitingItemSelected() {
        return (listWaiting.getSelectedIndex() != -1);
    }
    /**
     * Boolean check to see if selected served order has been billed
     * @return true if selected item has been billed, else false
     */
    public boolean hasSelectedServedOrderBeenBilled() {
        return servedListModel.get(listServed.getSelectedIndex()).billed;
    }
    /**
     * Getter to return selected order from served list
     * @return order selected from served list
     */
    public Order getSelectedServedOrder() {
        return servedListModel.get(listServed.getSelectedIndex());
    }
    /**
     * Clears list of all rows and their contents
     * @param list JList to clear
     */
    private void clearList(JList<String> list) {
        DefaultListModel m = new DefaultListModel();
        m.clear();
        list.setModel(m);
    }
    /**
     * Method to clear contents of both lists ( served and waiting )
     */
    private void clearLists() {
        clearList(listServed);
        clearList(listWaiting);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listWaiting = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listServed = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        listWaiting.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listWaiting);

        jLabel1.setText("Orders with Waiting State");

        listServed.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listServed);

        jLabel2.setText("Orders with Served State");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listServed;
    private javax.swing.JList<String> listWaiting;
    // End of variables declaration//GEN-END:variables
}
