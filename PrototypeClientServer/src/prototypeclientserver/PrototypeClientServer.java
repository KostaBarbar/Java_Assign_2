/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver;

import java.sql.SQLException;
import prototypeclientserver.ClientCustomer.ClientScreenController;
import prototypeclientserver.ClientReceptionist.ReceptionistScreenController;
import prototypeclientserver.ServerChef.ChefScreenController;

/**
 * Main file to run the program
 * @author stephenfleming & kosta
 */
public class PrototypeClientServer {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ChefScreenController chef = new ChefScreenController(); 
        ReceptionistScreenController receptionist = new ReceptionistScreenController(); 
        try {
            ClientScreenController customer = new ClientScreenController();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } 
    }
}
