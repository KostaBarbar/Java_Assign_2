/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver.ClientCustomer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import prototypeclientserver.MenuItem;
import prototypeclientserver.DataController;

/**
 *
 * @author stephenfleming
 */
public class ClientScreenModel {
    private DataController data;
    
    public ClientScreenModel() throws SQLException {
        data = new DataController();
    }
    
    public DataController getDataController() {
        return data;
    }
    
    /**
     * Combines the values of two MenuItems
     * Used for the output table panel, in the total row
     * @param a first MenuItem
     * @param b second MenuItem
     * @return result of combining MenuItem contents
     */
    public MenuItem combineMenuItems(MenuItem a, MenuItem b)
    {
        return new MenuItem(
                "Combined", 
                a.getMealType(), 
                "Combined", 
                a.getPrice() + b.getPrice(),
                a.getEnergy() + b.getEnergy(),
                a.getProtein() + b.getProtein(),
                a.getCarbs() + b.getCarbs(),
                a.getFat() + b.getFat(),
                a.getFibre() + b.getFibre(),
                0
        );
    }
    
/**
     * Converts ArrayLists into String arrays
     * @param arr ArrayList of MenuItems to convert 
     * @return string array of item names
     */
    public String[] getStringArray(ArrayList<MenuItem> arr)
    {
        //Create values array with required size
        String[] values = new String[arr.size()];
        //Loop through all MenuItems and add their names to the array
        for (int i = 0; i < arr.size(); i++)
        {
            values[i] = arr.get(i).getItemName();
        }
        //Return result
        return values;
    }
    
    /**
     * Validates inputs against regex expression
     * @param input string input to check 
     * @param r regex to check against
     * @return boolean if input passes regex test
     */
    public boolean validateInput(String input, String r)
    {
        Pattern regex = Pattern.compile(r);
        if (!regex.matcher(input).matches()) {
               return false;
        }
        return true;
    }
}
