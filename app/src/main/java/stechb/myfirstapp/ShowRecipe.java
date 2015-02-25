package stechb.myfirstapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


public class ShowRecipe extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe);
        Intent intent = getIntent();
        int id = intent.getIntExtra("MEALID",0);
        showNewMealById(id);

    }
    public int showNewMealById (int id) {
        ImageView mealView = (ImageView) findViewById(R.id.mealIcon);
        TextView mealName = (TextView) findViewById(R.id.mealName);
        TextView mealRecipe = (TextView) findViewById(R.id.mealRecipe);

        Meal meal = chooseByID(id);
        if (meal.getImage() != null) mealView.setImageBitmap(meal.getImage());
        mealName.setText(meal.getName());
        mealRecipe.setText(meal.getRecipe());
        return meal.getId();
    }

    public Meal chooseByID(int id) {

        DataBaseHelper db = new DataBaseHelper(this);
        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
            //throw new Error ("unable to create database");
        }
        db.openDataBase();
        Meal meal = db.getMealById(id);
        db.close();
        return meal;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
