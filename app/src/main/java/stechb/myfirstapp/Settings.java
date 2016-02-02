package stechb.myfirstapp;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


public class Settings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost2);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("search");
        tabSpec.setContent(R.id.searchTab);
        tabSpec.setIndicator("Search");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("IngredientsList");
        tabSpec.setContent(R.id.listTab);
        tabSpec.setIndicator("Ingredients List");
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
                    tv.setShadowLayer(0,0,0,Color.WHITE);
                }

                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
                tv.setShadowLayer(10,1,1,Color.WHITE);;

            }
        });
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
