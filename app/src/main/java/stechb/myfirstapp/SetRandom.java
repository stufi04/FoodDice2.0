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


public class SetRandom extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_random);
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
        toShowChosen.putExtra("queryType", "veil = 1");
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
