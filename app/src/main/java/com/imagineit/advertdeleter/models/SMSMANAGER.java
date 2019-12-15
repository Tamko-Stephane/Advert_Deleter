package com.imagineit.advertdeleter.models;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.net.URI;

public class SMSMANAGER {
    private Cursor sms_cursor;
    private Context app_context;
    private int current_scan_result=0;

    public int getCurrent_scan_result() {
        return current_scan_result;
    }

    public SMSMANAGER(Context app_context) {
        this.app_context = app_context;
    }

    public Cursor getCursorForURIQuery(Uri uri, String[] query){
        try {
            sms_cursor=app_context.getContentResolver().query(uri,query,null,null,null);
            if(sms_cursor!=null)
            current_scan_result=sms_cursor.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sms_cursor;
    }

    public void StopCursor(Uri uri, String[] query){

        try {
            app_context.getContentResolver().query(uri,query,null,null,null).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
