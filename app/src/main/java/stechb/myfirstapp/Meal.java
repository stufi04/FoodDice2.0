package stechb.myfirstapp;

import android.graphics.Bitmap;

/**
 * Created by iange_000 on 10-Feb-15.
 */
public class Meal {
    private String name;
    private String recipe;
    private boolean veggie;
    private Bitmap image;

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
    public Bitmap getImage(){return  this.image;}
}