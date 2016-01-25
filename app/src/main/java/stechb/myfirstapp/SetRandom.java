package stechb.myfirstapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import android.app.Activity;
import android.widget.TabHost;


public class SetRandom extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_random);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("meal");
        tabSpec.setContent(R.id.meatTab);
        tabSpec.setIndicator("Meat");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("cuisine");
        tabSpec.setContent(R.id.cuisineTab);
        tabSpec.setIndicator("Cuisine");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("course");
        tabSpec.setContent(R.id.courseTab);
        tabSpec.setIndicator("Course");
        tabHost.addTab(tabSpec);

    }

    public void chickenChosen (View view) {
        Log.d("Query", "Clicked chicken 1");
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        Log.d("Query", "Clicked chicken 2");
        toShowChosen.putExtra("queryType", "chicken = 1");
        Log.d("Query", "Clicked chicken 3");
        startActivity(toShowChosen);
    }

    public void porkChosen (View view) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "pork = 1");
        startActivity(toShowChosen);
    }

    public void beefChosen (View view) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "beef = 1");
        startActivity(toShowChosen);
    }

    public void fishChosen (View view) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "fish = 1");
        startActivity(toShowChosen);
    }

    public void veggieChosen (View view) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "veggie = 1");
        startActivity(toShowChosen);
    }

    public void cuisineChosen (View view, int category) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "cuisine = " + category);
        startActivity(toShowChosen);
    }

    public void courseChosen (View view, int category) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "course = " + category);
        startActivity(toShowChosen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_random, menu);
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
