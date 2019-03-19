package android.example.com.recipecooking;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Currency";
    public static final String COL_ID = "_id";
    public static final String COL_FOOD = "food";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";
    public static final String COL_COMMENT = "comment";
    public static final String DB_NAME = "asgment3.db";

    public DBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE "+ TABLE_NAME+" (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_FOOD + " TEXT NOT NULL," +
                COL_DESCRIPTION + " TEXT," +
                COL_DATE + " TEXT NOT NULL," +
                COL_COMMENT + " TEXT," +
                COL_TIME + " TEXT NOT NULL);";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTs "+TABLE_NAME);
        onCreate(db);
    }
}
