/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prototypeclientserver;

/**
 *
 * @author kosta
 */
public class MenuItem {
    
    private final String MenuDesc;
    private final String MealType;
    private String ItemName = "";
    private final int Price;
    private final int Energy;
    private final float Protein;
    private final float Carbs;
    private final float Fat;
    private final float Fibre;
    private final int ID;
    
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
    
    public void setItemName(String change) {
        ItemName = change;
    }
    public String getMenuDesc() {
        return MenuDesc;
    }

    public String getMealType() {
        return MealType;
    }

    public String getItemName() {
        return ItemName;
    }

    public int getPrice() {
        return Price;
    }

    public int getEnergy() {
        return Energy;
    }

    public float getProtein() {
        return Protein;
    }

    public float getCarbs() {
        return Carbs;
    }

    public float getFat() {
        return Fat;
    }

    public float getFibre() {
        return Fibre;
    }

    public int getID() {
        return ID;
    }
    
    @Override
    public String toString() {
        return ItemName;
    }
}
