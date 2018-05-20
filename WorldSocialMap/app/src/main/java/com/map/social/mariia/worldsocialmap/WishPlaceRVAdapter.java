package com.map.social.mariia.worldsocialmap;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WishPlaceRVAdapter extends RecyclerView.Adapter<WishPlaceRVAdapter.WishPlaceViewHolder>{

    public static class WishPlaceViewHolder extends RecyclerView.ViewHolder {
        CardView wishPlaceCardView;
        TextView wishCity;
        TextView wishPlace;
        WishPlaceViewHolder(View itemView) {
            super(itemView);
            wishPlaceCardView = (CardView)itemView.findViewById(R.id.wish_cv);
            wishCity = (TextView)itemView.findViewById(R.id.wish_city);
            wishPlace = (TextView)itemView.findViewById(R.id.wish_place);
        }
    }

    Context context;
    List<WantPlace> places;
    WishPlaceRVAdapter(List<WantPlace> places){
        this.places = places;
    }

    public WishPlaceRVAdapter(Context context, List<WantPlace> TempList) {

        this.places = TempList;

        this.context = context;
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    @Override
    public WishPlaceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wish_place, viewGroup, false);
        WishPlaceViewHolder pvh = new WishPlaceViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(WishPlaceRVAdapter.WishPlaceViewHolder placeViewHolder, int i) {
        placeViewHolder.wishCity.setText(places.get(i).getWishCity());
        placeViewHolder.wishPlace.setText(places.get(i).getPlace());
    }
}
