package com.map.social.mariia.worldsocialmap;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener {

    private TextView name;
    private TextView surename;
    private CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        View headerLayout = navigationView.getHeaderView(0);
        name = (TextView) headerLayout.findViewById(R.id.my_name);
        surename = (TextView) headerLayout.findViewById(R.id.my_surename);
        imageView = (CircleImageView) headerLayout.findViewById(R.id.my_super_photo);

        if (account != null) {
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            Uri personPhoto = account.getPhotoUrl();
            name.setText(personGivenName);
            surename.setText(personFamilyName);
            Picasso.get().load(personPhoto)
                    .transform(new CropCircleTransformation())
                    .into(imageView);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int id){
        Fragment newFragment = null;

        switch (id){
            case R.id.user_page:
                newFragment = new UserPage();
                break;
            case R.id.visited_places:
                newFragment = new CountryList();
                break;
            case R.id.wish_places:
                newFragment = new CountryWishList();
                break;

        }

        if(newFragment != null){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_user2, newFragment);
            transaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        displaySelectedScreen(id);


        return true;
    }

    // GoogleApiClient.OnConnectionFailedListener
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult var1) {
        Log.e(">>>>>>>>>>>>", "Connection failed");
    }
}
