package com.figjam.hackathon2019.clinic;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.figjam.hackathon2019.R;
import com.figjam.hackathon2019.models.Clinic;

public class ClinicDetailActivity extends AppCompatActivity {

    private static final String CLINIC = "clinic";
    private Clinic clinic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_detail);
        clinic = getIntent().getParcelableExtra(CLINIC);
    }
}
