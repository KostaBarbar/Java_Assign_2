/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantorderprocessor;

import java.util.regex.Pattern;
import restaurantorderprocessor.UI.Controller;
import restaurantorderprocessor.UI.MainWindow;
import restaurantorderprocessor.UI.Model;

import java.io.File;

/**
 *
 * @author stephenfleming & kosta
 */
public class RestaurantOrderProcessor {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        //Set-up MVC 
        //The View - JFrame
        MainWindow mainWin = new MainWindow();
        //The Model - Gets path relative to wherever it's placed
        //So that it works in any location
        Model model = new Model(fixedPath());
        //The Controller - Facilitate actions between model and view
        Controller ctrl = new Controller(model, mainWin);
        //Set JFrame to visible to start application
        mainWin.setVisible(true);
        
        System.out.println(fixedPath());
    }
    
    public static String fixedPath()
    {
        //The main folder name
        //By default the main folder name is the 
        String FOLDER_NAME = "Java_Assign_2";
        String result = "";
        String[] components = System.getProperty("user.dir").split(Pattern.quote(File.separator));
        int index;
        for (index = 0; index < components.length-1; index++)
        {
            if (components[index].equals(FOLDER_NAME))
                break;
            else
                result += components[index] + File.separator;
        }
        result += components[index] + File.separator + "PrototypeStandalone" + File.separator + "RestaurantOrderProcessor" + File.separator + "src/restaurantorderprocessor/items.csv";
        return result;
    }
}
