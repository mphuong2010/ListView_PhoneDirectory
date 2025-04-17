package com.example.listview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View; // Thêm import cho View
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SubActivity extends AppCompatActivity {
    TextView detail_name, detail_phone;
    ImageView detail_avatar;
    ImageView backButton; // Thêm ImageView cho nút quay lại
    int avatar_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        detail_name = findViewById(R.id.detail_name);
        detail_avatar = findViewById(R.id.detail_avatar);
        detail_phone = findViewById(R.id.detail_phone);
        backButton = findViewById(R.id.back_button); // Khởi tạo ImageView

        Intent myintent = getIntent();
        String name_contact = myintent.getStringExtra("name");
        avatar_contact = myintent.getIntExtra("avatar", R.drawable.img);
        String phone_contact = myintent.getStringExtra("phoneNumber");
        detail_name.setText(name_contact);
        detail_avatar.setImageResource(avatar_contact);
        detail_phone.setText(phone_contact);

        // Thêm OnClickListener cho nút quay lại
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Hoặc finish();
            }
        });
    }
}