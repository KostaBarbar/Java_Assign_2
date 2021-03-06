/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prototypeclientserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class to handle reading data from CSV
 * Can read in all values or specifically food or beverage
 * Formats inputs to ensure validity and common formatting
 * @author stephenfleming & kosta
 */
public class CSVReader {
    /**
     * Method to read in CSV file, format the data and return the result.
     * @param p path to CSV file from main folder
     * @return ArrayList<MenuItem> of all entries from CSV
     */
    public ArrayList<MenuItem> GetResult(String p)
    {
        return FormatCSVArray(CSVtoArray(p));
    }
    /**
     * Method to read in CSV file, format the data and return only food items
     * @param p path to CSV file from main folder
     * @return ArrayList<MenuItem> of all food entries from CSV
     */
    public ArrayList<MenuItem> GetFood(String p)
    {
        ArrayList<MenuItem> result = GetResult(p);
        return result.stream().filter(x -> "food".equals(x.getMenuDesc())).collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Method to get all food items from an ArrayList<MenuItem>
     * @param arr ArrayList to sort
     * @return filtered ArrayList containing only beverages
     */
    public ArrayList<MenuItem> GetFood(ArrayList<MenuItem> arr)
    {
        return arr.stream().filter(x -> "food".equals(x.getMenuDesc())).collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Method to read in CSV file, format the data and return only beverage items
     * @param p path to CSV file from main folder
     * @return ArrayList<MenuItem> of all beverage entries from CSV
     */
    public ArrayList<MenuItem> GetBeverage(String p)
    {
        ArrayList<MenuItem> result = GetResult(p);
        return result.stream().filter(x -> "Beverage".equals(x.getMenuDesc())).collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Method to get all beverages from an ArrayList<MenuItem>
     * @param arr ArrayList to sort
     * @return filtered ArrayList containing only beverages
     */
    public ArrayList<MenuItem> GetBeverage(ArrayList<MenuItem> arr)
    {
        return arr.stream().filter(x -> "Beverage".equals(x.getMenuDesc())).collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Sorts an ArrayList<MenuItem> via the mealtype corresponding to the sort variable 
     * @param sort mealtype string to sort by
     * @param result ArrayList<MenuItem> of all items to sort 
     * @return ArrayList<MenuItem> with items with the corresponding mealtype
     */
    public ArrayList<MenuItem> RadioSort(String sort, ArrayList<MenuItem> result)
    {
        return result.stream().filter(x -> sort.equals(x.getMealType())).collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Method to read in raw data from a CSV array
     * Given the format of the specific CSV, it skips reading the first line
     * @param p path to CSV file from main folder
     * @return ArrayList<MenuItem> of all entries found
     */ 
    public ArrayList<MenuItem> CSVtoArray(String p)
    {
        //Init variables
        ArrayList<MenuItem> result = new ArrayList<>();
        String splitby = ",";
        String line = "";
        //Try parse CSV
        try {
            BufferedReader br = new BufferedReader(new FileReader(p));
            //Skip first line
            br.readLine();
            //Loop through each item and add to result
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(splitby);
                result.add(new MenuItem(
                        fields[0], 
                        fields[1], 
                        fields[2], 
                        Integer.parseInt(fields[3]), 
                        Integer.parseInt(fields[4]), 
                        Float.parseFloat(fields[5]), 
                        Float.parseFloat(fields[6]), 
                        Float.parseFloat(fields[7]), 
                        Float.parseFloat(fields[8]), 
                        Integer.parseInt(fields[9])));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Return created list of menu items
        return result;
    }
    /**
     * Method to correct spelling errors in MenuItems
     * @param arr ArrayList<MenuItem> to format
     * @return formated ArrayList<MenuItem>
     */
    public ArrayList<MenuItem> FormatCSVArray(ArrayList<MenuItem> arr)
    {
        for (MenuItem m : arr)
        {
            //Trim trailing and leading spaces & Capitalise first characters
            String[] words = m.getItemName().trim().split(" ");
            String itemname = "";
            for (String s : words)
            {
                //Code Borrowed from Stack Overflow
                //Source : https://stackoverflow.com/questions/1892765/how-to-capitalize-the-first-character-of-each-word-in-a-string
                itemname += (s.length() > 1? s.substring(0,1).toUpperCase() + s.substring(1, s.length()) : s) + " ";
                //End Borrowed Code
            }
            //Trim again
            itemname = itemname.trim();
            //Set 
            m.setItemName(itemname);
        }
        return arr;        
    }
}
