package com.map.social.mariia.worldsocialmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WishPlaces extends Fragment {
    private String userId;
    private String country;
    private DatabaseReference db;
    private List<WantPlace> wishPlaces = new ArrayList<>();
    private MyProgressDialog progressDialog;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter ;
    final String TAG = "tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString("user_id");
            country = getArguments().getString("country");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.list_wish_places);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressDialog = new MyProgressDialog(getActivity());

        progressDialog.show();

        db= FirebaseDatabase.getInstance()
                .getReference()
                .child("wanted_place")
                .child(userId)
                .child(country);


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    WantPlace wantPlace = dataSnapshot.getValue(WantPlace.class);

                    wishPlaces.add(wantPlace);
                }

                adapter = new WishPlaceRVAdapter(getActivity(), wishPlaces);

                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
