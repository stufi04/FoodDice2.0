package stechb.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.io.IOException;


public class ShowChosen extends Activity{
ViewGroup thisLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Meal meal = chooseRandom();
        setContentView(R.layout.chosen_show);
        ImageView mealView;
        thisLayout = (ViewGroup) findViewById(R.id.thisLayout);
        mealView = (ImageView) findViewById(R.id.meal_image);
        if (meal.getImage() != null) mealView.setImageBitmap(meal.getImage());
        final TextView textView = (TextView) findViewById(R.id.name_view);
        textView.setTextSize(40);
        textView.setText(meal.getName());
        thisLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {

            }

            @Override
            public void onSwipeRight() {

            }


        });

    }


    public Meal chooseRandom() {

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
