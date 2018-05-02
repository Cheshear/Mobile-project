package com.map.social.mariia.worldsocialmap;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WantForm extends Fragment implements View.OnClickListener {
    private DatabaseReference mDatabase;
    Button submitBtn;
    EditText countryEditText;
    EditText cityEditText;
    EditText placeEditText;

    public WantForm() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_want_form, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        countryEditText = (EditText) getActivity().findViewById(R.id.want_country);
        cityEditText = (EditText) getActivity().findViewById(R.id.want_city);
        placeEditText = (EditText) getActivity().findViewById(R.id.want_place);
        submitBtn = (Button) getActivity().findViewById(R.id.want_btn);

        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final String country = countryEditText.getText().toString();

        final String city = cityEditText.getText().toString();

        final String place = placeEditText.getText().toString();

        if(country.length()==0)
        {
            countryEditText.requestFocus();
            countryEditText.setError("FIELD CANNOT BE EMPTY");
        }

        else if(!country.matches("[a-zA-Z ]+"))
        {
            countryEditText.requestFocus();
            countryEditText.setError("ENTER ONLY ALPHABETICAL CHARACTER");
        }

        else if(city.length()==0)
        {
            cityEditText.requestFocus();
            cityEditText.setError("FIELD CANNOT BE EMPTY");
        }

        else if(!city.matches("[a-zA-Z ]+"))
        {
            cityEditText.requestFocus();
            cityEditText.setError("ENTER ONLY ALPHABETICAL CHARACTER");
        }

        else if(place.length()==0)

        {
            placeEditText.requestFocus();
            placeEditText.setError("FIELD CANNOT BE EMPTY");
        }

        String userID = "";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userID = user.getUid();
        } else {
            Log.e("error","We have big troubles");
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        WantPlace wantPlase= new WantPlace(country, city, place);
        DatabaseReference wantPlaceReference = mDatabase.child("wanted_place").child(userID);
        wantPlaceReference
                .child("country_list")
                .setValue(new Country(country));
        wantPlaceReference
                .child(wantPlase.getWishCountry())
                .push()
                .setValue(wantPlase);
        UserPage userPage = new UserPage();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_user2, userPage);
        fragmentTransaction.commit();
    }
}
