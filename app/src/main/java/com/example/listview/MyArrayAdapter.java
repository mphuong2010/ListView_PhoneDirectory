package com.example.listview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<contact> {
    Activity context;
    int idlayout;
    ArrayList<contact> mylist;
    // Tạo Constructor để MainActivity gọi đến và truyền các tham số
    public MyArrayAdapter(Activity context, int idlayout, ArrayList<contact> mylist) {
        super(context, idlayout, mylist);
        this.context = context;
        this.idlayout = idlayout;
        this.mylist = mylist;
    }
    //Gọi đến hàm getView để xây dựng lại Adapter
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        LayoutInflater myInflactor = context.getLayoutInflater();
        convertView = myInflactor.inflate(idlayout,null);
        contact mycontact = mylist.get(position);
// Ứng với mỗi thuộc tính, ta thực hiện 2 việc
//- Gán id
        ImageView avatar = convertView.findViewById(R.id.contact_avatar);
// - Thiết lập dữ liệu
        avatar.setImageResource(mycontact.getAvatar());
//-------------textview-----------
        TextView txt_name = convertView.findViewById(R.id.txt_name);
        txt_name.setText(mycontact.getName());

        TextView txt_phone = convertView.findViewById(R.id.txt_phone);
        txt_phone.setText(mycontact.getPhoneNumber());
        return convertView;
    }
}