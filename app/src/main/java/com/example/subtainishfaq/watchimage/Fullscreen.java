package com.example.subtainishfaq.watchimage;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.cache.DiskLruBasedCache;
import com.android.volley.cache.plus.SimpleImageLoader;
import com.android.volley.ui.NetworkImageViewPlus;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.subtainishfaq.watchimage.utility.TinyDB;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fullscreen extends AppCompatActivity implements View.OnClickListener, ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    TextView tilte;
    ImageButton favButton;
    ImageButton wallpaperButton;
    ImageButton saveButton;
    Bitmap reponseBitMap;
    String TitleString;
   boolean isfav;
    TinyDB db;
    private String publicUrl;
    String entityKey="unknown";
    private ImageButton whatsButon;
    private ImageButton fbButton;
    private ImageButton shareButton;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Your entity key. May be passed as a Bundle parameter to your activity

            entityKey = getIntent().getStringExtra("imageUrl");



        // Now set the view for your activity to be the wrapped view.
        setContentView( R.layout.activity_fullscreen);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
       tilte= (TextView) tb.findViewById(R.id.toolbar_title);
       favButton= (ImageButton) tb.findViewById(R.id.favbutton);
       wallpaperButton= (ImageButton) tb.findViewById(R.id.wallpaperButton);
       saveButton= (ImageButton) tb.findViewById(R.id.saveButton);
        favButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        wallpaperButton.setOnClickListener(this);

        whatsButon=(ImageButton) findViewById(R.id.whatsbutton);
        fbButton=(ImageButton) findViewById(R.id.fbbutton);
         shareButton = (ImageButton) findViewById(R.id.normalshare);

        whatsButon.setOnClickListener(this);
        fbButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);


        setSupportActionBar(tb);
        //
        db=new TinyDB(this);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false);
         ab.setHideOnContentScrollEnabled(false);//

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        if(getIntent()!=null && getIntent().getStringArrayListExtra("imageUrls")!=null)
        {
          ArrayList<String> urls= getIntent().getStringArrayListExtra("imageUrls");
          ArrayList<String> names= getIntent().getStringArrayListExtra("names");

            for(int i=0 ; i<urls.size();i++){
                TextSliderView textSliderView = new TextSliderView(this);
                Log.d("urls",urls.size()+" got it");
                Log.d("urls",names.size()+" got it");
                // initialize a SliderLayout
                textSliderView
                        .description(names.get(i))
                        .image(urls.get(i))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra",names.get(i));

                mDemoSlider.addSlider(textSliderView);

            }
            mDemoSlider.setCurrentPosition(getIntent().getIntExtra("position",0));
        }


        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
//        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.stopAutoCycle();



        /*if(getIntent()!=null && getIntent().getStringExtra("imageUrl")!=null)
        {
            NetworkImageViewPlus rowImage= (NetworkImageViewPlus) findViewById(R.id.imageFullScreen);


            DiskLruBasedCache.ImageCacheParams cacheParams = new DiskLruBasedCache.ImageCacheParams(this, "CacheDirectory");
            cacheParams.setMemCacheSizePercent(0.5f);

            SimpleImageLoader mImageFetcher = new SimpleImageLoader(this,  cacheParams);
            mImageFetcher.setMaxImageSize(600);
            
            publicUrl=getIntent().getStringExtra("imageUrl");

            rowImage.setImageUrl(publicUrl, mImageFetcher);
            rowImage.setDefaultImageResId(R.drawable.preloader);
           rowImage.setFadeInImage(true);
            rowImage.setImageListener(new Response.Listener<BitmapDrawable>() {
                @Override
                public void onResponse(BitmapDrawable response) {
                    Fullscreen.this.reponseBitMap = response.getBitmap();

                }
            });
            TitleString=getIntent().getStringExtra("name");
            tilte.setText(TitleString);



        }
*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.favbutton:
                setTofav();
                return;
            case R.id.wallpaperButton:
                setwallpaperCustom();
                return;
            case R.id.saveButton:
                saveToexternal();
                return;
            case R.id.whatsbutton:
                whatsShare();
                return;
            case R.id.fbbutton:
                share_screen();
                return;
            case R.id.normalshare:
                simpleShare();
                return;


        }

    }

    private void simpleShare()
    {
        if(reponseBitMap!=null){
            Bitmap icon = this.reponseBitMap;

            String path = MediaStore.Images.Media.insertImage(getContentResolver(), icon, "title", null);
            Uri screenshotUri = Uri.parse(path);

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.putExtra(Intent.EXTRA_STREAM,screenshotUri);

            startActivity(Intent.createChooser(share, "Share Image"));

        }
    }


    private void whatsShare()
    {  if(reponseBitMap!=null){
        Bitmap icon = this.reponseBitMap;
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + "temporary_file.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.flush();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(TitleString!=null)
        share.putExtra(Intent.EXTRA_TEXT,TitleString+ " Shared from Sailor app");
        else
            share.putExtra(Intent.EXTRA_TEXT," Shared from Sailor app");


        share.putExtra(Intent.EXTRA_STREAM,
                Uri.parse(f.getAbsolutePath()));
        share.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(share, "Share Image"));
    }
    }


    public void share_screen( ) {
        if(reponseBitMap!=null) {
           ;

            Bitmap icon = this.reponseBitMap;
         /*   ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "tempor.jpeg");
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                fo.flush();
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), icon, "title", null);
            Uri screenshotUri = Uri.parse(path);

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");

            if (TitleString != null)
                share.putExtra(Intent.EXTRA_TEXT, TitleString + " Shared from Sailor app");
            else
                share.putExtra(Intent.EXTRA_TEXT, " Shared from Sailor app");


            share.putExtra(Intent.EXTRA_STREAM,screenshotUri);


            PackageManager pm = getApplicationContext().getPackageManager();
            List<ResolveInfo> activityList = pm.queryIntentActivities(share,
                    0);
            for (final ResolveInfo app : activityList) {
                if ((app.activityInfo.packageName).contains("com.facebook.katana")) {

                    {

                    final ActivityInfo activity = app.activityInfo;
                    final ComponentName name = new ComponentName(
                            activity.applicationInfo.packageName, activity.name);
                    share.addCategory(Intent.CATEGORY_LAUNCHER);
                    share.setComponent(name);
                    startActivity(share);
                    break;
                    }

                                                 }

                }

            }

    }


    private void setTofav()
    {
        //toggle fav favnot and save on destroy
        if(!isfav)
        {
            favButton.setImageResource(android.R.drawable.star_big_on);
            isfav=true;
        }
        else
        {
            favButton.setImageResource(android.R.drawable.star_big_off);
            isfav=false;
        }
    }

    private void saveToexternal()
    {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Save File?")
                .setContentText("Do You want to Save File!")
                .setConfirmText("Yes!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {


                        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                                "/SaudiNews";
                        File dir = new File(file_path);
                        if(!dir.exists())
                            dir.mkdirs();
                        File file = new File(dir,  TitleString+ ".png");
                        FileOutputStream fOut = null;
                        try {
                            fOut = new FileOutputStream(file);


                            Fullscreen.this.reponseBitMap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                            fOut.flush();
                            fOut.close();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            sDialog
                                    .showCancelButton(false)
                                    .setTitleText("Not Saved!")
                                    .setContentText("File is Not Saved!")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        }

                        sDialog
                                .showCancelButton(false)
                                .setTitleText("Saved!")
                                .setContentText("File is Saved!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                    }
                })
                .setCancelText("No!")
                .show();

    }

    private void setwallpaperCustom() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Change Wallpaper?")
                .setContentText("This will change your wallpaper!")
                .setConfirmText("Yes,Change it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        WallpaperManager myWallpaperManager
                                = WallpaperManager.getInstance(getApplicationContext());
                        try {
                            if(reponseBitMap!=null)
                                myWallpaperManager.setBitmap(reponseBitMap);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        sDialog
                                .showCancelButton(false)
                                .setTitleText("Changed!")
                                .setContentText("Your Wallpaper is Changed!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                    }
                })
              .setCancelText("No!")
                .show();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isfav) {
            ArrayList<String> favs = db.getListString("favourites");
           if(!favs.contains(publicUrl))
            favs.add(publicUrl);
            db.putListString("favourites",favs);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
}
