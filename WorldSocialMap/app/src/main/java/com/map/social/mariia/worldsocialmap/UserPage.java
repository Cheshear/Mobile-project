package com.map.social.mariia.worldsocialmap;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class UserPage extends Fragment {

    private TextView email;
    private TextView name;
    private TextView surename;
    private ImageView imageView;

    public UserPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        email = (TextView) getView().findViewById(R.id.email_value);
        name = (TextView) getView().findViewById(R.id.name_value);
        surename = (TextView) getView().findViewById(R.id.surename_value);
        if (account != null) {
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            Uri personPhoto = account.getPhotoUrl();
            email.setText(personEmail);
            name.setText(personGivenName);
            surename.setText(personFamilyName);
            Picasso.get().load(personPhoto).networkPolicy(NetworkPolicy.NO_CACHE).into(imageView);
        }
        return inflater.inflate(R.layout.fragment_user_page, container, false);
    }

}
