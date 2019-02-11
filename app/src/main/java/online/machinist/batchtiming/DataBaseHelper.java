package online.machinist.batchtiming;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, "mydb", null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table batchTime(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,day text,time text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists batchTime");
        onCreate(db);
    }
}
