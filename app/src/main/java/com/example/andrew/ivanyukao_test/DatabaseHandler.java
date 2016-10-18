package com.example.andrew.ivanyukao_test;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;

    // Database Name
    private static final String DB_NAME = "NewsDB";

    // Table name
    private static final String TABLE_NEWS = "news";

    private static String DB_PATH = null;

    // News Table Columns names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_IMG = "image";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TEXT = "text";

    private Context context;

    private SQLiteDatabase mDB;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public static String getColumnId() {
        return COLUMN_ID;
    }

    public static String getColumnImg() {
        return COLUMN_IMG;
    }

    public static String getColumnTitle() {
        return COLUMN_TITLE;
    }

    public static String getColumnText() {
        return COLUMN_TEXT;
    }

    public void createDataBase() throws IOException {
        //check database existence
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public Cursor getAllNews() throws SQLException {
        return mDB.query(TABLE_NEWS, null, null, null, null, null, null);
    }

    // open connection
    public synchronized void open() {
        mDB = getWritableDatabase();
    }

    // close connection
    public synchronized void close() {
        mDB.close();
    }

}
