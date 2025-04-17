package com.example.listview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    String[] name = {"Doraemon", "Conan", "Shin - cậu bé bút chì", "Gojo", "Naruto", "Anya"};
    String[] phoneNumber = {"222222222", "555555555", "888888888", "333333333", "999999999", "111111111"};
    int[] avatar = {R.drawable.doraemon, R.drawable.conan, R.drawable.shinosuke, R.drawable.gojo,
            R.drawable.naruto, R.drawable.anya};

    ArrayList<contact> mylist;
    ArrayList<contact> filteredList;
    MyArrayAdapter myadapter;
    ListView lv;
    ImageView addContactButton;
    ImageView avatarImageView;
    Button chooseAvatarButton;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;

    LinearLayout titleContainer;
    EditText searchInput;
    ImageView searchIcon;
    TextView contactCountTextView;
    ImageView backButton;
    private boolean isSearchMode = false;
    private String currentSearchText = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            mylist.add(new contact(Uri.parse("android.resource://" + getPackageName() + "/" + avatar[i]), name[i], phoneNumber[i]));
        }

        filteredList = new ArrayList<>(mylist);
        myadapter = new MyArrayAdapter(this, R.layout.list_item, filteredList);
        lv.setAdapter(myadapter);

        contactCountTextView = findViewById(R.id.contact_count_text_view);
        updateContactCount();
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            isSearchMode = false;
            showTitle();
            hideBackButton();
            showAllContacts();
            searchInput.setText("");
            adjustSearchInputWidth(false);
            showAddContactButton();
        });

        lv.setOnItemClickListener((parent, view, position, id) -> {
            contact selectedContact = filteredList.get(position);
            Intent intent = new Intent(MainActivity.this, SubActivity.class);

            intent.putExtra("name", selectedContact.getName());
            intent.putExtra("phoneNumber", selectedContact.getPhoneNumber());

            if (selectedContact.getAvatarUri() != null) {
                intent.putExtra("avatarUri", selectedContact.getAvatarUri().toString());
            } else {
                intent.putExtra("avatarUri", "");
            }

            startActivity(intent);
        });

        addContactButton = findViewById(R.id.add_contact_button);
        addContactButton.setOnClickListener(v -> showAddContactDialog());

        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Xóa liên hệ")
                    .setMessage("Bạn có chắc chắn muốn xóa liên hệ này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        mylist.remove(position);
                        filteredList.remove(position);
                        myadapter.notifyDataSetChanged();
                        updateContactCount();
                        Toast.makeText(MainActivity.this, "Đã xóa liên hệ", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        });

        titleContainer = findViewById(R.id.title_container);
        searchInput = findViewById(R.id.search_input);
        searchIcon = findViewById(R.id.search_icon);

        searchIcon.setOnClickListener(v -> {
            isSearchMode = true;
            showSearchInput();
            hideTitle();
            showBackButton();
            //hideAddContactButton(); //  Bỏ dòng này
            adjustSearchInputWidth(true);

            searchInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    currentSearchText = s.toString();
                    filterContacts(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            searchInput.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        performSearch();
                        return true;
                    }
                    return false;
                }
            });
        });
    }

    private void updateContactCount() {
        String countText = filteredList.size() + " người";
        contactCountTextView.setText(countText);
    }

    private void performSearch() {
        hideKeyboard(searchInput);
        showBackButton();

        myadapter.notifyDataSetChanged();

        searchInput.setText(currentSearchText);
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void hideTitle() {
        titleContainer.setVisibility(View.GONE);
    }

    private void showTitle() {
        titleContainer.setVisibility(View.VISIBLE);
    }

    private void showBackButton() {
        backButton.setVisibility(View.VISIBLE);
    }

    private void hideBackButton() {
        backButton.setVisibility(View.GONE);
    }

    private void showAllContacts() {
        filteredList.clear();
        filteredList.addAll(mylist);
        myadapter.notifyDataSetChanged();
        updateContactCount();
    }

    private void showSearchInput() {
        searchInput.setVisibility(View.VISIBLE);
        searchInput.requestFocus();
    }

    private void hideAddContactButton() {
        addContactButton.setVisibility(View.GONE);
    }

    private void showAddContactButton() {
        addContactButton.setVisibility(View.VISIBLE);
    }

    private void adjustSearchInputWidth(boolean isSearching) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) searchInput.getLayoutParams();
        if (isSearching) {
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        } else {
            params.width = 0;
        }
        searchInput.setLayoutParams(params);
    }


    private void filterContacts(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(mylist);
        } else {
            text = text.toLowerCase();
            for (contact item : mylist) {
                if (item.getName().toLowerCase().contains(text) ||
                        item.getPhoneNumber().contains(text)) {
                    filteredList.add(item);
                }
            }
        }
        myadapter.notifyDataSetChanged();
    }

    private void showContactDetails(contact contact) {
        Intent intent = new Intent(MainActivity.this, SubActivity.class);
        intent.putExtra("name", contact.getName());
        intent.putExtra("phoneNumber", contact.getPhoneNumber());
        if (contact.getAvatarUri() != null) {
            intent.putExtra("avatarUri", contact.getAvatarUri().toString());
        } else {
            intent.putExtra("avatarUri", "");
        }
        startActivity(intent);
    }

    private void showAddContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_contact_dialog, null);
        builder.setView(dialogView);

        final EditText nameEditText = dialogView.findViewById(R.id.name_edit_text);
        final EditText phoneEditText = dialogView.findViewById(R.id.phone_edit_text);
        avatarImageView = dialogView.findViewById(R.id.avatar_image_view);
        chooseAvatarButton = dialogView.findViewById(R.id.choose_avatar_button);

        chooseAvatarButton.setOnClickListener(v -> openImageChooser());

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            Uri avatarUri = selectedImageUri != null ? selectedImageUri : null;
            contact newContact = new contact(avatarUri, name, phone);
            mylist.add(newContact);
            filteredList.add(newContact);
            myadapter.notifyDataSetChanged();
            updateContactCount();
            Toast.makeText(MainActivity.this, "Đã thêm liên hệ", Toast.LENGTH_SHORT).show();
            selectedImageUri = null;
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                avatarImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("MainActivity", "Error setting image", e);
            }
        }
    }
}