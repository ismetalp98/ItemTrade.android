package app.anchorapp.bilkentacm.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import app.anchorapp.bilkentacm.R;

public class AddItem extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    TextInputLayout inputLayout;
    private Toolbar toolbar;
    ImageView view1;
    LinearLayout gallery;
    AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        gallery = findViewById(R.id.gallery);
        LayoutInflater inflate = LayoutInflater.from(this);
        for(int i = 0 ; i < 6 ; i++)
        {
            View view = inflate.inflate(R.layout.additem_photos,gallery,false);
            ImageView imageView = findViewById(R.id.additem_image);
            gallery.addView(view);
        }
        inputLayout = findViewById(R.id.additem_catagory);
        String[] dropdown = new String[]{
                "item1",
                "item2"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                AddItem.this,
                R.layout.dropdown_item,
                dropdown);

        autoCompleteTextView = findViewById(R.id.additem_autocomplete);
        autoCompleteTextView.setAdapter(adapter);
        toolbar = findViewById(R.id.additem_toolbar);
        setSupportActionBar(toolbar);


        /*RecyclerView recyclerView = findViewById(R.id.additem_recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<PhotoGroup> list = new ArrayList<>();

        PhotoGroup taskGroup1 = new PhotoGroup(getDrawable(R.drawable.ic_add_black_12dp));
        PhotoGroup taskGroup2 = new PhotoGroup(getDrawable(R.drawable.ic_add_black_12dp));
        PhotoGroup taskGroup3 = new PhotoGroup(getDrawable(R.drawable.ic_add_black_12dp));
        PhotoGroup taskGroup4 = new PhotoGroup(getDrawable(R.drawable.ic_add_black_12dp));
        PhotoGroup taskGroup5 = new PhotoGroup(getDrawable(R.drawable.ic_add_black_12dp));


        list.add(taskGroup1);
        list.add(taskGroup2);
        list.add(taskGroup3);
        list.add(taskGroup4);
        list.add(taskGroup5);


        recyclerView.setAdapter(new PhotoAdapter(list,getApplicationContext()));

        synchronized (recyclerView) {
            recyclerView.notifyAll();
        }*/
    }

    public void sendMessage(View view) {
        for(int i = 0 ; i < 6 ; i++) {
            if (view.equals(gallery.getChildAt(i))) {
                view1 = (ImageView) gallery.getChildAt(i);

                new MaterialAlertDialogBuilder(AddItem.this)
                        .setTitle("Title")
                        .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                askCameraPermission();
                            }
                        })
                        .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        }
    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }
        else
        {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE)
        {
            if(grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openCamera();
            }
            else
            {
                Toast.makeText(this,"Give permission",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }


    @SuppressLint("MissingSuperCall")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE)
        {
            Bitmap image =(Bitmap) data.getExtras().get("data");
            view1.setImageBitmap(image);
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();
    }
}
