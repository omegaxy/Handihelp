package com.example.sikanla.maquettehandi.UI.Activities;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.R;


/**
 * Created by Nicolas on 05/06/2017.
 */

public class LocationActivity extends Activity {

    private TextView myLocationText;
    private Location l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_fragment);

        myLocationText = (TextView)findViewById(R.id.textView1);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM );
        ;
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);

        String provider1 = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        l = locationManager.getLastKnownLocation(provider1);

        updateWithNewLocation(l, myLocationText);

        //requestLocationUpdates(String provider, long minTime, float minDistance, LocationListener listener)
        locationManager.requestLocationUpdates(provider1, 1, 1, locationListener);

    }


    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location, myLocationText);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int Status, Bundle extras) {
        }
    };


    private void updateWithNewLocation(Location l, TextView t) {
        //envoyer la localisation à la bdd


        String latLongString = "Aucun Emplacement trouvé";
        String adresseString = "Aucune adresse trouvée";

        if(l != null)
        {
            double lat = l.getLatitude();
            double lng = l.getLongitude();

            latLongString = "Latitude:" + lat + "\nLongitude:" +lng;

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try{
                List<Address> adresses = geocoder.getFromLocation(lat, lng, 1);
                StringBuilder stringBuilder = new StringBuilder();
                if(adresses.size() > 0)
                {
                    Address adresse = adresses.get(0);

                    for(int i=0; i<adresse.getMaxAddressLineIndex(); ++i)
                        stringBuilder.append(adresse.getAddressLine(i)).append("\n");

                    stringBuilder.append(adresse.getCountryName());

                }
                adresseString = stringBuilder.toString();
            }catch(IOException e){}
        }
        t.setText("Position :\n" + latLongString + "\nAdresse : " + adresseString+"\n");

    }


}
