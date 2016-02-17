package stechb.myfirstapp;

import android.app.Activity;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import org.apmem.tools.layouts.FlowLayout;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;


public class Ingredients extends Activity {

    HashMap<Integer, String> ingredientsMap = new HashMap<>();
    HashMap<String, Integer> invertedMap = new HashMap<>();
    ArrayList<Integer> availableIngredients;
    ArrayList<Integer> suggestedIngredients = new ArrayList<>();
    ArrayList<Integer> chosenIngredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        final TabHost tabHost = (android.widget.TabHost) findViewById(R.id.tabHost2);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("search");
        tabSpec.setContent(R.id.searchTab);
        tabSpec.setIndicator("Search");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("IngredientsList");
        tabSpec.setContent(R.id.listTab);
        tabSpec.setIndicator("All ingredients");
        tabHost.addTab(tabSpec);

        for (int i = 0; i < 2; i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#0D4D4D"));
            if (i == 0) tv.setShadowLayer(10, 1, 1, Color.WHITE);
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < 2; i++) {
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setShadowLayer(0, 0, 0, Color.WHITE);
                }

                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
                tv.setShadowLayer(10, 1, 1, Color.WHITE);

            }
        });

        DataBaseHelper db = new DataBaseHelper(this);
        ingredientsMap = db.getIngredientsMap();
        availableIngredients = new ArrayList<>(ingredientsMap.keySet());
        for(Integer id : availableIngredients) {
            invertedMap.put(ingredientsMap.get(id), id);
        }

        for (int i = 1; i <= 10; i++) {
            takeRandomSuggestion((Integer) i);
        }

        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayList<String> ingredients = new ArrayList<>(ingredientsMap.values());
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredients);
        actv.setAdapter(adapter);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String text = adapterView.getItemAtPosition(position).toString();
                Integer chosen = invertedMap.get(text);
                availableIngredients.remove(chosen);
                adapter.remove(text);
                actv.setAdapter(adapter);
                actv.setText("");
                addButtonToLayout(chosen, (FlowLayout) findViewById(R.id.chosenSet));
                updateSuggestions(chosen);
                //reset button in all ingredients
                Button b = (Button) findViewById(chosen);
                b.setEnabled(false);
                b.setBackgroundColor(Color.parseColor("#66888888"));
            }
        });

        final HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout relLayout = (RelativeLayout) findViewById(R.id.searchTab);
                scrollView.scrollTo((scrollView.getChildAt(0).getWidth() - relLayout.getWidth()) / 2, 0);
            }
        });


        //All ingredients tab filling

        String[] categories = {"Meat",
                "Seafood",
                "Vegetable",
                "Fruit",
                "Dairy",
                "Sauce",
                "Essentials",
                "Spices",
                "Sweet",
                "Others",
                "Nuts"};
        for(int i = 1; i<= categories.length;i++){
            ArrayList<Integer> ingr = db.getIngredientsByCAt(i);
            LinearLayout ll =(LinearLayout) findViewById(R.id.allIngredients);
            TextView textView = new TextView(this);
            textView.setText(categories[i - 1]);
            textView.setTextColor(Color.parseColor("#0D4D4D"));

            textView.setTextSize(20);
            LinearLayout.LayoutParams lastTxtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lastTxtParams.setMargins(0,20,0,0);
            textView.setLayoutParams(lastTxtParams);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.invalidate();
            ll.addView(textView);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
            FlowLayout fl  = (FlowLayout) inflater.inflate(R.layout.flow_layout, null);
            fl.setLayoutParams(lastTxtParams);
            fl.setGravity(Gravity.CENTER_HORIZONTAL);
            for(int ingredient : ingr){
                addButtonToLayout(ingredient,fl);
            }
            ll.addView(fl);
        }


    }

    public void updateSuggestions (Integer chosen) {

        for (int i = 1; i <= 10; i++) {

            Integer I = (Integer) i;
            String buttonID = "button" + I.toString();
            int resID = getResources().getIdentifier(buttonID, "id", "stechb.myfirstapp");
            Button b = (Button) findViewById(resID);

            if (b.getTag(R.id.ingredientID) == chosen) {
                takeRandomSuggestion(I);
                break;
            }

        }

    }

    public void takeRandomSuggestion(Integer buttonNum) {

        String buttonID = "button" + buttonNum.toString();
        int resID = getResources().getIdentifier(buttonID, "id", "stechb.myfirstapp");
        Button b = (Button) findViewById(resID);

        int ingrNum = availableIngredients.size();

        if (ingrNum == 0) {
            b.setVisibility(b.GONE);
            return;
        }

        Integer r = new Integer((int) (Math.random() * ingrNum));
        Integer ingredientNum = availableIngredients.get(r);
        String ingredient = ingredientsMap.get(ingredientNum);
        b.setText(ingredient);
        b.setTag(R.id.buttonID, buttonNum);
        b.setTag(R.id.ingredientID, ingredientNum);

        suggestedIngredients.add(ingredientNum);
        availableIngredients.remove(ingredientNum);

    }

    public void suggestedChosen(View view) {

        // remove from suggested, add to chosen
        Integer chosen = (Integer) view.getTag(R.id.ingredientID);
        suggestedIngredients.remove(chosen);
        chosenIngredients.add(chosen);
        Button b = (Button) findViewById(chosen);
        b.setEnabled(false);
        b.setBackgroundColor(Color.parseColor("#66888888"));

        // create a new random suggestion in the place of this one
        Integer buttonNum = (Integer) view.getTag(R.id.buttonID);
        takeRandomSuggestion(buttonNum);
        FlowLayout layout = (FlowLayout) findViewById(R.id.chosenSet);

        // remove from autocomplete
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) actv.getAdapter();
        adapter.remove(ingredientsMap.get(chosen));
        actv.setAdapter(adapter);
        
        addButtonToLayout(chosen, layout);

    }

    public void addButtonToLayout (int chosen, FlowLayout layout) {

        // inflate a new button and set its text and tag
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        Button b = (Button) inflater.inflate(R.layout.ingredient_button_layout, null);
        b.setTag(R.id.ingredientID, chosen);
        b.setText(ingredientsMap.get(chosen));

        // add button to the linear layout

        layout.addView(b);

        // set margins to the button
        FlowLayout.LayoutParams buttonLayoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, 8, 8, 0);
        b.setLayoutParams(buttonLayoutParams);
        if (layout != findViewById(R.id.chosenSet)){
            b.setId(chosen);
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Integer tag = (Integer) v.getTag(R.id.ingredientID);
                    v.setBackgroundColor(Color.parseColor("#66888888"));
                    chosenIngredients.add(tag);
                    addButtonToLayout(tag, (FlowLayout) findViewById(R.id.chosenSet));
                    v.setEnabled(false);
                    updateSuggestions(tag);

                    // remove from autocomplete
                    AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
                    ArrayAdapter<String> adapter = (ArrayAdapter<String>) actv.getAdapter();
                    adapter.remove(ingredientsMap.get(tag));
                    actv.setAdapter(adapter);


                }
            });
        }
    }

    public void removeFromChosen (View view) {

        // remove from chosen, add to available
        Integer clicked = (Integer) view.getTag(R.id.ingredientID);
        chosenIngredients.remove(clicked);
        availableIngredients.add(clicked);

        // add to autocomplete
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) actv.getAdapter();
        adapter.add(ingredientsMap.get(clicked));
        actv.setAdapter(adapter);
        //Reset button in all ingredients
        Button b = (Button) findViewById(clicked);
        b.setEnabled(true);
        b.setBackgroundColor(Color.parseColor("#44FFFFFF"));

        view.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
