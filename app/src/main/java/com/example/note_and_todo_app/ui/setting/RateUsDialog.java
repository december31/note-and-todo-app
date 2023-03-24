package com.example.note_and_todo_app.setting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.note_and_todo_app.R;

public class RateUsDialog extends Dialog {
    private float userRate = 0;


    public RateUsDialog(@NonNull Context context){

        super(context);
    }
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.rate_us_dialog_layout);

        final AppCompatButton rateNowBtn = findViewById(R.id.rateNowBtn);
        final AppCompatButton laterBtn = findViewById(R.id.laterBtn);
        final RatingBar ratingBar = findViewById(R.id.ratingBar);
        final ImageView ratingImage = findViewById(R.id.ratingImage);

        rateNowBtn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // you code goes here
                //userRate

            }
        }));

        laterBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dismiss();
            }
        }));

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {


            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //boolean rating;
                if(rating <= 1)
                {
                    ratingImage.setImageResource(R.drawable.one_star);
                }
                else if(rating <= 2)
                {
                    ratingImage.setImageResource(R.drawable.two_star);
                }
                else if(rating <= 3)
                {
                    ratingImage.setImageResource(R.drawable.three_star);
                }
                else if(rating <= 4)
                {
                    ratingImage.setImageResource(R.drawable.four_star);
                }
                else if (rating <= 5)
                {
                    ratingImage.setImageResource(R.drawable.five_star);
                }

                // animate emoji image
                animateImage(ratingImage);
                //selected ratingby
                 userRate = rating;
            }
        });
    }

    private void animateImage(ImageView ratingImage)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f
                );
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
        ;
    }
}
