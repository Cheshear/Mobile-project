package com.map.social.mariia.worldsocialmap;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CountryWishList extends ListFragment {
    DatabaseReference db;
    ArrayList<String> countries;
    final String TAG = "tag";
    String userID="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (account != null) {
            userID = account.getId();
        } else {
            Log.e("error","We have big troubles");
        }
        db= FirebaseDatabase.getInstance()
                .getReference()
                .child("wanted_place")
                .child(userID)
                .child("country_list");

        countries = new ArrayList<>();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Country country = dataSnapshot.getValue(Country.class);
                Log.e(TAG, country.getCountry());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseListOptions<String> options = new FirebaseListOptions.Builder<String>()
                .setQuery(db, String.class)
                .setLayout(android.R.layout.simple_list_item_2)
                .build();

        FirebaseListAdapter<String> fireAdapter = new FirebaseListAdapter<String>
                (options) {
            @Override
            protected void populateView(View v, String model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model);
                countries.add(model);
            }
        };

        setListAdapter(fireAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();

        /** Setting the multiselect choice mode for the listview */
        getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        VisitedPlaces visitedPlaces = new VisitedPlaces();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("user_id", userID);
        bundle.putString("country", countries.get(position));
        visitedPlaces.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_user2, visitedPlaces);
        fragmentTransaction.commit();
        // Capture the layout's TextView and set the string as its text
    }

}
