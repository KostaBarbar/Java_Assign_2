/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver;

import java.sql.SQLException;
import prototypeclientserver.ClientCustomer.ClientScreenController;
import prototypeclientserver.ServerChef.ChefScreenController;

/**
 *
 * @author stephenfleming
 */
public class PrototypeClientServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        ChefScreenController chef = new ChefScreenController();
        ClientScreenController customer = new ClientScreenController();
        /*
        try {
        TCPServer server = new TCPServer();
        Thread serverThread = new Thread(server);
       
        serverThread.start();
        
        Thread clientThread = new Thread(new TCPClient());
        clientThread.start();
        } catch (Exception e) {
            
        }
        */
    }
    
    
}
