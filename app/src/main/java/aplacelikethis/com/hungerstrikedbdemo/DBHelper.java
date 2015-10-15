package aplacelikethis.com.hungerstrikedbdemo;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String RECIPES_TABLE_NAME = "recipes";
    public static final String RECIPES_COLUMN_ID = "id";
    public static final String RECIPES_COLUMN_NAME = "name";
    public static final String RECIPES_COLUMN_INGREDIENT1 = "ingredient1";
    public static final String RECIPES_COLUMN_INGREDIENT2 = "ingredient2";
    public static final String RECIPES_COLUMN_INGREDIENT3 = "ingredient3";
    public static final String RECIPES_COLUMN_CALORIES = "calories";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table recipes " +
                        "(id integer primary key, name text,calories text,ingredient1 text, ingredient2 text,ingredient3 text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }

    public boolean insertRecipe(String name, String calories, String ingredient1, String ingredient2, String ingredient3)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("calories", calories);
        contentValues.put("ingredient1", ingredient1);
        contentValues.put("ingredient2", ingredient2);
        contentValues.put("ingredient3", ingredient3);
        db.insert("recipes", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from recipes where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, RECIPES_TABLE_NAME);
        return numRows;
    }

    public boolean updateRecipe(Integer id, String name, String calories, String ingredient1, String ingredient2, String ingredient3)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("calories", calories);
        contentValues.put("ingredient1", ingredient1);
        contentValues.put("ingredient2", ingredient2);
        contentValues.put("ingredient3", ingredient3);
        db.update("recipes", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteRecipe(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("recipes",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllRecipes()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from recipes", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(RECIPES_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}