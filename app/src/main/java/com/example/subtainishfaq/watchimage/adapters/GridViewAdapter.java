package com.example.subtainishfaq.watchimage.adapters;

/**
 * Created by subtainishfaq on 3/23/16.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.cache.DiskLruBasedCache;
import com.android.volley.cache.plus.SimpleImageLoader;
import com.android.volley.ui.NetworkImageViewPlus;
import com.example.subtainishfaq.watchimage.R;
import com.example.subtainishfaq.watchimage.utility.DiskLruImageCache;

import java.util.ArrayList;

/**
 * Created by Belal on 12/22/2015.
 */
public class GridViewAdapter extends BaseAdapter {

    //Imageloader to load images


    //Context
    private Context context;
    DiskLruBasedCache.ImageCacheParams cacheParams;
    DiskLruImageCache myCache;
    SimpleImageLoader mImageFetcher;

    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> images;


    public GridViewAdapter (Context context, ArrayList<String> images){
        //Getting all the values
        this.context = context;
        this.images = images;
        cacheParams = new DiskLruBasedCache.ImageCacheParams(context, "CacheDirectory");
        cacheParams.setMemCacheSizePercent(0.5f);
         mImageFetcher = new SimpleImageLoader(context,cacheParams);
        mImageFetcher.setMaxImageSize(300);

        //  myCache=cache;



    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Creating a linear layout
     LayoutInflater layoutinf= LayoutInflater.from(context);
        LinearLayout linearLayout= (LinearLayout) layoutinf.inflate(R.layout.grid_item_layout, parent, false);
        NetworkImageViewPlus rowImage= (NetworkImageViewPlus) linearLayout.findViewById(R.id.imageRowItem);



        rowImage.setImageUrl(images.get(position), mImageFetcher);
        rowImage.setDefaultImageResId(R.drawable.preloader);



        return linearLayout;
    }
}