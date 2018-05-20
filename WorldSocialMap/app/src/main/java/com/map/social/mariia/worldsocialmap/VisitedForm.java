package com.map.social.mariia.worldsocialmap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VisitedForm extends Fragment implements View.OnClickListener{

    Button addPhotoBtn;
    Button submitBtn;
    EditText countryEditText;
    EditText cityEditText;
    EditText placeEditText;
    EditText commentEditText;
    String userID="";
    public VisitedForm() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visited_form, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        addPhotoBtn = (Button) getActivity().findViewById(R.id.add_photo_btn);
        countryEditText = (EditText) getActivity().findViewById(R.id.country_visited);
        cityEditText = (EditText) getActivity().findViewById(R.id.city_visited);
        placeEditText = (EditText) getActivity().findViewById(R.id.place_visited);
        commentEditText = (EditText) getActivity().findViewById(R.id.comment);
        submitBtn = (Button) getActivity().findViewById(R.id.visited_btn);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (account != null) {
            userID = account.getId();
        } else {
            Log.e("error","We have big troubles");
        }

        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadPhoto uploadPhoto = new UploadPhoto();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("user_id", userID);
                uploadPhoto.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_user2, uploadPhoto);
                fragmentTransaction.commit();
            }
        });
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final String country = countryEditText.getText().toString();

        final String city = cityEditText.getText().toString();

        final String place = placeEditText.getText().toString();

        final String comment = commentEditText.getText().toString();

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

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Bundle bundle = getArguments();
        final VisitedPlace visitedPlace = new VisitedPlace( country, city, place, comment);
        if(bundle != null && bundle.getString("photo_url") != null){
            visitedPlace.setPathToFile(bundle.getString("photo_url"));
        }
        DatabaseReference visitedPlaceReference = ref.child("visited_place").child(userID);
        visitedPlaceReference
                .child("country_list")
                .push()
                .setValue(new Country(country));
        visitedPlaceReference
                .child(visitedPlace.getVisitedCountry())
                .push()
                .setValue(visitedPlace);
        UserPage userPage = new UserPage();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_user2, userPage);
        fragmentTransaction.commit();
    }

}
