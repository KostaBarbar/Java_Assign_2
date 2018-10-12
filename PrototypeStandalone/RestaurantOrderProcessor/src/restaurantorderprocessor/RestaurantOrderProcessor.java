/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantorderprocessor;

import restaurantorderprocessor.UI.Controller;
import restaurantorderprocessor.UI.MainWindow;
import restaurantorderprocessor.UI.Model;

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
        Model model = new Model(System.getProperty("user.dir") + "/src/restaurantorderprocessor/items.csv");
        //The Controller - Facilitate actions between model and view
        Controller ctrl = new Controller(model, mainWin);
        //Set JFrame to visible to start application
        mainWin.setVisible(true);
    }
}
