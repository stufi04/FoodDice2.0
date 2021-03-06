package stechb.myfirstapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import android.app.Activity;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


public class SetRandom extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_random);
        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

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

        for (int i = 0; i < 3; i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#0D4D4D"));
            if (i == 0) tv.setShadowLayer(10, 1, 1, Color.WHITE);
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < 3; i++) {
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setShadowLayer(0,0,0,Color.WHITE);
                }

                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
                tv.setShadowLayer(10,1,1,Color.WHITE);;

            }
        });

    }

    public void chickenChosen(View view) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "chicken = 1");
        startActivity(toShowChosen);
    }

    public void porkChosen(View view) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "pork = 1");
        startActivity(toShowChosen);
    }

    public void beefChosen(View view) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "beef = 1");
        startActivity(toShowChosen);
    }

    public void fishChosen(View view) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "fish = 1");
        startActivity(toShowChosen);
    }

    public void veggieChosen(View view) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "veggie = 1");
        startActivity(toShowChosen);
    }

    public void cuisineChosen (View view) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "cuisine = " + view.getTag());
        startActivity(toShowChosen);
    }

    public void courseChosen (View view) {
        Intent toShowChosen = new Intent(this, ShowChosen.class);
        toShowChosen.putExtra("queryType", "course = " + view.getTag());
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
