package com.ajinkya.foodie.model;

import android.net.Uri;

import com.ajinkya.foodie.BaseTestRunner;
import com.ajinkya.foodie.model.RestaurantListItem;
import com.ajinkya.foodie.model.RestaurantStatusType;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RestaurantListItemTest extends BaseTestRunner {

    private static final String SAMPLE_JSON_RESPONSE = "[{\"is_time_surging\":false,\"max_order_size\":null,\"delivery_fee\":0,\"max_composite_score\":10,\"id\":36,\"merchant_promotions\":[],\"average_rating\":4.6,\"menus\":[{\"is_catering\":false,\"subtitle\":\"\",\"id\":125639,\"name\":\"Agave Mexican Bistro (Dinner) (Mountain View)\"},{\"is_catering\":false,\"subtitle\":\"\",\"id\":125339,\"name\":\"Agave Mexican Bistro (Lunch) (Mountain View)\"}],\"composite_score\":9,\"status_type\":\"open\",\"is_only_catering\":false,\"status\":\"46 mins\",\"number_of_ratings\":1046,\"asap_time\":46,\"description\":\"Traditional Mexican Meets Contemporary California\",\"business\":{\"id\":40,\"name\":\"Agave Mexican Bistro\"},\"tags\":[\"Mexican\"],\"yelp_review_count\":297,\"business_id\":40,\"extra_sos_delivery_fee\":0,\"yelp_rating\":3.5,\"cover_img_url\":\"https://cdn.doordash.com/media/restaurant/cover/Agave-Mexican-Bistro.png\",\"header_img_url\":\"\",\"address\":{\"city\":\"Mountain View\",\"state\":\"CA\",\"street\":\"194 Castro Street\",\"lat\":37.394068,\"lng\":-122.079161,\"printable_address\":\"194 Castro Street, Mountain View, CA 94041, USA\"},\"price_range\":2,\"slug\":\"agave-mexican-bistro-mountain-view\",\"name\":\"Agave Mexican Bistro\",\"is_newly_added\":false,\"url\":\"/store/agave-mexican-bistro-mountain-view-36/\",\"service_rate\":10,\"promotion\":null,\"featured_category_description\":null},{\"is_time_surging\":false,\"max_order_size\":300000,\"delivery_fee\":0,\"max_composite_score\":10,\"id\":63,\"merchant_promotions\":[],\"average_rating\":4.7,\"menus\":[{\"is_catering\":false,\"subtitle\":\"\",\"id\":27575,\"name\":\"Fuki Sushi (Dinner)\"},{\"is_catering\":false,\"subtitle\":\"\",\"id\":40310,\"name\":\"Fuki Sushi (Lunch)\"},{\"is_catering\":true,\"subtitle\":\"\",\"id\":2875,\"name\":\"Fuki Sushi (Catering)\"}],\"composite_score\":9,\"status_type\":\"open\",\"is_only_catering\":false,\"status\":\"33 mins\",\"number_of_ratings\":6734,\"asap_time\":33,\"description\":\"Sushi Bar, Japanese\",\"business\":{\"id\":61,\"name\":\"Fuki Sushi\"},\"tags\":[\"Sushi\",\"Japanese\",\"Catering\"],\"yelp_review_count\":752,\"business_id\":61,\"extra_sos_delivery_fee\":0,\"yelp_rating\":3.5,\"cover_img_url\":\"https://cdn.doordash.com/media/restaurant/cover/Fuki-Sushi.png\",\"header_img_url\":\"https://res.cloudinary.com/doordash/image/fetch/c_scale,w_1200,h_564,q_80/https://doordash-static.s3.amazonaws.com/media/photos/e85adc64-f50d-473d-85e2-4661c067e9ba-retina-large.jpg\",\"address\":{\"city\":\"Palo Alto\",\"state\":\"CA\",\"street\":\"4119 El Camino Real\",\"lat\":37.4137395,\"lng\":-122.125722,\"printable_address\":\"4119 El Camino Real, Palo Alto, CA 94306, USA\"},\"price_range\":2,\"slug\":\"fuki-sushi-palo-alto\",\"name\":\"Fuki Sushi (El Camino Real)\",\"is_newly_added\":false,\"url\":\"/store/fuki-sushi-palo-alto-63/\",\"service_rate\":10,\"promotion\":null,\"featured_category_description\":null}]";

    @Test
    public void fromJsonArray() throws Exception {
        List<RestaurantListItem> restaurantListItems = RestaurantListItem.fromJsonArray(SAMPLE_JSON_RESPONSE);
        assertEquals("unexpected number of items parsed", 2, restaurantListItems.size());

        RestaurantListItem item1 = restaurantListItems.get(0);
        assertEquals("Agave Mexican Bistro", item1.getName());
        assertEquals(36, item1.getId());
        assertEquals(Uri.parse("https://cdn.doordash.com/media/restaurant/cover/Agave-Mexican-Bistro.png"), item1.getThumbnailUri());
        assertEquals(Uri.parse(""), item1.getHeaderImgUri());
        assertEquals("46 mins", item1.getStatus());
        assertEquals("$0.00", item1.getDeliveryFee());
        assertEquals(RestaurantStatusType.OPEN, item1.getStatusType());
        assertEquals("Mexican", item1.getTags());

        RestaurantListItem item2 = restaurantListItems.get(1);
        assertEquals("Fuki Sushi (El Camino Real)", item2.getName());
        assertEquals(63, item2.getId());
        assertEquals(Uri.parse("https://cdn.doordash.com/media/restaurant/cover/Fuki-Sushi.png"), item2.getThumbnailUri());
        assertEquals(Uri.parse("https://res.cloudinary.com/doordash/image/fetch/c_scale,w_1200,h_564,q_80/https://doordash-static.s3.amazonaws.com/media/photos/e85adc64-f50d-473d-85e2-4661c067e9ba-retina-large.jpg"), item2.getHeaderImgUri());
        assertEquals("33 mins", item2.getStatus());
        assertEquals("$0.00", item2.getDeliveryFee());
        assertEquals(RestaurantStatusType.OPEN, item2.getStatusType());
        assertEquals("Sushi,Japanese,Catering", item2.getTags());
    }

}