package com.example.sikanla.maquettehandi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
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
import com.example.sikanla.maquettehandi.DialogFragment.PickAideDialogFragment;
import com.example.sikanla.maquettehandi.UI.HistoricFragment;
import com.example.sikanla.maquettehandi.UI.InstantFragment;
import com.example.sikanla.maquettehandi.UI.ScheduledFragment;
import com.example.sikanla.maquettehandi.identification.User;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView firstnameHeader;
    private ImageView imageViewHeader;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        instantiateFAB();
        instantiateTabToolbarDrawer();
        instantiateNavigationView();


    }

    private void instantiateNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
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

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.mytabs);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void instantiateFAB() {
        floatingActionButton = new FloatingActionButton(this);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickAideDialogFragment pickAideDialogFragment = new PickAideDialogFragment();
                pickAideDialogFragment.show(getFragmentManager(), "ProfileDialogFragment");
            }
        });
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ScheduledFragment(), "Demandes");
        adapter.addFragment(new InstantFragment(), "Messages");
        adapter.addFragment(new HistoricFragment(),"testTab");
        viewPager.setAdapter(adapter);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_friend) {
//list of selected profile
        } else if (id == R.id.nav_history) {
//history of all previous assistance given and requested
        } else if (id == R.id.nav_notification) {
//history of all previous notifications
        } else if (id == R.id.nav_myapplication) {
//history of all the assistance requested and without response
        } else if (id == R.id.nav_settings) {
//where you can logout, change your data, etc.
        } else if (id == R.id.nav_help) {
//explain how the application works
        } else if (id == R.id.nav_propos) {
//Where we write the name of developpers
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



