package com.ajinkya.foodie.util;

import android.content.Context;
import android.content.SharedPreferences;

public class StarRestaurantUtils {

    private static final String STARRED_RESTAURANTS_PREFS = "starred_restaurants";

    public static boolean toggleAndGet(long restaurantId, Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(STARRED_RESTAURANTS_PREFS, Context.MODE_PRIVATE);
        boolean starred = sharedpreferences.getBoolean(String.valueOf(restaurantId), false);
        sharedpreferences.edit().putBoolean(String.valueOf(restaurantId), !starred).apply();
        return !starred;
    }

    public static boolean isStarred(long restaurantId, Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(STARRED_RESTAURANTS_PREFS, Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(String.valueOf(restaurantId), false);
    }
}
