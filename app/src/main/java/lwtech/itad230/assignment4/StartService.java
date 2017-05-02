package lwtech.itad230.assignment4;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;

public class StartService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    //static protected ArrayList<String> locations = new ArrayList<String>();
    private double lastLon, lastLat;
    private final double threshold = 5;

    // leave alone for now
    public StartService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if( mGoogleApiClient == null ) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mGoogleApiClient.connect();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onConnected(Bundle bundle) {
        int permissionCheck;
        permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if( permissionCheck == PackageManager.PERMISSION_DENIED) {
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if( mLastLocation == null ) {
            return;
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(4000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if( mLocationRequest != null){
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, (LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.distanceTo(mLastLocation) > threshold) {
            mLastLocation = location;
            String dat = DateFormat.getDateTimeInstance().format(new Date());
            Intent storage = new Intent(this, BackupIntentService.class);
            storage.setAction(BackupIntentService.ACTION_STORE);
            storage.putExtra(BackupIntentService.EXTRA_DATE, dat);
            storage.putExtra(BackupIntentService.EXTRA_LOC_LAT, location.getLatitude());
            storage.putExtra(BackupIntentService.EXTRA_LOC_LON, location.getLongitude());

        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onDestroy() {
        mGoogleApiClient.disconnect();
    }
}
