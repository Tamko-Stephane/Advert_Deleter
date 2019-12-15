package com.imagineit.advertdeleter.controllers.Loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

//import com.corpecca.genericdrugs.model.Query_Database_GreenDao;

import com.imagineit.advertdeleter.models.SMSMANAGER;

import androidx.annotation.NonNull;
import androidx.loader.content.CursorLoader;

//import android.support.annotation.NonNull;
//import androidx.core.content.CursorLoader;

public class MyCursorLoader extends CursorLoader {
    private SMSMANAGER smsmanager;
    Uri query_uri;
    String[] queries;
    public MyCursorLoader(@NonNull Context context, SMSMANAGER smsmanager, Uri uri, String[] queries) {
        super(context);
        this.smsmanager=smsmanager;
        query_uri=uri;
        this.queries=queries;
    }

    @Override
    public Cursor loadInBackground() {
        return smsmanager.getCursorForURIQuery(query_uri,queries);//Query_Database_GreenDao.getCountryCursor();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
       smsmanager.StopCursor(query_uri,queries);
        // Query_Database_GreenDao.stopCursor();
    }
}
