package com.ajinkya.foodie.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ajinkya.foodie.BaseTestRunner;
import com.ajinkya.foodie.util.StarRestaurantUtils;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;

public class StarRestaurantUtilsTest extends BaseTestRunner {

    Context shadowContext;

    @Before
    public void setup() {
        shadowContext = RuntimeEnvironment.application.getApplicationContext();
    }

    @Test
    public void toggleAndGet() throws Exception {
        SharedPreferences sharedPreferences = shadowContext.getSharedPreferences("starred_restaurants", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("42", false).commit();

        boolean isStarred = StarRestaurantUtils.toggleAndGet(42, RuntimeEnvironment.application.getApplicationContext());
        assertEquals("isStarred value is unexpected", true, isStarred);
        boolean storedValue = sharedPreferences.getBoolean("12", false);
        assertEquals("value in sharedPrefs is unexpected", false, storedValue);
    }

    @Test
    public void isStarred() throws Exception {
        SharedPreferences sharedPreferences = shadowContext.getSharedPreferences("starred_restaurants", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("42", true).commit();

        boolean starred = StarRestaurantUtils.isStarred(42, RuntimeEnvironment.application.getApplicationContext());
        assertEquals("isStarred returned unexpected value", true, starred);
    }

}