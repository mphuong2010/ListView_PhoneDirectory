package com.example.listview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log; // Import Log

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<contact> {
    Activity context;
    int idlayout;
    ArrayList<contact> mylist;

    public MyArrayAdapter(Activity context, int idlayout, ArrayList<contact> mylist) {
        super(context, idlayout, mylist);
        this.context = context;
        this.idlayout = idlayout;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        LayoutInflater myInflactor = context.getLayoutInflater();
        convertView = myInflactor.inflate(idlayout, null);
        contact mycontact = mylist.get(position);

        ImageView avatar = convertView.findViewById(R.id.contact_avatar);
        TextView txt_name = convertView.findViewById(R.id.txt_name);
        TextView txt_phone = convertView.findViewById(R.id.txt_phone);

        try {
            if (mycontact.getAvatarUri() != null) {
                avatar.setImageURI(mycontact.getAvatarUri());
            } else {
                avatar.setImageResource(R.drawable.img);
            }
        } catch (Exception e) {
            Log.e("MyArrayAdapter", "Error setting image", e);
            avatar.setImageResource(R.drawable.img); // Set default image on error
        }

        txt_name.setText(mycontact.getName());
        txt_phone.setText(mycontact.getPhoneNumber());
        return convertView;
    }
}