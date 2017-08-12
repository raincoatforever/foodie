package com.ajinkya.foodie.network;

import android.net.Uri;

import com.ajinkya.foodie.model.RestaurantInfo;
import com.ajinkya.foodie.model.RestaurantListItem;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkApi {

    OkHttpClient mHttpClient;

    private NetworkApi() {
        mHttpClient = new OkHttpClient.Builder().build();
    }

    private static NetworkApi instance = null;

    public static synchronized NetworkApi getInstance() {
        if(instance == null) {
            instance = new NetworkApi();
        }
        return instance;
    }

    private static final String SCHEME = "https";
    private static final String HOSTNAME = "api.doordash.com";
    private static final String RESTAURENT_RESOURCE_PATH = "restaurant";
    private static final String API_VERSION_2 = "v2";
    private static final String QUERY_PARAM_LAT = "lat";
    private static final String QUERY_PARAM_LONG = "lng";

    public List<RestaurantListItem> getRestaurants(double latitude, double longitude) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(HOSTNAME)
                .appendPath(API_VERSION_2)
                .appendPath(RESTAURENT_RESOURCE_PATH)
                .appendQueryParameter(QUERY_PARAM_LAT, String.valueOf(latitude))
                .appendQueryParameter(QUERY_PARAM_LONG, String.valueOf(longitude));

        String restaurants = executeGet(builder.build().toString());
        return RestaurantListItem.fromJsonArray(restaurants);
    }

    public RestaurantInfo getRestaurantInfo(long restaurantId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(HOSTNAME)
                .appendPath(API_VERSION_2)
                .appendPath(RESTAURENT_RESOURCE_PATH)
                .appendPath(String.valueOf(restaurantId));

        String restaurants = executeGet(builder.build().toString());
        return RestaurantInfo.fromJSON(restaurants);
    }


    private String executeGet(String url) {
        Request request = new Request.Builder()
                                     .url(url)
                                     .build();

        Response response = null;
        try {
            response = mHttpClient.newCall(request).execute();
            if(response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

}
