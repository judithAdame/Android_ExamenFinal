package com.example.android_examenfinal_servwebgooglemaps_dropdonwlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerTypeTransport;
    String typeTransport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<String> spinnerArray = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerArray.add("driving");
        spinnerArray.add("walking");
        spinnerArray.add("bicycling");

        spinnerTypeTransport = (Spinner) findViewById(R.id.spinnerTypeTransport);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeTransport.setAdapter(adapter);

        spinnerTypeTransport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeTransport=   spinnerTypeTransport.getItemAtPosition(spinnerTypeTransport.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),typeTransport,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    public void onClickValidation(View Main2Activity) {
        TextView latOrigine = (TextView) findViewById(R.id.editText13);
        TextView longOrigine = (TextView) findViewById(R.id.editText12);
        TextView latDestination = (TextView) findViewById(R.id.editText15);
        TextView longDestination = (TextView) findViewById(R.id.editText14);
        Intent monIntent = new Intent(this, Main2Activity.class);
        monIntent.putExtra("latOrigine", Double.parseDouble(latOrigine.getText().toString()));
        monIntent.putExtra("longOrigine", Double.parseDouble(longOrigine.getText().toString()));
        monIntent.putExtra("latDestination", Double.parseDouble(latDestination.getText().toString()));
        monIntent.putExtra("longDestination", Double.parseDouble(longDestination.getText().toString()));
        monIntent.putExtra("typeTransport", typeTransport.toString());
        startActivity(monIntent);
    }
}
