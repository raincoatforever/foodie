package com.ajinkya.foodie.model;

import android.net.Uri;

import com.ajinkya.foodie.BaseTestRunner;
import com.ajinkya.foodie.model.RestaurantInfo;
import com.ajinkya.foodie.model.RestaurantStatusType;

import org.junit.Test;

import static org.junit.Assert.*;

public class RestaurantInfoTest extends BaseTestRunner {

    private static final String SAMPLE_JSON_RESPONSE = "{\"phone_number\":\"+16503270107\",\"yelp_review_count\":435,\"max_order_size\":null,\"delivery_fee\":0,\"max_composite_score\":10,\"id\":14,\"average_rating\":4.6,\"tags\":[\"Greek\",\"Mediterranean\",\"Turkish\",\"Catering\"],\"delivery_radius\":10000,\"inflation_rate\":0.0,\"menus\":[{\"status\":\"Available now\",\"subtitle\":\"\",\"name\":\"Gyros Gyros (All Day)\",\"open_hours\":[[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}]],\"is_catering\":false,\"id\":59,\"status_type\":\"open\"},{\"status\":\"Order 24.0 hours in advance\",\"subtitle\":\"\",\"name\":\"Gyros Gyros (Catering)\",\"open_hours\":[[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}]],\"is_catering\":true,\"id\":46336,\"status_type\":\"order-advance\"}],\"show_store_menu_header_photo\":false,\"composite_score\":9,\"number_of_ratings\":5975,\"status_type\":\"open\",\"is_only_catering\":false,\"status\":\"24 - 34 mins\",\"object_type\":\"restaurant.restaurant\",\"description\":\"Greek and Mediterranean\",\"business\":{\"business_vertical\":null,\"id\":19,\"name\":\"Gyros Gyros\"},\"yelp_biz_id\":\"gyros-gyros-palo-alto\",\"asap_time\":29,\"yelp_rating\":3.5,\"extra_sos_delivery_fee\":0,\"business_id\":19,\"special_instructions_max_length\":null,\"cover_img_url\":\"https://cdn.doordash.com/media/restaurant/cover/gyros_gyros.png\",\"address\":{\"city\":\"Palo Alto\",\"subpremise\":\"\",\"id\":360,\"printable_address\":\"498 University Avenue, Palo Alto, CA 94301, USA\",\"state\":\"CA\",\"street\":\"498 University Avenue\",\"country\":\"United States\",\"lat\":37.448308,\"lng\":-122.1592192,\"shortname\":\"498 University Avenue\",\"zip_code\":\"94301\"},\"price_range\":2,\"slug\":\"gyros-gyros-palo-alto\",\"show_suggested_items\":true,\"name\":\"Gyros Gyros\",\"is_newly_added\":false,\"is_good_for_group_orders\":true,\"service_rate\":10.0,\"merchant_promotions\":[],\"header_image_url\":null}";

    @Test
    public void fromJSON() throws Exception {
        RestaurantInfo restaurantInfo = RestaurantInfo.fromJSON(SAMPLE_JSON_RESPONSE);
        assertEquals("+16503270107", restaurantInfo.getPhoneNumber());
        assertEquals("Gyros Gyros", restaurantInfo.getName());
        assertEquals("498 University Avenue, Palo Alto, CA 94301, USA", restaurantInfo.getPrintableAddress());
        assertEquals("498 University Avenue", restaurantInfo.getStreet());
        assertEquals("Palo Alto", restaurantInfo.getCity());
        assertEquals("CA", restaurantInfo.getState());
        assertEquals("United States", restaurantInfo.getCountry());
        assertEquals("94301", restaurantInfo.getZipCode());
        assertEquals("$$", restaurantInfo.getPriceRange());
        assertEquals(37.448308, restaurantInfo.getLatitude(), 0.0);
        assertEquals(-122.1592192, restaurantInfo.getLongitude(), 0.0);
        assertEquals(RestaurantStatusType.OPEN, restaurantInfo.getStatusType());
        assertEquals("24 - 34 mins", restaurantInfo.getStatus());
        assertEquals(true, restaurantInfo.isGoodForGroupOrders());
        assertEquals(false, restaurantInfo.isNewlyAdded());
        assertEquals(Uri.parse("https://cdn.doordash.com/media/restaurant/cover/gyros_gyros.png"), restaurantInfo.getCoverImgUri());
        assertEquals("Greek, Mediterranean, Turkish, Catering", restaurantInfo.getTags());
    }

}