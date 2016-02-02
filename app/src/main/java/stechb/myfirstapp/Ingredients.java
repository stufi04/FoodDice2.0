package stechb.myfirstapp;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class Ingredients extends Activity {

    HashMap<String, Integer> ingredientsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        DataBaseHelper db = new DataBaseHelper(this);
        ingredientsMap = db.getIngredientsMap();

        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        String[] ingredients = ingredientsMap.keySet().toArray(new String[ingredientsMap.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ingredients);
        actv.setAdapter(adapter);
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

    /*class IngredientAdapter extends ArrayAdapter {

        HashMap<String, Integer> map = new HashMap<>();

        public IngredientAdapter (HashMap<String, Integer> map) {
            this.map = map;
        }

        @Override
        public int getCount() {
            return map.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.list_layout, parent, false);

            TextView nameView = (TextView) row.findViewById(R.id.name);
            TextView manufacturerView = (TextView) row.findViewById(R.id.manufacturer);
            TextView latitudeView = (TextView) row.findViewById(R.id.latitude);
            TextView longitudeView = (TextView) row.findViewById(R.id.longitude);

            nameView.setText(starships.get(position).getName());
            manufacturerView.setText(starships.get(position).getManufacturer());
            latitudeView.setText(((Double) starships.get(position).getLatitude()).toString());
            longitudeView.setText(((Double)starships.get(position).getLongitude()).toString());

            return row;
        }
    }*/

}
