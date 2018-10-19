/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver;

import java.sql.SQLException;

/**
 *
 * @author stephenfleming
 */
public class DataController {
    private DataModel model;
   
    public DataController (){
        model = new DataModel();

        model.updateMenuItemList();
        model.updateOrderList();
        
        System.out.println(model.getMenuItems().size() + " " + model.getOrders().size());
    }
    
    public DataModel getDataModel() {
        return model;
    }
    
    public SQLConnector getSQL() {
        return model.getSQL();
    }
}
