package com.ajinkya.foodie.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ajinkya.foodie.R;
import com.ajinkya.foodie.model.RestaurantInfo;
import com.ajinkya.foodie.util.Utils;
import com.ajinkya.foodie.tasks.FetchRestaurantInfoTask;
import com.squareup.picasso.Picasso;

import static com.ajinkya.foodie.IntentExtraConstants.RESTAURANT_ID;
import static com.ajinkya.foodie.IntentExtraConstants.RESTAURANT_NAME;


public class RestaurantViewActivity extends AppCompatActivity implements FetchRestaurantInfoTask.Listener {

    private static final String GOOGLE_MAPS_PACKAGE_REF = "com.google.android.apps.maps";

    private ProgressBar mProgressBar;
    private ImageView mGoogleMapImageView;
    private TextView streetAddress1TextView;
    private TextView streetAddress2TextView;
    private TextView priceRangeTextView;
    private TextView phoneNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);
        Intent intent = getIntent();
        this.setTitle(intent.getStringExtra(RESTAURANT_NAME));
        long restaurantId= intent.getLongExtra(RESTAURANT_ID, -1L);
        if(restaurantId < 0) {
            throw new IllegalStateException("trying to open this RestaurantViewActivity without valid restaurantID");
        }
        mGoogleMapImageView = (ImageView) findViewById(R.id.google_map_imageview);
        streetAddress1TextView = (TextView) findViewById(R.id.street_address1);
        streetAddress2TextView = (TextView) findViewById(R.id.street_address2);
        priceRangeTextView = (TextView) findViewById(R.id.price_range);
        phoneNumberTextView = (TextView) findViewById(R.id.phone_number);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_restaurant_info);
        mProgressBar.setVisibility(View.VISIBLE);
        new FetchRestaurantInfoTask(this).execute(restaurantId);
    }


    @Override
    public void onComplete(final RestaurantInfo restaurantInfo) {
        mProgressBar.setVisibility(View.GONE);
        Picasso.with(getApplicationContext()).load(Utils.constructUriForStaticGoogleMapImage(restaurantInfo.getLatitude(), restaurantInfo.getLongitude()))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(mGoogleMapImageView);

        mGoogleMapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Utils.constructUriForGoogleMapsApp(restaurantInfo.getLatitude(), restaurantInfo.getLongitude(), restaurantInfo.getName());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                mapIntent.setPackage(GOOGLE_MAPS_PACKAGE_REF);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        streetAddress1TextView.setText(restaurantInfo.getStreet());
        streetAddress2TextView.setText(restaurantInfo.getCity() + " " + restaurantInfo.getState() + ", " + restaurantInfo.getZipCode());
        findViewById(R.id.address_section).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("address", restaurantInfo.getPrintableAddress());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getApplicationContext(), R.string.copy_confirm_toast, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        priceRangeTextView.setText(restaurantInfo.getPriceRange());
        phoneNumberTextView.setText(PhoneNumberUtils.formatNumber(restaurantInfo.getPhoneNumber()));
    }
}
