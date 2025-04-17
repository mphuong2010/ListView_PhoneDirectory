package com.example.listview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SubActivity extends AppCompatActivity {
    TextView detail_name, detail_phone;
    ImageView detail_avatar;
    ImageView backButton;
    Uri avatar_contact_uri;
    String avatarUriString;

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
        backButton = findViewById(R.id.back_button);

        Intent myintent = getIntent();
        String name_contact = myintent.getStringExtra("name");
        String phone_contact = myintent.getStringExtra("phoneNumber");
        avatarUriString = myintent.getStringExtra("avatarUri");

        if (name_contact != null) {
            detail_name.setText(name_contact);
        } else {
            detail_name.setText("No name");
        }

        if (phone_contact != null) {
            detail_phone.setText(phone_contact);
        } else {
            detail_phone.setText("No phone");
        }

        try {
            if (avatarUriString != null && !avatarUriString.isEmpty()) {
                avatar_contact_uri = Uri.parse(avatarUriString);
                detail_avatar.setImageURI(avatar_contact_uri);
            } else {
                detail_avatar.setImageResource(R.drawable.img);
            }
        } catch (Exception e) {
            Log.e("SubActivity", "Error setting image", e);
            detail_avatar.setImageResource(R.drawable.img);
        }

        backButton.setOnClickListener(v -> onBackPressed());
    }
}
