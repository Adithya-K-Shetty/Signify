package com.example.signify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.signify.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Animation topAni,bottomAni;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topAni = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAni = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        binding.appLogo.setAnimation(topAni);
        binding.appName.setAnimation(bottomAni);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Welcome.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}