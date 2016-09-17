package com.example.subtainishfaq.watchimage;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.subtainishfaq.watchimage.utility.TinyDB;

import java.util.ArrayList;

/**
 * Created by subtainishfaq on 3/24/16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here

            ArrayList<String> favourite=new ArrayList<String>();
            TinyDB db=new TinyDB(this);
            db.putListString("favourites",favourite);
            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }

}