package com.ajinkya.foodie.tasks;

import android.os.AsyncTask;

import com.ajinkya.foodie.model.RestaurantListItem;
import com.ajinkya.foodie.network.NetworkApi;

import java.util.List;

public class FetchRestaurantListTask extends AsyncTask<Double, Void, List<RestaurantListItem>> {
    private Listener mListener;

    public FetchRestaurantListTask(Listener listener) {
        mListener = listener;
    }

    @Override
    protected List<RestaurantListItem> doInBackground(Double... params) {
        return NetworkApi.getInstance().getRestaurants(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(List<RestaurantListItem> result) {
        if (result.isEmpty()) {
            mListener.onFailure();
        } else {
            mListener.onComplete(result);
        }
    }

    public interface Listener {
        void onComplete(List<RestaurantListItem> restaurants);

        void onFailure();
    }
}
