package stechb.myfirstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.FileNotFoundException;

import android.app.Activity;


public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "stechb.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Drawable myDrawable = getResources().getDrawable(R.drawable.healthy_meal);
        Bitmap bmp = ((BitmapDrawable) myDrawable).getBitmap();
        //ImageView titleScreen = (ImageView) findViewById(R.id.title_image);
        //titleScreen.setImageBitmap(bmp);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void chooseRandom(View view) throws FileNotFoundException {
        Intent intent = new Intent(this, ShowChosen.class);
        intent.putExtra("queryType", "1");
        startActivity(intent);
    }

    public void setRandom(View view) {
        Intent intent = new Intent(this, SetRandom.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, Ingredients.class);
        startActivity(intent);
    }
}
