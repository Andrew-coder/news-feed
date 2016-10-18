package com.example.andrew.ivanyukao_test;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomCursorAdapter extends CursorAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        ImageView image = (ImageView) view.findViewById(R.id.ivImg);
        byte[] bitmapdata=cursor.getBlob(cursor.getColumnIndex(DatabaseHandler.getColumnImg()));
        //converting byte array into bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        image.setImageBitmap(Bitmap.createScaledBitmap(bitmap,120,120,false));

        TextView title = (TextView) view.findViewById(R.id.newsTitle);
        title.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.getColumnTitle())));

        final TextView text = (TextView)view.findViewById(R.id.newsText);
        final StringBuilder textNews =  new StringBuilder(cursor.getString(cursor.getColumnIndex(DatabaseHandler.getColumnText())));
        text.setText(textNews.substring(0,45)+"...");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText(textNews);
            }
        });
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mInflater.inflate(R.layout.listitem, viewGroup, false);
    }
}
