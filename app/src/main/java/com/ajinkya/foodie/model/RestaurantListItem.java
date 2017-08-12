package com.ajinkya.foodie.model;

import android.net.Uri;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.LinkedList;


public class RestaurantListItem {

    private long id;
    private String name;
    private Uri thumbnailUri;
    private Uri headerImgUri;
    private String status;
    private long deliveryFee;
    private RestaurantStatusType statusType;
    private String tags;

    // ensure these can be created only by json so these stay read only representation of server's response
    private RestaurantListItem() {

    }

    public static LinkedList<RestaurantListItem> fromJsonArray(@NonNull String feedItemArray) {
        LinkedList<RestaurantListItem> feedsList = new LinkedList<>();
        try {
            JSONArray restaurants = new JSONArray(feedItemArray);

            for (int i = 0; i < restaurants.length(); i++) {
                RestaurantListItem restaurantListItem = fromJson(restaurants.optJSONObject(i));
                feedsList.add(restaurantListItem);
            }
        } catch (JSONException ignored) {

        }
        return feedsList;
    }

    private static RestaurantListItem fromJson(@NonNull JSONObject jsonObject) {
        RestaurantListItem restaurantListItem = new RestaurantListItem();
        try {
            restaurantListItem.id = jsonObject.getLong("id");
            restaurantListItem.name = jsonObject.getString("name");
            restaurantListItem.status = jsonObject.getString("status");
            restaurantListItem.thumbnailUri = Uri.parse(jsonObject.getString("cover_img_url"));
            restaurantListItem.headerImgUri = Uri.parse(jsonObject.getString("header_img_url"));
            restaurantListItem.deliveryFee = jsonObject.getLong("delivery_fee");
            // assume only open or closed for now
            restaurantListItem.statusType = jsonObject.getString("status_type").equals("open") ? RestaurantStatusType.OPEN : RestaurantStatusType.CLOSED;
            restaurantListItem.tags = jsonObject.getJSONArray("tags").toString().replace("[", "").replace("]", "").replace("\"", "");

        } catch (JSONException e) {
            restaurantListItem = new RestaurantListItem();
        }
        return restaurantListItem;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Uri getThumbnailUri() {
        return thumbnailUri;
    }

    public Uri getHeaderImgUri() {
        return headerImgUri;
    }

    public String getStatus() {
        return status;
    }

    public String getDeliveryFee() {
        return NumberFormat.getCurrencyInstance().format(deliveryFee/ 100L);
    }

    public RestaurantStatusType getStatusType() {
        return statusType;
    }

    public String getTags() {
        return tags;
    }
}