package com.map.social.mariia.worldsocialmap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class UserPage extends Fragment {

    private TextView email;
    private TextView name;
    private TextView surename;
    private CircleImageView imageView;
    private Button addWant;
    private Button addVisited;

    public UserPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_user_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        email = (TextView) getView().findViewById(R.id.email_value);
        name = (TextView) getView().findViewById(R.id.name_value);
        surename = (TextView) getView().findViewById(R.id.surename_value);
        imageView = (CircleImageView) getView().findViewById(R.id.profile_image);
        addWant = (Button) getView().findViewById(R.id.want_to_visit_btn);
        addVisited = (Button) getView().findViewById(R.id.visited_place_btn);

        if (account != null) {
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            Uri personPhoto = account.getPhotoUrl();
            email.setText(personEmail);
            name.setText(personGivenName);
            surename.setText(personFamilyName);
            Picasso.get().load(personPhoto)
                    .transform(new CropCircleTransformation())
                    .into(imageView);
            View.OnClickListener addWantListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WantForm wantForm = new WantForm();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_user2, wantForm);
                    fragmentTransaction.commit();
                }
            };
            addWant.setOnClickListener(addWantListener);

            View.OnClickListener addVisitedListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VisitedForm visitedForm = new VisitedForm();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_user2, visitedForm);
                    fragmentTransaction.commit();
                }
            };
            addVisited.setOnClickListener(addVisitedListener);


        }
    }
}
