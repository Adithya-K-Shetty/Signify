package com.example.signify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SignInfo extends AppCompatActivity {
    ImageView sign_image;
    TextView sign_title, what_it_means, what_to_do,severity_value;
    Button go_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_info);
        sign_image = findViewById(R.id.signImage);
        sign_title = findViewById(R.id.signTitle);
        what_it_means = findViewById(R.id.whatItMeans);
        what_to_do = findViewById(R.id.whatToDo);
        severity_value = findViewById(R.id.severity);

        Bundle bundle = getIntent().getBundleExtra("allSignsData");
        String displaySignName = bundle.getString("signName");
        String displaySignImage = bundle.getString("signImage");
        String displayWhatItMeans = bundle.getString("whatItMeans");
        String displayWhatToDo = bundle.getString("whatToDo");
        String displaySeverityValue = bundle.getString("severityValue");

        Glide.with(SignInfo.this).load(displaySignImage).into(sign_image);
        sign_title.setText(displaySignName);
        what_it_means.setText(displayWhatItMeans);
        what_to_do.setText(displayWhatToDo);
        severity_value.setText(displaySeverityValue);
    }
}