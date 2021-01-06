package com.example.profilui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends Activity {

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    TextView txtnama, txtstatus;
    EditText etnama, ettempatlahir, ettgllahir, etnohp, etnik, etnokk, etemail, etjk;
    Button ubahpass, keluar, editprofil;
    CircleImageView fotoprofile;
    private String imageUri;
    private static final int IMAGE_REQUEST = 1;

    public MainActivity(FirebaseFirestore firebaseFirestore, StorageReference storageReference) {
        this.firebaseFirestore = firebaseFirestore;
        this.storageReference = storageReference;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fotoprofile = findViewById(R.id.image_profil);
        txtnama = findViewById(R.id.name);
        txtstatus = findViewById(R.id.status);
        etnama = findViewById(R.id.et_nama);
        ettempatlahir = findViewById(R.id.et_tempatlahir);
        ettgllahir = findViewById(R.id.et_tgllahir);
        etnohp = findViewById(R.id.et_nohp);
        etnik = findViewById(R.id.et_nik);
        etnokk = findViewById(R.id.et_nokk);
        etemail = findViewById(R.id.et_email);
        etjk = findViewById(R.id.et_gender);
        ubahpass = findViewById(R.id.btn_ubah_pass);
        keluar = findViewById(R.id.btn_keluar);
        editprofil = findViewById(R.id.btn_edit_profil);
        readData();

        ubahpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UbahPassword.class);
                startActivity(intent);
            }
        });
        editprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityEditProfil.class);
                startActivity(intent);
            }
        });
    }

    private void readData() {
        firebaseFirestore.collection("USER")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        etnama.setText(document.getString("nama"));
                        ettempatlahir.setText(document.getString("tempatlahir"));
                        ettgllahir.setText(document.getString("tgllahir"));
                        etnohp.setText(document.getString("nohp"));
                        etnik.setText(document.getString("nik"));
                        etnokk.setText(document.getString("nokk"));
                        etemail.setText(document.getString("email"));
                         imageUri = document.getString("foto");
                        if (imageUri != ""){
                            Picasso.get().load(imageUri).fit().into(fotoprofile);
                        }else{
                            Picasso.get().load(R.drawable.profile_image).fit().into(fotoprofile);
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Error getting documents",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }



