package stechb.myfirstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


public class ShowChosen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Meal meal = chooseRandom();
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(meal.getName());
        setContentView(textView);
        ImageView mealView ;
        setContentView(R.layout.chosen_show);
        mealView = (ImageView) findViewById(R.id.meal_image);
        Log.d("2nACT"," "+meal.getImage().getByteCount());
        // TODO change null to meal.getImage()
        mealView.setImageBitmap(meal.getImage());

    }

    public Meal chooseRandom () {

        DataBaseHelper db = new DataBaseHelper(this);
        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
            //throw new Error ("unable to create database");
        }
        db.openDataBase();
        Meal meal = db.getRandomMeal();
        db.close();
        return meal;

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
