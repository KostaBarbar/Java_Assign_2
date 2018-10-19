/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver;

/**
 * Class to encapsulate the DataModel 
 * @author stephenfleming & kosta
 */

public class DataController {
    
    private DataModel model;
   /**
    * Public constructor to initialize the DataController
    */
    public DataController (){
        model = new DataModel();

        model.updateMenuItemList();
        model.updateOrderList();
        
        System.out.println(model.getMenuItems().size() + " " + model.getOrders().size());
    }
    /**
     * Getter for the model of the DataController
     * @return the model
     */
    public DataModel getDataModel() {
        return model;
    }
    /**
     * Getter for the SQLConnector of the DataController
     * @return the SQLConnector
     */
    public SQLConnector getSQL() {
        return model.getSQL();
    }
}
