package app.anchorapp.bilkentacm.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import app.anchorapp.bilkentacm.R;

public class Tab3Fragment extends Fragment {


    ImageView profileimage;
    TextView name, mail, phonenumber;
    String nametoedit, lastnametoedit;
    Button change, edit;
    FirebaseAuth fauth;
    ImageButton btnaddphonenumber;
    Button btnverifiy;
    FirebaseFirestore fStore;
    ImageButton profilepicbtn;
    StorageReference storageReference;
    FirebaseUser fuser;
    public static final int GALLERY_REQUEST_CODE = 105;


    public Tab3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        ////// Indicators //////////
        profileimage = view.findViewById(R.id.profileimage);
        name = view.findViewById(R.id.tw_name);
        mail = view.findViewById(R.id.tw_mail);
        btnaddphonenumber = view.findViewById(R.id.btn_add_phonenumber);
        phonenumber = view.findViewById(R.id.tw_phone);
        change = view.findViewById(R.id.btnchangepss);
        edit = view.findViewById(R.id.btneditprofile);
        profilepicbtn = view.findViewById(R.id.propic);
        fauth = FirebaseAuth.getInstance();
        fuser = fauth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        ////// Fletch data from database//////////
        final DocumentReference documentReference = fStore.collection("Users").document(fuser.getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                nametoedit = documentSnapshot.getString("name");
                lastnametoedit = documentSnapshot.getString("lastname");
                name.setText(nametoedit + " " + lastnametoedit);
                mail.setText(fuser.getEmail());
                btnaddphonenumber.setVisibility(View.VISIBLE);
                btnaddphonenumber.setClickable(true);
                phonenumber.setText(documentSnapshot.getString("phonenumber"));
            }
        });

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child("Users/"+fuser.getUid()+"/profile.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileimage);
            }
        });

        ////// Button Listeners//////////
        profilepicbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openfromgallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openfromgallery, GALLERY_REQUEST_CODE);
            }
        });

        btnaddphonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText newphonenumber = new EditText(view.getContext());
                final AlertDialog.Builder phonenumberdialog = new AlertDialog.Builder(view.getContext());
                phonenumberdialog.setTitle("Phone Number");
                phonenumberdialog.setView(newphonenumber);
                phonenumberdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String phone_number = newphonenumber.getText().toString();
                        HashMap<String, Object> edited = new HashMap<>();
                        edited.put("phonenumber", phone_number);
                        documentReference.update(edited);
                    }
                });
                phonenumberdialog.show();
            }
        });

        return view;
    }


    @SuppressLint("MissingSuperCall")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                final Uri contentUri = data.getData();
                Picasso.get().load(contentUri).into(profileimage);
                final StorageReference str = storageReference.child("Users/"+fuser.getUid()+"/profile.jpg");
                str.putFile(contentUri);
            }
        }
    }
}
