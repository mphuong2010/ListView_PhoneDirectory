package com.example.listview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String name[] ={"Doraemon", "Conan", "Shin - cậu bé bút chì", "Gojo", "Naruto", "Anya"};
    String phoneNumber[] ={"222222222", "555555555", "888888888", "333333333", "999999999", "111111111"};
    int avatar[] = {R.drawable.doraemon,R.drawable.conan,R.drawable.shinosuke,R.drawable.gojo,
            R.drawable.naruto,R.drawable.anya};
    ArrayList<contact> mylist; // Khai báo mảng chính
    MyArrayAdapter myadapter; //Khai báo Adapter
    ListView lv; //Khai báo Listview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // Đảm bảo layout này chứa ListView
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        for (int i = 0; i <name.length;i++)
        {
            mylist.add(new contact(avatar[i], name[i], phoneNumber[i]));
        }
        myadapter = new MyArrayAdapter(this,R.layout.list_item,mylist);
        lv.setAdapter(myadapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long
                    id) {
                Intent myintent = new Intent(MainActivity.this,SubActivity.class);
                myintent.putExtra("avatar",avatar[position]);
                myintent.putExtra("name",name[position]);
                myintent.putExtra("phoneNumber",phoneNumber[position]);
                startActivity(myintent);
            }
        });


    }

}