package com.map.social.mariia.worldsocialmap;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mariia on 01.05.2018.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PlaceViewHolder>{
    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView visitedCity;
        TextView visitedPlace;
        ImageView visitedPlacePhoto;
        TextView comment;
        PlaceViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            visitedCity = (TextView)itemView.findViewById(R.id.visited_city);
            visitedPlace = (TextView)itemView.findViewById(R.id.visited_place);
            visitedPlacePhoto = (ImageView)itemView.findViewById(R.id.visited_place_photo);
            comment = (TextView) itemView.findViewById(R.id.visited_place_comment);
        }
    }

    Context context;
    List<VisitedPlace> places;
    RVAdapter(List<VisitedPlace> places){
        this.places = places;
    }

    public RVAdapter(Context context, List<VisitedPlace> TempList) {

        this.places = TempList;

        this.context = context;
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place, viewGroup, false);
        PlaceViewHolder pvh = new PlaceViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder placeViewHolder, int i) {
        placeViewHolder.visitedCity.setText(places.get(i).getVisitedCity());
        placeViewHolder.visitedPlace.setText(places.get(i).getPlace());
        Picasso.get()
                .load(places.get(i).getPathToFile())
                .into(placeViewHolder.visitedPlacePhoto);
        placeViewHolder.comment.setText(places.get(i).getComment());
    }
}
