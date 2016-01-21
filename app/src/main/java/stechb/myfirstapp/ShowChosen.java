package stechb.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import java.io.IOException;
import java.util.ArrayList;


public class ShowChosen extends Activity{

    private ArrayList<Integer> recipes = new ArrayList<>();
    static int id;
    private String qType;
    int position = 0;
    DataBaseHelper db = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("Query", "Clicked chicken 4");
        setContentView(R.layout.chosen_show);
        //Log.d("Query", "Clicked chicken 5");
        Intent intent = getIntent();
        //Log.d("Query", "Clicked chicken 6");
        qType = intent.getStringExtra("queryType");
        //Log.d("Query",qType);
        //id = showNewRandomMeal();
        swipeMethod();

        View image = (View) findViewById(R.id.meal_image);
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRecipe();
            }
        });

        Log.d("Starting", "Done");

        recipes = getResultList();
        shuffleList();
        id = showNewRandomMeal();


        //arrowClicks();
    }



    public void swipeMethod () {
        OnSwipeTouchListener swipeListener = new OnSwipeTouchListener(this) {
            @Override
            public boolean onSwipeLeft() {
                if(position > 0) position--;
                else position = recipes.size() -1;
                ShowChosen.id = showNewRandomMeal();
                Log.d("Swipes","Left swipe " + ShowChosen.id);
                return true;
            }

            @Override
            public boolean onSwipeRight() {
                if(position < recipes.size() - 1) position++;
                else position = 0;
                ShowChosen.id = showNewRandomMeal();
                //goToRecipe();
                Log.d("Swipes","Right swipe");
                return true;
            }

            @Override
            public boolean onTap(){
                goToRecipe();
                Log.d("Swipes", "Tap");
                return true;
            }

        };
        final View thisLayout = (View) findViewById(R.id.thisLayout);
        thisLayout.setOnTouchListener(swipeListener);
        View image = (View) findViewById(R.id.meal_image);
        image.setOnTouchListener(swipeListener);

    }

    public void goToRecipe () {
        Intent intent = new Intent(this, ShowRecipe.class);
        intent.putExtra("MEALID",ShowChosen.id);
        startActivity(intent);
    }

    public int showNewRandomMeal () {

        ImageView mealView = (ImageView) findViewById(R.id.meal_image);
        TextView textView = (TextView) findViewById(R.id.name_view);


        db.openDataBase();
        Meal meal =  db.getMealById(recipes.get(position));
        db.close();

        if (meal.getImage() != null) mealView.setImageBitmap(meal.getImage());
        textView.setTextSize(40);
        textView.setText(meal.getName());
        return meal.getId();
    }

    private void shuffleList(){
        int size = recipes.size();
        for(int i = 0; i < size;i++){
            Integer r = new Integer((int) (Math.random() * size - 1));
            int temp = recipes.get(i);
            recipes.set(i, recipes.get(r));
            recipes.set(r, temp);
        }
    }
    public ArrayList<Integer> getResultList() {


        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
            //throw new Error ("unable to create database");
        }
        db.openDataBase();
        recipes = db.getResultList(qType);
        db.close();

        Log.d("Recipes", " " + recipes.size());
        return recipes;


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
