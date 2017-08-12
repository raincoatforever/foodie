package com.ajinkya.foodie.network;

import com.ajinkya.foodie.BaseTestRunner;
import com.ajinkya.foodie.model.RestaurantInfo;
import com.ajinkya.foodie.model.RestaurantListItem;
import com.ajinkya.foodie.network.NetworkApi;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;

import static org.junit.Assert.*;

public class NetworkApiTest extends BaseTestRunner {

    private static final String SAMPLE_JSON_RESPONSE_GET_RESTAURENTS = "[{\"is_time_surging\":false,\"max_order_size\":null,\"delivery_fee\":0,\"max_composite_score\":10,\"id\":36,\"merchant_promotions\":[],\"average_rating\":4.6,\"menus\":[{\"is_catering\":false,\"subtitle\":\"\",\"id\":125639,\"name\":\"Agave Mexican Bistro (Dinner) (Mountain View)\"},{\"is_catering\":false,\"subtitle\":\"\",\"id\":125339,\"name\":\"Agave Mexican Bistro (Lunch) (Mountain View)\"}],\"composite_score\":9,\"status_type\":\"open\",\"is_only_catering\":false,\"status\":\"46 mins\",\"number_of_ratings\":1046,\"asap_time\":46,\"description\":\"Traditional Mexican Meets Contemporary California\",\"business\":{\"id\":40,\"name\":\"Agave Mexican Bistro\"},\"tags\":[\"Mexican\"],\"yelp_review_count\":297,\"business_id\":40,\"extra_sos_delivery_fee\":0,\"yelp_rating\":3.5,\"cover_img_url\":\"https://cdn.doordash.com/media/restaurant/cover/Agave-Mexican-Bistro.png\",\"header_img_url\":\"\",\"address\":{\"city\":\"Mountain View\",\"state\":\"CA\",\"street\":\"194 Castro Street\",\"lat\":37.394068,\"lng\":-122.079161,\"printable_address\":\"194 Castro Street, Mountain View, CA 94041, USA\"},\"price_range\":2,\"slug\":\"agave-mexican-bistro-mountain-view\",\"name\":\"Agave Mexican Bistro\",\"is_newly_added\":false,\"url\":\"/store/agave-mexican-bistro-mountain-view-36/\",\"service_rate\":10,\"promotion\":null,\"featured_category_description\":null},{\"is_time_surging\":false,\"max_order_size\":300000,\"delivery_fee\":0,\"max_composite_score\":10,\"id\":63,\"merchant_promotions\":[],\"average_rating\":4.7,\"menus\":[{\"is_catering\":false,\"subtitle\":\"\",\"id\":27575,\"name\":\"Fuki Sushi (Dinner)\"},{\"is_catering\":false,\"subtitle\":\"\",\"id\":40310,\"name\":\"Fuki Sushi (Lunch)\"},{\"is_catering\":true,\"subtitle\":\"\",\"id\":2875,\"name\":\"Fuki Sushi (Catering)\"}],\"composite_score\":9,\"status_type\":\"open\",\"is_only_catering\":false,\"status\":\"33 mins\",\"number_of_ratings\":6734,\"asap_time\":33,\"description\":\"Sushi Bar, Japanese\",\"business\":{\"id\":61,\"name\":\"Fuki Sushi\"},\"tags\":[\"Sushi\",\"Japanese\",\"Catering\"],\"yelp_review_count\":752,\"business_id\":61,\"extra_sos_delivery_fee\":0,\"yelp_rating\":3.5,\"cover_img_url\":\"https://cdn.doordash.com/media/restaurant/cover/Fuki-Sushi.png\",\"header_img_url\":\"https://res.cloudinary.com/doordash/image/fetch/c_scale,w_1200,h_564,q_80/https://doordash-static.s3.amazonaws.com/media/photos/e85adc64-f50d-473d-85e2-4661c067e9ba-retina-large.jpg\",\"address\":{\"city\":\"Palo Alto\",\"state\":\"CA\",\"street\":\"4119 El Camino Real\",\"lat\":37.4137395,\"lng\":-122.125722,\"printable_address\":\"4119 El Camino Real, Palo Alto, CA 94306, USA\"},\"price_range\":2,\"slug\":\"fuki-sushi-palo-alto\",\"name\":\"Fuki Sushi (El Camino Real)\",\"is_newly_added\":false,\"url\":\"/store/fuki-sushi-palo-alto-63/\",\"service_rate\":10,\"promotion\":null,\"featured_category_description\":null}]";

    private static final String SAMPLE_JSON_RESPONSE_GET_RESTAURENT_INFO = "{\"phone_number\":\"+16503270107\",\"yelp_review_count\":435,\"max_order_size\":null,\"delivery_fee\":0,\"max_composite_score\":10,\"id\":14,\"average_rating\":4.6,\"tags\":[\"Greek\",\"Mediterranean\",\"Turkish\",\"Catering\"],\"delivery_radius\":10000,\"inflation_rate\":0.0,\"menus\":[{\"status\":\"Available now\",\"subtitle\":\"\",\"name\":\"Gyros Gyros (All Day)\",\"open_hours\":[[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":45}]],\"is_catering\":false,\"id\":59,\"status_type\":\"open\"},{\"status\":\"Order 24.0 hours in advance\",\"subtitle\":\"\",\"name\":\"Gyros Gyros (Catering)\",\"open_hours\":[[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}],[{\"hour\":9,\"minute\":45},{\"hour\":22,\"minute\":30}]],\"is_catering\":true,\"id\":46336,\"status_type\":\"order-advance\"}],\"show_store_menu_header_photo\":false,\"composite_score\":9,\"number_of_ratings\":5975,\"status_type\":\"open\",\"is_only_catering\":false,\"status\":\"24 - 34 mins\",\"object_type\":\"restaurant.restaurant\",\"description\":\"Greek and Mediterranean\",\"business\":{\"business_vertical\":null,\"id\":19,\"name\":\"Gyros Gyros\"},\"yelp_biz_id\":\"gyros-gyros-palo-alto\",\"asap_time\":29,\"yelp_rating\":3.5,\"extra_sos_delivery_fee\":0,\"business_id\":19,\"special_instructions_max_length\":null,\"cover_img_url\":\"https://cdn.doordash.com/media/restaurant/cover/gyros_gyros.png\",\"address\":{\"city\":\"Palo Alto\",\"subpremise\":\"\",\"id\":360,\"printable_address\":\"498 University Avenue, Palo Alto, CA 94301, USA\",\"state\":\"CA\",\"street\":\"498 University Avenue\",\"country\":\"United States\",\"lat\":37.448308,\"lng\":-122.1592192,\"shortname\":\"498 University Avenue\",\"zip_code\":\"94301\"},\"price_range\":2,\"slug\":\"gyros-gyros-palo-alto\",\"show_suggested_items\":true,\"name\":\"Gyros Gyros\",\"is_newly_added\":false,\"is_good_for_group_orders\":true,\"service_rate\":10.0,\"merchant_promotions\":[],\"header_image_url\":null}";

    @Test
    public void getInstance() throws Exception {
        NetworkApi instance1 = NetworkApi.getInstance();
        NetworkApi instance2 = NetworkApi.getInstance();
        assertTrue(instance1.hashCode() == instance2.hashCode());
    }

    @Test
    public void getRestaurants() throws Exception {
        NetworkApi instance = NetworkApi.getInstance();
        final Request request = new Request.Builder().get().url("https://www.doordash.com").build();
        OkHttpClient stubbedClient = new okhttp3.OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return new Response.Builder()
                        .body(RealResponseBody.create(MediaType.parse("Application/json"), SAMPLE_JSON_RESPONSE_GET_RESTAURENTS))
                        .request(request)
                        .protocol(Protocol.HTTP_1_0)
                        .code(200)
                        .message("someMessage")
                        .build();
            }
        }).build();
        instance.mHttpClient = stubbedClient;
        List<RestaurantListItem> restaurants = instance.getRestaurants(1, 1);
        assertEquals(2, restaurants.size());
    }

    @Test
    public void getRestaurantInfo() throws Exception {
        NetworkApi instance = NetworkApi.getInstance();
        final Request request = new Request.Builder().get().url("https://www.doordash.com").build();
        OkHttpClient stubbedClient = new okhttp3.OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return new Response.Builder()
                        .body(RealResponseBody.create(MediaType.parse("Application/json"), SAMPLE_JSON_RESPONSE_GET_RESTAURENT_INFO))
                        .request(request)
                        .protocol(Protocol.HTTP_1_0)
                        .code(200)
                        .message("someMessage")
                        .build();
            }
        }).build();
        instance.mHttpClient = stubbedClient;
        RestaurantInfo restaurantInfo = instance.getRestaurantInfo(1);
        assertEquals("Gyros Gyros", restaurantInfo.getName());
        assertEquals("498 University Avenue, Palo Alto, CA 94301, USA", restaurantInfo.getPrintableAddress());
    }

}