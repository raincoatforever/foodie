package com.ajinkya.foodie.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ajinkya.foodie.R;
import com.ajinkya.foodie.model.RestaurantListItem;
import com.ajinkya.foodie.viewAdapters.RestaurantsListViewAdapter;
import com.ajinkya.foodie.tasks.FetchRestaurantListTask;

import java.util.LinkedList;
import java.util.List;

import static com.ajinkya.foodie.IntentExtraConstants.RESTAURANT_ID;
import static com.ajinkya.foodie.IntentExtraConstants.RESTAURANT_NAME;

public class MainActivity extends AppCompatActivity implements FetchRestaurantListTask.Listener{

    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle(getString(R.string.restaurent_list_view_title));

        mRecyclerView = (RecyclerView) findViewById(R.id.restaurents_list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        new FetchRestaurantListTask(this).execute(37.422740, -122.139956);
    }

    @Override
    public void onComplete(List<RestaurantListItem> restaurants) {
        final Activity currentActivity = this;
        RestaurantsListViewAdapter adapter = new RestaurantsListViewAdapter(MainActivity.this, restaurants);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnRestaurantSelectedListener(new RestaurantsListViewAdapter.OnRestaurantSelectedListener() {
            @Override
            public void onItemClick(RestaurantListItem item) {
                Toast.makeText(MainActivity.this, item.getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(currentActivity, RestaurantViewActivity.class);
                intent.putExtra(RESTAURANT_ID, item.getId());
                intent.putExtra(RESTAURANT_NAME, item.getName());
                startActivity(intent);
            }
        });
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
    }

}
