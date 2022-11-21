package com.nvd.qrcode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nvd.qrcode.R;

public class ActivityCreateLocationQR extends AppCompatActivity {

    TextView txtLongtitude, txtLatitude, txtInstruction;
    EditText edtLongtitude, edtLatitude;
    Button btnCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_location_qr);

        txtLongtitude = findViewById(R.id.textLongtitude);
        txtLatitude = findViewById(R.id.textLatitude);
        txtInstruction = findViewById(R.id.textInstruction);
        edtLongtitude = findViewById(R.id.edtLongtitude);
        edtLatitude = findViewById(R.id.edtLatitude);
        btnCreate = findViewById(R.id.btnGenerate);

        Intent intent = getIntent();
        int reciverInstruction = intent.getIntExtra("instruction", 0);
        int reciverLongtitude = intent.getIntExtra("titleLongTitude", 0);
        int reciverLatitude = intent.getIntExtra("titleLaTitude", 0);

        String longitude = getResources().getString(reciverLongtitude);
        String latitude = getResources().getString(reciverLatitude);
        String instruction = getResources().getString(reciverInstruction);

        txtLongtitude.setText(longitude);
        txtLatitude.setText(latitude);
        txtInstruction.setText(instruction);

        btnCreate.setOnClickListener(v -> {
            Intent i = new Intent(ActivityCreateLocationQR.this, ActivityResultLocationQR.class);
            i.putExtra("longitude", edtLongtitude.getText().toString().trim());
            i.putExtra("latitude", edtLatitude.getText().toString().trim());
            startActivity(i);
        });
    }
}