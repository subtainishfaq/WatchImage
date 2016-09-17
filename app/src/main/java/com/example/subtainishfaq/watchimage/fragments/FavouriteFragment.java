package com.example.subtainishfaq.watchimage.fragments;

/**
 * Created by subtainishfaq on 3/23/16.
 */
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.subtainishfaq.watchimage.Fullscreen;
import com.example.subtainishfaq.watchimage.Home;
import com.example.subtainishfaq.watchimage.R;
import com.example.subtainishfaq.watchimage.adapters.GridViewAdapter;
import com.example.subtainishfaq.watchimage.utility.TinyDB;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class FavouriteFragment extends Fragment implements AdapterView.OnItemClickListener{

        GridView gridList;
        ArrayList<String> urlPublic;
        ArrayList<String> names;
     SweetAlertDialog pDialog;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.favourite_fragment, container, false);
        TinyDB db=new TinyDB(getContext());
        urlPublic=db.getListString("favourites");

        gridList= (GridView) v.findViewById(R.id.gridViewfav);
        gridList.setOnItemClickListener(this);
        gridList.setAdapter(new GridViewAdapter(getContext(), urlPublic));
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    ((Home) getActivity()).setCurrentHome();
                    return true;
                }
                return false;
                //
            }
        });


        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        Intent intent=new Intent(getContext(),Fullscreen.class);
        intent.putExtra("imageUrl", urlPublic.get(position));
        intent.putExtra("name", "fav-" + position);
        startActivity(intent);
    }


}