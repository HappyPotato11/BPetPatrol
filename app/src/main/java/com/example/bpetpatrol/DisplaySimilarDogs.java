package com.example.bpetpatrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DisplaySimilarDogs extends AppCompatActivity {

    String firstEmail, secondEmail, thirdEmail;
    ImageView img1, img2, img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_similar_dogs);

        Intent i = getIntent();
        firstEmail = i.getStringExtra("first");
        if (!(firstEmail == null)) {
            firstEmail = firstEmail.replace(".","");
        }
        secondEmail = i.getStringExtra("second");
        if (!(secondEmail == null)) {
            secondEmail = secondEmail.replace(".","");
        }
        thirdEmail = i.getStringExtra("third");
        if (!(thirdEmail == null)) {
            thirdEmail = thirdEmail.replace(".","");
        }

        if (!(firstEmail == null)) {
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Lost Pets").child(firstEmail);
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    img1 = (ImageView)findViewById(R.id.imgPet1);
                    String downloadUrl = ((Pet)dataSnapshot.getValue()).getImage();
                    Picasso.get().load(downloadUrl).into(img1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }

        if (!(secondEmail == null)) {
            DatabaseReference reff2 = FirebaseDatabase.getInstance().getReference().child("Lost Pets").child(secondEmail);
            reff2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    img2 = (ImageView)findViewById(R.id.imgPet2);
                    String downloadUrl = ((Pet)dataSnapshot.getValue()).getImage();
                    Picasso.get().load(downloadUrl).into(img2);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }

        if (!(thirdEmail == null)) {
            DatabaseReference reff3 = FirebaseDatabase.getInstance().getReference().child("Lost Pets").child(thirdEmail);
            reff3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    img3 = (ImageView)findViewById(R.id.imgPet3);
                    String downloadUrl = (dataSnapshot.getValue(Pet.class)).getImage();
                    Picasso.get().load(downloadUrl).into(img3);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }

    }

    ArrayList<Pet> selectedPets;

    public void passPets(View view) {

        selectedPets = new ArrayList<Pet>();

        CheckBox chk1 = (CheckBox)findViewById(R.id.chkPet1);
        CheckBox chk2 = (CheckBox)findViewById(R.id.chkPet2);
        CheckBox chk3 = (CheckBox)findViewById(R.id.chkPet3);

        if (chk1.isChecked()) {
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Lost Pets").child(firstEmail);
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Pet checked = (Pet)dataSnapshot.getValue();
                    selectedPets.add(checked);
                    Toast.makeText(DisplaySimilarDogs.this, "First pet was added", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
        if (chk2.isChecked()) {
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Lost Pets").child(secondEmail);
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Pet checked = (Pet)dataSnapshot.getValue();
                    selectedPets.add(checked);
                    Toast.makeText(DisplaySimilarDogs.this, "Second pet was added", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
        if (chk3.isChecked()) {
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Lost Pets").child(thirdEmail);
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Pet checked = (Pet)dataSnapshot.getValue();
                    selectedPets.add(checked);
                    Toast.makeText(DisplaySimilarDogs.this, "Third dog was added", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }

        /* DO SOMETHING WITH ARRAY LIST OF PETS HERE */
    }

}
