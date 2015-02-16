package stechb.myfirstapp;

/**
 * Created by iange_000 on 10-Feb-15.
 */
public class Meal {
    private String name;
    private String recipe;

    public Meal(String name,String recipe){

        this.name = name;
        this.recipe = recipe;

    }
    public String getName(){
        return this.name;
    }
    public String getRecipe(){
        return this.recipe;
    }
}