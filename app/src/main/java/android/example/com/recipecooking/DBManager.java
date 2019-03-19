package android.example.com.recipecooking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    public DBManager(Context context){
        this.context = context;
    }

    public DBManager open(){
        dbHelper = new DBHelper(context, 1);
        database = dbHelper.getWritableDatabase();
        return  this;
    }

    public void insert(String food, String description, String date, String time, String comment){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_FOOD, food);
        values.put(DBHelper.COL_DESCRIPTION, description);
        values.put(DBHelper.COL_DATE, date);
        values.put(DBHelper.COL_TIME, time);
        values.put(DBHelper.COL_COMMENT, comment);

        database.insert(DBHelper.TABLE_NAME,
                null,
                values);
    }

    public Cursor select(String where, String[] whereArgs, String order){
        String[] columns = new String[]{
                DBHelper.COL_ID,
                DBHelper.COL_FOOD,
                DBHelper.COL_DESCRIPTION,
                DBHelper.COL_DATE,
                DBHelper.COL_TIME,
                DBHelper.COL_COMMENT
        };
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                columns,where,whereArgs,null,
                null,order);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public void delete(int id){
        database.delete(DBHelper.TABLE_NAME,
                DBHelper.COL_ID + "= "+id,
                null);
    }

    public void update(int id, String food, String description, String date, String time, String commment){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COL_FOOD, food);
        contentValues.put(DBHelper.COL_DESCRIPTION, description);
        contentValues.put(DBHelper.COL_DATE, date);
        contentValues.put(DBHelper.COL_TIME, time);
        contentValues.put(DBHelper.COL_COMMENT, commment);
        database.update(DBHelper.TABLE_NAME,
                contentValues, DBHelper.COL_ID + "=" +id,
                null);
    }

    public void close(){
        database.close();
    }
}
