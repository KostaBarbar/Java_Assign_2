/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantorderprocessor.UI;


/**
 * MenuItem class
 * Container for information for each menu item
 * @author stephenfleming & kosta 
 */
public class MenuItem {
    
    private final String MenuDesc;
    private final String MealType;
    private String ItemName;
    private final int Price;
    private final int Energy;
    private final float Protein;
    private final float Carbs;
    private final float Fat;
    private final float Fibre;
    private final int ID;
    /**
     * Constructor for MenuItem
     * @param menudesc food/beverage information
     * @param mealtype breakfast/lunch/dinner information
     * @param itemname string name of item
     * @param price integer price of item ( dollars )
     * @param energy integer energy content of item
     * @param protein float protein energy of item
     * @param carbs float carbohydrate count of item
     * @param fat float fat content of item
     * @param fibre float fibre content of item
     * @param id integer id of item
     */
    public MenuItem(String menudesc, String mealtype, String itemname, int price, int energy, float protein, float carbs, float fat, float fibre, int id)
    {
        MenuDesc = menudesc;
        MealType = mealtype;
        ItemName = itemname;
        Price = price;
        Energy = energy;
        Protein = protein;
        Carbs = carbs;
        Fat = fat;
        Fibre = fibre;
        ID = id;
    }
    /**
     * Setter for Item Name
     * @param change string to change item name to
     */
    public void setItemName(String change) {
        ItemName = change;
    }
    /**
     * Getter for menu description
     * @return string menu description
     */
    public String getMenuDesc() {
        return MenuDesc;
    }
    /**
     * Getter for meal type
     * @return string meal type
     */
    public String getMealType() {
        return MealType;
    }
    /**
     * Getter for item name
     * @return string item name
     */
    public String getItemName() {
        return ItemName;
    }
    /**
     * Getter for item price
     * @return integer price
     */
    public int getPrice() {
        return Price;
    }
    /**
     * Getter for item energy
     * @return integer energy
     */
    public int getEnergy() {
        return Energy;
    }
    /**
     * Getter for item protein
     * @return float protein
     */
    public float getProtein() {
        return Protein;
    }
    /**
     * Getter for item carbohydrates
     * @return float carbs
     */
    public float getCarbs() {
        return Carbs;
    }
    /**
     * Getter for item fat
     * @return float fat
     */
    public float getFat() {
        return Fat;
    }
    /**
     * Getter for item fibre
     * @return float fibre
     */
    public float getFibre() {
        return Fibre;
    }
    /**
     * Getter for item id
     * @return integer id
     */
    public int getID() {
        return ID;
    }
    /**
     * Override for toString() method
     * Returns the MenuItems item name
     * @return string Item name
     */
    @Override
    public String toString() {
        return ItemName;
    }
}
