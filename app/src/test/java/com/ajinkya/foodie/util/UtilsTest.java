package com.ajinkya.foodie.util;

import android.net.Uri;

import com.ajinkya.foodie.BaseTestRunner;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class UtilsTest extends BaseTestRunner {

    @Test
    public void constructUriForStaticGoogleMapImage() {
        assertEquals("URI does not match", Utils.constructUriForStaticGoogleMapImage(100.00, 200.00),
                Uri.parse("https://maps.google.com/maps/api/staticmap?center=100.0,200.0&zoom=15&size=640x320&scale=4"));
    }

    @Test
    public void constructUriForGoogleMapsApp() {
        assertEquals("URI does not match", Utils.constructUriForGoogleMapsApp(100.00, 200.00, "query"),
                Uri.parse("geo:100.0,200.0?q=query"));
    }
}
