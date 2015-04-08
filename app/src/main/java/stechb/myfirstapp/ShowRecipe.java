package stechb.myfirstapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.IOException;


public class ShowRecipe extends Activity {

    private boolean slided = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe);
        Intent intent = getIntent();
        int id = intent.getIntExtra("MEALID",0);
        showNewMealById(id);
        swipeMethod();
    }

    public void showNewMealById (int id) {
        ImageView mealView = (ImageView) findViewById(R.id.mealIcon);
        TextView mealName = (TextView) findViewById(R.id.mealName);
        TextView mealRecipe = (TextView) findViewById(R.id.mealRecipe);
        TextView mealIngr = (TextView) findViewById(R.id.mealIngredients);

        Meal meal = chooseByID(id);
        if (meal.getImage() != null) mealView.setImageBitmap(meal.getImage());
        mealName.setText(meal.getName());
        mealRecipe.setText("Recipe:\n\n" + meal.getRecipe());
        mealIngr.setText("Ingredients:\n\n" + meal.getIngredients());
        mealRecipe.setMovementMethod(new ScrollingMovementMethod());
        mealIngr.setMovementMethod(new ScrollingMovementMethod());
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void swapDots() {
        ImageView dot1 = (ImageView) findViewById(R.id.dot1);
        ImageView dot2 = (ImageView) findViewById(R.id.dot2);
        Drawable d = dot1.getBackground();
        dot1.setBackground(dot2.getBackground());
        dot2.setBackground(d);
    }

    public void swipeMethod () {

        final View thisLayout = (View) findViewById(R.id.thisLayout);
        thisLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public boolean onSwipeRight() {
                if (slided==true) return false;
                ViewFlipper vf = (ViewFlipper) findViewById(R.id.flipper);
                vf.setAnimation(AnimationUtils.loadAnimation(thisLayout.getContext(), R.anim.slide_in_left));
                vf.showNext();
                slided=true;
                swapDots();
                return true;
            }

            @Override
            public boolean onSwipeLeft() {
                if (slided==false) return true;
                ViewFlipper vf = (ViewFlipper) findViewById(R.id.flipper);
                vf.setAnimation(AnimationUtils.loadAnimation(thisLayout.getContext(), R.anim.slide_in_right));
                vf.showPrevious();
                slided=false;
                swapDots();
                return true;
            }
        });

        final View v1 = (View) findViewById(R.id.mealRecipe);
        v1.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public boolean onSwipeRight() {
                ViewFlipper vf = (ViewFlipper) findViewById(R.id.flipper);
                vf.setAnimation(AnimationUtils.loadAnimation(v1.getContext(), R.anim.slide_in_left));
                vf.showNext();
                slided=true;
                swapDots();
                return true;
            }
        });

        final View v2 = (View) findViewById(R.id.mealIngredients);
        v2.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public boolean onSwipeLeft() {
                ViewFlipper vf = (ViewFlipper) findViewById(R.id.flipper);
                vf.setAnimation(AnimationUtils.loadAnimation(v2.getContext(), R.anim.slide_in_right));
                vf.showPrevious();
                slided=false;
                swapDots();
                return true;
            }
        });
    }
}
