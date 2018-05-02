package com.map.social.mariia.worldsocialmap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class UploadPhoto extends Fragment {
    private static final int CAMERA_REQUEST_CODE = 1;
    private StorageReference storageRef;
    private MyProgressDialog progressDialog;
    private ImageView photo;
    private ImageButton returnBtn;
    private String userId;
    public Bundle returnBundle;
    Uri downloadUri;

    public UploadPhoto() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        storageRef = FirebaseStorage.getInstance().getReference();
        userId = bundle.getString("user_id");
        returnBundle = new Bundle();
        ImageButton uploadBtn = (ImageButton) getActivity().findViewById(R.id.upload_btn);
        photo = (ImageView) getActivity().findViewById(R.id.uploaded_photo);
        returnBtn = (ImageButton) getActivity().findViewById(R.id.return_btn);
        progressDialog = new MyProgressDialog(getActivity());

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);


            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(returnBundle.getString("photo_url") == null){
                    Toast.makeText(getActivity(), "Please upload photo!!!", Toast.LENGTH_LONG).show();
                } else {
                    VisitedForm visitedForm = new VisitedForm();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    visitedForm.setArguments(returnBundle);
                    fragmentTransaction.replace(R.id.content_user2, visitedForm);
                    fragmentTransaction.commit();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            progressDialog.show();
            Uri uri = data.getData();

            StorageReference filepath = storageRef.child(userId).child("Photos").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();

                    downloadUri = taskSnapshot.getDownloadUrl();

                    Picasso.get().load(downloadUri)
                            .into(photo);

                    returnBundle.putString("photo_url", downloadUri.toString());

                    Toast.makeText(getActivity(), "Uploading finished ...", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
