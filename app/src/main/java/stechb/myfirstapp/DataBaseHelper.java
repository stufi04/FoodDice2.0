package stechb.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by iange_000 on 04-Feb-15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/stechb.myfirstapp/databases/";
    private static String DB_NAME = "recipes.db";
    private final Context myContext;
    private SQLiteDatabase myDB;

    //Constructor
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }


    //Create an empty DB and copy the existing one in it
    public void createDataBase() throws IOException {
        boolean dbExists = checkDataBase();
        if (dbExists) {
            //Do nothing
        } else {

            this.getReadableDatabase();
            try {
                this.close();
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Can not copy DB");
            }
        }
    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // DB do not exist
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDB != null)
            myDB.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList<Integer> getResultList(String q) {
        try {

            createDataBase();
            SQLiteDatabase db = getReadableDatabase();

            String query = "SELECT * FROM recipes WHERE ";
            query += q;

            Cursor c = db.rawQuery(query, null);

            ArrayList<Integer> recipes = new ArrayList<Integer>();

            c.moveToFirst();
            while (!c.isAfterLast()) {
                recipes.add(c.getInt(0));
                c.moveToNext();
            }

            c.close();
            db.close();

            return recipes;
        } catch (Exception e) {
            e = new Exception("cannot get meals from DB");
        }
        return null;

    }

    public ArrayList<Integer> getRecipesByIngredients(int type, ArrayList<Integer> ingredients ) {
        try {

            createDataBase();
            SQLiteDatabase db = getReadableDatabase();

            String query = "select R._id, count(I._id) from Recipes R, recipesIngredients C where (";
            for(Integer i : ingredients){
                query += ", C.ingredientID = " + i;
            }
            query += ") and C.ingredientID = I._id and C.recipeID = R._id group by R._id order by count(I._id) desc ";

            Cursor c = db.rawQuery(query, null);

            ArrayList<Integer> recipes = new ArrayList<Integer>();

            c.moveToFirst();

            int l = ingredients.size();

            if(type == 0) {
                while (!c.isAfterLast() && (c.getInt(1) > l - 3)) {

                    recipes.add(c.getInt(0));
                    c.moveToNext();

                }
            }
            if(type == 1){
                while (!c.isAfterLast() && (c.getInt(1) == l )) {

                    recipes.add(c.getInt(0));
                    c.moveToNext();

                }
            }
            c.close();
            db.close();

            return recipes;
        } catch (Exception e) {
            e = new Exception("cannot get meals from DB");
        }
        return null;

    }


    public HashMap<Integer, String> getIngredientsMap() {
        try {

            createDataBase();
            SQLiteDatabase db = getReadableDatabase();

            String query = "SELECT _id,name FROM ingredients";
            Cursor c = db.rawQuery(query, null);

            HashMap<Integer, String> ingredients = new HashMap<>();

            c.moveToFirst();
            while (!c.isAfterLast()) {
                ingredients.put(c.getInt(0), c.getString(1));
                c.moveToNext();
            }

            c.close();
            db.close();

            return ingredients;
        } catch (Exception e) {
            e = new Exception("cannot get ingredients from DB");
        }
        return null;

    }

    public Meal getMealById(int idOut) {
        try {
            int id;
            String name = "";
            String recipe = "";
            String ingredients = "";
            Bitmap image = null;
            byte[] byteArray = null;


            createDataBase();
            SQLiteDatabase db = getReadableDatabase();

            String query = "SELECT * FROM recipes WHERE _id = " + idOut;


            Cursor c = db.rawQuery(query, null);

            c.moveToFirst();

            name = String.valueOf(c.getString(1));
            id = c.getInt(0);

            recipe = String.valueOf(c.getString(2));
            ingredients = String.valueOf(c.getString(12));

            byteArray = c.getBlob(3);
            c.close();
            db.close();


            try {
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                Meal meal = new Meal(id, name, recipe, ingredients, bmp);
                return meal;
            } catch (Exception e) {
                e.printStackTrace();
                Meal meal = new Meal(id, name, recipe, ingredients, null);
                return meal;
            }
        } catch (Exception e) {
            e = new Exception("cannot get meal from DB");
        }
        return null;
    }
}