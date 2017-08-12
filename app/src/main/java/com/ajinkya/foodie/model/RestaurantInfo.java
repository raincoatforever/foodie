package com.ajinkya.foodie.model;

import android.net.Uri;
import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantInfo {

    private String phoneNumber;
    private String name;
    private String printableAddress;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String priceRange;
    private double latitude;
    private double longitude;
    private RestaurantStatusType statusType;
    private String status;
    private boolean isGoodForGroupOrders;
    private boolean isNewlyAdded;
    private Uri coverImgUri;
    private String tags;

    // same as RestaurantListItem, dont let this be created without a json
    private RestaurantInfo() {

    }

    public static RestaurantInfo fromJSON(@NonNull String restaurantInfoApiResponse) {
        RestaurantInfo restaurantInfo = new RestaurantInfo();
        try {
            JSONObject restaurantObj = new JSONObject(restaurantInfoApiResponse);
            restaurantInfo.name = restaurantObj.getString("name");
            restaurantInfo.phoneNumber = restaurantObj.getString("phone_number");
            JSONObject addressObj = restaurantObj.getJSONObject("address");
            restaurantInfo.printableAddress = addressObj.getString("printable_address");
            restaurantInfo.street = addressObj.getString("street");
            restaurantInfo.city = addressObj.getString("city");
            restaurantInfo.state = addressObj.getString("state");
            restaurantInfo.country = addressObj.getString("country");
            restaurantInfo.zipCode = addressObj.getString("zip_code");
            restaurantInfo.latitude = addressObj.getDouble("lat");
            restaurantInfo.longitude = addressObj.getDouble("lng");

            restaurantInfo.status = restaurantObj.getString("status");
            restaurantInfo.statusType = restaurantObj.getString("status_type").equals("open") ? RestaurantStatusType.OPEN : RestaurantStatusType.CLOSED;

            restaurantInfo.isGoodForGroupOrders = restaurantObj.getBoolean("is_good_for_group_orders");
            restaurantInfo.isNewlyAdded = restaurantObj.getBoolean("is_newly_added");

            restaurantInfo.priceRange = StringUtils.repeat("$", restaurantObj.getInt("price_range"));
            restaurantInfo.coverImgUri = Uri.parse(restaurantObj.getString("cover_img_url"));
            JSONArray tags = restaurantObj.getJSONArray("tags");
            restaurantInfo.tags = parseTags(tags);


        } catch (JSONException e) {
            restaurantInfo = new RestaurantInfo();
        }
        return restaurantInfo;
    }

    private static String parseTags(JSONArray tags) {
        String tagsString = "";
        try {
            for (int i = 0; i < tags.length(); i++) {
                tagsString = tagsString.concat(tags.getString(i));
                if(i != tags.length() - 1) {
                    tagsString = tagsString.concat(", ");
                }
            }
        } catch (JSONException e) {
            tagsString = "";
        }
        return tagsString;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPrintableAddress() {
        return printableAddress;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public RestaurantStatusType getStatusType() {
        return statusType;
    }

    public String getStatus() {
        return status;
    }

    public boolean isGoodForGroupOrders() {
        return isGoodForGroupOrders;
    }

    public boolean isNewlyAdded() {
        return isNewlyAdded;
    }

    public Uri getCoverImgUri() {
        return coverImgUri;
    }

    public String getTags() {
        return tags;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getState() {
        return state;
    }
}
