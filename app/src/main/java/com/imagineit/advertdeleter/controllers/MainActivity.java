package com.imagineit.advertdeleter.controllers;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.imagineit.advertdeleter.R;
import com.imagineit.advertdeleter.controllers.Adapters.MyCursorAdapter;
import com.imagineit.advertdeleter.controllers.Loaders.MyCursorLoader;
import com.imagineit.advertdeleter.models.SMSMANAGER;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor>, RadioGroup.OnCheckedChangeListener {

    private Button btn_scan_sms;
    private SMSMANAGER smsmanager;
    private MyCursorAdapter smsCursorAdapter;
    private ListView lv_all_sms;
    private RadioGroup rg_sms_locations;
    private String sms_location="inbox";
    // Create Inbox box URI
    Uri inboxURI;

    // List required columns
    String[] reqCols = new String[]{"_id", "address", "body", "date"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context=getApplicationContext();
        btn_scan_sms=findViewById(R.id.btn_scan_sms);
        lv_all_sms=findViewById(R.id.lv_all_sms);
        rg_sms_locations=findViewById(R.id.rg_sms_locations);
        btn_scan_sms.setOnClickListener(this);
        rg_sms_locations.setOnCheckedChangeListener(this);
        smsmanager=new SMSMANAGER(context);

        smsCursorAdapter= new MyCursorAdapter(context, "");
        lv_all_sms.setAdapter(smsCursorAdapter);
    }


    @Override
    public void onClick(View view) {
        int btn_clicked_id=view.getId();
        if(btn_clicked_id==R.id.btn_scan_sms){
            /*
            / Scan for undesired sms
            */
            Toast.makeText(this, "Scanning . . . ", Toast.LENGTH_SHORT).show();

            try {

                getAllSmsAndSetView(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    void getAllSmsAndSetView(MainActivity mainActivity){
        Log.i("Step", "getallsms method execution");
        //create adapter and give elements to list view
        if(LoaderManager.getInstance(this).getLoader(1) == null)
        {
            Log.i("Step", "Loader init");
        LoaderManager.getInstance(this).initLoader(1,null,mainActivity);
        }
        else
        { Log.i("Step", "loader restart");
            LoaderManager.getInstance(this).restartLoader(1,null,this);
        }


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        inboxURI= Uri.parse("content://sms/" + sms_location);
        Log.i("Step", "OncreateLoader method execution with URI: "+ inboxURI.toString());
        return new MyCursorLoader(getApplicationContext(),smsmanager,inboxURI ,reqCols);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.i("Step", "OnLoadFinished method execution");
        smsCursorAdapter.swapCursor(data);
        SetResultsFound();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.i("Step", "OnLoadReset method execution with: adapter count "+smsCursorAdapter.getCount());
        smsCursorAdapter.swapCursor(null);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        Log.i("Step", "onCheckedChanged method execution with i: "+i);
        switch (i){
            case R.id.rb_inbox: sms_location="inbox" ;
                    break;
            case R.id.rb_sent:  sms_location="sent";
                    break;
            case R.id.rb_outbox:    sms_location="outbox";
                    break;
        }
    }

    void SetResultsFound(){

        smsCursorAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Scan results: "+smsmanager.getCurrent_scan_result()+" messages", Toast.LENGTH_SHORT).show();
    }

}
