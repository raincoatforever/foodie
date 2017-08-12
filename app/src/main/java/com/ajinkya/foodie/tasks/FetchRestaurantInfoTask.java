package com.ajinkya.foodie.tasks;


import android.os.AsyncTask;

import com.ajinkya.foodie.network.NetworkApi;
import com.ajinkya.foodie.model.RestaurantInfo;

public class FetchRestaurantInfoTask extends AsyncTask<Long, Void, RestaurantInfo> {
    private Listener mListener;

    public FetchRestaurantInfoTask(Listener listener) {
        mListener = listener;
    }

    @Override
    protected RestaurantInfo doInBackground(Long... params) {
        return NetworkApi.getInstance().getRestaurantInfo(params[0]);
    }

    @Override
    protected void onPostExecute(RestaurantInfo restaurantInfo) {
        mListener.onComplete(restaurantInfo);
    }

    public interface Listener {
        void onComplete(RestaurantInfo restaurantInfo);
    }
}
