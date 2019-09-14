package com.example.bpetpatrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class SpinnersInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinners_info);

        Spinner spnB = (Spinner) findViewById(R.id.spnBehavior);
        // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.behaviors, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
            spnB.setAdapter(adapter);

        Spinner spnS = (Spinner) findViewById(R.id.spnShade);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.shades, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnS.setAdapter(adapter2);

        Spinner spnA = (Spinner) findViewById(R.id.spnAnimal);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.animals, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnA.setAdapter(adapter3);
    }

    public void submitInfo(View view) {
        String email = ((TextView)findViewById(R.id.txtEmail)).getText().toString();
        String name = ((TextView)findViewById(R.id.txtName)).getText().toString();

        //populating animal
        Animal animal = null;
        String animalString = ((Spinner)findViewById(R.id.spnAnimal)).getSelectedItem().toString();
        switch (animalString){
            case "dog":
                animal = Animal.Dog;
                break;
            case "cat":
                animal = Animal.Cat;
                break;
            case "snake":
                animal = Animal.Snake;
                break;
            case "hamster":
                animal = Animal.Hamster;
                break;
            case "pig":
                animal = Animal.Pig;
                break;
            case "hedgehog":
                animal = Animal.Hedgehog;
                break;
            case "parrot":
                animal = Animal.Parrot;
                break;
        }

        String breed = ((TextView)findViewById(R.id.txtBreed)).getText().toString();

        //populating colors
        ArrayList<Color> colors = new ArrayList<Color>();
        if (((CheckBox)findViewById(R.id.chkBlack)).isChecked()) {
            colors.add(Color.Black);
        }
        if (((CheckBox)findViewById(R.id.chkBlue)).isChecked()) {
            colors.add(Color.Blue);
        }
        if (((CheckBox)findViewById(R.id.chkRed)).isChecked()) {
            colors.add(Color.Red);
        }
        if (((CheckBox)findViewById(R.id.chkGreen)).isChecked()) {
            colors.add(Color.Green);
        }
        if (((CheckBox)findViewById(R.id.chkYellow)).isChecked()) {
            colors.add(Color.Yellow);
        }
        if (((CheckBox)findViewById(R.id.chkPink)).isChecked()) {
            colors.add(Color.Pink);
        }
        if (((CheckBox)findViewById(R.id.chkOrange)).isChecked()) {
            colors.add(Color.Orange);
        }
        if (((CheckBox)findViewById(R.id.chkWhite)).isChecked()) {
            colors.add(Color.White);
        }
        if (((CheckBox)findViewById(R.id.chkPurple)).isChecked()) {
            colors.add(Color.Purple);
        }
        if (((CheckBox)findViewById(R.id.chkBrown)).isChecked()) {
            colors.add(Color.Brown);
        }

        //populating shade
        ColorShade colorShade = null;
        String colorShadeString = ((Spinner)findViewById(R.id.spnShade)).getSelectedItem().toString();
        switch (colorShadeString){
            case "dark":
                colorShade = ColorShade.Dark;
                break;
            case "light":
                colorShade = ColorShade.Light;
                break;
        }

        //populating date
        int day = Integer.parseInt(((EditText)findViewById(R.id.txtDateD)).getText().toString());
        int month = Integer.parseInt(((EditText)findViewById(R.id.txtDateM)).getText().toString());
        int year = Integer.parseInt(((EditText)findViewById(R.id.txtDateY)).getText().toString());
        Date date = new Date(day, month, year);

        String location = ((TextView)findViewById(R.id.txtLocation)).getText().toString();

        //populating behavior
        Behavior behavior = null;
        String behaviorString = ((Spinner)findViewById(R.id.spnBehavior)).getSelectedItem().toString();
        switch (behaviorString){
            case "aggressive":
                behavior = Behavior.Aggressive;
                break;
            case "shy":
                behavior = Behavior.Shy;
                break;
            case "excited":
                behavior = Behavior.Excited;
                break;
        }

        String characteristic = ((TextView)findViewById(R.id.txtCharacteristic)).getText().toString();

        Pet lostPet = new Pet(email, name, animal, breed, colors,
                colorShade, date, location, behavior, characteristic);
        Pet.createLost(lostPet);

        Intent intent = new Intent(this, UploadImage.class);
        intent.putExtra("pet", lostPet);
        startActivity(intent);
    }
}
