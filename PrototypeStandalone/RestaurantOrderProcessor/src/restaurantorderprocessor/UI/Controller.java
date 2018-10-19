/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantorderprocessor.UI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;

/**
 * Controller to facilitate interactions between the model and the view
 *
 * @author stephenfleming & kosta
 */
public class Controller {

    private Model theModel;
    private MainWindow theView;

    private static final String TABLE_NUMBER_REGEX = "([1-8]{1})+";
    private static final String CUSTOMER_NAME_REGEX = "([A-Za-z ]{2,15})+";

    private boolean readyForRecieveOrder = false;

    public Controller(Model m, MainWindow v) {
        theModel = m;
        theView = v;
        //Button Event Listeners
        //Enter Data Listener
        theView.addEnterDataListener((ActionEvent e) -> {
            theView.setPrepareButtonEnabled(false);
            //Check if enter data has been pressed once already
            if (!readyForRecieveOrder) {
                //If it hasnt been clicked, check inputs
                if (validateInput()) {
                    //If they pass, ready Listener for second press
                    readyForRecieveOrder = true;
                    //Fill comboboxes with specified items
                    //Enable comboboxes
                    updateMenuItems();
                }
            } else {
                //Unready listener for next press
                readyForRecieveOrder = false;
                //Create new order from current inputs
                createNewOrder();
                //Reset the input options
                resetGUIOptions();
            }
        });
        //Display Choice Listener
        theView.addDisplayChoiceListener((ActionEvent e) -> {
            //If menu items are enabled
            if (readyForRecieveOrder) {
                //Change view to output panel
                theView.changeOutputPanel(1);
                //Update table to show current selection
                updateTable();
            } else {
                //Prompt users with input
                theView.displayPopup("Please choose the menu items to view Nutrition Information");
            }
        });
        //Display Order Listener
        theView.addDisplayOrderListener(new DOFListener());
        //Clear Display Listener
        theView.addClearDisplayListener((ActionEvent e) -> {
            //Reset input options
            resetGUIOptions();
            //Set output panel to empty panel
            theView.changeOutputPanel(0);
        });
        //Quit Listener
        theView.addQuitListener((ActionEvent e) -> {
            //On press, close program
            theView.dispose();
        });
        //Prepare Listener
        theView.addPrepareListener((ActionEvent e) -> {
            //Serve the current selected order
            theView.getOrderStatus().serveSelectedOrder();
        });
        //Bill Listener
        theView.addBillListener((ActionEvent e) -> {
            //Check if item has been billed yet
            if (theView.getOrderStatus().hasSelectedServedOrderBeenBilled()) {
                return;
            }
            //If not billed, bill the selected order
            boolean wasRem = theView.getOrderStatus().billSelectedOrder();
            
            if (!wasRem)
                return;
            
            //Disable bill button again
            theView.setBillButtonEnabled(false);
            //Show bill
            updateTableWithSelectedOrder(theView.getOrderStatus().getSelectedServedOrder());
            //Change output panel to show the bill
            theView.changeOutputPanel(1);
            
            theView.getOrderStatus().removeSelectedBillOrder();
        });
        //enable prepare button when item selected
        theView.getOrderStatus().addListSelectionEventListenerToWaiting((ListSelectionEvent e) -> {
            if (theView.getOrderStatus().isWaitingItemSelected()) {
                theView.setPrepareButtonEnabled(true);
            } else {
                theView.setPrepareButtonEnabled(false);
            }
        });
        //enable bill button when item selected
        theView.getOrderStatus().addListSelectionEventListenerToServed((ListSelectionEvent e) -> {
            if (theView.getOrderStatus().hasSelectedServedOrderBeenBilled()) {
                theView.setBillButtonEnabled(false);
            } else {
                theView.setBillButtonEnabled(true);
            }
        });
    }

    /**
     * Checks if inputs to customer details have been correctly inputted Checks
     * against: - Customer Name - Table Number - Menu Type Displays pop-up error
     * messages if inputs fail
     *
     * @return boolean if checks pass
     */
    public boolean validateInput() {
        //Check Customer Name against regex
        if (theModel.validateInput(theView.getCustomerDetails().getCustomerName(), CUSTOMER_NAME_REGEX)) //Check Table Number against regex
        {
            if (theModel.validateInput(theView.getCustomerDetails().getTableNumber(), TABLE_NUMBER_REGEX)) //Check if a meal type has been selected
            {
                if (theView.getCustomerDetails().getMenuType() != null) {
                    //Enable dropdown comboboxes
                    theView.getMenuItems().EnableMenuDropdowns();
                    return true;
                } else {
                    theView.displayPopup("Error: Select Meal Type!");
                }
            } else {
                theView.displayPopup("Error: Table Number Invalid!");
            }
        } else {
            theView.displayPopup("Error: Customer Name Invalid!");
        }
        return false;
    }

    /**
     * Method to update combo-boxes with their appropriate inputs Grabs the meal
     * type selected from radio buttons Gets a list of food and beverages with
     * that type Updates combo-boxes with the result
     */
    public void updateMenuItems() {
        //Get the meal type as string as stored as a string in MenuItems
        String mealType = theView.getCustomerDetails().getMenuType();
        //Filter food using stream and creates arraylist of appropriate items
        ArrayList<MenuItem> food = theModel.getFood().stream().filter(x -> mealType.equals(x.getMealType())).collect(Collectors.toCollection(ArrayList::new));
        //Filter beverages using stream and creates arraylist of appropriate items
        ArrayList<MenuItem> beverage = theModel.getBeverage().stream().filter(x -> mealType.equals(x.getMealType())).collect(Collectors.toCollection(ArrayList::new));
        //Set comboboxes in view to show updated items
        theView.getMenuItems().setFoodItems(theModel.getStringArray(food));
        theView.getMenuItems().setBeverageItems(theModel.getStringArray(beverage));
    }

    /**
     * Updates output panel table with current selection of combo-box values
     */
    public void updateTable() {
        //Grab food and beverage MenuItems
        MenuItem food = theModel.getSpecificFood(theView.getMenuItems().getCurrentFood());
        MenuItem beverage = theModel.getSpecificBeverage(theView.getMenuItems().getCurrentBeverage());
        //Combine items to show total
        MenuItem combined = theModel.combineMenuItems(food, beverage);
        //Update the table in the view with the appropriate values obtained 
        theView.updateOutputTable(food, beverage, combined);
    }

    /**
     * Updates output panel table with values from a specified order
     *
     * @param o order to be displayed
     */
    public void updateTableWithSelectedOrder(Order o) {
        //Grab associated items
        MenuItem food = o.getFood();
        MenuItem bev = o.getBeverage();
        //Get combined item totals
        MenuItem combined = theModel.combineMenuItems(food, bev);
        //Update the table in the view with the appropriate values obtained
        theView.updateOutputTable(food, bev, combined);
    }

    /**
     * Creates a new order in the waiting state table Grabs values from the
     * current view
     */
    private void createNewOrder() {
        //Parse table number from cust details
        int table = parseInt(theView.getCustomerDetails().getTableNumber());
        //Grab customer name
        String custName = theView.getCustomerDetails().getCustomerName();
        //Create new order with current values and assign to waiting list
        theView.getOrderStatus().addOrderToWaitingList(
                theModel.createOrder(custName, table,
                        new String[]{theView.getMenuItems().getCurrentFood().trim(), theView.getMenuItems().getCurrentBeverage().trim()}
                )
        );
    }

    /**
     * Resets all input options Resets: - Customer Name to "" - Table Number to
     * "" - Meal Types to unselected - Combo-boxes to original inputs Disables:
     * - Combo-boxes
     */
    private void resetGUIOptions() {
        //Reset Customer Details
        theView.getCustomerDetails().resetCustomerOptions();
        //Reset Menu Items
        theView.getMenuItems().resetMenuItems();
    }

    /**
     * Searches through all orders and returns those who are on the specified
     * table number.
     *
     * @param tablenum number to filter by
     * @return ArrayList<Order> with tablenum as their table number
     */
    private ArrayList<Order> getOrders(int tablenum) {
        ArrayList<Order> orders = new ArrayList<>();
        for (Order o : theModel.getOrders()) {
            if (o.getTable() == tablenum) {
                orders.add(o);
            }
        }
        return orders;
    }

    /**
     * Listener class for the Display Order button Uses a focus listener to
     * decide what to display Checks if a order was just selected prior to
     * button click Else displays all orders for the table specified in customer
     * details
     *
     */
    private class DOFListener implements FocusListener {

        //Action Event listener, only runs on button press
        @Override
        public void focusGained(FocusEvent e) {
            //Check if orders list is empty
            if (!theModel.getOrders().isEmpty()) {
                //Grab a list of all orders
                ArrayList<Order> ol = theModel.getOrders();
                //Get the component which just lost focus
                Component a = e.getOppositeComponent();

                //Check if component a was a part of the table
                if (a.getClass().equals(JList.class)) {
                    //Change output panel to order output
                    theView.changeOutputPanel(2);
                    //Cast component to JList ( table entries count as JList )
                    JList temp = (JList) a;
                    //Assign selected order from the table
                    Order ord = (Order) temp.getSelectedValue();
                    //Update panel title
                    theView.changeOutputOrderTitle("Ordered Items For Highlighted Order");
                    //Update panel table
                    theView.updateOutputOrderTable(ord);
                } //If component wasn't a table, check if a table number has been inserted
                else if (!theView.getCustomerDetails().getTableNumber().equals("")) {
                    //Change output panel to order output
                    theView.changeOutputPanel(2);
                    //Get ArrayList of orders with the correct
                    ArrayList<Order> ords = getOrders(Integer.parseInt(theView.getCustomerDetails().getTableNumber()));
                    //Update panel title
                    theView.changeOutputOrderTitle("Ordered Items at Table" + theView.getCustomerDetails().getTableNumber());
                    //Update panel table
                    theView.updateOutputOrderTable(ords);
                } //Display error message, asking to choose an order
                else {
                    theView.displayPopup("Please select a table number or an order to view.");
                }
            } else {
                //Make focus listener unable to refocus on itself
                //Prevents endless popup loop
                theView.setDisplayOrderFocus(false);
                theView.displayPopup("There are no orders to display.");
                //Re-enable focusable after display popup has been closed.
                theView.setDisplayOrderFocus(true);
            }
        }

        /**
         * When focus lost do nothing Method needs to be overridden
         *
         * @param e
         */
        @Override
        public void focusLost(FocusEvent e) {
        }

    }
}
