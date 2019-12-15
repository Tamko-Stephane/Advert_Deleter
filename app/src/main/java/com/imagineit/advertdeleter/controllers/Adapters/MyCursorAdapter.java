package com.imagineit.advertdeleter.controllers.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.imagineit.advertdeleter.R;

import java.util.Calendar;

public class MyCursorAdapter extends CursorAdapter {
    private final String location;
   public class ViewHolderCurSorAdapter{
        TextView tv_sms_send_date;
        TextView tv_sms_sender;
        TextView tv_sms_msg;

       public TextView gettv_sms_send_date() {
           return tv_sms_send_date;
       }
   }

    public MyCursorAdapter(Context context, String location) {
        super(context, null, false);
        this.location=location;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view;

           view= LayoutInflater.from(context).inflate(R.layout.sms_info_line,parent,false);

            //view= LayoutInflater.from(context).inflate(R.layout.country_name_spinner_item_orange,parent,false);

        ViewHolderCurSorAdapter viewHolderCA=new ViewHolderCurSorAdapter();
        viewHolderCA.tv_sms_send_date=view.findViewById(R.id.tv_sms_send_date);
        viewHolderCA.tv_sms_sender=view.findViewById(R.id.tv_sms_sender);
        viewHolderCA.tv_sms_msg=view.findViewById(R.id.tv_sms_msg);
        view.setTag(viewHolderCA);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolderCurSorAdapter viewHolderCurSorAdapter=(ViewHolderCurSorAdapter) view.getTag();
        String sms_body=cursor.getString(cursor.getColumnIndex("body"));
        String sms_sent_date=cursor.getString(cursor.getColumnIndex("date"));
        String sms_sender_address=cursor.getString(cursor.getColumnIndex("address"));
        viewHolderCurSorAdapter.tv_sms_send_date.setText(getDateFromMillis(sms_sent_date));
        viewHolderCurSorAdapter.tv_sms_sender.setText(sms_sender_address);
        viewHolderCurSorAdapter.tv_sms_msg.setText(sms_body);
    }

    private String getDateFromMillis(String date_in_millis){
        long millis_date=10;
        if(!date_in_millis.isEmpty())
        millis_date=Long.valueOf(date_in_millis);

       Calendar calendar=Calendar.getInstance();
       calendar.setTimeInMillis(millis_date);
        Log.i("Step", "getStaeFromMillis method execution: Year "+ calendar.get(Calendar.YEAR));
        return calendar.get(Calendar.YEAR)+"/"
                +calendar.get(Calendar.MONTH)
                +"/"+calendar.get(Calendar.DAY_OF_MONTH)+" "
                + calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE);
    }
}
