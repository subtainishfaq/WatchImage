package com.example.subtainishfaq.watchimage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.subtainishfaq.watchimage.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by subtainishfaq on 3/24/16.
 */
public class CustomListAdapter extends BaseAdapter {

    //Context
    private Context context;

    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> categories;


    public CustomListAdapter (Context context, ArrayList<String> images){
        //Getting all the values
        this.context = context;
        this.categories = images;

    }


    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        LayoutInflater layoutinf= LayoutInflater.from(context);

        rowView = layoutinf.inflate(R.layout.list_row, null);
        TextView tv= (TextView) rowView.findViewById(R.id.categoryLabel);
        tv.setText(categories.get(position));

        return rowView;
    }
}
