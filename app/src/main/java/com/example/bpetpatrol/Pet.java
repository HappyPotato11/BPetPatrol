package com.example.bpetpatrol;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("serial")
public class Pet implements Serializable {
    private String email;

    private String name;
    private Animal animal;
    private String breed; //put in defined breeds if you can

    private ArrayList<Color> colors;
    private ColorShade colorShade;

    private Date lastSeenDate;
    private String lastSeenLocation; //do actual date integration if time

    private Behavior behavior;
    private String definingCharacteristic;

    private String image;

    /* Constructors */
    //constructor for making a LOST pet
    public Pet(String e, String n, Animal a, String br, ArrayList<Color> c, ColorShade cs,
               Date lsd, String lsl, Behavior be, String dc) {
        email = e;
        name = n;
        animal = a;
        breed = br;
        colors = c;
        colorShade = cs;
        lastSeenDate = lsd;
        lastSeenLocation = lsl;
        behavior = be;
        definingCharacteristic = dc;
    }

    //constructor for making a FOUND pet (minus last seen date)
    public Pet(String e, String n, Animal a, String br, ArrayList<Color> c, ColorShade cs,
               String lsl, Behavior be, String dc) {
        email = e;
        name = n;
        animal = a;
        breed = br;
        colors = c;
        colorShade = cs;
        lastSeenLocation = lsl;
        behavior = be;
        definingCharacteristic = dc;
    }

    /* for setting image */
    public void setImage(String url) {
        image = url;
    }

    /* Accessors */
    public String getImage() { return image; }
    public String getName() {
        return name;
    }
    public Animal getAnimal() {
        return animal;
    }
    public String getBreed() {
        return breed;
    }
    public ArrayList<Color> getColors() {
        return colors;
    }
    public ColorShade getColorShade() {
        return colorShade;
    }
    public Behavior getBehavior() {
        return behavior;
    }

    // this should only be passed to the lost owner to verify, not used for comparison
    public String getDefiningCharacteristic() {
        return definingCharacteristic;
    }
    // only used to get pictures from the most recent dates
    public Date getLastSeenDate() {
        return lastSeenDate;
    }
    // this should only be passed to the lost owner to verify, not used for comparison
    public String getLastSeenLocation() {
        return lastSeenLocation;
    }
    // only used to contact
    public String getOwnerEmail() {
        return email;
    }

    /* Methods */

    // come up with better comparison algorithm later
    public static boolean areSimilar(Pet lostPet, Pet foundPet) {
        int similarCharacteristics = 0;

        // comparisons
        if (lostPet.getName().toLowerCase().equals(foundPet.getName().toLowerCase())) {
            similarCharacteristics++;
        }
        if (lostPet.getAnimal() == foundPet.getAnimal()) {
            similarCharacteristics++;
        }
        if (lostPet.getBreed().toLowerCase().equals(foundPet.getBreed().toLowerCase())) {
            similarCharacteristics++;
        }

        int size, similarColors = 0;
        ArrayList<Color> smallerList, biggerList;
        if (lostPet.getColors().size() <= foundPet.getColors().size()) {
            size = lostPet.getColors().size();
            smallerList = lostPet.getColors();
            biggerList = foundPet.getColors();
        }
        else {
            size = foundPet.getColors().size();
            smallerList = foundPet.getColors();
            biggerList = lostPet.getColors();
        }
        for (int i = 0; i < size; i++) {
            if (smallerList.get(i).equals(biggerList.get(i))) {
                similarColors++;
            }
        }
        if (similarColors >= size / 2) {
            similarCharacteristics++;
        }

        if (lostPet.getColorShade() == foundPet.getColorShade()) {
            similarCharacteristics++;
        }
        if (lostPet.getBehavior() == foundPet.getBehavior()) {
            similarCharacteristics++;
        }

        return (similarCharacteristics >= 3);
    }

    // pushes all pet data to the database
    public static void createLost(Pet pet) {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Lost Pets");
        String email = pet.getOwnerEmail().replace(".", "");
        reff.child(email).setValue(pet);
    }

    // returns data snapshot for a particular lost pet (identified by phone number)
    public static void getLostData(String email) {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Lost Pets").child(email);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pet lostPet = dataSnapshot.getValue(Pet.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
