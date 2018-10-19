/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver;

/**
 * Class used as an interface between the DB Table and an Order Object
 * @author stephenfleming & kosta
 */
public class OrderRecord {
        public int id;
        public String name;
        public int table;
        
        public String food;
        public String beverage;
        
        public boolean served;
        public boolean billed;
    }
