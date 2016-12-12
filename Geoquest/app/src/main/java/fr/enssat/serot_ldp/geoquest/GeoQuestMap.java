package fr.enssat.serot_ldp.geoquest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

/**
 * Created by yserot on 12/12/16.
 */

public class GeoQuestMap extends AppCompatActivity {
    private MapView map;
    private IMapController mapController;
    private ItemizedOverlay<OverlayItem> mMyLocationOverlay;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(false);
        map.setMultiTouchControls(false);

        mapController = map.getController();
        mapController.setZoom(18);
        GeoPoint startPoint = new GeoPoint(48.7333, -3.4667);
        mapController.setCenter(startPoint);

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            LocationListener mlocListener = new GeoQuestMap.MyLocationListener();
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
        }

        final ArrayList<OverlayItem> items = new ArrayList<>();
        items.add(new OverlayItem("Geolocation", "My location", startPoint));
        this.mMyLocationOverlay = new ItemizedIconOverlay<>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, getApplicationContext());
        this.map.getOverlays().add(this.mMyLocationOverlay);
    }

/* Class My Location Listener */

    public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc)
        {
            loc.getLatitude();
            loc.getLongitude();
            String Text = "My current location is: " +
                    "Latitude = " + loc.getLatitude() +
                    "Longitude = " + loc.getLongitude();
            Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
            GeoPoint newPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());
            mapController.setCenter(newPoint);

            map.getOverlays().remove(mMyLocationOverlay);
            final ArrayList<OverlayItem> items = new ArrayList<>();
            items.add(new OverlayItem("Geolocation", "My location", newPoint));
            mMyLocationOverlay = new ItemizedIconOverlay<>(items,
                    new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                        @Override
                        public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                            return true;
                        }

                        @Override
                        public boolean onItemLongPress(final int index, final OverlayItem item) {
                            return false;
                        }
                    }, getApplicationContext());

            map.getOverlays().add(mMyLocationOverlay);
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

    }

}
