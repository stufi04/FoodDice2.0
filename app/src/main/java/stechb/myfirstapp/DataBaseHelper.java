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



/**
 * Created by iange_000 on 04-Feb-15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    //Change the package name when finish
    //private static String DB_PATH = Context.getFilesDir().getPath();
    private static String DB_PATH = "/data/data/stechb.myfirstapp/databases/";
    //private static String DB_PATH = "/app/src/main/assets";
    //TODO change the name with the real name
    private static String DB_NAME = "recipes.db";
    private final Context myContext;
    private SQLiteDatabase myDB;

    //Constructor
    public DataBaseHelper(Context context){
        super(context, DB_NAME,null,1);
        this.myContext = context;}


    //Create an empty DB and copy the existing one in it
    public void createDataBase() throws IOException{
        boolean dbExists = checkDataBase();
        if(dbExists){
            //Do nothing
        }
        else{

            this.getReadableDatabase();
            try
            {
                this.close();
                copyDataBase();
            }catch (IOException e){
                throw new Error("Can not copy DB");
            }
        }
    }

    public boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e){
            // DB do not exist
        }
        if (checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true :false;
    }
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
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

        if(myDB != null)
            myDB.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getMealFromDB(){
        try {

            String dbString = "";
            createDataBase();
            SQLiteDatabase db = getReadableDatabase();

            String query = "SELECT * FROM recipes WHERE recipes.name = 'testname';";


            Cursor c = db.rawQuery(query, null);

            int i = 0;
            while (!c.isAfterLast()) {
                i++;
                c.moveToNext();

            }
            c.moveToFirst();
            Integer r = new Integer((int) (Math.random() * i-1));
            c.move(r);

            dbString = String.valueOf(c.getString(1));
            db.close();
            return dbString;
        }
        catch (Exception e) {return "Musaka";}
    }
    public Meal getRandomMeal(){
        try {

            String name = "";
            String recipe = "";
            Bitmap image = null;
            byte[] byteArray = null;
            Integer id ;

            createDataBase();
            SQLiteDatabase db = getReadableDatabase();

            String query = "SELECT * FROM recipes WHERE 1";


            Cursor c = db.rawQuery(query, null);

            int i = 0;
            while (!c.isAfterLast()) {
                i++;
                c.moveToNext();

            }
            c.moveToFirst();

                Integer r = new Integer((int) (Math.random() * i - 1));
                c.move(r);
                name = String.valueOf(c.getString(1));



            recipe = String.valueOf(c.getString(2));

            byteArray = c.getBlob(3);
            c.close();
            db.close();


            try{
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Meal meal = new Meal(name,recipe,bmp);
                Log.d("MYMSG","FROM TRY"+bmp.getByteCount());
            return meal;}
            catch (Exception e){
                e.printStackTrace();
                Meal meal = new Meal(name,recipe,null);
                Log.d("MYMSG","FROM FROMCATCH");
                return meal;
            }
        }
        catch (Exception e) {e = new Exception("cannot get meal from DB");}
        return null;
    }
}