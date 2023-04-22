package com.example.signify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Welcome extends AppCompatActivity{
    Animation car_animation;
    ImageView car_image;
    ImageButton welcome_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        car_animation = AnimationUtils.loadAnimation(this,R.anim.car_bottom_to_top);
        car_image =(ImageView) findViewById(R.id.carImage);
        welcome_btn = (ImageButton) findViewById(R.id.welcomeBtn);
        car_image.setAnimation(car_animation);

        welcome_btn.setAnimation(clickAnimation());
        welcome_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Welcome.this, Dashboard.class);
                startActivity(i);

            }
        });

    }
    public AlphaAnimation clickAnimation() {
        return new AlphaAnimation(1F, 0.4F); // Change "0.4F" as per your recruitment.
    }
}