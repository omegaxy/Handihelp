package com.example.sikanla.maquettehandi;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import com.example.sikanla.maquettehandi.UI.FormPlannedRequestActi;
import com.example.sikanla.maquettehandi.UI.ParametersFragment;
import com.example.sikanla.maquettehandi.UI.TabFragment;

import com.example.sikanla.maquettehandi.identification.User;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TextView firstnameHeader;
    private ImageView imageViewHeader;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        instantiateNavigationView();
        instantiateFAB();
        instantiateTabToolbarDrawer();
        launchFragment(new TabFragment(), "Accueil");


    }

    private void instantiateNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setCheckedItem(R.id.nav_accueil);
        User user = new User();
        View headerView = navigationView.getHeaderView(0);
        imageViewHeader = (ImageView) headerView.findViewById(R.id.user_pp);
        firstnameHeader = (TextView) headerView.findViewById(R.id.firstname_header);
        firstnameHeader.setText(user.getFirstName());
        ImageRequester imageRequester = new ImageRequester();
        imageRequester.getImage(user.getUserId(), this, new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String url) {
                if (url != null)
                    Picasso.with(getApplicationContext()).load(url).into(imageViewHeader);
            }
        });
    }

    private void instantiateTabToolbarDrawer() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();

            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                navigationView.bringToFront();

            }
        };
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void instantiateFAB() {
        floatingActionButton = new FloatingActionButton(this);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PickAideDialogFragment pickAideDialogFragment = new PickAideDialogFragment();
                //pickAideDialogFragment.show(getFragmentManager(), "ProfileDialogFragment");
                startActivity(new Intent(MainActivity.this, FormPlannedRequestActi.class));


            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //todo manage onback pressed to prevent coming back to login screen
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (!item.isChecked()) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Fragment fragment = new Fragment();

            TabLayout tabLayout = (TabLayout) findViewById(R.id.mytabs);
            tabLayout.setVisibility(View.GONE);

            switch (item.getItemId()) {
                case R.id.nav_accueil:
                    fragment = new TabFragment();
                    navigationView.setCheckedItem(R.id.nav_accueil);
                    break;
                case R.id.nav_friend:
                    break;
                case R.id.nav_settings:
                    navigationView.setCheckedItem(R.id.nav_settings);

                    fragment = new ParametersFragment();
            }

            launchFragment(fragment, item.getTitle().toString());
            item.setChecked(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void launchFragment(Fragment fragment, String title) {
        if (fragment != null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(title);
        }
    }
}



