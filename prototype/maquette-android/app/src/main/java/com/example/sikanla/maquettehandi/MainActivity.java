package com.example.sikanla.maquettehandi;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import com.example.sikanla.maquettehandi.DialogFragment.HelpChoice_DF;
import com.example.sikanla.maquettehandi.DialogFragment.ProfileDialogFragment;
import com.example.sikanla.maquettehandi.Model.User;
import com.example.sikanla.maquettehandi.UI.Menu.FriendsFragment;
import com.example.sikanla.maquettehandi.UI.Menu.InstantFragment;
import com.example.sikanla.maquettehandi.UI.Menu.MyPlannedFragment;
import com.example.sikanla.maquettehandi.UI.Menu.NotificationFragment;
import com.example.sikanla.maquettehandi.UI.Menu.ParametersFragment;
import com.example.sikanla.maquettehandi.UI.TabFragment;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.InstantRequester;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView firstnameHeader;
    private ImageView imageViewHeader;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instantiateNavigationView();
        instantiateFAB();
        instantiateTabToolbarDrawer();

        ProtectedHuaweyApps protectedHuaweyApps = new ProtectedHuaweyApps();
        protectedHuaweyApps.ifHuaweiAlert(this);
        askGpsPermission();

        notificationIntent();

    }

    private void notificationIntent() {
        String menuFragment = getIntent().getStringExtra("menuFragment");

        if (menuFragment != null) {
            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (menuFragment.equals("NotificationFragment")) {
                prepareLayout();
                navigationView.setCheckedItem(R.id.nav_notification);
                launchFragment(new NotificationFragment(),"Planifié");
            } else if (menuFragment.equals("InstantFragment")) {
                prepareLayout();
                navigationView.setCheckedItem(R.id.nav_notification);
                launchFragment(new InstantFragment(),"Instantanée");
            }
        } else {
            launchFragment(new TabFragment(), "Accueil");
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        askGpsPermission();
    }

    private void askGpsPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        } else {
            fetchPosition();
        }
    }

    private void fetchPosition() {
        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            InstantRequester instantRequester= new InstantRequester();
            instantRequester.updatePosition(getBaseContext(), String.valueOf(gps.getLongitude()), String.valueOf(gps.getLatitude()), new InstantRequester.InstantCB() {
                @Override
                public void onInstantCB(boolean success) {

                }
            });
        }
        gps.stopUsingGPS();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchPosition();
                }
                return;
            }
        }
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
        imageViewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
                Bundle args = new Bundle();
                args.putString("id", user.getUserId());
                profileDialogFragment.setArguments(args);
                profileDialogFragment.show(getFragmentManager(), "answerPlanned");
            }
        });
        ImageRequester imageRequester = new ImageRequester();
        imageRequester.getImage(user.getUserId(), this, new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String url) {
                if (url != null)
                    Picasso.with(getApplicationContext()).load(url).centerCrop().fit().into(imageViewHeader);
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
                HelpChoice_DF helpChoice_df = new HelpChoice_DF();
                helpChoice_df.show(getFragmentManager(), "help-choice");
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
            prepareLayout();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setCheckedItem(R.id.nav_settings);
            Fragment fragment = new ParametersFragment();
            launchFragment(fragment, item.getTitle().toString());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (!item.isChecked()) {
            prepareLayout();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Fragment fragment = new Fragment();

            switch (item.getItemId()) {
                case R.id.nav_accueil:
                    floatingActionButton.setVisibility(View.VISIBLE);
                    fragment = new TabFragment();
                    navigationView.setCheckedItem(R.id.nav_accueil);
                    break;

                case R.id.nav_instant:
                    fragment = new InstantFragment();
                    navigationView.setCheckedItem(R.id.nav_accueil);
                    break;

                case R.id.nav_friend:
                    fragment = new FriendsFragment();
                    navigationView.setCheckedItem(R.id.nav_friend);
                    break;
                case R.id.nav_settings:
                    navigationView.setCheckedItem(R.id.nav_settings);

                    fragment = new ParametersFragment();
                    break;

                case R.id.nav_myapplication:
                    navigationView.setCheckedItem(R.id.nav_myapplication);

                    fragment = new MyPlannedFragment();
                    break;
                case R.id.nav_notification:
                    navigationView.setCheckedItem(R.id.nav_notification);

                    fragment = new NotificationFragment();
                    break;
            }

            launchFragment(fragment, item.getTitle().toString());
            item.setChecked(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mytabs);
        tabLayout.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
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



