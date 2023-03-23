package com.example.signify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Welcome extends AppCompatActivity {
    Animation car_animation;
    ImageView car_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        car_animation = AnimationUtils.loadAnimation(this,R.anim.car_bottom_to_top);
        car_image =(ImageView) findViewById(R.id.carImage);
        car_image.setAnimation(car_animation);

    }
}