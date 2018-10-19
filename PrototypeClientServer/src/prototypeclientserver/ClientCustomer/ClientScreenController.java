/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver.ClientCustomer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import prototypeclientserver.DataModel;

import prototypeclientserver.MenuItem;
import prototypeclientserver.Order;
import prototypeclientserver.components.NutritionalInfoTable;

/**
 * Client screen controller class
 * @author stephenfleming & kosta
 */
public class ClientScreenController {
    private static final String TABLE_NUMBER_REGEX = "([1-8]{1})+";
    private static final String CUSTOMER_NAME_REGEX = "([A-Za-z ]{2,15})+";
    private static final String ADMIN_PASSWORD = "admin";
    
    private boolean readyForRecieveOrder = false;
    
    private ClientScreenView view;
    private ClientScreenModel customerModel;
    
    private DataModel dataModel;
    
    public ClientScreenController() throws SQLException {
        view = new ClientScreenView();
        
        customerModel = new ClientScreenModel();
        dataModel = new DataModel();
        dataModel.resetOrders();
        
        view.setVisible(true);
        
        setViewEventListeners();
    }
    /**
     * Setter for all the event listeners on the form
     */
    private void setViewEventListeners() {
        //Button Event Listeners
        //Enter Data Listener
        view.addEnterDataListener((ActionEvent e) -> {
            //Check if enter data has been pressed once already
            if (!readyForRecieveOrder) {
                //If it hasnt been clicked, check inputs
                if (validateInput())
                {
                    //If they pass, ready Listener for second press
                    readyForRecieveOrder = true;
                    //Fill comboboxes with specified items
                    //Enable comboboxes
                    updateMenuItems();
                }
            } else {
                //Unready listener for next press
                readyForRecieveOrder = false;
                try {
                    //Create new order from current inputs
                    createNewOrder();
                    dataModel.getSQL().getOrders();
                } catch (SQLException ex) {
                    Logger.getLogger(ClientScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Reset the input options
                resetGUIOptions();
                view.setOutputPanel(0);
                view.resetOutputPanel();
            }
        });
        //Display Choice Listener
        view.addDisplayChoiceListener((ActionEvent e) -> {
            //If menu items are enabled
            if (readyForRecieveOrder){
                //Change view to output panel
                //view.changeOutputPanel(1);
                //Update table to show current selection
                view.setOutputPanel(2);
                updateTable();
            } else {
                //Prompt users with input
                view.displayPopup("Please choose the menu items to view Nutrition Information");
            }
        });
        //Display Order Listener
        view.addDisplayOrderListener((ActionEvent e) -> {
            if (dataModel.getOrders().isEmpty())
                return;
            view.setOutputPanel(1);
            view.updateOrderTable(dataModel.getOrders());
        });
        //Reset Listener
        view.addResetListener((ActionEvent e) -> {
            //Code to create a password insertion popup borrowed
            //Source : https://stackoverflow.com/questions/8881213/joptionpane-to-get-password
            JPanel panel = new JPanel();
            JLabel label = new JLabel("Enter staff password:");
            JPasswordField pass = new JPasswordField(10);
            panel.add(label);
            panel.add(pass);
            String[] options = new String[]{"OK", "Cancel"};
            int option = JOptionPane.showOptionDialog(null, panel, "The title",
                                     JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                     null, options, options[1]);
            //End borrowed
            if(option == 0) // pressing OK button
            {
                char[] password = pass.getPassword();
                String temp = new String(password);
                if (!temp.equals(ADMIN_PASSWORD))
                {
                    view.displayPopup("Incorrect password!");
                }
                else
                {
                    //Reset gui options
                    resetGUIOptions();
                    //Reset output panel to empty
                    view.setOutputPanel(0);
                    //Clear anything leftover in the panel
                    view.resetOutputPanel();
                    //Reset the models orders
                    dataModel.resetOrders();
                    //Display popup to indicate successful reset
                    view.displayPopup("Form reset!");
                }   
            }
        });
    }
    /**
     * Updates the display choice table with the current items selected
     */
    public void updateTable()
    {
        //Grab food and beverage MenuItems
        MenuItem food = dataModel.getMenuItem(view.getMenuItems().getCurrentFood());
        MenuItem beverage = dataModel.getMenuItem(view.getMenuItems().getCurrentBeverage());
        
        //Combine items to show total
        MenuItem combined = dataModel.combineMenuItems(food, beverage);
        
        //Update the table in the view with the appropriate values obtained 
        view.updateOutputTable(food, beverage, combined);
    }
    
    /**
     * Updates output panel table with values from a specified order
     * @param o order to be displayed
     */
    public void updateTableWithSelectedOrder(Order o) {
        System.out.println(o);
        
        //Grab associated items
        MenuItem food = o.getFood();
        MenuItem bev = o.getBeverage();
        
        //Get combined item totals
        MenuItem combined = dataModel.combineMenuItems(food, bev);
        
        //Update the table in the view with the appropriate values obtained
        //view.addContentToOutputPanel(new food, bev, combined);
        view.addContentToOutputPanel(new NutritionalInfoTable(food, bev, combined));
    }
    
    /**
     * Resets all input options 
     * Resets:
     *      - Customer Name to ""
     *      - Table Number to ""
     *      - Meal Types to unselected
     *      - Combo-boxes to original inputs
     * Disables:
     *      - Combo-boxes
     */
    private void resetGUIOptions() 
    {
        //Reset Customer Details
        view.getCustomerDetails().resetCustomerOptions();
        //Reset Menu Items
        view.getMenuItems().resetMenuItems();
    }
    
   /**
     * Creates a new order in the waiting state table
     * Grabs values from the current view 
     */
    private void createNewOrder() throws SQLException {
        //Parse table number from cust details
        int table = parseInt(view.getCustomerDetails().getTableNumber());
        //Grab customer name
        String custName = view.getCustomerDetails().getCustomerName();
        
        dataModel.getSQL().addOrder(dataModel.createOrder(custName, table, new String[] {view.getMenuItems().getCurrentFood().trim(), view.getMenuItems().getCurrentBeverage().trim()}));
    }   
    
    /**
     * Checks if inputs to customer details have been correctly inputted
     * Checks against:
     *      - Customer Name
     *      - Table Number
     *      - Menu Type
     * Displays pop-up error messages if inputs fail
     * @return boolean if checks pass
     */
    public boolean validateInput()
    {
        //Check Customer Name against regex
        if (customerModel.validateInput(view.getCustomerDetails().getCustomerName(), CUSTOMER_NAME_REGEX))
            //Check Table Number against regex
            if (customerModel.validateInput(view.getCustomerDetails().getTableNumber(), TABLE_NUMBER_REGEX))
                //Check if a meal type has been selected
                if (view.getCustomerDetails().getMenuType() != null) {
                    //Enable dropdown comboboxes
                    view.getMenuItems().EnableMenuDropdowns();
                    return true;
                } else
                    view.displayPopup("Error: Select Meal Type!");
            else
                view.displayPopup("Error: Table Number Invalid!");
        else
            view.displayPopup("Error: Customer Name Invalid!"); 
        return false;
    }
    
     /**
     * Method to update combo-boxes with their appropriate inputs
     * Grabs the meal type selected from radio buttons
     * Gets a list of food and beverages with that type
     * Updates combo-boxes with the result
     */
    public void updateMenuItems()
    {
        //Get the meal type as string as stored as a string in MenuItems
        String mealType = view.getCustomerDetails().getMenuType();
        //Filter food using stream and creates arraylist of appropriate items
        ArrayList<MenuItem> food = dataModel.getFood().stream().filter(x -> mealType.equals(x.getMealType())).collect(Collectors.toCollection(ArrayList::new));
        //Filter beverages using stream and creates arraylist of appropriate items
        ArrayList<MenuItem> beverage = dataModel.getBeverage().stream().filter(x -> mealType.equals(x.getMealType())).collect(Collectors.toCollection(ArrayList::new));
        //Set comboboxes in view to show updated items
        view.getMenuItems().setFoodItems(dataModel.getStringArray(food));
        view.getMenuItems().setBeverageItems(dataModel.getStringArray(beverage));
    }
   
  /**
     * Listener class for the Display Order button
     * Uses a focus listener to decide what to display
     * Checks if a order was just selected prior to button click
     * Else displays all orders for the table specified in customer details
     * 
     */
    private class DOFListener implements FocusListener
    {
        //Action Event listener, only runs on button press
        @Override
        public void focusGained(FocusEvent e) {
            //Check if orders list is empty
            if (!dataModel.getOrders().isEmpty())
            {
                //Grab a list of all orders
                ArrayList<Order> ol = dataModel.getOrders();
                //Get the component which just lost focus
                Component a = e.getOppositeComponent();
                                
                //Check if component a was a part of the table
                if (a.getClass().equals(JList.class))
                {
                    //Change output panel to order output
                   // view.changeOutputPanel(2);
                    //Cast component to JList ( table entries count as JList )
                    JList temp = (JList)a;
                    //Assign selected order from the table
                    Order ord = (Order) temp.getSelectedValue();
                    //Update panel title
                    //view.changeOutputOrderTitle("Ordered Items For Highlighted Order");
                    //Update panel table
                    //view.updateOutputOrderTable(ord);
                }
                //If component wasn't a table, check if a table number has been inserted
                else if (!view.getCustomerDetails().getTableNumber().equals("")) 
                {
                    //Change output panel to order output
                  //  view.changeOutputPanel(2);
                    //Get ArrayList of orders with the correct
                    ArrayList<Order> ords = getOrders(Integer.parseInt(view.getCustomerDetails().getTableNumber()));
                    //Update panel title
                    //view.changeOutputOrderTitle("Ordered Items at Table" + view.getCustomerDetails().getTableNumber());
                    //Update panel table
                   // view.updateOutputOrderTable(ords);
                }
                //Display error message, asking to choose an order
                else
                {
                    view.displayPopup("Please select a table number or an order to view.");
                }
            }
            else
            {
                //Make focus listener unable to refocus on itself
                //Prevents endless popup loop
              //  view.setDisplayOrderFocus(false);
               // view.displayPopup("There are no orders to display.");
                //Re-enable focusable after display popup has been closed.
                //view.setDisplayOrderFocus(true);
            }
        }
        /**
         * When focus lost do nothing
         * Method needs to be overridden
         * @param e 
         */
        @Override
        public void focusLost(FocusEvent e) {}
        /**
         * Searches through all orders and returns those who are on the specified table number.
         * @param tablenum number to filter by
         * @return ArrayList<Order> with tablenum as their table number
         */        
        private ArrayList<Order> getOrders(int tablenum)
        {
            ArrayList<Order> orders = new ArrayList<>();
            for (Order o : dataModel.getOrders())
            {
                if (o.getTable() == tablenum)
                    orders.add(o);
            }
            return orders;
        }
    }
}
