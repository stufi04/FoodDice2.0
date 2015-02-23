package stechb.myfirstapp;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by iange_000 on 10-Feb-15.
 */

public class Meal {
    private String name;
    private String recipe;
    private boolean veggie;
    private boolean chicken;
    private boolean beef;
    private boolean pork;
    private boolean fish;
    private Bitmap image;
    public static ArrayList<String> lastFive = new ArrayList<>();

    public Meal(String name,String recipe,Bitmap image){

        this.name = name;
        this.recipe = recipe;
        this.image = image;

    }
    public String getName(){
        return this.name;
    }
    public String getRecipe(){
        return this.recipe;
    }
    public boolean isVeggie() { return this.veggie; }
    public boolean isChicken() { return this.chicken; }
    public boolean isPork() { return this.pork; }
    public boolean isBeef() { return this.beef; }
    public boolean isFish() { return this.fish; }
    public Bitmap getImage(){return  this.image;}
    public static ArrayList getLastFive() {return lastFive;}
    public static Boolean custContains(String name){
        boolean contains = false;
        for(int i=0 ; i<=lastFive.size();i++){
            if(lastFive.get(i).equals(name)) return true;

        }
        return contains;
    }
}