package com.ajinkya.foodie.viewAdapters;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajinkya.foodie.R;
import com.ajinkya.foodie.model.RestaurantListItem;
import com.ajinkya.foodie.model.RestaurantStatusType;
import com.ajinkya.foodie.util.StarRestaurantUtils;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.ajinkya.foodie.util.Utils.imageViewAnimatedChange;

public class RestaurantsListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RestaurantListItem> restaurantList;
    private Context mContext;
    private OnRestaurantSelectedListener onRestaurantSelectedListener;

    private static final int TYPE_VIEW_WITH_COVERIMAGE = 0;
    private static final int TYPE_VIEW_WITHOUT_COVERIMAGE = 1;

    public RestaurantsListViewAdapter(final Context context, List<RestaurantListItem> restaurantList) {
        this.restaurantList = restaurantList;
        this.mContext = context;
        Collections.sort(this.restaurantList, new Comparator<RestaurantListItem>() {
            @Override
            public int compare(RestaurantListItem item1, RestaurantListItem item2) {
                if(StarRestaurantUtils.isStarred(item1.getId(), context)) {
                    return -1;
                } else if(StarRestaurantUtils.isStarred(item2.getId(), context) ){
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_VIEW_WITH_COVERIMAGE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurent_list_item_view, viewGroup, false);
                return new ViewHolderWithCoverImage(view);
            case TYPE_VIEW_WITHOUT_COVERIMAGE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurent_list_item_without_cover_image,viewGroup, false);
                return new ViewHolderWithoutCoverImage(view);
            default:
                throw new IllegalStateException();

        }
    }

    @Override
    public int getItemViewType(int position) {
        RestaurantListItem restaurantListItem = restaurantList.get(position);
        if(restaurantListItem.getHeaderImgUri() != null && !TextUtils.isEmpty(restaurantListItem.getHeaderImgUri().toString())) {
            return TYPE_VIEW_WITH_COVERIMAGE;
        } else {
            return TYPE_VIEW_WITHOUT_COVERIMAGE;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        final RestaurantListItem restaurantListItem = restaurantList.get(i);
        RestaurantCardView restaurantCardView = (RestaurantCardView) viewHolder;
        restaurantCardView.customiseForItem(restaurantListItem);
    }

    @Override
    public int getItemCount() {
        return (null != restaurantList ? restaurantList.size() : 0);
    }

    private interface RestaurantCardView {
        void customiseForItem(RestaurantListItem item);
    }

    private class ViewHolderWithoutCoverImage extends RecyclerView.ViewHolder implements RestaurantCardView {
        ImageView thumbnailImageView;
        ImageView starImageView;
        TextView titleTextView;
        TextView tagsView;
        TextView statusView;
        TextView deliveryFeeView;
        View baseLayout;

        ViewHolderWithoutCoverImage(View view) {
            super(view);
            this.thumbnailImageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.starImageView = (ImageView) view.findViewById(R.id.star);
            this.titleTextView = (TextView) view.findViewById(R.id.title);
            this.tagsView = (TextView) view.findViewById(R.id.tags);
            this.statusView = (TextView) view.findViewById(R.id.statusBox);
            this.deliveryFeeView = (TextView) view.findViewById(R.id.delivery_fees);
            this.baseLayout = view.findViewById(R.id.base_layout_for_card_without_cover_image);
        }

        @Override
        public void customiseForItem(final RestaurantListItem item) {
            Picasso.with(mContext).load(item.getThumbnailUri())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(thumbnailImageView);

            if(item.getStatusType() == RestaurantStatusType.OPEN) {
                statusView.setText(Html.fromHtml(item.getStatus()));
            } else {
                statusView.setText(Html.fromHtml(mContext.getString(R.string.pre_order_string)));
            }

            deliveryFeeView.setText(Html.fromHtml(item.getDeliveryFee()));
            titleTextView.setText(Html.fromHtml(item.getName()));
            tagsView.setText(Html.fromHtml(item.getTags()));

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRestaurantSelectedListener.onItemClick(item);
                }
            };
            boolean starred = StarRestaurantUtils.isStarred(item.getId(), mContext);
            if(starred) {
                imageViewAnimatedChange(mContext, starImageView, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_new));
            } else {
                imageViewAnimatedChange(mContext, starImageView, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star));
            }
            starImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean starred = StarRestaurantUtils.toggleAndGet(item.getId(), mContext);
                    if(starred) {
                        imageViewAnimatedChange(mContext, starImageView, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_new));
                    } else {
                        imageViewAnimatedChange(mContext, starImageView, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star));
                    }
                }
            });

            baseLayout.setOnClickListener(clickListener);
        }
    }

    private class ViewHolderWithCoverImage extends RecyclerView.ViewHolder implements RestaurantCardView{
        ImageView coverImageView;
        ImageView starImageView;
        ImageView thumbnailImageView;
        TextView titleView;
        TextView tagsView;
        TextView statusView;
        TextView deliveryFeeView;
        View baseLayout;

        ViewHolderWithCoverImage(View view) {
            super(view);
            this.thumbnailImageView = (ImageView) view.findViewById(R.id.thumbnail_under_cover);
            this.starImageView = (ImageView) view.findViewById(R.id.star_under_cover);
            this.coverImageView = (ImageView) view.findViewById(R.id.coverImage);
            this.titleView = (TextView) view.findViewById(R.id.title_under_cover);
            this.tagsView = (TextView) view.findViewById(R.id.tags_under_cover);
            this.statusView = (TextView) view.findViewById(R.id.statusBox_under_cover);
            this.deliveryFeeView = (TextView) view.findViewById(R.id.delivery_fees_under_cover);
            this.baseLayout = view.findViewById(R.id.base_layout_for_card);
        }

        @Override
        public void customiseForItem(final RestaurantListItem item) {
            Picasso.with(mContext).load(item.getThumbnailUri())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(thumbnailImageView);

            Picasso.with(mContext).load(item.getHeaderImgUri())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(coverImageView);

            titleView.setText(Html.fromHtml(item.getName()));
            tagsView.setText(Html.fromHtml(item.getTags()));
            if(item.getStatusType() == RestaurantStatusType.OPEN) {
                statusView.setText(Html.fromHtml(item.getStatus()));
            } else {
                statusView.setText(Html.fromHtml(mContext.getString(R.string.pre_order_string)));
            }

            deliveryFeeView.setText(Html.fromHtml(item.getDeliveryFee()));

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRestaurantSelectedListener.onItemClick(item);
                }
            };

            boolean starred = StarRestaurantUtils.isStarred(item.getId(), mContext);
            if(starred) {
                imageViewAnimatedChange(mContext, starImageView, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_new));
            } else {
                imageViewAnimatedChange(mContext, starImageView, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star));
            }

            starImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean starred = StarRestaurantUtils.toggleAndGet(item.getId(), mContext);
                    if(starred) {
                        imageViewAnimatedChange(mContext, starImageView, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_new));
                    } else {
                        imageViewAnimatedChange(mContext, starImageView, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star));
                    }
                }
            });
            baseLayout.setOnClickListener(listener);
        }
    }

    public void setOnRestaurantSelectedListener(OnRestaurantSelectedListener onRestaurantSelectedListener) {
        this.onRestaurantSelectedListener = onRestaurantSelectedListener;
    }

    public interface OnRestaurantSelectedListener {
        void onItemClick(RestaurantListItem item);
    }
}