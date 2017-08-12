package com.ajinkya.foodie.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class Utils {

    public static Uri constructUriForStaticGoogleMapImage(double latitude, double longitude) {
        return Uri.parse("https://maps.google.com/maps/api/staticmap?center=" + latitude
                + "," + longitude + "&zoom=15&size=640x320&scale=4");
    }

    public static void imageViewAnimatedChange(Context context, final ImageView v, final Bitmap newImage) {
        final Animation animOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        final Animation animIn  = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(newImage);
                animIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(animIn);
            }
        });
        v.startAnimation(animOut);
    }

    public static Uri constructUriForGoogleMapsApp(double latitude, double longitude, String query) {
        return Uri.parse("geo:" + latitude + "," + longitude + "?q=" + Uri.encode(query));
    }
}
