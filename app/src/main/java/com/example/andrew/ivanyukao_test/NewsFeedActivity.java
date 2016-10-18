package com.example.andrew.ivanyukao_test;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.support.v4.content.CursorLoader;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import java.io.IOException;

public class NewsFeedActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>{
    DatabaseHandler database;
    private CustomCursorAdapter adapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        database=new DatabaseHandler(this);
        try {
            database.createDataBase();
        }
        catch(IOException ex) {
            throw new Error("Unable to create database");
        }
        database.open();
        // create an adapter and set list
        adapter = new CustomCursorAdapter(this, null, 0);
        listView = (ListView) findViewById(R.id.lvNews);
        listView.setAdapter(adapter);

        // creating loader for reading data
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new NewsLoader(this, database);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("top",listView.getFirstVisiblePosition());
        outState.putParcelable("listview",listView.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listView.setSelection(savedInstanceState.getInt("top"));

    }

    static class NewsLoader extends CursorLoader  {

        DatabaseHandler db;

        public NewsLoader(Context context, DatabaseHandler db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            return db.getAllNews();
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
        }

        @Override
        protected void onStopLoading() {
            super.onStopLoading();
        }
    }
}
