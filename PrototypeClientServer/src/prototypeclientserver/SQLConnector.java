/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypeclientserver;

import java.sql.*;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to connect to SQL
 * Contains all functionality to interact with the database, create it etc
 * @author stephenfleming & kosta
 */
public class SQLConnector {
    
    private final String user = "root";
    private final String pass = "";
    private final String dbname = "JA2DB";
    private final String getconn = "jdbc:mysql://localhost:3306/";
    
    private Connection myConn = null;
    private Statement myStmt = null;
    private ResultSet myRs = null;
    
    public SQLConnector(){}
    /**
     * Try connect to SQL
     * @return boolean if could connect to SQL
     */
    public boolean testConnection()
    {   
        try {
            myConn = DriverManager.getConnection(getconn + "?user=" + user + "&password=" + pass);
            //Connection successfully connected check sourced from Stack Overflow
            //Source: https://stackoverflow.com/questions/7764671/java-jdbc-connection-status
            if (!myConn.isClosed() || myConn != null)
                return true;
            return false;
            //End borrowed code
        } catch (SQLException ex) {
            ex.printStackTrace();
            //Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    /**
     * Boolean test to check if database exists
     * @return true if database exists, else false
     */
    public boolean testDatabaseExists()
    {
        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("show databases like '" + dbname + "'");
            if (myRs.next())
                return true;
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    /**
     * Function to create the database, the tables, and populate them with values
     * @param food food MenuItems to add to database
     * @param bev beverage MenuItems to add to database
     * @throws SQLException 
     */
    public void createAndPopulateDatabase(ArrayList<MenuItem> food, ArrayList<MenuItem> bev) throws SQLException
    {
        createDatabase();
        connectToDatabase();
        createTables();
        populateTables(food, bev);
    }
    /**
     * Gets the last ID from the database
     * @return int of the last ID
     */
    public int getLastOrderId() {
       ArrayList<OrderRecord> result = new ArrayList<>();
        String sqlQuery = "SELECT * FROM `orders`";
        try {
            PreparedStatement p = myConn.prepareStatement(sqlQuery);
            ResultSet rs = p.executeQuery(sqlQuery);
            rs.last();
            return rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Could not get orders " + e.toString());
            System.out.println(myStmt);
        }
        return 0;
    }
    /**
     * Function to create the SQL database
     * @throws SQLException 
     */
    private void createDatabase() throws SQLException
    {
        myStmt.executeUpdate("create database " + dbname);
    }
    /**
     * Function to call the creation functions of the MenuItems and Orders Tables
     * @throws SQLException 
     */
    private void createTables() throws SQLException
    {
        createTableMenuItems();
        createTableOrdersWaiting();
    }
    /**
     * Populates the MenuItems tables
     * @param food food MenuItems to add to database
     * @param bev beverage MenuItems to add to database
     * @throws SQLException 
     */
    private void populateTables(ArrayList<MenuItem> food, ArrayList<MenuItem> bev) throws SQLException
    {
        populateTableMenuItems(food, bev);
    }
    /**
     * Creates the Orders table in the SQL database
     * @throws SQLException 
     */
    private void createTableOrdersWaiting() throws SQLException
    {
        String sqlQuery = 
                "CREATE TABLE orders " +
                "(id auto_increment INT NOT NULL PRIMARY KEY, " +
                "custname VARCHAR(25) NOT NULL, " +
                "tablenumber INT NOT NULL, " +
                "foodname VARCHAR(100) NOT NULL, " + 
                "beveragename VARCHAR(100) NOT NULL, " + 
                "served BOOLEAN NOT NULL, " + 
                "billed BOOLEAN NOT NULL, " + 
                "time DATETIME DEFAULT CURRENT_TIMESTAMP)";
        myStmt.executeUpdate(sqlQuery);
    }
    /**
     * Creates the MenuItems tables in the SQL database
     * @throws SQLException 
     */
    private void createTableMenuItems() throws SQLException
    {
        String sqlQuery = 
                "CREATE TABLE menuitems " +
                "(menudesc VARCHAR(12) NOT NULL, " + 
                "mealtype VARCHAR(12) NOT NULL, " +
                "itemname VARCHAR(100) NOT NULL, " +
                "price INT NOT NULL, " + 
                "energy INT NOT NULL, " + 
                "protein FLOAT NOT NULL, " + 
                "carbs FLOAT NOT NULL, " + 
                "fat FLOAT NOT NULL, " + 
                "fibre FLOAT NOT NULL, " + 
                "id INT NOT NULL)";
        myStmt.executeUpdate(sqlQuery);
    }
    /**
     * Populates the MenuItems table with values
     * @param food food MenuItems to add to database
     * @param bev beverage MenuItems to add to database
     * @throws SQLException 
     */
    private void populateTableMenuItems(ArrayList<MenuItem> food, ArrayList<MenuItem> bev) throws SQLException
    {
        for (MenuItem m : food)
        {
            String sqlQuery = 
                    "INSERT INTO menuitems VALUES (" +
                    "'" + m.getMenuDesc() + "', " +
                    "'" + m.getMealType() + "', " +
                    "'" + m.getItemName() + "', " +
                    m.getPrice() + ", " + 
                    m.getEnergy() + ", " + 
                    m.getProtein() + ", " + 
                    m.getCarbs() + ", " + 
                    m.getFat() + ", " + 
                    m.getFibre() + ", " + 
                    m.getID() + ")"; 
            myStmt.executeUpdate(sqlQuery);
        }
        for (MenuItem m : bev)
        {
            String sqlQuery = 
                    "INSERT INTO menuitems VALUES (" +
                    "'" + m.getMenuDesc() + "', " +
                    "'" + m.getMealType() + "', " +
                    "'" + m.getItemName() + "', " +
                    m.getPrice() + ", " + 
                    m.getEnergy() + ", " + 
                    m.getProtein() + ", " + 
                    m.getCarbs() + ", " + 
                    m.getFat() + ", " + 
                    m.getFibre() + ", " + 
                    m.getID() + ")"; 
            myStmt.executeUpdate(sqlQuery);
        }
    }
    /**
     * Getter for all the MenuItems in the MenuItem table
     * @return ArrayList<MenuItem> of MenuItems from table
     * @throws SQLException 
     */
    public ArrayList<MenuItem> getMenuItems() throws SQLException
    {
        ArrayList<MenuItem> result = new ArrayList<>();
        String sqlQuery = "SELECT * FROM menuitems";
        myRs = myStmt.executeQuery(sqlQuery);
        while (myRs.next()) {
            result.add(new MenuItem(
                    myRs.getString(1), 
                    myRs.getString(2), 
                    myRs.getString(3), 
                    Integer.parseInt(myRs.getString(4)), 
                    Integer.parseInt(myRs.getString(5)), 
                    Float.parseFloat(myRs.getString(6)), 
                    Float.parseFloat(myRs.getString(7)), 
                    Float.parseFloat(myRs.getString(8)), 
                    Float.parseFloat(myRs.getString(9)), 
                    Integer.parseInt(myRs.getString(10))
            ));
        }
        return result;
    }
    /**
     * Function to add an order to the order table in the database
     * @param o order to add
     * @throws SQLException 
     */
    public void addOrder(Order o) throws SQLException
    {
        String sqlQuery = 
                "INSERT INTO orders (custname, tablenumber, foodname, beveragename, served, billed) VALUES (" +
                "'" + o.getCustomerName() + "', " + 
                o.getTable() + ", " + 
                "'" + o.getFood() + "', " +
                "'" + o.getBeverage() + "', " + 
                o.isServed() + ", " +
                o.isBilled() + ")";
        myStmt.executeUpdate(sqlQuery);
    }
    /**
     * Function to connect to the database, presuming it exists
     * @throws SQLException 
     */
    public void connectToDatabase() throws SQLException
    {
        TimeZone timeZone = TimeZone.getTimeZone("AEST");
        TimeZone.setDefault(timeZone);
        myConn = DriverManager.getConnection(getconn + dbname, user, pass);
        myStmt = myConn.createStatement();
    }
    /**
     * Getter for the orders listed in the database
     * Programatically iterates over each result set from the query and creates an order object for each
     * @return ArrayList<OrderRecord> of all orders from database
     * @throws SQLException 
     */    
    public ArrayList<OrderRecord> getOrders()  throws SQLException {
        ArrayList<OrderRecord> result = new ArrayList<>();
        String sqlQuery = "SELECT * FROM `orders`";
        try {
            PreparedStatement p = myConn.prepareStatement(sqlQuery);
            
            ResultSet rs = p.executeQuery(sqlQuery);
            while (rs.next()) {
                //table schema
                //id, name, tab_num, foodname, beveragename, served, billed, time
                
                // Customer name, orderid, table, items
                OrderRecord newOrder = new OrderRecord();
                
                newOrder.name = rs.getString(2);
                newOrder.id = rs.getInt(1);
                newOrder.table = rs.getInt(3);
                
                newOrder.food = rs.getString(4);
                newOrder.beverage = rs.getString(5);
                
                newOrder.served = (rs.getInt(6) == 1);
                newOrder.billed = (rs.getInt(7) == 1);
                
                result.add(newOrder);
            }
        } catch (SQLException e) {
            System.out.println("Could not get orders " + e.toString());
            System.out.println(myStmt);
        }
        return result;
    }
    /**
     * Updates an existing order's served or billed state 
     * @param orderId id of order in database
     * @param column column to update (either billed or served)
     * @param newValue boolean to change to
     */
    public void updateOrderBoolean(int orderId, String column, boolean newValue) {
        int bToInt = 0;
        if (newValue)
            bToInt = 1;
        
        String sqlQuery = "UPDATE orders SET " + column + "=?" + " WHERE id=?;";
        
        System.out.println(sqlQuery);
        try {
            PreparedStatement p = myConn.prepareStatement(sqlQuery);
            
            p.setInt(1, bToInt);
            p.setInt(2, orderId);
            
            p.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.toString());
        }
    }
}
